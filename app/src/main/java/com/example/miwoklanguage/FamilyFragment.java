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

public class FamilyFragment extends Fragment {

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

    public FamilyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_words, container, false);

        final ArrayList<WordTranslation> wordsArrayList = new ArrayList<>();
        wordsArrayList.add(new WordTranslation(R.drawable.family_father,"әpә",  "father", R.raw.family_father));
        wordsArrayList.add(new WordTranslation(R.drawable.family_mother,"әṭa", "mother", R.raw.family_mother));
        wordsArrayList.add(new WordTranslation(R.drawable.family_son,"angsi", "son", R.raw.family_son));
        wordsArrayList.add(new WordTranslation(R.drawable.family_daughter,"tune", "daughter", R.raw.family_daughter));
        wordsArrayList.add(new WordTranslation(R.drawable.family_older_brother,"taachi", "older brother", R.raw.family_older_brother));
        wordsArrayList.add(new WordTranslation(R.drawable.family_younger_brother,"chalitti", "younger brother", R.raw.family_younger_brother));
        wordsArrayList.add(new WordTranslation(R.drawable.family_older_sister,"teṭe", "older sister", R.raw.family_older_sister));
        wordsArrayList.add(new WordTranslation(R.drawable.family_younger_sister,"kolliti", "younger sister", R.raw.family_younger_sister));
        wordsArrayList.add(new WordTranslation(R.drawable.family_grandmother,"ama", "grandmother ", R.raw.family_grandmother));
        wordsArrayList.add(new WordTranslation(R.drawable.family_grandfather,"paapa","grandfather", R.raw.family_grandfather));

        wordsTranslationAdapter = new WordTranslationAdapter(getActivity(), wordsArrayList, R.color.category_family);

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
