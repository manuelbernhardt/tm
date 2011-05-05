package util;

import java.util.List;

import controllers.deadbolt.Deadbolt;

/**
 * Simple role check, doing the same thing as Deadbolt's require tag
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Require {

    public static boolean roles(List<List<String>> roles) throws Throwable {
        boolean allowAccess = false;
        for(int i = 0; !allowAccess && i < roles.size(); i++) {
            allowAccess = Deadbolt.hasRoles(roles.get(i));
        }
        return allowAccess;
    }
}