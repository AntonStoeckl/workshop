package domain.customer.value;

import java.util.UUID;

public class ID {

    private final UUID value;

    public ID(UUID value) {
        this.value = value;
    }

    public static ID generate() {
        return new ID(UUID.randomUUID());
    }

}
