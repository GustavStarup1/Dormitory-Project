import com.example.mydorm.validators.PasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {
    private PasswordValidator passwordValidator;

    @BeforeEach
    public void setUp() {
        passwordValidator = new PasswordValidator();
    }

    @Test
    public void testValidPassword() {
        assertTrue(passwordValidator.isValid("Valid1Password!"));
    }

    @Test
    public void testNullPassword() {
        assertFalse(passwordValidator.isValid(null));
    }

    @Test
    public void testShortPassword() {
        assertFalse(passwordValidator.isValid("Short1!"));
    }

    @Test
    public void testNoUpperCase() {
        assertFalse(passwordValidator.isValid("valid1password!"));
    }

    @Test
    public void testNoLowerCase() {
        assertFalse(passwordValidator.isValid("VALID1PASSWORD!"));
    }

    @Test
    public void testNoDigit() {
        assertFalse(passwordValidator.isValid("ValidPassword!"));
    }

    @Test
    public void testNoSpecialCharacter() {
        assertFalse(passwordValidator.isValid("Valid1Password"));
    }
}