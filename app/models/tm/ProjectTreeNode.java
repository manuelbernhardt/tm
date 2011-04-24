package models.tm;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import models.account.Account;
import models.account.AccountEntity;
import models.tree.jpa.TreeNode;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class ProjectTreeNode extends TreeNode implements AccountEntity {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Project project;

    public ProjectTreeNode(Project project) {
        this.project = project;
    }

    public boolean isInAccount(Account account) {
        // TODO add logging here
        return project.account.getId().equals(account.getId());
    }
}
