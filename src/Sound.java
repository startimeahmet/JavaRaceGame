import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.IOException;
import java.io.InputStream;

public class Sound {

    AudioStream audioStream;

    public int soundSeconds = 0;

    public String soundFile;

    public Sound(String soundFile,int soundSeconds)
    {
        this.soundFile = soundFile;

        this.soundSeconds = soundSeconds;
    }
    public void playSound()
    {
        InputStream inputStream = getClass().getResourceAsStream(soundFile);
        try
        {
            audioStream = new AudioStream(inputStream);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        AudioPlayer.player.start(audioStream);
    }
    public void closeSound()
    {
        AudioPlayer.player.stop(audioStream);
    }
    public void runSoundThread()
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    if(soundSeconds != 0)
                        playSound();
                    try
                    {
                        Thread.sleep(soundSeconds * 1000);
                        AudioPlayer.player.stop(audioStream);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
}
