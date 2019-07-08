import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Sound {
    public static void playSound(String filepath){
        InputStream music;
        try {
            music = new FileInputStream(new File(filepath));
            AudioStream audioStream = new AudioStream(music);
            AudioPlayer.player.start(audioStream);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
