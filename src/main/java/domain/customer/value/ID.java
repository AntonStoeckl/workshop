package domain.customer.value;

import java.util.Objects;
import java.util.UUID;

public class ID {

    public final String value = UUID.randomUUID().toString();

    public static ID generate() {
        return new ID();
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
