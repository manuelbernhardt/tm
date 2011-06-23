package controllers.admin;

import controllers.TMController;

/**
 * @author Nikola Milivojevic
 */
public class Admin extends TMController {
    public static void index(){
        if(TMController.isUserSuperAdmin()){
            Users.index();
        }
        else if(TMController.isProjectSuperAdmin()){
            Projects.index();
        }
        else{
            AccountSettings.index();
        }
    }
}
