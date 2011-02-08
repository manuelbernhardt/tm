package models.tree.jpa;

import models.tree.GenericTreeNode;
import models.tree.Node;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractNode extends Model implements Node {

    public String name;

    @OneToOne
    public TreeNode treeNode;

    public GenericTreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(GenericTreeNode node) {
        this.treeNode = (TreeNode) node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}