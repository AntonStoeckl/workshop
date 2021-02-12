package domain.customer.command;

import domain.customer.value.Hash;
import domain.customer.value.ID;

public class ConfirmCustomerEmailAddress {

    public final ID customerId;
    public final Hash hash;

    public ConfirmCustomerEmailAddress(ID customerId, Hash hash) {
        this.customerId = customerId;
        this.hash = hash;
    }

    public static ConfirmCustomerEmailAddress build(ID customerId, Hash hash) {
        return new ConfirmCustomerEmailAddress(customerId, hash);
    }
}
