package models.tm;

import java.util.List;
import javax.persistence.*;

import models.account.Account;
import models.account.AccountEntity;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;
import play.db.jpa.JPA;
import play.db.jpa.JPABase;
import play.db.jpa.Model;
import tree.JSTreeNode;
import tree.persistent.AbstractTree;
import tree.persistent.GenericTreeNode;
import tree.persistent.NodeType;

/**
 * Implementation of a {@link GenericTreeNode} for TM. This is necessary so as to store project and account ID and to apply
 * a hibernate filter on them.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
// TODO FIXME $%^&*( hibernate does not support filters in subclasses so we have to copy-paste the whole TreeNode definition here for now.
@Filters({@Filter(name = "account"), @Filter(name = "projects")})
@Table(name = "tm_ProjectTreeNode")
public class ProjectTreeNode extends Model implements GenericTreeNode, AccountEntity {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Account account;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Project project;

    public ProjectTreeNode(Project project) {
        this.project = project;
        this.account = project.account;
    }

    public boolean isInAccount(Account account) {
        // TODO add logging here
        return project.account.getId().equals(account.getId());
    }

    public String treeId;
    public String name;
    public transient NodeType nodeType;
    public String type;
    public boolean opened;
    public int level;
    public Long nodeId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    public ProjectTreeNode threadRoot;

    // let's assume nobody creates such mad hierarchies
    @Column(length = 5000)
    public String path;

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(Long id) {
        this.nodeId = id;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType type) {
        this.nodeType = type;
    }

    public GenericTreeNode getParent() {
        if (this.threadRoot.getId().equals(this.getId())) {
            return this;
        } else {
            return find("from ProjectTreeNode n where level = ? and ? like concat(path, '%')", level - 1, path).first();
        }
    }

    public List<JSTreeNode> getChildren() {
        return JPA.em().createQuery("from ProjectTreeNode n where n.treeId = '" + treeId + "' and n.level = :level and n.path like :pathLike and n.threadRoot = :threadRoot order by n.path asc").setParameter("level", level + 1).setParameter("pathLike", path + "%").setParameter("threadRoot", threadRoot).getResultList();
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

    public ProjectTreeNode getThreadRoot() {
        return threadRoot;
    }

    public void setThreadRoot(GenericTreeNode threadRoot) {
        this.threadRoot = (ProjectTreeNode) threadRoot;
    }

    public boolean isContainer() {
        return nodeType.isContainer();
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean create() {
        if(nodeType != null) {
            this.type = nodeType.getName();
        }
        return super.create();
    }

    @Override
    public JPABase save() {
        if(nodeType != null) {
            this.type = nodeType.getName();
        }
        return super.save();
    }

    @PostLoad
    public void doLoad() {
        this.nodeType = AbstractTree.getNodeType(this.type);
    }

    public int compareTo(JSTreeNode o) {
        return this.getName().compareTo(o.getName());
    }

}
