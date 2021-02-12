package domain.customer;

import domain.customer.command.ConfirmCustomerEmailAddress;
import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerEmailAddressConfirmationFailed;
import domain.customer.event.CustomerEmailAddressConfirmed;
import domain.customer.event.CustomerRegistered;
import domain.customer.event.Event;
import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

public class CustomerTest {

    @Test
    public void RegisterCustomer() {
        // When RegisterCustomer
        RegisterCustomer registerCustomer = RegisterCustomer.build("john@doe.com", "John", "Doe");
        CustomerRegistered customerRegistered = Customer.register(registerCustomer);

        // Then CustomerRegistered and the payload should be as expected
        assertNotNull(customerRegistered);
        assertNotNull(customerRegistered.customerId);
        assertNotNull(customerRegistered.emailAddress);
        assertNotNull(customerRegistered.hash);
        assertNotNull(customerRegistered.personName);

        assertEquals(registerCustomer.emailAddress, customerRegistered.emailAddress);
        assertEquals(registerCustomer.personName, customerRegistered.personName);

        
    }
    
    @Test
    public void ConfirmCustomerEmail() {
      //Given CustomerRegistered
      CustomerRegistered customerRegistered = CustomerRegistered.build(ID.generate(),EmailAddress.build("john@doe.com"), Hash.generate(), PersonName.build("John", "Doe"));
      Customer state = Customer.reconstitute(List.of(customerRegistered));
      
      //When ConfirmCustomerEmailAddress with right confirmation hash
      ConfirmCustomerEmailAddress confirmCommand = ConfirmCustomerEmailAddress.build(customerRegistered.customerId, customerRegistered.hash);
      List<Event> confirmationResult = state.confirm(confirmCommand);
      
      //Then CustomerEmailAddressConfirmed and payload as expected
      assertNotNull(confirmationResult);
      assertEquals(1, confirmationResult.size());
      
      Event resultEvent = confirmationResult.get(0);
      assertTrue(resultEvent instanceof CustomerEmailAddressConfirmed);
      assertEquals(customerRegistered.customerId, ((CustomerEmailAddressConfirmed)resultEvent).customerId);
      
      assertTrue(state.isEmailAddressConfirmed);
    }
    
    @Test
    public void ConfirmCustomerEmailWithWrongHash() {
      //Given CustomerRegistered
      CustomerRegistered customerRegistered = CustomerRegistered.build(ID.generate(),EmailAddress.build("john@doe.com"), Hash.generate(), PersonName.build("John", "Doe"));
      Customer state = Customer.reconstitute(List.of(customerRegistered));
      
      //When ConfirmCustomerEmailAddress with wrong confirmation hash
      ConfirmCustomerEmailAddress confirmCommand = ConfirmCustomerEmailAddress.build(customerRegistered.customerId, Hash.generate());
      List<Event> confirmationResult = state.confirm(confirmCommand);
      
      //Then CustomerEmailAddressConfirmFailed and payload as expected
      assertNotNull(confirmationResult);
      assertEquals(1, confirmationResult.size());
      
      Event resultEvent = confirmationResult.get(0);
      assertTrue(resultEvent instanceof CustomerEmailAddressConfirmationFailed);
      assertEquals(customerRegistered.customerId, ((CustomerEmailAddressConfirmationFailed)resultEvent).customerId);
      
      assertFalse(state.isEmailAddressConfirmed);
    }
    
    @Test
    public void ConfirmCustomerEmailDuplicate() {
      //Given CustomerRegistered
      ID customerId = ID.generate();
      CustomerRegistered customerRegistered = CustomerRegistered.build(customerId,EmailAddress.build("john@doe.com"), Hash.generate(), PersonName.build("John", "Doe"));
      CustomerEmailAddressConfirmed mailConfirmed = CustomerEmailAddressConfirmed.build(customerId);
      
      Customer state = Customer.reconstitute(List.of(customerRegistered, mailConfirmed));
      
      //When ConfirmCustomerEmailAddress with right confirmation hash as duplicate
      ConfirmCustomerEmailAddress confirmCommand = ConfirmCustomerEmailAddress.build(customerId, customerRegistered.hash);
      List<Event> confirmationResult = state.confirm(confirmCommand);
      
      //Then no event
      assertNotNull(confirmationResult);
      assertEquals(0, confirmationResult.size());
      
      assertTrue(state.isEmailAddressConfirmed);
    }
    
    @Test
    public void ConfirmCustomerEmailDuplicateWithWrongHash() {
      //Given CustomerRegistered
      ID customerId = ID.generate();
      CustomerRegistered customerRegistered = CustomerRegistered.build(customerId,EmailAddress.build("john@doe.com"), Hash.generate(), PersonName.build("John", "Doe"));
      CustomerEmailAddressConfirmed mailConfirmed = CustomerEmailAddressConfirmed.build(customerId);
      
      Customer state = Customer.reconstitute(List.of(customerRegistered, mailConfirmed));
      
      //When ConfirmCustomerEmailAddress with wrong confirmation hash after successful confirmation
      ConfirmCustomerEmailAddress confirmCommand = ConfirmCustomerEmailAddress.build(customerId, Hash.generate());
      List<Event> confirmationResult = state.confirm(confirmCommand);
      
      //Then CustomerEmailAddressConfirmFailed and payload as expected
      assertNotNull(confirmationResult);
      assertEquals(1, confirmationResult.size());
      
      Event resultEvent = confirmationResult.get(0);
      assertTrue(resultEvent instanceof CustomerEmailAddressConfirmationFailed);
      assertEquals(customerRegistered.customerId, ((CustomerEmailAddressConfirmationFailed)resultEvent).customerId);
      
      assertTrue(state.isEmailAddressConfirmed);
    }
}
