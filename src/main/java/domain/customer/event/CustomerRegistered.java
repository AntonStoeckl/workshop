package domain.customer.event;

import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;

public class CustomerRegistered implements Event {

    private final ID id;
    private final Hash hash;
    private final EmailAddress emailAddress;
    private final PersonName name;

    private CustomerRegistered(ID id, Hash hash, EmailAddress emailAddress, PersonName name) {
        this.id = id;
        this.hash = hash;
        this.emailAddress = emailAddress;
        this.name = name;
    }


    public static CustomerRegistered build(ID customerID, EmailAddress emailAddress, Hash confirmationHash, PersonName name) {
        return new CustomerRegistered(customerID, confirmationHash, emailAddress, name);
    }

    public ID getId() {
        return id;
    }

    public Hash getHash() {
        return hash;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public PersonName getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CustomerRegistered{" + "id=" + id + ", hash=" + hash + ", emailAddress=" + emailAddress + ", name=" + name + '}';
    }

}
