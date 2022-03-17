package ra.replace.id.scoat.AuthHandler;
//Developed By A. RAHMAN - 2022

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignUpActivity extends AppCompatActivity {

    EditText signUpFullName, signUpEmail, signUpPassword, signUpPasswordConfirm;
    Button signUpButton;
    String name, email, password, confirmation;
    int role = 1;
    TextView userSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        userSignUp = findViewById(R.id.signUpOpenSignInID);
        signUpFullName = findViewById(R.id.signUpFullNameID);
        signUpEmail = findViewById(R.id.signUpEmailID);
        signUpPassword = findViewById(R.id.signUpPasswordID);
        signUpPassword.setTransformationMethod(new PasswordTransformationMethod());
        signUpPasswordConfirm = findViewById(R.id.signUpPasswordConfirmID);
        signUpPasswordConfirm.setTransformationMethod(new PasswordTransformationMethod());
        signUpButton = findViewById(R.id.signUpButtonID);

        //Sign Up Account
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserAccount();
            }
        });

        //Open Sign Up Activity
        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    // Check User Account Before Sign Up
    private void checkUserAccount() {
        name = signUpFullName.getText().toString();
        email = signUpEmail.getText().toString();
        password = signUpPassword.getText().toString();
        confirmation = signUpPasswordConfirm.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            alertFail("Name, Email and Password is required");
        } else {
            sendRegister();
        }
    }

    private void alertSuccess(String s) {
        s = "You have successfully registered on our database";
        new AlertDialog.Builder(this)
                .setTitle("Succes")
                .setMessage(s)
                .setPositiveButton("Sign In Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                }).show();
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

    // Sign Up User Account
    private void sendRegister() {

        JSONObject params = new JSONObject();
        try {
            params.put("name",name);
            params.put("email",email);
            params.put("password",password);
            params.put("password_confirmation",confirmation);
            params.put("role",role);
        } catch (JSONException e){
            e.printStackTrace();
        }

        String data = params.toString();
        String url = getString(R.string.api_server)+"/register";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(SignUpActivity.this, url);
                http.setMethod("post");
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if (code == 201 || code == 200){
                            alertSuccess("Register Successfully");
                        } else if( code == 422){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("message");
                                alertFail(msg);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error " + code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }


}