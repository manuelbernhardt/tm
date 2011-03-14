package controllers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.TMController;
import controllers.tree.ChildProducer;
import controllers.tree.SimpleNode;
import controllers.tree.TreeDataHandler;
import models.project.Project;
import models.project.ProjectCategory;
import models.project.Role;
import models.tm.User;
import models.tree.JSTreeNode;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ProjectRolesTreeDataHandler implements TreeDataHandler {

    public static final String CATEGORY = "category";
    public static final String PROJECT = "project";

    public String getName() {
        return "projectRolesTree";
    }

    public List<? extends JSTreeNode> getChildren(Long parentId, String[] args) {

        if (parentId == -1) {
            Long userId = Long.parseLong(args[0]);
            User u = User.findById(userId);
            if (u == null) {
                // TODO logging
                return new ArrayList<JSTreeNode>();
            }
            if (!u.isInAccount(TMController.getUserAccount())) {
                // TODO logging
                return new ArrayList<JSTreeNode>();
            }

            final ProjectChildProducer pcp = new ProjectChildProducer(u);
            final CategoryChildProducer ccp = new CategoryChildProducer(pcp, u);

            // produce project categories and "loose" projects
            List<JSTreeNode> rootNodes = new ArrayList<JSTreeNode>();
            Map<ProjectCategory, JSTreeNode> categories = new HashMap<ProjectCategory,JSTreeNode>();
            Map<Project, JSTreeNode> projects = new HashMap<Project, JSTreeNode>();
            for (Project p : u.getProjects()) {
                ProjectCategory pc = p.projectCategory;
                if (pc != null && !categories.containsKey(pc)) {
                    categories.put(pc, new SimpleNode(pc.id, pc.name, CATEGORY, true, true, ccp));
                } else if (pc == null && !projects.containsKey(p)) {
                    projects.put(p, new SimpleNode(p.id, p.name, PROJECT, true, true, pcp));
                }
            }
            rootNodes.addAll(categories.values());
            rootNodes.addAll(projects.values());

            return rootNodes;
        } else {
            System.out.println("WOULOULOU");

        }

        return null;
    }

    public Long create(Long parentId, Long position, String name, String type, Long id) {
        return null;
    }

    public boolean rename(Long id, String name, String type) {
        return false;
    }

    public void copy(Long id, Long target, Long position) {
    }

    public void move(Long id, Long target, Long position) {
    }

    public boolean remove(Long id, Long parentId, String type) throws Exception {
        return false;
    }

    private static class CategoryChildProducer implements ChildProducer {

        final private User user;
        final private ChildProducer projectChildProducer;

        private CategoryChildProducer(ChildProducer projectChildProducer, User user) {
            this.projectChildProducer = projectChildProducer;
            this.user = user;
        }

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> ps = new ArrayList<JSTreeNode>();
            for (Project p : user.getProjects()) {
                ProjectCategory pc = p.projectCategory;
                if (pc.getId().equals(id)) {
                    ps.add(new SimpleNode(p.id, p.name, PROJECT, true, true, projectChildProducer));
                }
            }
            return ps;
        }
    }

    private static class ProjectChildProducer implements ChildProducer {

        public static final String ROLE = "role";

        private final User user;

        private ProjectChildProducer(User user) {
            this.user = user;
        }

        public List<JSTreeNode> produce(Long id) {
            List<JSTreeNode> rs = new ArrayList<JSTreeNode>();
            for (Role r : user.projectRoles) {
                if (r.project.getId().equals(id)) {
                    rs.add(new SimpleNode(r.id, r.name, ROLE, false, false, null));
                }
            }
            return rs;
        }
    }

}
