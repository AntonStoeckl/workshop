package domain.customer.event;

import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;

public class CustomerRegistered implements Event {

    public static CustomerRegistered build(ID customerID, EmailAddress emailAddress, Hash confirmationHash, PersonName name) {
        // TODO
        return null;
    }
}
