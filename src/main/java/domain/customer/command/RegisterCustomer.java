package domain.customer.command;

import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;

public class RegisterCustomer {

    public final Hash confirmationHash;
    public final EmailAddress emailAddress;
    public final ID customerId;
    public final PersonName personName;

    private RegisterCustomer(Hash confirmationHash, EmailAddress emailAddress, ID customerId, PersonName personName) {
        this.confirmationHash = confirmationHash;
        this.emailAddress = emailAddress;
        this.customerId = customerId;
        this.personName = personName;
    }

    public static RegisterCustomer build(String email, String givenName, String familyName) {
        return new RegisterCustomer(Hash.generate(), EmailAddress.build(email), ID.generate(), PersonName.build(givenName, familyName));
    }
}
