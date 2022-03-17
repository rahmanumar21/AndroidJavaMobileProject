package ra.replace.id.scoat.UserActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ra.replace.id.scoat.AuthHandler.SignInActivity;
import ra.replace.id.scoat.HttpRequest.Http;
import ra.replace.id.scoat.R;

public class ClassInProgressActivity extends AppCompatActivity {

    Button userOpenClassroom, btnSend;
    TextView userTimeCurrent, userFullName, userClassSignOutButton, userCourseName, userTimeStart, userTimeEnds;
    FusedLocationProviderClient fusedLocationProviderClient;
    String currentTime, userName, courseName, courseLinkURL, courseTimeStart, courseTimeEnd, courseID, courseID2, locationID, studentID;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_in_progress);

        userOpenClassroom = findViewById(R.id.userOpenClassroomID);
        userTimeCurrent = findViewById(R.id.userCurrentTimeID);
        userFullName = findViewById(R.id.userFullNameID);
        userClassSignOutButton = findViewById(R.id.userClassSignOutButtonID);
        userCourseName = findViewById(R.id.userCourseNameID);
        userTimeStart = findViewById(R.id.userTimeStartID);
        userTimeEnds = findViewById(R.id.userTimeEndsID);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Get data in the class
        userName = getIntent().getStringExtra("keyUserName");
        courseID = getIntent().getStringExtra("keyCourseID");
        courseName = getIntent().getStringExtra("keyCourseName");
        courseLinkURL = getIntent().getStringExtra("keyLink");
        courseTimeStart = getIntent().getStringExtra("keyTimeStart");
        courseTimeEnd = getIntent().getStringExtra("keyTimeEnd");

        String timeStart = courseTimeStart;
        String timeEnd = courseTimeEnd;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE dd'th' MMM, hh:mm");
        Date dateTimeStart = null;
        Date dateTimeEnd = null;
        try {
            dateTimeStart = inputFormat.parse(timeStart);
            dateTimeEnd = inputFormat.parse(timeEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTimeStart = outputFormat.format(dateTimeStart);
        String formattedTimeEnd = outputFormat.format(dateTimeEnd);

        //User Infromation
        userFullName.setText(userName);
        userCourseName.setText(courseName);
        userTimeStart.setText(formattedTimeStart);
        userTimeEnds.setText(formattedTimeEnd);

        //User Current Time
        currentTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        userTimeCurrent.setText(currentTime);

        //Class URL
        Uri uri = Uri.parse(courseLinkURL);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));

        Toast.makeText(ClassInProgressActivity.this, "You made it into the classroom, " + userName, Toast.LENGTH_LONG).show();

        userOpenClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(courseLinkURL);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });


        //User Sign Out
        userClassSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignOut();
            }
        });

        String url = getString(R.string.api_server)+"/get_student_locations";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(ClassInProgressActivity.this, url);
                http.setToken(true);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = http.getStatusCode();
                        if(code == 200){

                            if (ActivityCompat.checkSelfPermission(ClassInProgressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED &&
                                    ActivityCompat.checkSelfPermission(ClassInProgressActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                            != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }

                            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {
                                    Location location = task.getResult();
                                    if (location != null) {

                                        try {

                                            JSONObject response = new JSONObject(http.getResponse());
                                            JSONArray responseArray = response.getJSONArray("data");
                                            if (responseArray.length()>0){
                                                for(int i=0; i < responseArray.length(); i++){
                                                    JSONObject jsonObject = responseArray.getJSONObject(i);
                                                    locationID = jsonObject.getString("id");
                                                    studentID = jsonObject.getString("student_id");
                                                }
                                            }

                                            userCourseName.setText(courseName);

                                            JSONObject params = new JSONObject();
                                            try {
                                                params.put("student_id",studentID);
                                                params.put("location_id",locationID);
                                                params.put("course_id",courseID);
                                            } catch (JSONException e){
                                                e.printStackTrace();
                                            }

                                            String data = params.toString();
                                            String url = getString(R.string.api_server)+"/attendance";

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Http http = new Http(ClassInProgressActivity.this, url);
                                                    http.setMethod("post");
                                                    http.setToken(true);
                                                    http.setData(data);
                                                    http.send();

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                        }
                                                    });
                                                }
                                            }).start();
//
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(ClassInProgressActivity.this, "Error "+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }


    //User Sign Out
    private void userSignOut(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ClassInProgressActivity.this)
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
                                Http http = new Http(ClassInProgressActivity.this, url);
                                http.setMethod("post");
                                http.setToken(true);
                                http.send();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Integer code = http.getStatusCode();
                                        if (code == 200){
                                            Toast.makeText(ClassInProgressActivity.this, "You sign out in the class", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(ClassInProgressActivity.this, SignInActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(ClassInProgressActivity.this, "Error " + code, Toast.LENGTH_LONG).show();
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

    //Close App
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ClassInProgressActivity.this)
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
                                Http http = new Http(ClassInProgressActivity.this, url);
                                http.setMethod("post");
                                http.setToken(true);
                                http.send();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Integer code = http.getStatusCode();
                                        if (code == 200){
                                            ActivityCompat.finishAffinity(ClassInProgressActivity.this);
                                            Toast.makeText(ClassInProgressActivity.this, "You sign out in the class", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(ClassInProgressActivity.this, "Error " + code, Toast.LENGTH_LONG).show();
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