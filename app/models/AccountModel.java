package models;

/**
 * Implementors of this class need to define:
 * - a @ManyToOne to Account
 * - the UniqueConstraint on (naturalId, account)
 * themselves.
 *
 * See http://opensource.atlassian.com/projects/hibernate/browse/HHH-5949
 * See http://opensource.atlassian.com/projects/hibernate/browse/HHH-5950
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface AccountModel {

    public Account getAccount();

}
