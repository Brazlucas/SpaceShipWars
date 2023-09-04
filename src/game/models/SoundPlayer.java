package game.models;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
  private Clip clip;

  public SoundPlayer(String soundFilePath) {
    try {
      File soundFile = new File(soundFilePath);
      System.out.println(soundFile);
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
      clip = AudioSystem.getClip();
      clip.open(audioIn);
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  public void play() {
    if (clip.isRunning()) {
      clip.stop();
    }
    clip.setFramePosition(0);
    clip.start();
  }

  public void stop() {
    if (clip.isRunning()) {
      clip.stop();
    }
  }
}