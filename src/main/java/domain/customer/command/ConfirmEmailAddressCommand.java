package domain.customer.command;

import domain.customer.value.Hash;
import domain.customer.value.ID;

public class ConfirmEmailAddressCommand {

    public final ID customerId;
    public final Hash confirmationHash;

    private ConfirmEmailAddressCommand(ID customerId, Hash confirmationHash) {
        this.customerId = customerId;
        this.confirmationHash = confirmationHash;
    }

    public static ConfirmEmailAddressCommand build(String customerId, String confirmationHash) {
        return new ConfirmEmailAddressCommand(ID.from(customerId), Hash.from(confirmationHash));
    }
}
