package domain.customer.value;

import java.util.UUID;

public class Hash {

    private final UUID value;

    public Hash(UUID value) {
        this.value = value;
    }

    public static Hash generate() {
        return new Hash(UUID.randomUUID());
    }

}
