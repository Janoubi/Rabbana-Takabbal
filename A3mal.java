package hackathon_g043.hajjcontrol;

public class A3mal {
private String mLocation;
private String mTitle;
private boolean mStatus;
private String mId;
public A3mal(String title,boolean status,String location,String Id){
    mId=Id;
    mLocation=location;
    mTitle=title;
    mStatus=status;
}

    public String getmTitle() {
        return mTitle;
    }

    public boolean getmStatus(){
    return mStatus;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmId() {
        return mId;
    }
}
