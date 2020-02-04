package com.vaishakhiification.mycodingquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    static int score;
    static int noOfPasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        score = intent.getBooleanExtra("passed", false) ? MainActivity.score + 1 : MainActivity.score;
        noOfPasses = MainActivity.noOfPasses;
        TextView resultView = findViewById(R.id.resultView);
        TextView scoreView = findViewById(R.id.scoreView);
        String resultString = String.format(getString(R.string.resultLabel), score, noOfPasses);
        resultView.setText(score == noOfPasses ? getString(R.string.pass).toUpperCase() : getString(R.string.fail).toUpperCase());
        scoreView.setText(resultString);
    }

    public void shareResults(View view) {
        String result = String.format(getString(R.string.shareMsg), score, noOfPasses);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, result);
        String chooserTitle = getString(R.string.chooser);
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        startActivity(chosenIntent);
    }

    public void retry(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("noOfPasses", noOfPasses);
        startActivity(intent);
    }
}
