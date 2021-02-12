package domain.customer;

import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerRegistered;
import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;

public class Customer {

    /*TODO: vielleicht für später
       final ID customerId;
    final EmailAddress emailAddress;
    final Hash hash;
    final PersonName personName;*/

    public static CustomerRegistered register(RegisterCustomer registerCustomer) {

        return CustomerRegistered.build(registerCustomer.customerId,
                                        registerCustomer.emailAddress,
                                        registerCustomer.hash,
                                        registerCustomer.personName);
    }
}
