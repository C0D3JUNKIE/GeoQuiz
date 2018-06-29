package mockingbird.cloud.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QUIZ_ACTIVITY:";
    private static final String KEY_INDEX = "index";
    private static final int CHEAT_REQUEST_CODE = 0;

    private int currentIndex = 0;
    private boolean isCheater;

    private TextView questionTextView;

    private Button trueButton;
    private Button falseButton;
    private Button cheatButton;

    private ImageButton nextButton;
    private ImageButton previousButton;



    final private Question[] questionBank = new Question[]{
            new Question(R.string.question_one, true),
            new Question(R.string.question_two, true),
            new Question(R.string.question_three, false),
            new Question(R.string.question_four, false),
            new Question(R.string.question_five, true),
            new Question(R.string.question_six, true),
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d( TAG, "PROCESS: onCreate(Bundle) called.");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        questionTextView = (TextView) findViewById(R.id.tv_question);
        questionTextView.setText(R.string.question_text);


        trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton = (Button) findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        nextButton = (ImageButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                isCheater = false;
                updateQuestion();
            }
        });

        previousButton = (ImageButton) findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex - 1) % questionBank.length;
                updateQuestion();
            }
        });

        cheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              boolean answerIsTrue = questionBank[currentIndex].isAnswerTrue();
              Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
              startActivityForResult( intent, CHEAT_REQUEST_CODE);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "PROCESS: onSaveInstanceState() called.");
        outState.putInt(KEY_INDEX, currentIndex);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d( TAG, "PROCESS: onPostResume() called.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d( TAG, "PROCESS: onStart() called.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d( TAG, "PROCESS: onStop() called.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d( TAG, "PROCESS: onDestroy() called.");
    }

    private void updateQuestion(){
        Log.d(TAG, "Updating question text display.", new Exception());
        int question = questionBank[currentIndex].getTextResourceId();
        questionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressesTrue){

        boolean answerTrue = questionBank[currentIndex].isAnswerTrue();
        int messageResponseId = 0;

        if(isCheater){
          messageResponseId = R.string.judgement_toast;
        }else{
          if(userPressesTrue == answerTrue){
            messageResponseId = R.string.correct_toast;
          }else{
            messageResponseId = R.string.incorrect_toast;
          }
        }

        Toast.makeText(this, messageResponseId, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode != Activity.RESULT_OK){
          return;
        }
        if(requestCode == CHEAT_REQUEST_CODE){
          if(data == null){
            return;
          }
          isCheater = CheatActivity.wasAnswerShown(data);
        }
    }
}
