package controllers;

import tree.persistent.AbstractTree;
import tree.persistent.TreeStorage;

/**
 * TM specific implementation of an {@link AbstractTree}.
 * It customizes the node persistence (by telling the Tree module to use the @{link ProjectTreeNode}
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public abstract class TMTree extends AbstractTree {

    @Override
    protected TreeStorage getStorage() {
        return new TMJPATreeStorage();
    }

}
