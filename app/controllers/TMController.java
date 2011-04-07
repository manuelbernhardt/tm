package controllers;

import controllers.deadbolt.Deadbolt;
import models.account.Account;
import models.account.AccountEntity;
import models.account.Auth;
import models.tm.Project;
import models.tm.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Util;
import play.mvc.With;

/**
 * Parent controller for the TM application.
 * Sets common view template data such as username, visible menu elements, ...
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@With(Deadbolt.class)
public class TMController extends Controller {

    @Before
    public static void setConnectedUser() {
        if (Security.isConnected()) {
            // FIXME search by account, too!
            Auth a = Auth.find("byEmail", Security.connected()).first();
            renderArgs.put("firstName", a.firstName);
            renderArgs.put("lastName", a.lastName);

            if(!request.controller.startsWith("admin")) {
                renderArgs.put("activeProject", getActiveProject());
            }

        }
    }

    public static User getConnectedUser() {
        if (Security.isConnected()) {
            // FIXME search by account, too!
            User user = User.find("from User u where u.authentication.email = ?", Security.connected()).first();
            return user;
        } else {
            // TODO test this!
            flash.put("url", "GET".equals(request.method) ? request.url : "/");
            try {
                Secure.login();
            } catch (Throwable throwable) {
                // TODO
                throwable.printStackTrace();
            }

        }
        return null;
    }

    public static Account getUserAccount() {
        return getConnectedUser().authentication.account;
    }

    /**
     * Gets the active project for the connected user, <code>null</code> if none is set
     *
     * @return the active {@see Project}
     */
    public static Project getActiveProject() {
        
        if(request.controller.startsWith("admin")) {
            throw new RuntimeException("Active project can't be fetched in the admin area");
        }

        // TODO freaking cache this or we have an extra query each time we create a project-related entity!
        if (session.get("activeProject") != null) {
            Long id = Long.valueOf(session.get("activeProject"));
            if (id != null) {
                return Project.findById(id);
            }
        }
        return null;
    }

    @Util
    public static void checkInAccount(AccountEntity accountEntity) {
        if (!accountEntity.isInAccount(getUserAccount())) {
            unauthorized();
        }
    }

}
