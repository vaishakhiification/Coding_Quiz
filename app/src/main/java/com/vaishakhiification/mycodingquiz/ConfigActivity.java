package com.vaishakhiification.mycodingquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

public class ConfigActivity extends AppCompatActivity {
    static boolean timerOn = false;
    static int timerLimit = -1;
    static boolean imageButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
    }

    public void startQuiz(View view) {
        Switch timerSwitch = findViewById(R.id.switch_page_timer);
        timerOn = timerSwitch.isChecked();
        EditText timerLimitText = findViewById(R.id.page_timer_limit);
        String limitText = String.valueOf(timerLimitText.getText());
        timerLimit = !limitText.isEmpty() ? Integer.valueOf(limitText) : timerLimit;
        ToggleButton button_toggle = findViewById(R.id.button_type_toggle);
        imageButton = button_toggle.isChecked();
        Intent p1Intent = new Intent(this, Page1Activity.class);
        startActivity(p1Intent);
    }
}
