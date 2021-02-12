package domain.customer.event;

import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;

public class CustomerRegistered implements Event {

    public final ID customerId;
    public final EmailAddress emailAddress;
    public final Hash hash;
    public final PersonName personName;

    public CustomerRegistered(ID customerId, EmailAddress emailAddress, Hash hash, PersonName personName) {
        this.customerId = customerId;
        this.emailAddress = emailAddress;
        this.hash = hash;
        this.personName = personName;
    }

    public static CustomerRegistered build(ID customerID, EmailAddress emailAddress, Hash confirmationHash, PersonName name) {
        return new CustomerRegistered(customerID, emailAddress, confirmationHash, name);
    }
}
