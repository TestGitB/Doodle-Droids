package org.nullsys.androidgames.framework.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import org.nullsys.androidgames.framework.Audio;
import org.nullsys.androidgames.framework.Music;
import org.nullsys.androidgames.framework.Sound;

public class AndroidAudio implements Audio {

    AssetManager assets;
    SoundPool soundPool;

    public AndroidAudio(Activity activity) {
	activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	assets = activity.getAssets();
	soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public Music newMusic(String filename) {
	try {
	    AssetFileDescriptor assetDescriptor = assets.openFd(filename);
	    return new AndroidMusic(assetDescriptor);
	} catch (IOException e) {
	    throw new RuntimeException("Couldn't load music '" + filename + "'");
	}
    }

    @Override
    public Sound newSound(String filename) {
	try {
	    AssetFileDescriptor assetDescriptor = assets.openFd(filename);
	    int soundId = soundPool.load(assetDescriptor, 0);
	    return new AndroidSound(soundPool, soundId);
	} catch (IOException e) {
	    throw new RuntimeException("Couldn't load sound '" + filename + "'");
	}
    }
}
