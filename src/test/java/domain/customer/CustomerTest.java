package domain.customer;

import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerRegistered;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerTest {

    @Test
    public void RegisterCustomer() {
        // When RegisterCustomer
        RegisterCustomer registerCustomer = RegisterCustomer.build("john@doe.com", "John", "Doe");
        CustomerRegistered customerRegistered = Customer.register(registerCustomer);

        // Then CustomerRegistered
        assertNotNull(customerRegistered);
        assertNotNull(customerRegistered.customerId);
        assertNotNull(customerRegistered.emailAddress);
        assertNotNull(customerRegistered.hash);
        assertNotNull(customerRegistered.personName);

        assertEquals(registerCustomer.emailAddress, customerRegistered.emailAddress);
        assertEquals(registerCustomer.personName, customerRegistered.personName);

        //  and the payload should be as expected
        // TODO assert that the event contains the correct values
    }
}
