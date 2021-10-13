package domain.customer.event;

import domain.customer.value.Hash;
import domain.customer.value.ID;

public class CustomerEmailAddressConfirmedEvent implements Event {

    public final ID customerId;

    private CustomerEmailAddressConfirmedEvent(ID customerId) {
        this.customerId = customerId;
    }

    public static CustomerEmailAddressConfirmedEvent build(ID customerId) {
        return new CustomerEmailAddressConfirmedEvent(customerId);
    }
}
