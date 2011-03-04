package controllers.simpletree;

import java.util.List;

import models.tree.JSTreeNode;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface ChildProducer {

    List<JSTreeNode> produce(Long id);
}
