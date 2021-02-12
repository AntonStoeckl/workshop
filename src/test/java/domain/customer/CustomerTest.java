package domain.customer;

import domain.customer.command.ChangeCustomerEmailAddress;
import domain.customer.command.ChangeCustomerName;
import domain.customer.command.ConfirmCustomerEmailAddress;
import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerEmailAddressChanged;
import domain.customer.event.CustomerEmailAddressConfirmationFailed;
import domain.customer.event.CustomerEmailAddressConfirmed;
import domain.customer.event.CustomerNameChanged;
import domain.customer.event.CustomerRegistered;
import domain.customer.event.Event;
import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class CustomerTest {

    final String validmail = "john@doe.com";
    final String first = "John";
    final String last = "Doe";
    ID id;
    EmailAddress email;
    Hash hash;
    PersonName name;

    @BeforeEach
    public void init() {
        id = ID.generate();
        email = EmailAddress.build(validmail);
        hash = Hash.generate();
        name = PersonName.build(first, last);
    }

    @Test
    public void registerCustomer() {
        // When RegisterCustomer
        RegisterCustomer registerCustomer = RegisterCustomer.build(validmail, first, last);
        CustomerRegistered customerRegistered = Customer.register(registerCustomer);

        // Then CustomerRegistered
        assertNotNull(customerRegistered);

        //  and the payload should be as expected
        assertEquals(registerCustomer.getId(), customerRegistered.getId());
        assertEquals(registerCustomer.getHash(), customerRegistered.getHash());
        assertEquals(validmail, customerRegistered.getEmailAddress().getValue());
        assertEquals(first, customerRegistered.getName().getGivenName());
        assertEquals(last, customerRegistered.getName().getFamilyName());
    }

    @Test
    public void confirmation_hash_is_correct() {
        CustomerRegistered theEvent = CustomerRegistered.build(id, email, hash, name);

        Customer customer = Customer.rebuild(Arrays.asList(theEvent));

        ConfirmCustomerEmailAddress command = ConfirmCustomerEmailAddress.build(id.getValue(), hash.getValue());
        Optional<Event> optionalEvent = customer.confirmEmailAddress(command);

        Event event = optionalEvent.get();

        assertTrue(event instanceof CustomerEmailAddressConfirmed);
        assertEquals(command.getCustomerID(), ((CustomerEmailAddressConfirmed) event).getId());
    }

    @Test
    public void confirmation_hash_is_incorrect() {
        CustomerRegistered theEvent = CustomerRegistered.build(id, email, hash, name);

        Customer customer = Customer.rebuild(Arrays.asList(theEvent));

        ConfirmCustomerEmailAddress command = ConfirmCustomerEmailAddress.build(id.getValue(), UUID.randomUUID());
        Optional<Event> optionalEvent = customer.confirmEmailAddress(command);

        Event event = optionalEvent.get();

        assertTrue(event instanceof CustomerEmailAddressConfirmationFailed);
        assertEquals(command.getCustomerID(), ((CustomerEmailAddressConfirmationFailed) event).getId());
    }

    @Test
    public void confirmation_hash_already_checked() {
        CustomerRegistered registeredEvent = CustomerRegistered.build(id, email, hash, name);
        CustomerEmailAddressConfirmed firstConfirmEvent = new CustomerEmailAddressConfirmed(id);

        Customer customer = Customer.rebuild(Arrays.asList(registeredEvent, firstConfirmEvent));

        ConfirmCustomerEmailAddress command = ConfirmCustomerEmailAddress.build(id.getValue(), hash.getValue());
        Optional<Event> optionalEvent = customer.confirmEmailAddress(command);

        assertFalse(optionalEvent.isPresent());
    }

    @Test
    public void reconfirm_with_wrong_hash() {
        CustomerRegistered registeredEvent = CustomerRegistered.build(id, email, hash, name);
        CustomerEmailAddressConfirmed firstConfirmEvent = new CustomerEmailAddressConfirmed(id);

        Customer customer = Customer.rebuild(Arrays.asList(registeredEvent, firstConfirmEvent));

        ConfirmCustomerEmailAddress command = ConfirmCustomerEmailAddress.build(id.getValue(), UUID.randomUUID());
        Optional<Event> optionalEvent = customer.confirmEmailAddress(command);

        Event event = optionalEvent.get();

        assertTrue(event instanceof CustomerEmailAddressConfirmationFailed);
        assertEquals(command.getCustomerID(), ((CustomerEmailAddressConfirmationFailed) event).getId());
    }

    @Test
    public void email_address_changed() {
        final String newMail = "max.mustermann@xyz.com";
        CustomerRegistered theEvent = CustomerRegistered.build(id, email, hash, name);

        Customer customer = Customer.rebuild(Arrays.asList(theEvent));

        ChangeCustomerEmailAddress command = ChangeCustomerEmailAddress.build(id, hash, EmailAddress.build(newMail));
        Optional<Event> optionalEvent = customer.changeEmailAddress(command);

        Event event = optionalEvent.get();

        assertTrue(event instanceof CustomerEmailAddressChanged);
        CustomerEmailAddressChanged addressChangedEvent = (CustomerEmailAddressChanged) event;
        assertEquals(command.getId(), addressChangedEvent.getId());
        assertEquals(command.getEmailAddress(), addressChangedEvent.getEmailAddress());
        assertEquals(command.getConfirmationHash(), addressChangedEvent.getConfirmationHash());
    }

    @Test
    public void change_email_address_to_registered_value() {
        CustomerRegistered theEvent = CustomerRegistered.build(id, email, hash, name);

        Customer customer = Customer.rebuild(Arrays.asList(theEvent));

        ChangeCustomerEmailAddress command = ChangeCustomerEmailAddress.build(id, hash, email);
        Optional<Event> optionalEvent = customer.changeEmailAddress(command);

        assertFalse(optionalEvent.isPresent());
    }

    @Test
    public void change_email_address_to_current_value() {
        final String newMail = "max.mustermann@xyz.com";
        EmailAddress currentMail = EmailAddress.build(newMail);
        Hash currentHash = Hash.generate();
        CustomerRegistered registered = CustomerRegistered.build(id, email, hash, name);
        CustomerEmailAddressChanged current = CustomerEmailAddressChanged.build(id, currentHash, currentMail);

        Customer customer = Customer.rebuild(Arrays.asList(registered, current));

        ChangeCustomerEmailAddress command = ChangeCustomerEmailAddress.build(id, currentHash, currentMail);
        Optional<Event> optionalEvent = customer.changeEmailAddress(command);

        assertFalse(optionalEvent.isPresent());
    }

    @Test
    public void reconfirm_email_after_change() {
        final String newMail = "max.mustermann@xyz.com";
        EmailAddress currentMail = EmailAddress.build(newMail);
        Hash currentHash = Hash.generate();
        CustomerRegistered customerRegistered = CustomerRegistered.build(id, email, hash, name);
        CustomerEmailAddressConfirmed registeredMailConfirmed = new CustomerEmailAddressConfirmed(id);
        CustomerEmailAddressChanged actualEmailChanged = CustomerEmailAddressChanged.build(id, currentHash, currentMail);

        Customer customer = Customer.rebuild(Arrays.asList(customerRegistered, registeredMailConfirmed, actualEmailChanged));

        ConfirmCustomerEmailAddress confirmCommand = ConfirmCustomerEmailAddress.build(id.getValue(), currentHash.getValue());
        Optional<Event> optionalEvent = customer.confirmEmailAddress(confirmCommand);

        Event confirmEvent = optionalEvent.get();

        assertTrue(confirmEvent instanceof CustomerEmailAddressConfirmed);
        assertEquals(confirmCommand.getCustomerID(), ((CustomerEmailAddressConfirmed) confirmEvent).getId());
    }

    @Test
    public void customers_name_changed() {
        final String first = "Max";
        final String last = "Mustermann";
        PersonName changedName = PersonName.build(first, last);

        CustomerRegistered theEvent = CustomerRegistered.build(id, email, hash, name);

        Customer customer = Customer.rebuild(Arrays.asList(theEvent));

        ChangeCustomerName changeCommand = ChangeCustomerName.build(id.getValue(), first, last);
        Optional<Event> optionalEvent = customer.changeName(changeCommand);

        Event changeEvent = optionalEvent.get();

        assertTrue(changeEvent instanceof CustomerNameChanged);
        CustomerNameChanged customerEvent = (CustomerNameChanged) changeEvent;
        assertEquals(id, customerEvent.getId());
        assertEquals(changedName, customerEvent.getName());
    }
}
