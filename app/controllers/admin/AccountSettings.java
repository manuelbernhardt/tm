package controllers.admin;

import controllers.TMController;
import models.account.Account;
import play.data.validation.Valid;

/**
 * @author: Gwenael Alizon <gwenael.alizon@oxiras.com>
 */
public class AccountSettings extends TMController {

    public static void index() {
        render();
    }

    public static void accountDetails(String[] fields) {
        renderFields(getConnectedUserAccount(), fields);
    }

    public static void updateDetails(@Valid Account account) {
        account.save();
        ok();
    }

}
