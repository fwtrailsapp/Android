package seniordesign.ipfw.fw_trails_app;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Jaron on 2/6/2016.
 */
public class ActivityHistoryControllerUnitTests {

   private static HttpClientUtil activityHistoryController;


   // What needs to be done before the class is ran
   @BeforeClass
   public static void setUp() {
      activityHistoryController = new HttpClientUtil();
   }

   // what needs to be done after the class is ran
   @AfterClass
   public static void tearDown() {

   }


   @Test
   public void demoTest() {

      assertNotNull(activityHistoryController);
   }
}
