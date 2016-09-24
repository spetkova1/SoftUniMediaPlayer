package homeworks.second.softuni.bg.softunisecondhomework.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import homeworks.second.softuni.bg.softunisecondhomework.R;
import homeworks.second.softuni.bg.softunisecondhomework.interfaces.IMediaPlayer;
import homeworks.second.softuni.bg.softunisecondhomework.interfaces.ISpotifyService;

/**
 * Created by spetkova on 9/22/16.
 */

public class SpotifyService extends Service implements IMediaPlayer {

    private static final String TAG = SpotifyService.class.getSimpleName();
    MediaPlayer mediaPlayer;
    IBinder binder = new SpotifyServiceBinder();
    ISpotifyService callback;

    public void setServiceCallback(ISpotifyService callback) {
        this.callback = callback;

        if (callback != null) {
            callback.onServiceInvocation();
        }
    }

    @Override
    public void play(int songId) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(getApplicationContext(), songId);
            mediaPlayer.setLooping(false);
            initMediaPlayerPreferences();
            mediaPlayer.start();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void fw(int songId) {

    }

    @Override
    public void ffw(int songId) {

    }

    @Override
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public class SpotifyServiceBinder extends Binder {
        public SpotifyService getService() {
            return SpotifyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void initMediaPlayerPreferences() {
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service started!", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);


    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service stopped!", Toast.LENGTH_SHORT).show();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        super.onDestroy();
    }


}
