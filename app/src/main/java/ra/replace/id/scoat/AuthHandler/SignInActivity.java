package ra.replace.id.scoat.AuthHandler;
//Developed By A. RAHMAN - 2022

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ra.replace.id.scoat.HttpRequest.Http;
import ra.replace.id.scoat.R;
import ra.replace.id.scoat.Storage.LocalStorage;
import ra.replace.id.scoat.UserActivity.UserActivity;

public class SignInActivity extends AppCompatActivity {

    EditText signInEmail, signInPassword;
    Button signInButton;
    TextView signInOpenSignUp;
    String email, password;
    LocalStorage localStorage;
    private android.R.attr window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        localStorage = new LocalStorage(SignInActivity.this);
        signInEmail = findViewById(R.id.signInEmailID);
        signInPassword = findViewById(R.id.signInPasswordID);
        signInPassword.setTransformationMethod(new PasswordTransformationMethod());
        signInButton = findViewById(R.id.userOpenClassroomID);
        signInOpenSignUp = findViewById(R.id.userClassSignOutButtonID);

        //Sign In to App
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSignIn();
            }
        });

        //Open Sign Up
        signInOpenSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    //User Account Check
    private void checkSignIn() {
        email = signInEmail.getText().toString();
        password = signInPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            alertFail("Email and Password is required");
        } else {
            userSignIn();
        }
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Failed")
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


    //SignIn to App
    private void userSignIn() {

        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
        } catch (JSONException e){
            e.printStackTrace();
        }

        String data = params.toString();
        String url = getString(R.string.api_server)+"/login";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(SignInActivity.this, url);
                http.setMethod("post");
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String token = response.getString("token");
                                localStorage.setToken(token);
                                Intent intent = new Intent(SignInActivity.this, UserActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else if(code == 422){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else if(code == 401){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(SignInActivity.this, "Eror "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    //Close App
    @Override
    public void onBackPressed() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SignInActivity.this)
                .setTitle("Confirmation")
                .setCancelable(false)
                .setMessage("Are your sure want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(SignInActivity.this);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        builder.show();

    }

}