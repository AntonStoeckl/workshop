package domain.customer.command;

import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;

public class RegisterCustomer {

    public final ID customerId;
    public final EmailAddress emailAddress;
    public final Hash hash;
    public final PersonName personName;

    public RegisterCustomer(EmailAddress emailAddress, PersonName personName) {
        this.customerId = ID.generate();
        this.emailAddress = emailAddress;
        this.hash = Hash.generate();
        this.personName = personName;
    }

    public static RegisterCustomer build(String emailAddress, String givenName, String familyName) {
        return new RegisterCustomer(EmailAddress.build(emailAddress), PersonName.build(givenName, familyName));
    }
}
