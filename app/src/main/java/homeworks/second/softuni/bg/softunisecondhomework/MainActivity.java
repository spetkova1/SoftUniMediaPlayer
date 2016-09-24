package homeworks.second.softuni.bg.softunisecondhomework;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import homeworks.second.softuni.bg.softunisecondhomework.entity.ListModel;
import homeworks.second.softuni.bg.softunisecondhomework.interfaces.IMediaPlayer;
import homeworks.second.softuni.bg.softunisecondhomework.interfaces.ISpotifyService;
import homeworks.second.softuni.bg.softunisecondhomework.services.SpotifyService;


public class MainActivity extends Activity implements Observer, ISpotifyService, View.OnClickListener, RecyclerViewAdapter.OnItemClickListener, IMediaPlayer {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mBtnInsertData;
    private ArrayList<ListModel> myData;

    private ImageView mPlay, mStop, mPause, mFastforward, mReverse;
    private TextView mPlaySong;
    private SpotifyService.SpotifyServiceBinder mServiceBinder;
    private Intent mSpotifyIntent;

    RecyclerViewAdapter.OnItemClickListener mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlaySong = (TextView) findViewById(R.id.playsong);
        mPlay = (ImageView) findViewById(R.id.play);
        mPlay.setOnClickListener(this);

        mStop = (ImageView) findViewById(R.id.stop);
        mStop.setOnClickListener(this);

        mPause = (ImageView) findViewById(R.id.pause);
        mPause.setOnClickListener(this);

        mFastforward = (ImageView) findViewById(R.id.fastforward);
        mFastforward.setOnClickListener(this);

        mReverse = (ImageView) findViewById(R.id.reverse);
        mReverse.setOnClickListener(this);


        mListener = new MainActivity().mListener;

        myData = new ArrayList<>();


        int mSongFileId[] = new int[]{
                R.raw.song_1,
                R.raw.song_2,
                R.raw.song_3,
                R.raw.song_4,
                R.raw.song_5,
                R.raw.song_6,
                R.raw.song_7,
                R.raw.song_8,
                R.raw.song_9,
                R.raw.song_10,
        };


        for (int i = 0; i < mSongFileId.length; i++) {


            ListModel listModel = new ListModel(mSongFileId[i], "Song " + (i + 1), "Singer " + (i + 1));
            myData.add(i, listModel);
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        //Linear Layout Grid Manager
//        mLayoutManager = new GridLayoutManager(this, 3);
//        mRecyclerView.setLayoutManager(mLayoutManager);

        //Linear Layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Drawable drawable = getDrawable(R.drawable.divider);

        //Set Custom Decoration
        MyRecycleViewCustomDecoration mItemCustomDecoration = new MyRecycleViewCustomDecoration(drawable);
        mRecyclerView.addItemDecoration(mItemCustomDecoration);

        //Set adapter
        mAdapter = new RecyclerViewAdapter(myData, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i, String title, int position) {
                mPlaySong.setText(title);
                play(i);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mSpotifyIntent = new Intent(this, SpotifyService.class);
        bindService(mSpotifyIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        startService(mSpotifyIntent);

        //Select first song by default
        mPlaySong.setText(myData.get(0).getmTitle());
    }


    public void OnServiceStarted(View view) {
        mSpotifyIntent = new Intent(this, SpotifyService.class);
        bindService(mSpotifyIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        startService(mSpotifyIntent);
    }

    public void OnServiceStopped(View view) {
        if (mSpotifyIntent != null) {
            unbindService(serviceConnection);
            stopService(mSpotifyIntent);
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mServiceBinder = (SpotifyService.SpotifyServiceBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            stop();
        }
    };

    @Override
    public void onServiceInvocation() {
        Toast.makeText(getApplicationContext(), "Service started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable observable, Object o) {

    }


    @Override
    public void play(int songId) {
        mServiceBinder.getService().play(songId);
        mPlay.setVisibility(View.GONE);
        mPause.setVisibility(View.VISIBLE);
        mStop.setVisibility(View.VISIBLE);

    }

    @Override
    public void stop() {
        mServiceBinder.getService().stop();
        mStop.setVisibility(View.GONE);
        mPlay.setVisibility(View.VISIBLE);
        mPause.setVisibility(View.GONE);

    }

    @Override
    public void pause() {
        mServiceBinder.getService().pause();
        mStop.setVisibility(View.GONE);
        mPause.setVisibility(View.GONE);
        mPlay.setVisibility(View.VISIBLE);

    }

    @Override
    public void fw(int songId) {

    }

    @Override
    public void ffw(int songId) {

    }

    @Override
    public void onItemClick(int i, String songTitle, int position) {
    }

    @Override
    public void onClick(View view) {
        int pos = RecyclerViewAdapter.selectedPos;
        if (view.getId() == mStop.getId()) {
            stop();
        } else if (view.getId() == mPlay.getId()) {
            mRecyclerView.findViewHolderForAdapterPosition(pos).itemView.performClick();
        } else if (view.getId() == mPause.getId()) {
            pause();
        } else if (view.getId() == mFastforward.getId()) {
            pos += 1;
            if (pos < mAdapter.getItemCount()) {
                mLayoutManager.scrollToPosition(pos + 1);
                mRecyclerView.findViewHolderForAdapterPosition(pos).itemView.performClick();
            }
        } else if (view.getId() == mReverse.getId()) {
            if (pos > 0) {
                pos -= 1;
                mLayoutManager.scrollToPosition(pos - 1);
                mRecyclerView.findViewHolderForAdapterPosition(pos).itemView.performClick();

            }
        }


    }
}
