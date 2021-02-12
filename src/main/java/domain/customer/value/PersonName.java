package domain.customer.value;

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

}
