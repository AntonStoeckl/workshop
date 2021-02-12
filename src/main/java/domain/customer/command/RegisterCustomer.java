package domain.customer.command;

import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;
import domain.customer.value.PersonName;

public class RegisterCustomer {

    private final ID id;
    private final Hash hash;
    private final EmailAddress emailAddress;
    private final PersonName name;

    private RegisterCustomer(EmailAddress emailAddress, PersonName name) {
        this.id = ID.generate();
        this.hash = Hash.generate();
        this.emailAddress = emailAddress;
        this.name = name;
    }

    public static RegisterCustomer build(String emailAddress, String givenName, String familyName) {
        return new RegisterCustomer(EmailAddress.build(emailAddress), PersonName.build(givenName, familyName));
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
        return "RegisterCustomer{" + "id=" + id + ", hash=" + hash + ", emailAddress=" + emailAddress + ", name=" + name + '}';
    }

}
