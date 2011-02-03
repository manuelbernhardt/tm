package models.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class TreeStore {

    Map<Long, Node> nodes = new HashMap<Long, Node>();

    public TreeStore() {
        // initialize the parent of them all
        createNode("_ROOT_", NodeType.ROOT);
    }

    public void addNode(Node n, Node parent) {
        nodes.put(n.getId(), n);
        for (Node c : n.getChildren()) {
            nodes.put(c.getId(), c);
        }
        if (parent != null) {
            nodes.get(parent.getId()).addChild(n);
        }
    }

    public Node createNode(String title, NodeType type) {
        Node n = new Node(getNextId(), title, type);
        nodes.put(n.getId(), n);
        return n;
    }

    public Node createNode(Node parent, String title, NodeType type) {
        Node c = createNode(title, type);
        parent.addChild(c);
        return c;
    }

    public Node getNode(Long id) {
        return nodes.get(id);
    }

    public List<Node> getChildren(Long id) {
        return nodes.get(id).getChildren();
    }

    protected Long getNextId() {
        return new Long(nodes.size()+1);
    }

}
