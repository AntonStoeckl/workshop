package domain.customer.value;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IDTest {

    @Test
    public void generateNonEmptyId() {
        //given

        //when
        ID result = ID.generate();

        //then
        assertFalse(result.value.isBlank());
    }

    @Test
    public void generatesUniqueIds() {
        assertNotEquals(ID.generate(), ID.generate());
    }
}
