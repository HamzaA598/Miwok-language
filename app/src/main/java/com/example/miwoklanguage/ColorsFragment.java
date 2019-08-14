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
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsFragment extends Fragment {

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

    public ColorsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_words, container, false);

        final ArrayList<WordTranslation> wordsArrayList = new ArrayList<>();
        wordsArrayList.add(new WordTranslation(R.drawable.color_red,"weṭeṭṭi","red", R.raw.color_red));
        wordsArrayList.add(new WordTranslation(R.drawable.color_mustard_yellow,"chiwiiṭә","mustard yellow", R.raw.color_mustard_yellow));
        wordsArrayList.add(new WordTranslation(R.drawable.color_dusty_yellow,"ṭopiisә","dusty yellow", R.raw.color_dusty_yellow));
        wordsArrayList.add(new WordTranslation(R.drawable.color_green,"chokokki", "green", R.raw.color_green));
        wordsArrayList.add(new WordTranslation(R.drawable.color_brown,"ṭakaakki", "brown", R.raw.color_brown));
        wordsArrayList.add(new WordTranslation(R.drawable.color_gray,"ṭopoppi", "gray", R.raw.color_gray));
        wordsArrayList.add(new WordTranslation(R.drawable.color_black,"kululli", "black", R.raw.color_black));
        wordsArrayList.add(new WordTranslation(R.drawable.color_white,"kelelli", "white", R.raw.color_white));

        wordsTranslationAdapter = new WordTranslationAdapter(getActivity(), wordsArrayList, R.color.category_colors);

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
