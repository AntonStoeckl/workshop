/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.customer.event;

import domain.customer.value.ID;

/**
 *
 * @author Konrad Renner
 */
public class CustomerEmailAddressConfirmationFailed implements Event {
    private final ID id;

    public CustomerEmailAddressConfirmationFailed(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CustomerEmailAddressConfirmationFailed{" + "id=" + id + '}';
    }

    public ID getId() {
        return id;
    }

}
