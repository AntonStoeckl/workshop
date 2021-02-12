package domain.customer;

import domain.customer.command.ConfirmCustomerEmailAddress;
import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerEmailAddressConfirmed;
import domain.customer.event.CustomerRegistered;
import domain.customer.event.Event;
import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;
import java.util.Arrays;
import java.util.Optional;
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
}
