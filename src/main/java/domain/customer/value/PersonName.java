package domain.customer.value;

import java.util.Objects;

public  final class PersonName {

    private final String givenName;
    private final String familyName;

    private PersonName(String givenName, String familyName) {
        this.givenName = givenName;
        this.familyName = familyName;
    }

    public static PersonName build(String givenName, String familyName) {
        return new PersonName(givenName, familyName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonName that = (PersonName) o;
        return Objects.equals(givenName, that.givenName) && Objects.equals(familyName, that.familyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenName, familyName);
    }
}
