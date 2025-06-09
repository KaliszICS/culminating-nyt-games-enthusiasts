package logic;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class AudioHandler {
    
    public void playSound(String soundURL){
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(soundURL)));
            clip.start();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
