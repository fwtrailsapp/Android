package seniordesign.ipfw.fw_trails_app;



import junit.framework.*;

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.mockito.runners.MockitoJUnitRunner;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import dalvik.annotation.TestTargetClass;
/**
 * Created by Jaron on 2/6/2016.
 */
public class AccountCreateUnitTests {

   private AccountCreateFragment actCreate = new AccountCreateFragment();


   // What needs to be done before the class is ran
   @BeforeClass
   public static void setUp() {

   }

   // what needs to be done after the class is ran
   @AfterClass
   public static void tearDown() {
   }

   @Test
   public void demoTest() {

      assertNotNull(actCreate);
   }
}
