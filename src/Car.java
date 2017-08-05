import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Car
{
    public int speedX = 5;
    public int speedY = 5;
    public static int car_selected = 0;
    public BufferedImage carImg;
    public int x;
    public int y;
    public int screenWidth;
    public int screenHeight;
    public int extraSpace = 10;//Space between the car that can go and window
    public Car(int screenWidth, int screenHeight)
    {
        String[] car_color = {"car_red.png","car_blue.png","car_pink.png","car_green.png","car_purple.png","car_darkgreen.png"};
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        try
        {
            carImg = ImageIO.read(this.getClass().getResource(car_color[car_selected]));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        if(carImg != null)
        {
            this.x = (screenWidth - carImg.getWidth()) / 2;
            this.y = screenHeight - carImg.getHeight() - extraSpace;
        }
    }

    public void drawCar(Graphics2D g)
    {
        g.drawImage(carImg, x, y, null);
    }

    public void updateCarPos(boolean right, boolean left, boolean up, boolean down)
    {
        if(right)
        {
            if(x + carImg.getWidth() + extraSpace < screenWidth)
                x += speedX;
        }
        if(left)
        {
            if(x > extraSpace)
                x -= speedX;
        }
        if(up)
        {
            if(y > extraSpace)
                y -= speedY;
        }
        if(down)
        {
            if(y + carImg.getHeight() + extraSpace < screenHeight)
                y += speedY;
        }
    }
    public int getX()
    {
        return x;
    }
    public int getY(){
        return y;
    }
}
