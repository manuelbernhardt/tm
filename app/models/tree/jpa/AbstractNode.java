package models.tree.jpa;

import models.tree.GenericTreeNode;
import models.tree.Node;
import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

/**
 * Extend this template class if you don't want to re-implement the Node interface and are fine with a joined inheritance strategy
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractNode extends Model implements Node {

    public String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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