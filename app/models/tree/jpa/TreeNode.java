package models.tree.jpa;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

import controllers.tree.AbstractTree;
import controllers.tree.JPATreeStorage;
import controllers.tree.NodeType;
import models.tree.GenericTreeNode;
import models.tree.Node;
import play.db.jpa.Model;

/**
 * TODO optimize this table (indexes)
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class TreeNode extends Model implements GenericTreeNode {

    public String name;
    public transient NodeType type;
    public String typeName;
    public boolean opened;
    public int level;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public TreeNode threadRoot;

    // let's assume nobody creates such mad hierarchies
    @Column(length = 5000)
    public String path;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public AbstractNode abstractNode;

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
        if(this.threadRoot.getId() == this.getId()) {
            return this;
        } else {
            return find("from TreeNode n where level = ? and ? like concat(path, '%')", level - 1, path).first();
        }
    }

    public List<? extends GenericTreeNode> getChildren() {
        return JPATreeStorage.getChildren(level, path, threadRoot);
    }

    public boolean isOpen() {
        return opened;
    }

    public void setOpen(boolean opened) {
        this.opened = opened;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public TreeNode getThreadRoot() {
        return threadRoot;
    }

    public void setThreadRoot(GenericTreeNode threadRoot) {
        this.threadRoot = (TreeNode) threadRoot;
    }

    @PrePersist
    public void doSave() {
        if(type != null) {
            this.typeName = type.getName();
        }
    }

    @PostLoad
    public void doLoad() {
        this.type = AbstractTree.getNodeType(this.typeName);
    }
}
