package domain.customer;

import domain.customer.command.ChangeCustomerEmailAddressCommand;
import domain.customer.command.ConfirmEmailAddressCommand;
import domain.customer.command.RegisterCustomerCommand;
import domain.customer.event.*;
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
    EmailAddress testEmail2;
    ID testId;
    Hash testHash;
    CustomerRegisteredEvent customerRegisteredEvent;
    CustomerEmailAddressConfirmedEvent emailConfirmedEvent;
    CustomerEmailAddressChangedEvent emailChangedEvent;
    Hash testHash2;


    @BeforeEach
    private void setupEvents() {
        testName = PersonName.build("John", "Doe");
        testEmail = EmailAddress.build("test@test.de");
        testEmail2 = EmailAddress.build("test2@blah.blub");
        testId = ID.generate();
        testHash = Hash.generate();
        testHash2 = Hash.generate();

        customerRegisteredEvent = CustomerRegisteredEvent.build(testId, testEmail, testHash, testName);
        emailConfirmedEvent = CustomerEmailAddressConfirmedEvent.build(testId);
        emailChangedEvent = CustomerEmailAddressChangedEvent.build(testHash2, testEmail2, testId);
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

        // then CustomerEmailAddressFailedEvent
        assertEquals(eventList.size(), 1);
        CustomerEmailAddressConfirmationFailedEvent failedEvent = (CustomerEmailAddressConfirmationFailedEvent) eventList.get(0);

        // and the payload should be expected
        assertEquals(failedEvent.customerId, testId);
    }

    @Test
    void reconfirmEmailAddressWithRightHash() {
        // given CustomerRegistered
        Customer presentCustomer = Customer.reconstitute(List.of(customerRegisteredEvent, emailConfirmedEvent));

        // when reconfirm email address
        ConfirmEmailAddressCommand confirmCommand = ConfirmEmailAddressCommand.build(testId.value, testHash.value);
        List<Event> eventList = presentCustomer.confirmEmailAddress(confirmCommand);

        // then nix
        assertEquals(eventList.size(), 0);
    }

    @Test
    void reconfirmEmailAddressWithWrongHash() {
        // given CustomerRegistered
        Customer presentCustomer = Customer.reconstitute(List.of(customerRegisteredEvent, emailConfirmedEvent));

        // when reconfirm email address
        ConfirmEmailAddressCommand confirmCommand = ConfirmEmailAddressCommand.build(testId.value, "testHash.value");
        List<Event> eventList = presentCustomer.confirmEmailAddress(confirmCommand);

        // then CustomerEmailAddressFailedEvent
        assertEquals(eventList.size(), 1);
        CustomerEmailAddressConfirmationFailedEvent failedEvent = (CustomerEmailAddressConfirmationFailedEvent) eventList.get(0);

        // and the payload should be expected
        assertEquals(failedEvent.customerId, testId);
    }

    @Test
    void changeCustomerEmailAddress() {
        // given CustomerRegistered
        Customer presentCustomer = Customer.reconstitute(List.of(customerRegisteredEvent));
        ChangeCustomerEmailAddressCommand cmd = ChangeCustomerEmailAddressCommand.build("neu@test.blah", testId.value);

        // when
        List<Event> eventList = presentCustomer.changeEmailAddress(cmd);

        // then
        assertEquals(1, eventList.size());
        CustomerEmailAddressChangedEvent event = (CustomerEmailAddressChangedEvent) eventList.get(0);

        assertEquals(event.customerId, testId);
        assertEquals(event.emailAddress, cmd.emailAddress);
        assertEquals(event.confirmationHash, cmd.confirmationHash);

    }

    @Test
    void changeCustomerEmailAddressToRegisteredValue() {
        // given CustomerRegistered
        Customer presentCustomer = Customer.reconstitute(List.of(customerRegisteredEvent));
        ChangeCustomerEmailAddressCommand cmd = ChangeCustomerEmailAddressCommand.build(testEmail.value, testId.value);

        // when
        List<Event> eventList = presentCustomer.changeEmailAddress(cmd);

        // then
        assertEquals(0, eventList.size());
    }

    @Test
    void changeCustomerEmailAddressToPreviouslyChangedValue() {
        // given CustomerRegistered
        Customer presentCustomer = Customer.reconstitute(List.of(customerRegisteredEvent, emailChangedEvent));
        ChangeCustomerEmailAddressCommand cmd = ChangeCustomerEmailAddressCommand.build(testEmail2.value, testId.value);

        // when
        List<Event> eventList = presentCustomer.changeEmailAddress(cmd);

        // then
        assertEquals(0, eventList.size());
    }

    @Test
    void confirmChangedCustomerEmailAddressWhichWasPreviouslyConfirmed() {
        // given CustomerRegistered
        Customer presentCustomer = Customer.reconstitute(List.of(customerRegisteredEvent, emailConfirmedEvent, emailChangedEvent));
        ConfirmEmailAddressCommand confirmCommand = ConfirmEmailAddressCommand.build(testId.value, testHash2.value);

        // when
        List<Event> eventList = presentCustomer.confirmEmailAddress(confirmCommand);

        // then
        assertEquals(1, eventList.size());
        CustomerEmailAddressConfirmedEvent confirmedEvent = (CustomerEmailAddressConfirmedEvent) eventList.get(0);

        // and the payload should be expected
        assertEquals(confirmedEvent.customerId, testId);
    }
}
