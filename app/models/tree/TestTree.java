package models.tree;

import controllers.tree.AbstractTree;
import controllers.tree.NodeType;

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
