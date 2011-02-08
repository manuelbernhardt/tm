package controllers.tree;

import models.tree.Node;

import java.lang.reflect.Constructor;
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
     * @return the Class of the root type
     */
    protected abstract NodeType getRootType();

    public Node createNode(Long parentId, NodeType parentType, Long position, String name, NodeType type) {
        try {
            Constructor c = type.getNodeClass().getDeclaredConstructor();
            Node n = (Node) c.newInstance();
            if(parentType != null) {
                Node parent = getNode(parentId, parentType.getNodeClass());
                if(parent == null) {
                    throw new RuntimeException("Could not find parent node with ID " + parentId);
                }
                n.setParent(parent);
            }
            n.setName(name);
            // TODO configurable
            n.setOpened(true);
            n.setType(type);

            storage.save(n);
            return n;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Node getNode(Long id, Class<? extends Node> nodeClass) {
        return storage.getNode(id, nodeClass);
    }

    public List<Node> getChildren(Long parentId, NodeType type) {
        return storage.getChildren(parentId, type.getNodeClass());
    }
}
