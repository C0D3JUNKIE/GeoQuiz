package mockingbird.cloud.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity{


  private static final String TAG = "CHEAT_ACTIVITY: ";
  private static final String EXTRA_ANSWER_IS_TRUE = "cloud.mockingbird.geoquiz.answer_is_true";
  private static final String EXTRA_ANSWER_SHOWN = "cloud.mockingbird.geoquiz.answer_shown";

  private boolean answerIsTrue;
  private TextView answerTextView;
  private Button showAnswerButton;

  public static Intent newIntent(Context packageContext, boolean answerIsTrue){

    Intent intent = new Intent(packageContext, CheatActivity.class);
    intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    return intent;

  }

  public static boolean wasAnswerShown(Intent result){

    return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);

  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);
    Log.d(TAG,"onCreate was called.");

    answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

    answerTextView = (TextView) findViewById(R.id.tv_answer);

    showAnswerButton = (Button) findViewById(R.id.show_answer_button);
    showAnswerButton.setOnClickListener(new OnClickListener() {
      @RequiresApi(api = VERSION_CODES.LOLLIPOP)
      @Override
      public void onClick(View view) {
        if(answerIsTrue){
          answerTextView.setText(R.string.true_button);
        }else{
          answerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);

        if(Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP){

          int cx = showAnswerButton.getWidth()/2;
          int cy = showAnswerButton.getHeight()/2;
          float radius = showAnswerButton.getWidth();

          Animator animation = ViewAnimationUtils.createCircularReveal(showAnswerButton, cx, cy, radius, 0);
          animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              super.onAnimationEnd(animation);
              Log.d(TAG,"onCreate - onAnimationEnd was called.");
              showAnswerButton.setVisibility(View.INVISIBLE);
            }
          });

          animation.start();

        }else{
          showAnswerButton.setVisibility(View.INVISIBLE);
        }
      }

    });

  }

  private void setAnswerShownResult(boolean isAnswerShown){

    Intent data = new Intent();
    data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
    setResult(RESULT_OK, data);

  }


}
