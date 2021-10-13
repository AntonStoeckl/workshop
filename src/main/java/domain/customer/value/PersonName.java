package domain.customer.value;

import java.util.Objects;

public  final class PersonName {

    public final String value;

    private PersonName(String value) {
        this.value = value;
    }

    public static PersonName build(String givenName, String familyName) {
        return new PersonName(givenName + " " + familyName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonName)) return false;
        PersonName that = (PersonName) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
