package com.example.miwoklanguage;

public class WordTranslation {

    private int mImageResourceId = -1, mAudioResourceId;

    private String mMiwokWord, mEnglishTranslation;

    public WordTranslation(String mMiwokTranslation, String mEnglishTranslation, int audioResourceId)
    {
        this.mMiwokWord = mMiwokTranslation;
        this.mEnglishTranslation = mEnglishTranslation;
        this.mAudioResourceId = audioResourceId;
    }

    public WordTranslation(int mImageResourceId, String mMiwokTranslation, String mEnglishTranslation, int audioResourceId)
    {
        this.mImageResourceId = mImageResourceId;
        this.mAudioResourceId = audioResourceId;
        this.mMiwokWord = mMiwokTranslation;
        this.mEnglishTranslation = mEnglishTranslation;
    }

    public int getImageResourceId()
    {
        return mImageResourceId;
    }

    public String getMiwokWord()
    {
        return mMiwokWord;
    }

    public String getEnglishTranslation()
    {
        return mEnglishTranslation;
    }

    public boolean hasImage()
    {
        return mImageResourceId != -1;
    }

    public int getAudioResourceId()
    {
        return mAudioResourceId;
    }

}
