package controllers.preparation;

import controllers.TMController;
import models.project.test.Instance;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Instances extends TMController {

    public static void content(Long instanceId) {
        Instance instance = getInstance(instanceId);
        render(instance);
    }

    public static void tags(Long instanceId) {
        Instance instance = getInstance(instanceId);
        render(instance);
    }

    public static void schedule(Long instanceId) {
        Instance instance = getInstance(instanceId);
        render(instance);
    }

    public static void testData(Long instanceId) {
        Instance instance = getInstance(instanceId);
        render(instance);
    }

    private static Instance getInstance(Long instanceId) {
        if (instanceId == null) {
            return null;
        }
        Instance instance = Instance.<Instance>findById(instanceId);
        if (instance == null) {
            return null;
        }
        checkInAccount(instance);
        return instance;
    }
}
