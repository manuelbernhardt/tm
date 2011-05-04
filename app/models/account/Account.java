package models.account;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import models.general.Address;
import models.general.TemporalModel;
import play.data.binding.NoBinding;
import play.data.validation.Email;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
@Entity
public class Account extends TemporalModel {

    @NoBinding
    @Column(nullable = false)
    public String name;

    public String contactFirstName;

    public String contactLastName;

    @Email
    public String email;

    public String phone;

    @Embedded
    public Address address;

    public Double credit;

    public String vatCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Account account = (Account) o;

        if (address != null ? !address.equals(account.address) : account.address != null) return false;
        if (contactFirstName != null ? !contactFirstName.equals(account.contactFirstName) : account.contactFirstName != null)
            return false;
        if (contactLastName != null ? !contactLastName.equals(account.contactLastName) : account.contactLastName != null)
            return false;
        if (credit != null ? !credit.equals(account.credit) : account.credit != null) return false;
        if (email != null ? !email.equals(account.email) : account.email != null) return false;
        if (!name.equals(account.name)) return false;
        if (phone != null ? !phone.equals(account.phone) : account.phone != null) return false;
        if (vatCode != null ? !vatCode.equals(account.vatCode) : account.vatCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (contactFirstName != null ? contactFirstName.hashCode() : 0);
        result = 31 * result + (contactLastName != null ? contactLastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (credit != null ? credit.hashCode() : 0);
        result = 31 * result + (vatCode != null ? vatCode.hashCode() : 0);
        return result;
    }
}
