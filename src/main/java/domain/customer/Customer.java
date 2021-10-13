package domain.customer;

import domain.customer.command.ChangeCustomerEmailAddressCommand;
import domain.customer.command.ConfirmEmailAddressCommand;
import domain.customer.command.RegisterCustomerCommand;
import domain.customer.event.*;
import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;

import java.util.List;

public class Customer {

    private ID customerId;
    private Hash confirmationHash;
    private boolean isEmailConfirmed;
    private EmailAddress emailAddress;

    private Customer(ID customerId, Hash confirmationHash, EmailAddress emailAddress) {
        this.customerId = customerId;
        this.confirmationHash = confirmationHash;
        this.emailAddress = emailAddress;
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
                customer = new Customer(registeredEvent.customerId, registeredEvent.confirmationHash, registeredEvent.emailAddress);
            } else if (event instanceof CustomerEmailAddressConfirmedEvent) {
                customer.isEmailConfirmed = true;
            } else if (event instanceof CustomerEmailAddressChangedEvent) {
                CustomerEmailAddressChangedEvent changedEvent = (CustomerEmailAddressChangedEvent) event;
                customer.isEmailConfirmed = false;
                customer.emailAddress = changedEvent.emailAddress;
                customer.confirmationHash = changedEvent.confirmationHash;
            }

        }

        return customer;
    }

    public List<Event> confirmEmailAddress(ConfirmEmailAddressCommand command) {

        // business logic
        if (!this.confirmationHash.equals(command.confirmationHash)) {
            return List.of(CustomerEmailAddressConfirmationFailedEvent.build(this.customerId));
        } else if(!isEmailConfirmed) {
            return List.of(CustomerEmailAddressConfirmedEvent.build(this.customerId));
        } else {
            return List.of();
        }

    }

    public List<Event> changeEmailAddress(ChangeCustomerEmailAddressCommand cmd) {
        if (emailAddress.equals(cmd.emailAddress)) {
            return List.of();
        } else {
            return List.of(CustomerEmailAddressChangedEvent.build(cmd.confirmationHash, cmd.emailAddress, cmd.customerId));
        }
    }
}
