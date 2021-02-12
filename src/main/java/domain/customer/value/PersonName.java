package domain.customer.value;

import java.util.Objects;

public final class PersonName {

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
    public String toString() {
        return "PersonName{" + "givenName=" + givenName + ", familyName=" + familyName + '}';
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.givenName);
        hash = 11 * hash + Objects.hashCode(this.familyName);
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
        final PersonName other = (PersonName) obj;
        if (!Objects.equals(this.givenName, other.givenName)) {
            return false;
        }
        if (!Objects.equals(this.familyName, other.familyName)) {
            return false;
        }
        return true;
    }

}
