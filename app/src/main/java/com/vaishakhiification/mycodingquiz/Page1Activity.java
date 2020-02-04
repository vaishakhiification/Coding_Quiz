package com.vaishakhiification.mycodingquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class Page1Activity extends AppCompatActivity {


    private boolean bound = false;
    private boolean isTimerRunning;
    private TimerService timerService;
    private QuestionSolutions q1;
    private QuestionSolutions q2;

    private static final int NOTIFICATION_ID = 1;
    private Bundle savedInstanceState;
    Spinner answerSpinner_1;
    Spinner answerSpinner_2;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TimerService.TimerBinder timerBinder = (TimerService.TimerBinder) iBinder;
            timerService = timerBinder.getTimer();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        this.savedInstanceState = savedInstanceState;
        String q_title = getString(R.string.question);
        TextView titleView_1 = findViewById(R.id.titleView_1);
        titleView_1.setText(String.format(q_title, 1));

        TextView titleView_2 = findViewById(R.id.titleView_2);
        titleView_2.setText(String.format(q_title, 2));

        String question = getString(R.string.q1);
        String solution = getString(R.string.solution1);
        answerSpinner_1 = findViewById(R.id.answerSpinner_1);
        int noOfOptions = answerSpinner_1.getCount() - 2;
        q1 = new QuestionSolutions(question, solution, noOfOptions);
        TextView q1_TextView = findViewById(R.id.q1View);
        q1_TextView.setText(question);

        question = getString(R.string.q3);
        solution = getString(R.string.solution3);
        answerSpinner_2 = findViewById(R.id.answerSpinner_2);
        noOfOptions = answerSpinner_2.getCount() - 2;
        q2 = new QuestionSolutions(question, solution, noOfOptions);
        TextView q2_TextView = findViewById(R.id.q2View);
        q2_TextView.setText(question);

        if (ConfigActivity.imageButton) {
            ImageButton imageButton = findViewById(R.id.submitImageBtn);
            imageButton.setVisibility(View.VISIBLE);
        } else {
            Button button = findViewById(R.id.submitBtn);
            button.setVisibility(View.VISIBLE);
        }


        if (ConfigActivity.timerOn) {
            LinearLayout timerLayout = findViewById(R.id.total_timer_layout);
            timerLayout.setVisibility(LinearLayout.VISIBLE);
            timerLayout = findViewById(R.id.timer_layout);
            timerLayout.setVisibility(LinearLayout.VISIBLE);

            Intent intent = new Intent(this, TimerService.class);
            intent.putExtra("reset", true);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);

            runTimer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isTimerRunning = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (ConfigActivity.timerOn) {
            savedInstanceState.putInt("t1_seconds", timerService.getMs_1());

            savedInstanceState.putInt("t2_seconds", timerService.getMs_2());
            savedInstanceState.putBoolean("t2_running", timerService.getRunning_2());
            savedInstanceState.putBoolean("t2_wasRunning", timerService.getWasRunning_2());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isTimerRunning = false;
    }

    public void submitAnswer(View view) {
        String selected_1 = String.valueOf(answerSpinner_1.getSelectedItem());
        String selected_2 = String.valueOf(answerSpinner_2.getSelectedItem());
        q1.incrementNoOfChances();
        q2.incrementNoOfChances();
        if (q1.checkSolution(selected_1) || q2.checkSolution(selected_2)) {
            goToPage2();
        } else if (q1.checkLimit() && q2.checkLimit()) {
            goToResult(false);
        } else {
            showTryAgainAlert();
        }
    }

    private void goToPage2() {
        Intent page2Intent = new Intent(this, Page2Activity.class);
        if (bound) {
            unbindService(connection);
            bound = false;
        }
        startActivity(page2Intent);
        finish();
    }

    private void goToResult(boolean result) {
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra("passed", result);
        if (bound) {
            unbindService(connection);
            bound = false;
        }
        startActivity(resultIntent);
        finish();
    }

    private void showTryAgainAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.wrong_answer);
        alert.setMessage(R.string.please_try_again);
        alert.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert.show();
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final TextView finalTimeView = findViewById(R.id.final_time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bound) {
                    timerService.setRunning_2(isTimerRunning);

                    if (savedInstanceState != null) {
                        timerService.setMs_1(savedInstanceState.getInt("t1_seconds"));
                        timerService.setMs_2(savedInstanceState.getInt("t2_seconds"));
                        timerService.setRunning_2(savedInstanceState.getBoolean("t2_running"));
                        timerService.setWasRunning_2(savedInstanceState.getBoolean("t2_wasRunning"));
                        savedInstanceState = null;
                    }

                    String time1 = String.format(Locale.getDefault(),
                            "%d:%02d:%02d", timerService.getHours_1(), timerService.getMinutes_1(), timerService.getMs_1());
                    String time2 = String.format(Locale.getDefault(),
                            "%d:%02d:%02d", timerService.getHours_2(), timerService.getMinutes_2(), timerService.getMs_2());
                    if (timerService.getMs_1() == ConfigActivity.timerLimit || timerService.getMs_2() == ConfigActivity.timerLimit) {
                        timerService.timeOut();
                        goToResult(false);
                        return;
                    }
                    finalTimeView.setText(time1);
                    timeView.setText(time2);

                    timerService.incrementMs_1();
                    if (timerService.getRunning_2()) {
                        timerService.incrementMs_2();
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}
