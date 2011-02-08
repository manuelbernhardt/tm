package models.tree.jpa;

import controllers.tree.AbstractTree;
import controllers.tree.NodeType;
import models.tree.GenericTreeNode;
import models.tree.Node;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class TreeNode extends Model implements GenericTreeNode {

    public String name;
    public transient NodeType type;
    public String typeName;
    public boolean opened;
    public String path;

    @OneToOne
    public AbstractNode abstractNode;

    @ManyToOne
    public TreeNode parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getNode() {
        return abstractNode;
    }

    public void setNode(Node n) {
        this.abstractNode = (AbstractNode) n;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public GenericTreeNode getParent() {
        return parent;
    }

    public void setParent(GenericTreeNode parent) {
        this.parent = (TreeNode) parent;
    }

    public List<? extends GenericTreeNode> getChildren() {
        return find("from TreeNode n where n.parent.id = ?", getId()).fetch();
    }

    public void addChild(GenericTreeNode child) {
        child.setParent(this);
    }

    public boolean isOpen() {
        return opened;
    }

    public void setOpen(boolean opened) {
        this.opened = opened;
    }

    @PrePersist
    public void doSave() {
        this.typeName = type.getName();
    }

    @PostLoad
    public void doLoad() {
        this.type = AbstractTree.getNodeType(this.typeName);
    }
}
