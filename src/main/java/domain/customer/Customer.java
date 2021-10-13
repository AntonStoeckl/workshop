package domain.customer;

import domain.customer.command.ConfirmEmailAddressCommand;
import domain.customer.command.RegisterCustomerCommand;
import domain.customer.event.CustomerEmailAddressConfirmedEvent;
import domain.customer.event.CustomerEmailAddressFailedEvent;
import domain.customer.event.CustomerRegisteredEvent;
import domain.customer.event.Event;
import domain.customer.value.Hash;
import domain.customer.value.ID;

import java.util.List;

public class Customer {

    private ID customerId;
    private Hash confirmationHash;

    public Customer(ID customerId, Hash confirmationHash) {

        this.customerId = customerId;
        this.confirmationHash = confirmationHash;
    }

    public static CustomerRegisteredEvent register(RegisterCustomerCommand command) {
        // check customerId new
        return CustomerRegisteredEvent.build(command.customerId, command.emailAddress, command.confirmationHash, command.personName);
    }

    public static Customer reconstitute(List<Event> events) {
        Customer customer = null;

        for (Event event : events) {
            if (event instanceof CustomerRegisteredEvent) {
                CustomerRegisteredEvent registeredEvent = (CustomerRegisteredEvent) event;
                customer = new Customer(registeredEvent.customerId, registeredEvent.confirmationHash);
            }

        }

        return customer;
    }

    public List<Event> confirmEmailAddress(ConfirmEmailAddressCommand command) {

        // business logic
        if (this.confirmationHash.equals(command.confirmationHash)) {
            return List.of(CustomerEmailAddressConfirmedEvent.build(this.customerId));

        } else {
            return List.of(CustomerEmailAddressFailedEvent.build(this.customerId));

        }

    }
}
