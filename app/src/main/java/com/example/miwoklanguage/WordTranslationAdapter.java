package com.example.miwoklanguage;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordTranslationAdapter extends ArrayAdapter<WordTranslation> {

    private int mColorResourceId;

    public WordTranslationAdapter(Context context, ArrayList<WordTranslation> wordTranslation, int colorResourceId)
    {
        super(context, 0, wordTranslation);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {

        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);

        final WordTranslation currentWordTranslation = getItem(position);

        ImageView wordImage = listItemView.findViewById(R.id.image);

        if(currentWordTranslation.hasImage())
        {
            wordImage.setImageResource(currentWordTranslation.getImageResourceId());
            wordImage.setVisibility(View.VISIBLE);
        }
        else
            wordImage.setVisibility(View.GONE);

        TextView miwokWord = listItemView.findViewById(R.id.miwokWord);
        miwokWord.setText(currentWordTranslation.getMiwokWord());

        TextView englishTranslation = listItemView.findViewById(R.id.englishTranslation);
        englishTranslation.setText(currentWordTranslation.getEnglishTranslation());

        LinearLayout wordsLayout = listItemView.findViewById(R.id.wordsLinearLayout);
        int backgroundColor = ContextCompat.getColor(getContext(), mColorResourceId);
        wordsLayout.setBackgroundColor(backgroundColor);

        return listItemView;
    }
}
