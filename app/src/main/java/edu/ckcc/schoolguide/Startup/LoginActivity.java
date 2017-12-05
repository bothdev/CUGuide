package edu.ckcc.schoolguide.Startup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginFragment;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import edu.ckcc.schoolguide.Activity.HomeActivity;
import edu.ckcc.schoolguide.R;

public class LoginActivity extends Activity {

    private final String APP_USERNAME = "schoolguide";
    private final String APP_PASSWORD = "12345678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
      //  FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_login);

        // Test with Git only
        loginWithWebService("schoolguide", "12345678");


    }


    public void onLoginButtonClick(View view){

        EditText etxtUsername = (EditText) findViewById(R.id.etxt_username);
        EditText etxtPassword = (EditText) findViewById(R.id.etxt_password);

        String inputUsername = etxtUsername.getText().toString();
        String inputPassword = etxtPassword.getText().toString();

        if (inputUsername.equals(APP_USERNAME) && inputPassword.equals(APP_PASSWORD)){
            // Start main activity
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Notify user for logining fail
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Login Fail");
            alertDialog.setMessage("Incorrect username or password");
            alertDialog.setPositiveButton("OK", null);
            alertDialog.show();
        }

    }

    private void loginWithWebService(String username, String password){
        // Do any thing...
    }


}