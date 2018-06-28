package mockingbird.cloud.geoquiz;

import android.content.Intent;
import android.os.Bundle;
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

    private int curentIndex = 0;

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
            curentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
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
                curentIndex = (curentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        previousButton = (ImageButton) findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curentIndex = (curentIndex - 1) % questionBank.length;
                updateQuestion();
            }
        });

        cheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              boolean answerIsTrue = questionBank[curentIndex].isAnswerTrue();
              Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
              startActivity(intent);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "PROCESS: onSaveInstanceState() called.");
        outState.putInt(KEY_INDEX, curentIndex);
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
        int question = questionBank[curentIndex].getTextResId();
        questionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressesTrue){

        boolean answerTrue = questionBank[curentIndex].isAnswerTrue();
        int messageResponseId;

        if(userPressesTrue == answerTrue){
            messageResponseId = R.string.correct_toast;
        }else{
            messageResponseId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResponseId, Toast.LENGTH_SHORT).show();

    }

}
