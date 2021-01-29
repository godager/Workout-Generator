
import utils.Password;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @org.junit.jupiter.api.Test
    void passwordValid() {
        assertTrue(Password.passwordValid("12345678"));
        assertFalse(Password.passwordValid("1234567"));
    }
}