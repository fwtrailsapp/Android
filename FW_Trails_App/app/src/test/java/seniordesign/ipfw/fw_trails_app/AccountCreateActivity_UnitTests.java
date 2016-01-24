package seniordesign.ipfw.fw_trails_app;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.*;

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.mockito.runners.MockitoJUnitRunner;

import dalvik.annotation.TestTargetClass;

/**
 * Created by Jaron on 1/23/2016.
 */
public class AccountCreateActivity_UnitTests {

   private static AccountCreateActivity actCreate;
   private String createNewAccountTitle = "Create New Account";

   // Tests that the AccountCreateActivity class can be instantiated.
   @BeforeClass
   public static void setUp() {
      actCreate = new AccountCreateActivity();
   }

   @AfterClass
   public static void tearDown() {
      actCreate = null;
   }

   @Test
   public void demoTest() {

      assertNotNull(actCreate);
   }

   @Test
   public void createNewAccountTitleVerification() {
      assertEquals(createNewAccountTitle,actCreate.getSupportActionBar().getTitle());
   }

}
