/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.customer.command;

import domain.customer.value.Hash;
import domain.customer.value.ID;
import java.util.UUID;

/**
 *
 * @author Konrad Renner
 */
public class ConfirmCustomerEmailAddress {
    private final ID customerID;
    private final Hash confirmationHash;

    private ConfirmCustomerEmailAddress(ID customerID, Hash confirmationHash) {
        this.customerID = customerID;
        this.confirmationHash = confirmationHash;
    }

    public static ConfirmCustomerEmailAddress build(UUID id, UUID hash) {
        return new ConfirmCustomerEmailAddress(new ID(id), new Hash(hash));
    }

    public ID getCustomerID() {
        return customerID;
    }

    public Hash getConfirmationHash() {
        return confirmationHash;
    }

    @Override
    public String toString() {
        return "ConfirmCustomerEmailAddress{" + "customerID=" + customerID + ", confirmationHash=" + confirmationHash + '}';
    }

}
