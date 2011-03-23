package controllers;

import models.project.test.Instance;
import play.mvc.Util;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Lookups {

    @Util
    public static Instance getInstance(Long instanceId) {
        if (instanceId == null) {
            return null;
        }
        Instance instance = Instance.<Instance>findById(instanceId);
        if (instance == null) {
            return null;
        }
        TMController.checkInAccount(instance);
        return instance;
    }
}
