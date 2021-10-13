package domain.customer.event;

import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;

public class CustomerEmailAddressChangedEvent implements Event {

    public final Hash confirmationHash;
    public final EmailAddress emailAddress;
    public final ID customerId;


    private CustomerEmailAddressChangedEvent(Hash confirmationHash, EmailAddress emailAddress, ID customerId) {
        this.confirmationHash = confirmationHash;
        this.emailAddress = emailAddress;
        this.customerId = customerId;
    }
    public static CustomerEmailAddressChangedEvent build(Hash confirmationHash, EmailAddress emailAddress, ID customerId) {
        return new CustomerEmailAddressChangedEvent(confirmationHash, emailAddress, customerId);
    }
}
