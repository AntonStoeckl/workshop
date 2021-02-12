/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.customer.event;

import domain.customer.value.ID;
import domain.customer.value.PersonName;
import java.util.UUID;

/**
 *
 * @author Konrad Renner
 */
public class CustomerNameChanged implements Event {
    private final ID id;
    private final PersonName name;

    private CustomerNameChanged(ID id, PersonName name) {
        this.id = id;
        this.name = name;
    }

    public static CustomerNameChanged build(UUID id, String first, String last) {
        return new CustomerNameChanged(new ID(id), PersonName.build(first, last));
    }

    public ID getId() {
        return id;
    }

    public PersonName getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CustomerNameChanged{" + "id=" + id + ", name=" + name + '}';
    }

}
