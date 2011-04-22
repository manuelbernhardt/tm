package models.account;

/**
 * Denotes an entity belonging to an account.
 * This may go away if we introduce hibernate filters.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface AccountEntity {

    public Long getId();

    public boolean isInAccount(Account account);
}
