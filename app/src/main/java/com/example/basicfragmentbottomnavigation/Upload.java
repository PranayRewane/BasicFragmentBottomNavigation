package com.example.basicfragmentbottomnavigation;
/*
public class Upload {
    private String mName;
    private String mImageUrl;
    private String mAddr,mSub,mAge;




    public Upload() {
        //empty constructor needed
    }

    public Upload(String address,String imageUrl,String subject,String name) {
        if (name.trim().equals("")) {
            name = "No Name";


        }

        mName = name;
        mImageUrl = imageUrl;
        //mAge=age;
        mSub=subject;
        mAddr=address;
    }

    public String getSub() {
        return mSub;
    }

    public void setSub(String subject){
        mSub = subject;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }






    public String getAddr() {
        return mAddr;
    }

    public void setmAddr(String mAddr) {
        this.mAddr = mAddr;
    }



    public String getAge() {
        return mAge;
    }
}
*/

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mDate;
    private String mLoc;
    private String mSubject;
    private String mAgegrp;
    public Upload() {
        //empty constructor needed
    }

    public Upload(String subject,String name, String imageUrl, String date,String loc,String age) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mDate = date;
        mName = name;
        mImageUrl = imageUrl;
        mLoc = loc;
        mSubject = subject;
        mAgegrp=age;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getDate() {
        return mDate;
    }
    public void setDate(String date) {
        mDate = date;
    }


    public String getLoc() {
        return mLoc;
    }
    public void setLoc(String loc) {
        mLoc = loc;
    }

    public String getSubject() {
        return mSubject;
    }
    public void setSubject(String subject) {
        mSubject = subject;
    }

}

