package domain.customer.value;

import java.util.Objects;
import java.util.UUID;

public class ID {

    private final UUID value;

    public ID(UUID value) {
        this.value = value;
    }

    public static ID generate() {
        return new ID(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.value);
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
        final ID other = (ID) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

}
