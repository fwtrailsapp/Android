package seniordesign.ipfw.fw_trails_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class AccountStatisticsActivity extends AppCompatActivity {

   private final String accountStatTitle = "Your Statistics";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_account_statistics);
      getSupportActionBar().setTitle(accountStatTitle);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   }
}
