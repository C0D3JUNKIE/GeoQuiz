package mockingbird.cloud.geoquiz;

class Question {

    private int textResourceId;
    private boolean answerTrue;

    public Question(int textResourceId, boolean answerTrue) {
        this.textResourceId = textResourceId;
        this.answerTrue = answerTrue;
    }

    public int getTextResourceId() {
        return textResourceId;
    }

    public void setTextResourceId(int textResourceId) {
        this.textResourceId = textResourceId;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

}
