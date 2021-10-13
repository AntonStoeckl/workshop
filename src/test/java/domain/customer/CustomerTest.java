package domain.customer;

import domain.customer.command.ConfirmEmailAddressCommand;
import domain.customer.command.RegisterCustomerCommand;
import domain.customer.event.CustomerEmailAddressConfirmedEvent;
import domain.customer.event.CustomerEmailAddressFailedEvent;
import domain.customer.event.CustomerRegisteredEvent;
import domain.customer.event.Event;
import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    PersonName testName;
    EmailAddress testEmail;
    ID testId;
    Hash testHash;
    CustomerRegisteredEvent customerRegisteredEvent;


    @BeforeEach
    private void setupEvents() {
        testName = PersonName.build("John", "Doe");
        testEmail = EmailAddress.build("test@test.de");
        testId = ID.generate();
        testHash = Hash.generate();

        customerRegisteredEvent = CustomerRegisteredEvent.build(testId, testEmail, testHash, testName);
    }

    @Test
    public void RegisterCustomer() {
        // When RegisterCustomer
        RegisterCustomerCommand registerCustomerCommand = RegisterCustomerCommand.build("john@doe.com", "John", "Doe");
        CustomerRegisteredEvent customerRegisteredEvent = Customer.register(registerCustomerCommand);

        // Then CustomerRegistered
        assertNotNull(customerRegisteredEvent);
        assertNotNull(customerRegisteredEvent.customerId);
        assertNotNull(customerRegisteredEvent.confirmationHash);
        assertNotNull(customerRegisteredEvent.emailAddress);
        assertNotNull(customerRegisteredEvent.personName);

        //  and the payload should be as expected
        assertEquals(customerRegisteredEvent.confirmationHash, registerCustomerCommand.confirmationHash);
        assertEquals(customerRegisteredEvent.emailAddress, registerCustomerCommand.emailAddress);
        assertEquals(customerRegisteredEvent.customerId, registerCustomerCommand.customerId);
        assertEquals(customerRegisteredEvent.personName, registerCustomerCommand.personName);

        assertEquals(customerRegisteredEvent.emailAddress, EmailAddress.build("john@doe.com"));
        assertEquals(customerRegisteredEvent.personName, PersonName.build("John", "Doe"));
    }

    @Test
    void confirmEmailAddressWithRightHash() {
        // given CustomerRegistered
        Customer presentCustomer = Customer.reconstitute(List.of(customerRegisteredEvent));

        // when confirm email address
        ConfirmEmailAddressCommand confirmCommand = ConfirmEmailAddressCommand.build(testId.value, testHash.value);
        List<Event> eventList = presentCustomer.confirmEmailAddress(confirmCommand);

        // then CustomerEmailAddressConfirmed
        assertEquals(eventList.size(), 1);
        CustomerEmailAddressConfirmedEvent confirmedEvent = (CustomerEmailAddressConfirmedEvent) eventList.get(0);

        // and the payload should be expected
        assertEquals(confirmedEvent.customerId, testId);
    }

    @Test
    void confirmEmailAddressWithWrongHash() {
        // given CustomerRegistered
        Customer presentCustomer = Customer.reconstitute(List.of(customerRegisteredEvent));

        // when confirm email address
        ConfirmEmailAddressCommand confirmCommand = ConfirmEmailAddressCommand.build(testId.value, "testHash.value");
        List<Event> eventList = presentCustomer.confirmEmailAddress(confirmCommand);

        // then CustomerEmailAddressConfirmed
        assertEquals(eventList.size(), 1);
        CustomerEmailAddressFailedEvent failedEvent = (CustomerEmailAddressFailedEvent) eventList.get(0);

        // and the payload should be expected
        assertEquals(failedEvent.customerId, testId);
    }
}
