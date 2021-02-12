package domain.customer.event;

import domain.customer.value.ID;

public class CustomerEmailAddressConfirmed implements Event {
    public final ID customerId;
    
    private CustomerEmailAddressConfirmed(ID customerId) {
        this.customerId = customerId;
    }

    public static CustomerEmailAddressConfirmed build(ID customerID) {
        return new CustomerEmailAddressConfirmed(customerID);
    }
}
