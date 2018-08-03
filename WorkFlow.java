package hackathon_g043.hajjcontrol;

public class WorkFlow {

    private String mTitle;
    private String mCompletionPercent;

    public WorkFlow(String title,String completionPercent){

        mTitle=title;
        mCompletionPercent=completionPercent;
    }


    public String getmTitle() {
        return mTitle;
    }

    public String getmCompletionPercent() {
        return mCompletionPercent;
    }
}
