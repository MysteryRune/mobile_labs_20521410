package uit.huyphambuinhat.lab04;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.AsyncListUtil;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
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
        setContentView(R.layout.register_layout);

        TextView click_on_greeting = (TextView) findViewById(R.id.login_layout_navigator);
        click_on_greeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button signUp_btt = (Button) findViewById(R.id.signUp_btt);
        signUp_btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fullNameField = (EditText) findViewById(R.id.full_name);
                EditText phoneNumberField = (EditText) findViewById(R.id.phone_number);
                EditText usernameField = (EditText) findViewById(R.id.username);
                EditText passwordField = (EditText) findViewById(R.id.password);

                String fullName = fullNameField.getText().toString();
                String phoneNumber = phoneNumberField.getText().toString();
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                password = encryptPass.sha256(password);
                if (
                        (fullName != null && !fullName.equals("")) &&
                        (phoneNumber != null && !phoneNumber.equals("")) &&
                        (username != null && !username.equals("")) &&
                        (password != null && !password.equals("")) &&
                        (username.length() >= 6 && password.length() >= 6)
                ){
                    showPopupWindowRegisterSuccessfully(view);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    // Create a new user with a first and last name
                    Map<String, Object> user = new HashMap<>();
                    user.put("fullname", fullName);
                    user.put("phoneNumber", phoneNumber);
                    user.put("username", username);
                    user.put("password", password);


                    // Add a new document with a generated ID
                    db.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });


                    Intent intent = new Intent(RegisterActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    showPopupWindowRegisterFailed(view);
                }
            }
        });
    }

    public void showPopupWindowRegisterSuccessfully(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_register_successfully_message, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 800);

        // Auto hide Popup Window after secs
        new Handler().postDelayed(new Runnable() {
            public void run() {
                popupWindow.dismiss();
            }
        }, 2 * 1000);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void showPopupWindowRegisterFailed(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_register_failed_message, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 800);

        // Auto hide Popup Window after secs
        new Handler().postDelayed(new Runnable() {
            public void run() {
                popupWindow.dismiss();
            }
        }, 2 * 1000);

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
