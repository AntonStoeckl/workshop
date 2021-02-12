package domain.customer.value;

public final class EmailAddress {

    private final String value;

    private EmailAddress(String emailAddress) {
        this.value = emailAddress;
    }


    public static EmailAddress build(String emailAddress) {
        return new EmailAddress(emailAddress);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "EmailAddress{" + "value=" + value + '}';
    }

}
