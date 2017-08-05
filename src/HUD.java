import javax.swing.*;

public class HUD extends JPanel {
    public long startTime = System.currentTimeMillis();

    public long endTime;

    public long elapsedTime;

    public int fuel = 200; //Should not be changed

    public int score = 0;

    public int FUEL_REDUCE_SPEED = 50;

    public void getElapsedTime() {
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        score++;
    }

    public void runHudThread()
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        Thread.sleep(FUEL_REDUCE_SPEED);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    fuel--;
                }
            }
        };
        thread.start();
    }
}