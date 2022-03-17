package ra.replace.id.scoat.UserActivity;
//Developed By A. RAHMAN - 2022

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ra.replace.id.scoat.AuthHandler.SignInActivity;
import ra.replace.id.scoat.HttpRequest.Http;
import ra.replace.id.scoat.R;

public class UserActivity extends AppCompatActivity {

    TextView userFullName, userSignOutButton, userCourseName;
    Button userAttendClassButton;
    FusedLocationProviderClient fusedLocationProviderClient;
    String link, userName, courseName, courseLinkURL, courseTimeStart, courseTimeEnd, courseID, studentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userFullName = findViewById(R.id.userFullNameID);
        userCourseName = findViewById(R.id.userCourseNameID);
        userAttendClassButton = findViewById(R.id.userOpenClassroomID);
        userSignOutButton = findViewById(R.id.userClassSignOutButtonID);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);




        //Get Course
        courseInfo();

        //Get User
        userAccountInfo();

        //Send "I attend class"
        userAttendClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAttendClass();
            }
        });

        //User Sign Out
        userSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignOut();
            }
        });

    }

    //Get course information
    //Get User Information
    private void courseInfo() {
        String url = getString(R.string.api_server)+"/course";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(UserActivity.this, url);
                http.setToken(true);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                JSONArray responseArray = response.getJSONArray("data");
                                if (responseArray.length()>0){
                                    for(int i=0; i < responseArray.length(); i++){
                                        JSONObject jsonObject = responseArray.getJSONObject(i);
                                        courseID = jsonObject.getString("id");
                                        courseName = jsonObject.getString("course");
                                        courseLinkURL = jsonObject.getString("course_url_link");
                                        courseTimeStart = jsonObject.getString("time_start");
                                        courseTimeEnd = jsonObject.getString("time_end");
                                    }
                                }

                                userCourseName.setText(courseName);

                                Toast.makeText(UserActivity.this, "You made it sign in, " + userName, Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(UserActivity.this, "Error "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    //Get User Information
    private void userAccountInfo() {
        String url = getString(R.string.api_server)+"/user";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(UserActivity.this, url);
                http.setToken(true);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                studentID = response.getString("id");
                                userName = response.getString("name");
                                userFullName.setText(userName);

                                Toast.makeText(UserActivity.this, "You made it sign in, " + userName, Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(UserActivity.this, "Error "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    //Send Attend Class
    private void sendAttendClass() {
        String url = getString(R.string.api_server)+"/user";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(UserActivity.this, url);
                http.setToken(true);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){

                            if (ActivityCompat.checkSelfPermission(UserActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED &&
                                    ActivityCompat.checkSelfPermission(UserActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                            != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }

                            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {
                                    Location location = task.getResult();
                                    if (location != null) {
                                        Geocoder geocoder = new Geocoder(UserActivity.this, Locale.getDefault());

                                        try {
                                            List<Address> addresses = geocoder.getFromLocation(
                                                    location.getLatitude(), location.getLongitude(), 1
                                            );

                                            JSONObject response = new JSONObject(http.getResponse());
                                            String student_id = response.getString("id");
                                            String latitude = String.valueOf(addresses.get(0).getLatitude());
                                            String longtitude = String.valueOf(addresses.get(0).getLongitude());
                                            String addressline = addresses.get(0).getAddressLine(0);

                                            JSONObject params = new JSONObject();
                                            try {
                                                params.put("student_id",student_id);
                                                params.put("latitude",latitude);
                                                params.put("longtitude",longtitude);
                                                params.put("addressline",addressline);
                                            } catch (JSONException e){
                                                e.printStackTrace();
                                            }

                                            String data = params.toString();
                                            String url = getString(R.string.api_server)+"/student_locations";
//                                            String url = getString(R.string.api_server)+"/attendance";

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Http http = new Http(UserActivity.this, url);
                                                    http.setMethod("post");
                                                    http.setToken(true);
                                                    http.setData(data);
                                                    http.send();

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Integer code = http.getStatusCode();
                                                            if (code == 201 || code == 200){
                                                                alertSuccess("Welcome " + userName + "! Thank you for coming to class");
                                                            } else if( code == 422){
                                                                try {
                                                                    JSONObject response = new JSONObject(http.getResponse());
                                                                    String msg = response.getString("message");
                                                                    alertFail(msg);
                                                                } catch (JSONException e){
                                                                    e.printStackTrace();
                                                                }
                                                            } else {
                                                                Toast.makeText(UserActivity.this, "Error " + code, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            }).start();
//
                                        } catch (IOException | JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(UserActivity.this, "Error "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }

    //User Sign Out
    private void userSignOut(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserActivity.this)
                .setTitle("Confirmation")
                .setCancelable(false)
                .setMessage("Are your sure want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = getString(R.string.api_server)+"/logout";
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Http http = new Http(UserActivity.this, url);
                                http.setMethod("post");
                                http.setToken(true);
                                http.send();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Integer code = http.getStatusCode();
                                        if (code == 200){
                                            Toast.makeText(UserActivity.this, "You sign out in the class", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(UserActivity.this, SignInActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(UserActivity.this, "Error " + code, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }).start();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        builder.show();
    }

    //User Alert
    private void alertSuccess(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Successfully sent to the lecturer")
                .setMessage(s)
                .setPositiveButton("Open Class", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Class in Progress
                        Intent intent = new Intent(getApplicationContext(), ClassInProgressActivity.class);
                        intent.putExtra("keyStudentID", studentID);
                        intent.putExtra("keyUserName", userName);
                        intent.putExtra("keyCourseID", courseID);
                        intent.putExtra("keyCourseName", courseName);
                        intent.putExtra("keyLink", courseLinkURL);
                        intent.putExtra("keyTimeStart", courseTimeStart);
                        intent.putExtra("keyTimeEnd", courseTimeEnd);
                        startActivity(intent);

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


    //Close App
    @Override
    public void onBackPressed() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserActivity.this)
                .setTitle("Confirmation")
                .setCancelable(false)
                .setMessage("Are your sure want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = getString(R.string.api_server)+"/logout";
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Http http = new Http(UserActivity.this, url);
                                http.setMethod("post");
                                http.setToken(true);
                                http.send();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Integer code = http.getStatusCode();
                                        if (code == 200){
                                            Toast.makeText(UserActivity.this, "You sign out in the class", Toast.LENGTH_LONG).show();
                                            ActivityCompat.finishAffinity(UserActivity.this);
                                        } else {
                                            Toast.makeText(UserActivity.this, "Error " + code, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }).start();
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