package com.example.demo;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Manages the playback of audio files in the application.
 * <p>
 * The SoundManager class provides methods to play, stop, pause, resume,
 * and adjust the volume of audio files. It supports looping audio for
 * background music and can handle sound effects.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     SoundManager soundManager = new SoundManager();
 *     soundManager.playSound("/path/to/audio/file.wav");
 * </pre>
 *
 * @author michellealessandra
 * @version 1.0
 */
public class SoundManager {

    private Clip clip;

    /**
     * Plays an audio file from the specified file path.
     * <p>
     * If an audio file is already playing, it will be replaced with the new file.
     * </p>
     *
     * @param filePath the path to the audio file to be played.
     * @throws IllegalArgumentException if the audio file cannot be found.
     */
    public void playSound(String filePath) {
        try {
            URL resource = getClass().getResource(filePath);
            if (resource == null) {
                throw new IllegalArgumentException("Audio file not found: " + filePath);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(resource);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays an audio file on a continuous loop from the specified file path.
     * <p>
     * If an audio file is already playing, the method ensures the file is not interrupted
     * unless a new file is explicitly requested.
     * </p>
     *
     * @param filePath the path to the audio file to be looped.
     * @throws IllegalArgumentException if the audio file cannot be found.
     */
    public void playSoundLoop(String filePath) {
        try {
            if (clip != null && clip.isRunning()) {
                return;
            }
            stopSound();

            URL resource = getClass().getResource(filePath);
            if (resource == null) {
                throw new IllegalArgumentException("Audio file not found: " + filePath);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(resource);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing audio, if any.
     * <p>
     * This method ensures the clip is stopped, closed, and released from memory.
     * </p>
     */
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }

    /**
     * Sets the volume of the currently playing audio.
     *
     * @param volume the volume level as a double between 0.0 (mute) and 1.0 (maximum volume).
     * @throws IllegalArgumentException if the volume is out of bounds or the clip does not support volume adjustment.
     */
    public void setVolume(double volume) {
        if (clip != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (20.0 * Math.log10(volume));
            volumeControl.setValue(dB);
        }
    }

    /**
     * Pauses the currently playing audio.
     * <p>
     * The audio can be resumed using the {@link #resumeSound()} method.
     * </p>
     */
    public void pauseSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Resumes the paused audio.
     */
    public void resumeSound() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }
}
