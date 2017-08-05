import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


public class Random_Car {
    public int speedY = 5;
    public BufferedImage carImg;
    public int x;
    public int y;
    public int screenWidth = MainClass.SCREEN_WIDTH;
    public int screenHeight = MainClass.SCREEN_HEIGHT;
    public int extraSpace = 10;//Space between the car that can go and window
    public int[] road_parts = {0,extraSpace + screenWidth/6, extraSpace + 2*screenWidth/6, extraSpace + 3*screenWidth/6,extraSpace + 4*screenWidth/6,5*screenWidth/6};
    public Random randomNum = new Random();

    public Random_Car(){
    }
    public Random_Car(String car_name) {
        try
        {
            carImg = ImageIO.read(this.getClass().getResource(car_name));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (carImg != null)
        {
            x = road_parts[randomNum.nextInt(6)];
            y = -(carImg.getHeight() + 3*extraSpace);
        }
    }
    public void drawCar(Graphics2D g)
    {
        g.drawImage(carImg, x, y, null);
    }
    public void updateCarPos()
    {
        y += speedY;
        if(y + extraSpace> screenHeight) {
            speedY = 3 + randomNum.nextInt(10);
            y = -carImg.getHeight();
            x = road_parts[randomNum.nextInt(6)];
        }
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

}