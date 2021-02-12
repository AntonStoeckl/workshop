package domain.customer;

import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerRegistered;
import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;

public class Customer {

    private final ID id;
    private final Hash hash;
    private final EmailAddress emailAddress;
    private final PersonName name;

    private Customer(ID id, Hash hash, EmailAddress emailAddress, PersonName name) {
        this.id = id;
        this.hash = hash;
        this.emailAddress = emailAddress;
        this.name = name;
    }


    public static CustomerRegistered register(RegisterCustomer registerCustomer) {
        Customer theNewCustomer = new Customer(registerCustomer.getId(),
                registerCustomer.getHash(),
                registerCustomer.getEmailAddress(),
                registerCustomer.getName());

        return CustomerRegistered.build(theNewCustomer.id, theNewCustomer.emailAddress, theNewCustomer.hash, theNewCustomer.name);
    }

}
