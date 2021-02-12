package domain.customer.value;

import java.util.Objects;
import java.util.UUID;

public class ID {

    private final String value;

    private ID(String value) {
        this.value = value;
    }

    public static ID generate() {
        return new ID(UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ID id = (ID) o;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
