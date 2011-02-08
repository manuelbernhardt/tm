package models.tree.test;

import controllers.tree.AbstractTree;
import controllers.tree.NodeType;
import models.tree.test.Folder;
import models.tree.test.Root;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class TestTree extends AbstractTree {

    @Override
    public String getName() {
        return "testTree";
    }

    @Override
    protected NodeType[] getNodes() {
        return new NodeType[]{type(Root.class, true), type(Folder.class, true)};
    }

    @Override
    protected NodeType getRootType() {
        return getNodeType(Root.class);
    }
}
