package controllers;

import controllers.tree.TreeController;

/**
 * Wrapper of the TreeController for TM (extending TMController means we inherit the routes and @Before methods
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class TMTreeController extends TMController {

    public static void create(String treeId, Long parentId, Long position, String name, String type) {
        TreeController.createDirect(treeId, parentId, position, name, type);
    }

    public static void remove(String treeId, Long id) {
        TreeController.removeDirect(treeId, id);
    }

    public static void rename(String treeId, Long id, String name, String type) {
        TreeController.renameDirect(treeId, id, name, type);
    }

    public static void move(String treeId, Long id, Long target, Long position, String name, boolean copy) {
        TreeController.moveDirect(treeId, id, target, position, name, copy);
    }

    public static void getChildren(String treeId, Long id, String... args) {
        TreeController.getChildrenDirect(treeId, id, args);
    }

}
