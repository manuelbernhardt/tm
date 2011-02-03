package models.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Node {
    private Long id;
    private String title;
    private NodeType type;
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private boolean opened = false;
    private List<Node> children = new ArrayList<Node>();

    public Long getId() {
        return id;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public List<Node> getChildren() {
        return children;
    }

    public boolean isOpened() {
        return opened;
    }

    public String getTitle() {
        return title;
    }

    public NodeType getType() {
        return type;
    }

    public boolean isContainer() {
        return type == models.tree.NodeType.FOLDER || type == models.tree.NodeType.ROOT;
    }

    public Node(Long id, String title, NodeType type) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.attributes.put("id", "node_" + id);
        this.attributes.put("rel", type.toString().toLowerCase());
    }

    public void addChild(Node child) {
        children.add(child);
    }

}
