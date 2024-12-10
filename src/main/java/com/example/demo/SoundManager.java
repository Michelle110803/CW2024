package com.example.demo;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SoundManager{
    private Clip clip;

    public void playSound(String filePath) {
        try {
            System.out.println("Attempting to play sound: " + filePath);
            URL resource = getClass().getResource(filePath);
            if (resource == null) {
                throw new IllegalArgumentException("Audio file not found: " + filePath);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(resource);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            System.out.println("Sound is playing: " + filePath);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }




    public void playSoundLoop(String filePath) {
        try {
            System.out.println("Attempting to loop sound: " + filePath);
            URL resource = getClass().getResource(filePath);
            if (resource == null) {
                throw new IllegalArgumentException("Audio file not found: " + filePath);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(resource);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Sound is looping: " + filePath);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }



    public void stopSound(){
        if(clip != null && clip.isRunning()){
            clip.stop();
        }
    }
}