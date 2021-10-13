package domain.customer;

import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerRegistered;

public class Customer {

    public static CustomerRegistered register(RegisterCustomer command) {
        return CustomerRegistered.build(command.customerId, command.emailAddress, command.confirmationHash, command.personName);
    }
}
