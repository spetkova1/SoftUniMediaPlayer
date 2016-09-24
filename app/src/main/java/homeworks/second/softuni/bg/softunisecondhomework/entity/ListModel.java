package homeworks.second.softuni.bg.softunisecondhomework.entity;

/**
 * Created by spetkova on 9/10/16.
 */

public class ListModel {
    private String mTitle;
    private String mSinger;
    private int mFileName;


    public String getmTitle() {
        return mTitle;
    }

    public String getmSinger() {
        return mSinger;
    }


    public int getmFileName() {
        return mFileName;
    }


    public ListModel(int mFileName, String mTitle, String mSinger) {

        this.mTitle = mTitle;
        this.mSinger = mSinger;
        this.mFileName = mFileName;
    }
}
