package org.aquarium.utils.music;

import org.aquarium.state.ParametersService;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class MusicPlayer {

    File fileAccess;
    AudioInputStream audioInput;
    Clip clipHandler;

    public MusicPlayer(String musicFilePathname) {
        fileAccess = new File(musicFilePathname);
        try {
            audioInput = AudioSystem.getAudioInputStream(fileAccess);
            clipHandler = AudioSystem.getClip();
            clipHandler.open(audioInput);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public MusicPlayer(int levelNumber) {
        ParametersService ps = ParametersService.getInstance();
        fileAccess = new File(ps.getLevelMusicFilePathname(levelNumber));
        try {
            audioInput = AudioSystem.getAudioInputStream(fileAccess);
            clipHandler = AudioSystem.getClip();
            clipHandler.open(audioInput);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void play() {
        try {
            clipHandler.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void stop() {
        try {
            clipHandler.stop();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void main(String[] args) {
        ParametersService ps = ParametersService.getInstance();
        System.out.println(ps.getLevelMusicFilePathname(1));
        System.out.println(ps.getLevelMusicFilePathname(0));

        MusicPlayer mp1 = new MusicPlayer(ps.getLevelMusicFilePathname(1));
        MusicPlayer mp2 = new MusicPlayer(0);

        mp1.play();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        mp1.stop();

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        mp2.play();

        try {
            Thread.sleep(8000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        mp2.stop();
    }
}