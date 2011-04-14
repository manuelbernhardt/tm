package controllers;

import java.util.Map;

import controllers.tree.TreeController;

/**
 * Wrapper of the TreeController for TM (extending TMController means we inherit the routes and @Before methods
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class TMTreeController extends TMController {

    public static void create(String treeId, Long parentId, String parentType, Long position, String name, String type, Map<String, String> args) {
        TreeController.createDirect(treeId, parentId, parentType, position, name, type, args);
    }

    public static void remove(String treeId, Long id, Long parentId, String type, Map<String, String> args) {
        TreeController.removeDirect(treeId, id, parentId, type, args);
    }

    public static void rename(String treeId, Long id, String name, String type) {
        TreeController.renameDirect(treeId, id, name, type);
    }

    public static void move(String treeId, Long id, String type, Long target, String targetType, Long position, String name, boolean copy) {
        TreeController.moveDirect(treeId, id, type, target, targetType, position, name, copy);
    }

    public static void getChildren(String treeId, Long id, String type, Map<String, String> args) {
        TreeController.getChildrenDirect(treeId, id, type, args);
    }

}
