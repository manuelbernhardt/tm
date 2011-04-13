package models.tm.test;

/**
 * Simple interface to make re-usage of code in {@link controllers.ParameterHandler} easier
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface ParameterHolder {
    Param getParam(String name);
}
