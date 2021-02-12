/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.customer.command;

import domain.customer.value.ID;
import domain.customer.value.PersonName;
import java.util.UUID;

/**
 *
 * @author Konrad Renner
 */
public class ChangeCustomerName {
    private final ID customerID;
    private final PersonName name;

    private ChangeCustomerName(ID customerID, PersonName name) {
        this.customerID = customerID;
        this.name = name;
    }

    public static ChangeCustomerName build(UUID id, String first, String last) {
        return new ChangeCustomerName(new ID(id), PersonName.build(first, last));
    }

    @Override
    public String toString() {
        return "ChangeCustomerName{" + "customerID=" + customerID + ", name=" + name + '}';
    }

    public ID getCustomerID() {
        return customerID;
    }

    public PersonName getName() {
        return name;
    }

}
