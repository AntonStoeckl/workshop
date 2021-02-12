package domain.customer.event;

import domain.customer.value.ID;

public class CustomerEmailAddressConfirmationFailed implements Event {
    public final ID customerId;
    
    private CustomerEmailAddressConfirmationFailed(ID customerId) {
        this.customerId = customerId;
    }

    public static CustomerEmailAddressConfirmationFailed build(ID customerID) {
        return new CustomerEmailAddressConfirmationFailed(customerID);
    }
}
