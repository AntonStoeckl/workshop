package domain.customer.value;

import java.util.Objects;
import java.util.UUID;

public class Hash {

    private final UUID value;

    public Hash(UUID value) {
        this.value = value;
    }

    public static Hash generate() {
        return new Hash(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hash other = (Hash) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

}
