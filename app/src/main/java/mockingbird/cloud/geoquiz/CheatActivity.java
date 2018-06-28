package mockingbird.cloud.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity{

  private static final String EXTRA_ANSWER_IS_TRUE = "cloud.mockingbird.geoquiz.answer_is_true";
  private static final String EXTRA_ANSWER_SHOWN = "cloud.mockingbird.geoquiz.answer_shown";

  private boolean answerIsTrue;
  private TextView answerTextView;
  private Button showAnswerButton;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);

    answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

    answerTextView = (TextView) findViewById(R.id.tv_answer);

    showAnswerButton = (Button) findViewById(R.id.show_answer_button);
    showAnswerButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if(answerIsTrue){
          answerTextView.setText(R.string.true_button);
        }else{
          answerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
      }
    });

  }

  private void setAnswerShownResult(boolean isAnswerShown){
    Intent data = new Intent();
    data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
    setResult(RESULT_OK, data);
  }

  public static Intent newIntent(Context packageContext, boolean answerIsTrue){

    Intent intent = new Intent(packageContext, CheatActivity.class);
    intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    return intent;

  }






}
