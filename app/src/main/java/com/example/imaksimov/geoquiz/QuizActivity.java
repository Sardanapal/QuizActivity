package com.example.imaksimov.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button prevButton;
    private TextView questionTextView;

    private Question[] questionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int currentIndex = 0;
    private int correctAnswers = 0;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceSave");
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        Log.d(TAG, "onCreate called");

        trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTheAnswer(true);
            }
        });

        falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                checkTheAnswer(false);
            }
        });

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        prevButton = findViewById(R.id.prev_button);
        if(prevButton != null) {
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentIndex--;
                    if (currentIndex < 0) {
                        currentIndex = questionBank.length - 1;
                    }
                    updateQuestion();
                }
            });
        }

        questionTextView = findViewById(R.id.question_text_view);
        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });
        updateQuestion();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    private void updateQuestion() {
        int question = questionBank[currentIndex].getTextResourceId();
        questionTextView.setText(question);
        boolean isEnabled = !questionBank[currentIndex].isAnswered();
        trueButton.setEnabled(isEnabled);
        falseButton.setEnabled(isEnabled);
    }

    private void checkTheAnswer(boolean truePressed){
        questionBank[currentIndex].setAnswered(true);
        boolean isAnswerTrue = questionBank[currentIndex].isAnswerTrue();
        int msgId = 0;
        if (truePressed == isAnswerTrue){
            msgId = R.string.correct_toast;
            correctAnswers++;
        } else {
            msgId = R.string.incorrect_toast;
        }
        Toast.makeText(QuizActivity.this, msgId, Toast.LENGTH_SHORT).show();

        isAllAnswered();
    }

    private void isAllAnswered(){
        boolean allAnswered = true;
        for(Question question : questionBank){
            if(!question.isAnswered()){
                allAnswered = false;
            }
        }
        if(allAnswered){
            long percentage = Math.round((double)correctAnswers / questionBank.length * 100);
            Log.i(TAG, "Perc = " + percentage);
            String msg = getResources().getString(R.string.result_perc);
            Log.i(TAG, msg);
            msg = msg.replace(":perc", String.valueOf(percentage));
            Toast toast = Toast.makeText(QuizActivity.this, msg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
