package domain.customer;

import domain.customer.command.ChangeCustomerEmailAddress;
import domain.customer.command.ConfirmCustomerEmailAddress;
import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerEmailAddressChanged;
import domain.customer.event.CustomerEmailAddressConfirmationFailed;
import domain.customer.event.CustomerEmailAddressConfirmed;
import domain.customer.event.CustomerRegistered;
import domain.customer.event.Event;
import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;
import java.util.List;
import java.util.Optional;

public class Customer {

    private ID id;
    private Hash hash;
    private EmailAddress emailAddress;
    private PersonName name;
    private boolean emailAddressConfirmed;

    private Customer() {
    }

    public static CustomerRegistered register(RegisterCustomer registerCustomer) {
        CustomerRegistered event = CustomerRegistered.build(registerCustomer.getId(),
                registerCustomer.getEmailAddress(),
                registerCustomer.getHash(),
                registerCustomer.getName());
        return event;
    }

    public static Customer rebuild(List<Event> events) {
        final Customer customer = new Customer();
        events.stream().forEach(event -> ApplyStrategy.applyEvent(customer, event));
        return customer;
    }

    public Optional<Event> confirmEmailAddress(ConfirmCustomerEmailAddress command) {
        Event event;
        if (!command.getConfirmationHash().equals(this.getHash())) {
            event = new CustomerEmailAddressConfirmationFailed(id);
        } else if (!this.emailAddressConfirmed) {
            event = new CustomerEmailAddressConfirmed(id);
        } else {
            event = null;
        }
        return Optional.ofNullable(event);
    }

    public Optional<Event> changeEmailAddress(ChangeCustomerEmailAddress command) {
        Event event;
        if (command.getEmailAddress().equals(this.emailAddress)) {
            event = null;
        } else {
            event = CustomerEmailAddressChanged.build(command.getId(),
                    command.getConfirmationHash(),
                    command.getEmailAddress());
        }

        return Optional.ofNullable(event);
    }

    public ID getId() {
        return id;
    }

    public Hash getHash() {
        return hash;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public PersonName getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", hash=" + hash + ", emailAddress=" + emailAddress + ", name=" + name + '}';
    }

    enum ApplyStrategy {
        CUSTOMER_REGISTERED {
            @Override
            void apply(Customer customer, Event event) {
                apply(customer, (CustomerRegistered) event);
            }

            void apply(Customer customer, CustomerRegistered event) {
                customer.id = event.getId();
                customer.hash = event.getHash();
                customer.emailAddress = event.getEmailAddress();
                customer.name = event.getName();
            }
        }, EMAIL_CONFIRMED {
            @Override
            void apply(Customer customer, Event event) {
                apply(customer, (CustomerEmailAddressConfirmed) event);
            }

            void apply(Customer customer, CustomerEmailAddressConfirmed event) {
                customer.emailAddressConfirmed = true;
            }
        }, EMAIL_CHANGED {
            @Override
            void apply(Customer customer, Event event) {
                apply(customer, (CustomerEmailAddressChanged) event);
            }

            void apply(Customer customer, CustomerEmailAddressChanged event) {
                customer.emailAddress = event.getEmailAddress();
                customer.hash = event.getConfirmationHash();
                customer.emailAddressConfirmed = false;
            }
        };

        public static void applyEvent(Customer customer, Event event) {
            if (event instanceof CustomerRegistered) {
                ApplyStrategy.CUSTOMER_REGISTERED.apply(customer, event);
            } else if (event instanceof CustomerEmailAddressConfirmed) {
                ApplyStrategy.EMAIL_CONFIRMED.apply(customer, event);
            } else if (event instanceof CustomerEmailAddressChanged) {
                ApplyStrategy.EMAIL_CHANGED.apply(customer, event);
            }
            // ignore Event
        }

        abstract void apply(Customer customer, Event event);
    }
}
