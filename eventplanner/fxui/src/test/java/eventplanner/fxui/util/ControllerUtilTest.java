package eventplanner.fxui.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public class ControllerUtilTest {

    private boolean flag = false;
    
    @Test
    public void testGetValidationFocusListener() {
        Supplier<Boolean> validation = () -> true;
        Runnable ifValid = () -> flag = true;
        Runnable ifInvalid = () -> flag = false;

        // Testing when focus on field is lost
        ControllerUtil.getValidationFocusListener(validation, ifValid, ifInvalid).changed(null, true, false);
        assertTrue(flag);

        validation = () -> false;
        ControllerUtil.getValidationFocusListener(validation, ifValid, ifInvalid).changed(null, true, false);
        assertFalse(flag);

        // Assert that flag does not change when field is focused
        validation = () -> true;
        ControllerUtil.getValidationFocusListener(validation, ifValid, ifInvalid).changed(null, false, true);
        assertFalse(flag);

        
    }
}
