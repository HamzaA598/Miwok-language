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

public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_words, container, false);

        final ArrayList<WordTranslation> wordsArrayList = new ArrayList<>();
        wordsArrayList.add(new WordTranslation("minto wuksus", "Where are you going?", R.raw.phrase_where_are_you_going));
        wordsArrayList.add(new WordTranslation("tinnә oyaase'nә", "What is your name?", R.raw.phrase_what_is_your_name));
        wordsArrayList.add(new WordTranslation("oyaaset...", "My name is...", R.raw.phrase_my_name_is));
        wordsArrayList.add(new WordTranslation("kuchi achit", "I’m feeling good.", R.raw.phrase_im_feeling_good));
        wordsArrayList.add(new WordTranslation("әәnәs'aa?", "Are you coming?", R.raw.phrase_are_you_coming));
        wordsArrayList.add(new WordTranslation("hәә’ әәnәm", "Yes, I’m coming.", R.raw.phrase_yes_im_coming));
        wordsArrayList.add(new WordTranslation("әәnәm", "I’m coming.", R.raw.phrase_im_coming));
        wordsArrayList.add(new WordTranslation("yoowutis", "Let’s go.", R.raw.phrase_lets_go));
        wordsArrayList.add(new WordTranslation("әnni'nem", "Come here.", R.raw.phrase_come_here));

        wordsTranslationAdapter = new WordTranslationAdapter(getActivity(), wordsArrayList, R.color.category_phrases);

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
