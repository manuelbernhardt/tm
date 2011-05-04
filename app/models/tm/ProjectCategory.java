package models.tm;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.account.Account;
import models.account.AccountModel;
import play.data.validation.Required;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"naturalId", "account_id"})})
public class ProjectCategory extends AccountModel {

    @Column(nullable = false)
    @Required
    public String name;

    public ProjectCategory(Account account, String name) {
        super(account);
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects(this);
    }

    public static List<Project> projects(ProjectCategory category) {
        return Project.find("from Project p where p.projectCategory = ?", category).fetch();
    }

    public static List<ProjectCategory> findByAccount(Long id) {
        return ProjectCategory.find("from ProjectCategory pc where pc.account.id = ?", id).<ProjectCategory>fetch();
    }
}
