package com.example.administrator.test;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class IsMediaPlayer {
    Context context;
    MediaPlayer mp=new MediaPlayer();
    String url;
    public IsMediaPlayer(Context context, String url) {
        this.context=context;
        this.url=url;
    }
    public void isplay(){
        try {
            mp.setDataSource(url);
            mp.prepare();
            mp.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });
    }
}
