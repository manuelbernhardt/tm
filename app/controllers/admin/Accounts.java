package controllers.admin;

import controllers.CRUD;
import models.account.Account;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@CRUD.For(Account.class)
public class Accounts extends CRUD {
}
