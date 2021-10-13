package domain.customer.value;

import java.util.Objects;
import java.util.UUID;

public class ID {

    public final String value;

    public ID(String id) {
        this.value = id;
    }

    public static ID generate() {
        return new ID(UUID.randomUUID().toString());
    }

    public static ID from(String id) {
        // validate it!
        return new ID(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ID)) return false;
        ID id = (ID) o;
        return value.equals(id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
