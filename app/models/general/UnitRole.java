package models.general;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import controllers.Defects;
import controllers.Execution;
import controllers.Preparation;
import controllers.Repository;
import controllers.Requirements;


/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class UnitRole implements models.deadbolt.Role {

    /* Account unit roles */
    public static final String ACCOUNTADMIN = "accountAdmin";

    public static final String USERCREATE = "userCreate";
    public static final String USEREDIT = "userEdit";
    public static final String USERDELETE = "userDelete";

    public static final String PROJECTCREATE = "projectCreate";
    public static final String PROJECTEDIT = "projectEdit";
    public static final String PROJECTDELETE = "projectDelete";


    /* Project unit roles */
    public static final String PROJECTADMIN = "projectAdmin";

    public static final String REQVIEW = "reqView";
    public static final String REQCREATE = "reqCreate";
    public static final String REQEDIT = "reqEdit";
    public static final String REQDELETE = "reqDelete";

    public static final String TESTREPOVIEW = "testRepoView";
    public static final String TESTREPOCREATE = "testRepoCreate";
    public static final String TESTREPOEDIT = "testRepoEdit";
    public static final String TESTREPODELETE = "testRepoDelete";

    public static final String TESTPREPVIEW = "testPrepView";
    public static final String TESTPREPCREATE = "testPrepCreate";
    public static final String TESTPREPEDIT = "testPrepEdit";
    public static final String TESTPREPDELETE = "testPrepDelete";

    public static final String TESTEXECVIEW = "testExecView";
    public static final String TESTEXECCREATE = "testExecCreate";
    public static final String TESTEXECEDIT = "testExecEdit";
    public static final String TESTEXECDELETE = "testExecDelete";

    public static final String DEFECTVIEW = "defectView";
    public static final String DEFECTCREATE = "defectCreate";
    public static final String DEFECTEDIT = "defectEdit";
    public static final String DEFECTDELETE = "defectDelete";


    private final static ImmutableMap<String, String> viewRolesPerController = ImmutableMap.of(
            Requirements.class.getName(), UnitRole.REQVIEW,
            Repository.class.getName(), UnitRole.TESTREPOVIEW,
            Preparation.class.getName(), UnitRole.TESTPREPVIEW,
            Execution.class.getName(), UnitRole.TESTEXECVIEW,
            Defects.class.getName(), UnitRole.DEFECTVIEW
    );

    private final static ImmutableMap<String, String> createRolesPerController = ImmutableMap.of(
            Requirements.class.getName(), UnitRole.REQCREATE,
            Repository.class.getName(), UnitRole.TESTREPOCREATE,
            Preparation.class.getName(), UnitRole.TESTPREPCREATE,
            Execution.class.getName(), UnitRole.TESTEXECCREATE,
            Defects.class.getName(), UnitRole.DEFECTCREATE
    );

    private final static ImmutableMap<String, String> editRolesPerController = ImmutableMap.of(
            Requirements.class.getName(), UnitRole.REQEDIT,
            Repository.class.getName(), UnitRole.TESTREPOEDIT,
            Preparation.class.getName(), UnitRole.TESTPREPEDIT,
            Execution.class.getName(), UnitRole.TESTEXECEDIT,
            Defects.class.getName(), UnitRole.DEFECTEDIT
    );

    private final static ImmutableMap<String, String> deleteRolesPerController = ImmutableMap.of(
            Requirements.class.getName(), UnitRole.REQDELETE,
            Repository.class.getName(), UnitRole.TESTREPODELETE,
            Preparation.class.getName(), UnitRole.TESTPREPDELETE,
            Execution.class.getName(), UnitRole.TESTEXECDELETE,
            Defects.class.getName(), UnitRole.DEFECTDELETE
    );


    private String name;

    public String getRoleName() {
        return name;
    }

    public UnitRole(String name) {
        this.name = name;
    }

    public static UnitRole role(String role) {
        return new UnitRole(role);
    }

    public static List<UnitRole> roles(String... unitRoles) {
        List<UnitRole> roles = new ArrayList<UnitRole>();
        for(String r : unitRoles) {
            roles.add(role(r));
        }
        return roles;
    }

    public static UnitRole getViewRole(Class controllerClass) {
        return role(createRolesPerController.get(controllerClass.getName()));
    }

    public static UnitRole getCreateRole(Class controllerClass) {
        return role(viewRolesPerController.get(controllerClass.getName()));
    }

    public static UnitRole getEditRole(Class controllerClass) {
        return role(editRolesPerController.get(controllerClass.getName()));
    }

    public static UnitRole getDeleteRole(Class controllerClass) {
        return role(editRolesPerController.get(controllerClass.getName()));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UnitRole && ((UnitRole) o).getRoleName().equals(this.getRoleName());
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}


