package uit.huyphambuinhat.lab04;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    public void HideSystemStatusBar(int AndroidVersion) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if (AndroidVersion < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    public void HideSystemNavigationBar() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int AndroidVersion = Build.VERSION.SDK_INT;
        HideSystemStatusBar(AndroidVersion);
        HideSystemNavigationBar();
        setContentView(R.layout.login_layout);

        TextView click_on_greeting = (TextView) findViewById(R.id.register_layout_navigator);
        click_on_greeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button login_btt = (Button) findViewById(R.id.login_btt);
        login_btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText passwordField = (EditText) findViewById(R.id.password);
                String password = passwordField.getText().toString();
                password = encryptPass.sha256(password);

                checkLogin();
            }
        });
    }

    private CollectionReference usersRef;

    protected void checkLogin(){
        EditText usernameField = (EditText) findViewById(R.id.username);
        String username = usernameField.getText().toString();
        EditText passwordField = (EditText) findViewById(R.id.password);
        String encryptPassword = passwordField.getText().toString();
        encryptPassword = encryptPass.sha256(encryptPassword);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usersRef = db.collection("users");
        Query query = usersRef.whereEqualTo("username", username);
        String finalEncryptPassword = encryptPassword;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Check if the password matches
                        if (document.getString("password").equals(finalEncryptPassword)) {
                            // Password is correct
                            Toast.makeText(getApplicationContext(), "Log In Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,
                                    HomePageActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Password is incorrect
                            Toast.makeText(LoginActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Log.d(TAG, "Error getting data for account: ", task.getException());
                }
            }
        });
    }

    public void showPopupWindowWrongUsernamePass(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_wrong_username_password_message, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 200);

        // Auto hide Popup Window after secs
        new Handler().postDelayed(new Runnable(){
            public void run() {
                popupWindow.dismiss();
            }
        }, 2 *1000);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
