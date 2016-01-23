package seniordesign.ipfw.fw_trails_app;

import android.app.Application;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import junit.framework.*;
import org.junit.Test;

import org.junit.*;


import org.junit.runner.RunWith;


import android.support.test.runner.AndroidJUnit4;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static LoginActivity loginActivity;
    public ApplicationTest() {
        super(Application.class);
    }
    @BeforeClass
    public static void createLoginView() {
        loginActivity = new LoginActivity();
    }

    @Test
    public void UsernameFieldExists() {
        assert (loginActivity.findViewById(R.id.usernameEditText) != null);
    }

    @Test
    public void PasswordFieldExists() {
        assert (loginActivity.findViewById(R.id.passwordEditText) != null);
    }

    @AfterClass
    public static void destroyLoginView() {
        loginActivity = null;
    }
}