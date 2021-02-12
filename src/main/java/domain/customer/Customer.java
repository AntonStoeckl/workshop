package domain.customer;

import java.util.List;
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

public class Customer {
    public ID customerId;
    public EmailAddress emailAddress;
    public Hash hash;
    public PersonName personName;
    public boolean isEmailAddressConfirmed;
  
    public static CustomerRegistered register(RegisterCustomer registerCustomer) {

        return CustomerRegistered.build(registerCustomer.customerId,
                                        registerCustomer.emailAddress,
                                        registerCustomer.hash,
                                        registerCustomer.personName);
    }

    public List<Event> confirm(ConfirmCustomerEmailAddress confirmCommand) {
      if (this.hash.equals(confirmCommand.hash)) {
        if (!this.isEmailAddressConfirmed) {
          Event result = CustomerEmailAddressConfirmed.build(confirmCommand.customerId);
          this.apply(result);
          return List.of(result);
        }
        else {
          return List.of();
        }
      }
      else {
        return List.of(CustomerEmailAddressConfirmationFailed.build(confirmCommand.customerId));
      }
    }

    public static Customer reconstitute(List<Event> events) {
      Customer state = new Customer();
      
      for (Event event : events) {
        state.apply(event);
      }
      
      return state;
    }
    
    private void apply(Event event) {
      if (event instanceof CustomerRegistered) {
        CustomerRegistered registeredEvent = (CustomerRegistered) event;
        this.customerId = registeredEvent.customerId;
        this.emailAddress = registeredEvent.emailAddress;
        this.hash = registeredEvent.hash;
        this.personName = registeredEvent.personName;
        this.isEmailAddressConfirmed = false;
      }
      else if (event instanceof CustomerEmailAddressConfirmed) {
        this.isEmailAddressConfirmed = true;
      }
    }
}
