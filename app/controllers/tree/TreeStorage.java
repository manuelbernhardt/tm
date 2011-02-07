package controllers.tree;

import models.tree.Node;

import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public abstract class TreeStorage {

    public abstract Node save(Node n);

    public abstract Node getNode(Long id, Class<? extends Node> nodeClass);

    public abstract List<Node> getChildren(Long parentId, Class<? extends Node> nodeClass);
}
