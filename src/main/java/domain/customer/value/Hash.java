package domain.customer.value;

import java.util.Objects;
import java.util.UUID;

public class Hash {

    public final String value = UUID.randomUUID().toString();

    public static Hash generate() {
        return new Hash();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hash)) return false;
        Hash hash = (Hash) o;
        return value.equals(hash.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
