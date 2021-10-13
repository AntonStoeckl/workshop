package domain.customer.event;

import domain.customer.value.ID;

public class CustomerEmailAddressConfirmationFailedEvent implements Event {

    public final ID customerId;

    private CustomerEmailAddressConfirmationFailedEvent(ID customerId) {
        this.customerId = customerId;
    }

    public static CustomerEmailAddressConfirmationFailedEvent build(ID customerId) {
        return new CustomerEmailAddressConfirmationFailedEvent(customerId);
    }
}
