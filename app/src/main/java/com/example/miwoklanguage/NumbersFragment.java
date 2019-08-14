package com.example.miwoklanguage;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersFragment extends Fragment {

    WordTranslationAdapter wordsTranslationAdapter;
    ListView mWordsListView;
    AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange)
            {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    break;

                case AudioManager.AUDIOFOCUS_LOSS:
                    mediaPlayer.stop();
                    releaseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    mediaPlayer.start();

            }
        }
    };

    private void releaseMediaPlayer()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public NumbersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_words, container, false);

        final ArrayList<WordTranslation> wordsArrayList = new ArrayList<>();
        wordsArrayList.add(new WordTranslation(R.drawable.number_one,"lutti", "one", R.raw.number_one));
        wordsArrayList.add(new WordTranslation(R.drawable.number_two,"otiiko", "two", R.raw.number_two));
        wordsArrayList.add(new WordTranslation(R.drawable.number_three,"tolookosu", "three", R.raw.number_three));
        wordsArrayList.add(new WordTranslation(R.drawable.number_four,"oyyisa", "four", R.raw.number_four));
        wordsArrayList.add(new WordTranslation(R.drawable.number_five,"massokka", "five", R.raw.number_five));
        wordsArrayList.add(new WordTranslation(R.drawable.number_six,"temmokka", "six", R.raw.number_six));
        wordsArrayList.add(new WordTranslation(R.drawable.number_seven,"kenekaku", "seven", R.raw.number_seven));
        wordsArrayList.add(new WordTranslation(R.drawable.number_eight,"kawinta", "eight", R.raw.number_eight));
        wordsArrayList.add(new WordTranslation(R.drawable.number_nine,"wo’e", "nine", R.raw.number_nine));
        wordsArrayList.add(new WordTranslation(R.drawable.number_ten,"na’aacha", "ten", R.raw.number_ten));

        wordsTranslationAdapter = new WordTranslationAdapter(getActivity(), wordsArrayList, R.color.category_numbers);

        mWordsListView = rootView.findViewById(R.id.wordsListView);

        mWordsListView.setAdapter(wordsTranslationAdapter);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        mWordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                releaseMediaPlayer();
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                                                            AudioManager.STREAM_MUSIC,
                                                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mediaPlayer = MediaPlayer.create(getActivity(), wordsArrayList.get(position).getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }

            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}
