package domain.customer.event;

import domain.customer.value.Hash;
import domain.customer.value.ID;

public class CustomerEmailAddressFailedEvent implements Event {

    public final ID customerId;

    private CustomerEmailAddressFailedEvent(ID customerId) {
        this.customerId = customerId;
    }

    public static CustomerEmailAddressFailedEvent build(ID customerId) {
        return new CustomerEmailAddressFailedEvent(customerId);
    }
}
