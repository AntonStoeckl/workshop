package domain.customer;

import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerRegistered;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void RegisterCustomer() {
        final String mail = "john@doe.com";
        final String first = "John";
        final String last = "Doe";
        // When RegisterCustomer
        RegisterCustomer registerCustomer = RegisterCustomer.build(mail, first, last);
        CustomerRegistered customerRegistered = Customer.register(registerCustomer);

        // Then CustomerRegistered
        assertNotNull(customerRegistered);

        //  and the payload should be as expected
        assertNotNull(customerRegistered.getId());
        assertNotNull(customerRegistered.getHash());
        assertEquals(mail, customerRegistered.getEmailAddress().getValue());
        assertEquals(first, customerRegistered.getName().getGivenName());
        assertEquals(last, customerRegistered.getName().getFamilyName());
    }
}
