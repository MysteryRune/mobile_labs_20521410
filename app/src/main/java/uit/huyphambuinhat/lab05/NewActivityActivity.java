package uit.huyphambuinhat.lab05;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class NewActivityActivity extends AppCompatActivity{

    private Button btnBackXml;
    private Animation.AnimationListener animationListener;

    private void findViewsByIds() {
        btnBackXml = (Button) findViewById(R.id.btn_back);
    }

    private void handleTranslationPageClickAnimationXml(Button btn, int animId, int animId2) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewActivity = new Intent(NewActivityActivity.this, MainActivity.class);
                startActivity(iNewActivity);

                overridePendingTransition(animId, animId2);
            }
        });
    }

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
//        HideSystemStatusBar(AndroidVersion);
        HideSystemNavigationBar();

        setContentView(R.layout.active_new_active);

        findViewsByIds();

        handleTranslationPageClickAnimationXml(btnBackXml, R.xml.anim_enter, R.xml.anim_exit);
    }

}
