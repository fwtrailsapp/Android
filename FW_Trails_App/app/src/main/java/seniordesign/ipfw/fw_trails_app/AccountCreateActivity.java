package seniordesign.ipfw.fw_trails_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AccountCreateActivity extends AppCompatActivity {

   private String createNewAccountTitle = "Create New Account";
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_account_create);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setTitle(createNewAccountTitle);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   }

}
