package domain.customer.value;

import java.util.Objects;

public final class EmailAddress {

    public final String value;

    private EmailAddress(String value) {
        this.value = value;
    }

    public static EmailAddress build(String emailAddress) {
        return new EmailAddress(emailAddress);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailAddress)) return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
