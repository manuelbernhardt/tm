package controllers.tree;

import models.tree.GenericTreeNode;
import models.tree.Node;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public abstract class AbstractTree {

    protected static Map<String, NodeType> nodeTypes = new HashMap<String, NodeType>();
    protected static Map<Class, NodeType> nodeTypesByClass = new HashMap<Class, NodeType>();

    public static NodeType type(Class<? extends Node> nodeClass, boolean isContainer) {
        String name = nodeClass.getSimpleName().toLowerCase();
        NodeType nodeType = new NodeType(name, isContainer, nodeClass);
        nodeTypes.put(name, nodeType);
        nodeTypesByClass.put(nodeClass, nodeType);
        return nodeType;
    }

    public static NodeType getNodeType(String name) {
        return nodeTypes.get(name);
    }

    public static NodeType getNodeType(Class type) {
        return nodeTypesByClass.get(type);
    }

    private TreeStorage storage = null;

    public abstract String getName();

    public void init() {
        if (getStorageType() == StorageType.JPA) {
            storage = new JPATreeStorage();
        } else {
            throw new RuntimeException("Unknown storage type " + getStorageType());
        }

        // initialize node types
        getRootType();
        getNodes();
    }

    protected enum StorageType {JPA}

    protected StorageType getStorageType() {
        return StorageType.JPA;
    }

    protected abstract NodeType[] getNodes();

    /**
     * The type of the parent of all nodes in the tree, i.e. which type is at the root of the tree.
     * For the moment we don't support mixed root nodes
     *
     * @return the Class of the root type
     */
    protected abstract NodeType getRootType();

    public GenericTreeNode createNode(Long parentId, Long position, String name, NodeType type) {
        try {
            GenericTreeNode node = storage.getNewGenericTreeNode();
            populateTreeNode(node, parentId, name, type);
            node = storage.create(node);

            Node object = createObjectNode(name, type);
            object.setTreeNode(node);
            object = storage.create(object);

            node.setNode(object);
            // compute only when we have an ID
            node.setPath(computePath(node.getParent(), node.getId(), node.getName()));
            node = storage.update(node);

            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** the rules for creating the path should be the same as in the TreeStorage **/
    private String computePath(GenericTreeNode parent, Long id, String name) {
        String path = "";
        if(parent != null) {
            path += parent.getPath();
            path += "___";
        }
        path += name;
        path += id;
        return path;
    }

    public void remove(Long id) throws Exception {
        // TODO make configurable
        storage.remove(id, true);
    }

    public void rename(Long id, String name) {
        storage.rename(id, name);
    }

    public void copy(Long id, Long target, Long position) {
        storage.copy(id, target, false);
    }

    public void move(Long id, Long target, Long position) {
        storage.move(id, target);
    }

    private Node createObjectNode(String name, NodeType type) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor c = type.getNodeClass().getDeclaredConstructor();
        Node object = (Node) c.newInstance();
        object.setName(name);
        return object;
    }

    private void populateTreeNode(GenericTreeNode n, Long parentId, String name, NodeType type) {
        GenericTreeNode parent = getNode(parentId);
        if (parent == null && parentId != -1) {
            throw new RuntimeException("Could not find parent node with ID " + parentId);
        }
        n.setParent(parent);
        n.setName(name);
        n.setType(type);

        // TODO configurable
        n.setOpen(false);
    }

    public GenericTreeNode getNode(Long id) {
        return storage.getTreeNode(id);
    }

    public List<GenericTreeNode> getChildren(Long parentId, NodeType type) {
        return storage.getChildren(parentId);
    }
}
