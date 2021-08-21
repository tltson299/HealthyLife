package com.myapplication.healthylife.model;

import android.os.Parcelable;

import androidx.versionedparcelable.VersionedParcelize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class Exercise implements Serializable {
    private int id;
    private String name;
    private String level;
    private int duration;
    private int progress;
    private int image;
    private boolean isFinished;
    private boolean isRecommended;
    private boolean isOthers;
    private boolean isFirst;
    private int[] types;
    private int video;
    private String description;
    private String tutorial;

    public Exercise(int id, String name, String level, int duration, int image, int[] types, int video, String description, String tutorial) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.duration = duration;
        this.progress = 0;
        this.image = image;
        this.isFinished = false;
        this.isRecommended = false;
        this.isOthers = false;
        this.isFirst = false;
        this.types = types;
        this.video = video;
        this.description = description;
        this.tutorial = tutorial;
    }

    public Exercise(int id, String name, String level, int duration, int progress, int image, boolean isFinished, boolean isRecommended, boolean isOthers, boolean isFirst, int[] types, int video, String description, String tutorial) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.duration = duration;
        this.progress = progress;
        this.image = image;
        this.isFinished = isFinished;
        this.isRecommended = isRecommended;
        this.isOthers = isOthers;
        this.isFirst = isFirst;
        this.types = types;
        this.video = video;
        this.description = description;
        this.tutorial = tutorial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    public boolean isOthers() {
        return isOthers;
    }

    public void setOthers(boolean others) {
        isOthers = others;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public int[] getTypes() {
        return types;
    }

    public void setTypes(int[] types) {
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTutorial() {
        return tutorial;
    }

    public void setTutorial(String tutorial) {
        this.tutorial = tutorial;
    }
}
