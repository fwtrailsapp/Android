package seniordesign.ipfw.fw_trails_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AccountCreateActivity extends AppCompatActivity {

   private final String createAcctActivityTitle = "Create Account";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_account_create);
      getSupportActionBar().setTitle(createAcctActivityTitle);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   }
}
