package models.tree;

import controllers.tree.AbstractTree;
import controllers.tree.NodeType;
import play.db.jpa.Model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "nodeType",
        discriminatorType = DiscriminatorType.STRING
)
public class AbstractNode extends Model implements Node {

    public String name;
    public transient NodeType type;
    public String typeName;
    public boolean opened;
    public String path;

    @ManyToOne
    public AbstractNode parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = (AbstractNode) parent;
    }

    public List<? extends Node> getChildren() {
        return find("from AbstractNode n where n.parent.id = ?", getId()).fetch();
    }

    public void addChild(Node child) {
        child.setParent(this);
    }

    public boolean isOpen() {
        return opened;
    }

    public void setOpened(boolean opened) {
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
