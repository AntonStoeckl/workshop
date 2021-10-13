package domain.customer.command;

import domain.customer.event.CustomerEmailAddressChangedEvent;
import domain.customer.value.EmailAddress;
import domain.customer.value.Hash;
import domain.customer.value.ID;

public class ChangeCustomerEmailAddressCommand {

    public final Hash confirmationHash;
    public final EmailAddress emailAddress;
    public final ID customerId;


    private ChangeCustomerEmailAddressCommand(EmailAddress emailAddress, ID customerId) {
        this.confirmationHash = Hash.generate();
        this.emailAddress = emailAddress;
        this.customerId = customerId;
    }

    public static ChangeCustomerEmailAddressCommand build(String emailAddress, String customerId) {
        return new ChangeCustomerEmailAddressCommand(EmailAddress.build(emailAddress), ID.from(customerId));
    }
}
