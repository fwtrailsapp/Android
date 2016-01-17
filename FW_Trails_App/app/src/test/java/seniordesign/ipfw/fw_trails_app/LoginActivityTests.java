package seniordesign.ipfw.fw_trails_app;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by Jaron on 1/17/2016.
 */
public class LoginActivityTests {

    /*
    Verify that LoginView exists.
     */
    @Test
    public void LoginViewExists() {
        LoginActivity loginActivity = new LoginActivity();

        assertTrue(loginActivity != null);
    }

}
