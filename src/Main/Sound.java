package Main;

import TratareExceptii.CustomException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];
    public FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {

        soundURL[0] = getClass().getResource("/Sound/adventure.wav");
        soundURL[1] = getClass().getResource("/Sound/health.wav");
        soundURL[2] = getClass().getResource("/Sound/pickup.wav");
        soundURL[3] = getClass().getResource("/Sound/door.wav");
        soundURL[4] = getClass().getResource("/Sound/cursor.wav");
        soundURL[5] = getClass().getResource("/Sound/stairs.wav");
        soundURL[6] = getClass().getResource("/Sound/burning.wav");
        soundURL[7] = getClass().getResource("/Sound/damageSE.wav");

    }

    public void setFile(int i) throws CustomException {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();

        }catch(Exception e) {
            throw new CustomException("Audio corupt", e);
        }
    }
    public void play() {

        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
    public void checkVolume() {

        switch(volumeScale) {
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }
}
