package controllers.tree;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.tree.GenericTreeNode;
import models.tree.Node;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public abstract class AbstractTree {

    protected static Map<String, NodeType> nodeTypes = new HashMap<String, NodeType>();
    protected static Map<Class, NodeType> nodeTypesByClass = new HashMap<Class, NodeType>();

    /**
     * Registers a new {@link NodeType}. This method should be used when implementing {@link #getNodes()}
     * @param nodeClass the Class of the tree node (must implement GenericTreeNode or extend a convenience class such as {@link models.tree.jpa.AbstractNode})
     * @param isContainer whether this node can be a container of other nodes (i.e. a non-leaf node)
     * @return a registered {@link NodeType}
     */
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

    protected static NodeType getNodeType(Class type) {
        return nodeTypesByClass.get(type);
    }

    protected enum StorageType {JPA}

    protected StorageType getStorageType() {
        return StorageType.JPA;
    }

    protected void init() {
        if (getStorageType() == StorageType.JPA) {
            storage = new JPATreeStorage();
        } else {
            throw new RuntimeException("Unknown storage type " + getStorageType());
        }

        // initialize node types
        getRootType();
        getNodes();
    }

    private TreeStorage storage = null;

    /**
     * The qualifier for this tree
     * @return a qualifier for the tree, unique for all the application.
     */
    public abstract String getName();

    /**
     * Retuns all the possible node types for this tree.<br>
     * Register a type with {@link #type(Class, boolean)}
     * @return an Array of {@link NodeType}
     */
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
            object = storage.create(object);

            node.setNode(object);

            // compute only when we have an ID
            node.setPath(storage.computePath(storage.getTreeNode(parentId), node.getId(), node.getName()));
            node = storage.update(node);

            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(Long id) throws Exception {
        // TODO make configurable
        storage.remove(id, true);
    }

    public void rename(Long id, String name) {
        storage.rename(id, name);
    }

    public void copy(Long id, Long target, Long position) {
        storage.copy(id, target, true, getNodes());
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
        if(parent == null) {
            n.setLevel(0);
            n.setThreadRoot(n);
        } else {
            n.setLevel(parent.getLevel() + 1);
            n.setThreadRoot(parent.getThreadRoot());
        }
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
