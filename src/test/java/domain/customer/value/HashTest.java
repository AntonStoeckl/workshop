package domain.customer.value;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HashTest {

    @Test
    public void generateNonEmptyHash() {
        //given

        //when
        Hash result = Hash.generate();

        //then
        assertFalse(result.value.isBlank());
    }

    @Test
    public void generatesUniqueHashes() {
        assertNotEquals(Hash.generate(), Hash.generate());
    }
}
