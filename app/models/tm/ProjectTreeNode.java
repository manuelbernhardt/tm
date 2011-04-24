package models.tm;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import models.account.Account;
import models.account.AccountEntity;
import models.tree.jpa.TreeNode;
import org.hibernate.annotations.Filter;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
// TODO FIXME $%^&*( hibernate does not support filters in subclasses
// we can work around this by programatically adding a filter at configuration time
// but play does not yet give us access to this so we %^&I need to file a $%^&* ticket
@Filter(name="project")
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
