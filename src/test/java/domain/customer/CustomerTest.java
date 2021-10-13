package domain.customer;

import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerRegistered;
import domain.customer.value.EmailAddress;
import domain.customer.value.PersonName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void RegisterCustomer() {
        // When RegisterCustomer
        RegisterCustomer registerCustomer = RegisterCustomer.build("john@doe.com", "John", "Doe");
        CustomerRegistered customerRegistered = Customer.register(registerCustomer);

        // Then CustomerRegistered
        assertNotNull(customerRegistered);
        assertNotNull(customerRegistered.customerId);
        assertNotNull(customerRegistered.confirmationHash);
        assertNotNull(customerRegistered.emailAddress);
        assertNotNull(customerRegistered.personName);

        //  and the payload should be as expected
        assertEquals(customerRegistered.confirmationHash, registerCustomer.confirmationHash);
        assertEquals(customerRegistered.emailAddress, registerCustomer.emailAddress);
        assertEquals(customerRegistered.customerId, registerCustomer.customerId);
        assertEquals(customerRegistered.personName, registerCustomer.personName);

        assertEquals(customerRegistered.emailAddress, EmailAddress.build("john@doe.com"));
        assertEquals(customerRegistered.personName, PersonName.build("John", "Doe"));
    }
}
