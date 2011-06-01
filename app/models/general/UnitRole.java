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

    public static final String ACCOUNTADMIN = "accountAdmin";

    public static final String USERCREATE = "userCreate";
    public static final String USEREDIT = "userEdit";
    public static final String USERDELETE = "userDelete";

    public static final String PROJECTCREATE = "projectCreate";
    public static final String PROJECTEDIT = "projectEdit";
    public static final String PROJECTDELETE = "projectDelete";

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


    private final static ImmutableMap<String, String> createRolesPerController = ImmutableMap.of(
            Requirements.class.getName(), UnitRole.REQCREATE,
            Repository.class.getName(), UnitRole.TESTREPOCREATE,
            Preparation.class.getName(), UnitRole.TESTPREPCREATE,
            Execution.class.getName(), UnitRole.TESTEXECCREATE,
            Defects.class.getName(), UnitRole.DEFECTCREATE
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

    public static UnitRole getCreateRole(Class controllerClass) {
        return role(createRolesPerController.get(controllerClass.getName()));
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


