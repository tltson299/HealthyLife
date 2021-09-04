package com.myapplication.healthylife.fragments.mainfragments;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.myapplication.healthylife.R;
import com.myapplication.healthylife.databinding.FragmentTimerBinding;
import com.myapplication.healthylife.local.DatabaseHelper;
import com.myapplication.healthylife.model.Exercise;
import com.myapplication.healthylife.model.Timer;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

import kotlin.jvm.Synchronized;

public class TimerFragment extends Fragment{
    FragmentTimerBinding binding;
    DatabaseHelper db;

    private ArrayList<Exercise> list;
    private ArrayList<Timer> listTimer;
    int i = 0;

    CountDownTimer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new DatabaseHelper(getContext());
        binding = FragmentTimerBinding.inflate(getLayoutInflater());
        list = db.getRecommendedExerciseList();
        listTimer = convert(list);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvName.setText(listTimer.get(i).getName());
        binding.tvStatus.setText(listTimer.get(i).getStatus());

        binding.video.setVideoURI(Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+listTimer.get(i).getVideo()));
        MediaController ctrl = new MediaController(getContext());
        ctrl.setVisibility(View.GONE);
        binding.video.setMediaController(ctrl);
        binding.video.start();
        binding.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        countDown(listTimer);
    }

    synchronized private void countDown(ArrayList<Timer> listTimer)    {
        timer = new CountDownTimer(listTimer.get(i).getTime(), 1000) {
            @Override
            public void onTick(long l) {
                updateTime(l);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFinish() {
                if(++i < listTimer.size()) {
                    binding.tvName.setText(listTimer.get(i).getName());
                    binding.tvStatus.setText(listTimer.get(i).getStatus());
                    binding.video.setVideoURI(Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+listTimer.get(i).getVideo()));
                    binding.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.setLooping(true);
                        }
                    });
                    countDown(listTimer);
                }else   {
                    binding.video.stopPlayback();
                }
            }
        }.start();
    }

    private void updateTime(long time)   {
        int min = (int)time/60000;
        int sec = (int)time%60000/1000;

        String text;
        text = ""+min;
        text += " : ";
        if (sec < 10)   {
            text += "0";
        }
        text += sec;
        binding.tvTime.setText(text);
    }

    private ArrayList<Timer> convert (ArrayList<Exercise> list) {
        ArrayList<Timer> returnList = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            String name = list.get(i).getName();
            String status = "";
            long time = 0;
            int video = list.get(i).getVideo();
            boolean isBreak = false;

            for (int j = 1; j <= (list.get(i).getnumSet() * 2) - 1; ++j) {
                if (!isBreak) {
                    status = "Set " + Math.round(j / 2.0);
                    time = list.get(i).getDurationSet() * 1000;
                    isBreak = true;
                }
                else {
                    status = "Break " + (j / 2) + "-" + ((j / 2) + 1);
                    time = list.get(i).getbreakSet() * 1000;
                    isBreak = false;
                }
                returnList.add(new Timer(name, status, time, video));
            }

            status = "Next Exercise";
            time = list.get(i).getbreakEx() * 1000;
            returnList.add(new Timer(name, status, time, video));
        }

        return returnList;
    }
}