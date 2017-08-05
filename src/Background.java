import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


public class Background
{
    public BufferedImage backgroundImg;
    public int straightLineThickness = 7;
    public int smallLineThickness = 5;
    public int smallLineLength = 50;
    public int spaceBetweenSmallLines = 150;
    public int smallLineSpeed = 3;
    public int screenWidth;
    public int screenHeight;

    //Point is a built in class that can store x and y values
    public ArrayList<Point> smallLineListLeft = new ArrayList<>();
    public ArrayList<Point> smallLineListMiddle = new ArrayList<>();
    public ArrayList<Point> smallLineListRight = new ArrayList<>();

    public Background(int screenWidth, int screenHeight)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        try
        {
            backgroundImg = ImageIO.read(this.getClass().getResource("road.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        createSmallLines();
        startSmallLinesThread();
    }

    public void startSmallLinesThread()
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    for(int i=0;i<4;i++)
                    {
                        smallLineListLeft.get(i).y = (smallLineListLeft.get(i).y + smallLineSpeed) % screenHeight;
                        smallLineListMiddle.get(i).y = (smallLineListMiddle.get(i).y + smallLineSpeed) % screenHeight;
                        smallLineListRight.get(i).y = (smallLineListRight.get(i).y + smallLineSpeed) % screenHeight;
                    }
                    try
                    {
                        Thread.sleep(10);
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

    public void createSmallLines()
    {
        for(int i=0;i<4;i++)
        {
            smallLineListLeft.add(new Point(screenWidth / 6, 75 + i * (smallLineLength + spaceBetweenSmallLines)));
            smallLineListMiddle.add(new Point(screenWidth / 2, 75 + i * (smallLineLength + spaceBetweenSmallLines)));
            smallLineListRight.add(new Point(5 * screenWidth / 6, 75 + i * (smallLineLength + spaceBetweenSmallLines)));
        }
    }

    public void drawBackground(Graphics2D g)
    {
        g.drawImage(backgroundImg, 0, 0, null);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(straightLineThickness));
        g.drawLine(screenWidth / 3, 0, screenWidth / 3, screenHeight);
        g.drawLine(2*screenWidth / 3, 0, 2* screenWidth / 3, screenHeight);
        for(int i = 0; i < 4; i++)
        {
            g.setStroke(new BasicStroke(smallLineThickness));
            g.drawLine(smallLineListLeft.get(i).x, smallLineListLeft.get(i).y, smallLineListLeft.get(i).x, smallLineListLeft.get(i).y + smallLineLength);
            g.drawLine(smallLineListMiddle.get(i).x, smallLineListMiddle.get(i).y, smallLineListMiddle.get(i).x, smallLineListMiddle.get(i).y + smallLineLength);
            g.drawLine(smallLineListRight.get(i).x, smallLineListRight.get(i).y, smallLineListRight.get(i).x, smallLineListRight.get(i).y + smallLineLength);
        }
    }
}
