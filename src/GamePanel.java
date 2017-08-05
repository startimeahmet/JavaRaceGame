
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener
{
    public HUD hud = new HUD();

    private Random random = new Random();

    private int UPARROWSPAWNTIME = random.nextInt(5);//Time of spawn after object is eaten

    private int DOWNARROWSPAWNTIME = random.nextInt(5);//Time of spawn after object is eaten

    private int CARSPEEDVAR = 2;//Speed increased or decreased when car gets the object

    public int MAXSPAWNTIME = 10;

    private int FUEL_VALUE = 25;

    public static int highscore = 0;

    private long speedUpGetTime = System.currentTimeMillis();

    private long speedDownGetTime = System.currentTimeMillis();

    private int random_num = random.nextInt(6);

    public Thread thread;

    public Random_Car[] random_car = {new Random_Car("random_car_red.png"),new Random_Car("random_car_blue.png"),new Random_Car("random_car_darkgreen.png"),
            new Random_Car("random_car_green.png"), new Random_Car("random_car_pink.png"),new Random_Car("random_car_purple.png")};

    public Collision car_collision,obj_collision,speedup_collision,speeddown_collision;

    public boolean right, left, up, down;

    public Background background;

    public Car playerCar;

    public Random_Car fuel_item,downward_arrow,upward_arrow;

    public Sound crash_sound,item_sound,game_sound;

    public GamePanel(MainClass mainClass)
    {
        setSize(MainClass.SCREEN_WIDTH, MainClass.SCREEN_HEIGHT);

        setFocusable(true);

        addKeyListener(this);

        setVisible(true);

        background = new Background(MainClass.SCREEN_WIDTH, MainClass.SCREEN_HEIGHT);

        playerCar = new Car(MainClass.SCREEN_WIDTH, MainClass.SCREEN_HEIGHT);

        fuel_item = new Random_Car("fuel.png");

        downward_arrow = new Random_Car("downward_arrow.png");

        upward_arrow = new Random_Car("upward_arrow.png");

        game_sound = new Sound("Car_sound.wav",31);

        crash_sound = new Sound("car_crash.wav",1);

        item_sound = new Sound("item_sound.wav",1);

        game_sound.runSoundThread();

        hud.runHudThread();

        runGameThread(mainClass);
    }
    public void runGameThread(MainClass mainClass)
    {
        thread = new Thread()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    fuel_item.updateCarPos();
                    if((System.currentTimeMillis() - speedDownGetTime )/1000 > DOWNARROWSPAWNTIME){
                        downward_arrow.updateCarPos();
                        if(downward_arrow.getY()+ downward_arrow.extraSpace> downward_arrow.screenHeight - 5){
                            downward_arrow = new Random_Car();
                            speedDownGetTime = System.currentTimeMillis();
                            DOWNARROWSPAWNTIME = random.nextInt(MAXSPAWNTIME);
                            downward_arrow = new Random_Car("downward_arrow.png");
                        }
                    }
                    if((System.currentTimeMillis() - speedUpGetTime )/1000 > UPARROWSPAWNTIME){
                        upward_arrow.updateCarPos();
                        if(upward_arrow.getY()+ upward_arrow.extraSpace> upward_arrow.screenHeight - 5){
                            upward_arrow = new Random_Car();
                            speedUpGetTime = System.currentTimeMillis();
                            UPARROWSPAWNTIME = random.nextInt(MAXSPAWNTIME);
                            upward_arrow = new Random_Car("upward_arrow.png");
                        }

                    }

                    playerCar.updateCarPos(right, left, up, down);
                    random_car[random_num].updateCarPos();
                    repaint();
                    hud.getElapsedTime();
                    car_collision = new Collision(playerCar,random_car[random_num]);
                    obj_collision = new Collision(playerCar,fuel_item);
                    speedup_collision = new Collision(playerCar,upward_arrow);
                    speeddown_collision = new Collision(playerCar,downward_arrow);
                    if(car_collision.isCollision()) {
                        crash_sound.playSound();
                        game_sound.closeSound();
                        if(hud.score > highscore){
                            highscore = hud.score;
                            JOptionPane.showMessageDialog(null, "          NEW HIGH SCORE!!!");
                        }
                        else
                            JOptionPane.showMessageDialog(null, "             YOU CRASHED");
                        mainClass.mainPanel = new MainPanel(mainClass);
                        mainClass.getContentPane().removeAll();
                        mainClass.getContentPane().add(mainClass.mainPanel);
                        mainClass.mainPanel.requestFocusInWindow();
                        mainClass.revalidate();
                        game_sound.soundSeconds = 0;
                        break;
                    }
                    else if(obj_collision.isCollision()){
                        item_sound.playSound();
                        fuel_item = new Random_Car();
                        if(hud.fuel + FUEL_VALUE >= 200)
                            hud.fuel = 200 - FUEL_VALUE;
                        hud.fuel+=FUEL_VALUE;
                        fuel_item = new Random_Car("fuel.png");
                    }
                    else if(speeddown_collision.isCollision()){
                        item_sound.playSound();
                        downward_arrow = new Random_Car();
                        if(playerCar.speedY > 2){
                            speedDownGetTime = System.currentTimeMillis();
                            DOWNARROWSPAWNTIME = random.nextInt(MAXSPAWNTIME);
                            playerCar.speedX-=CARSPEEDVAR;
                            playerCar.speedY-=CARSPEEDVAR;
                        }
                        downward_arrow = new Random_Car("downward_arrow.png");
                    }

                    else if(speedup_collision.isCollision()){
                        item_sound.playSound();
                        upward_arrow = new Random_Car();
                        if(playerCar.speedY < 16){
                            speedUpGetTime = System.currentTimeMillis();
                            UPARROWSPAWNTIME = random.nextInt(MAXSPAWNTIME);
                            playerCar.speedX+=CARSPEEDVAR;
                            playerCar.speedY+=CARSPEEDVAR;
                        }
                        upward_arrow = new Random_Car("upward_arrow.png");
                    }
                    else if(hud.fuel == 0)
                    {
                        game_sound.closeSound();
                        JOptionPane.showMessageDialog(null, "YOU ARE OUT OF FUEL");
                        mainClass.mainPanel = new MainPanel(mainClass);
                        mainClass.getContentPane().removeAll();
                        mainClass.getContentPane().add(mainClass.mainPanel);
                        mainClass.mainPanel.requestFocusInWindow();
                        mainClass.revalidate();
                        game_sound.soundSeconds = 0;
                        break;
                    }
                    if(random_car[random_num].y + random_car[random_num].extraSpace > random_car[random_num].screenHeight - 5) {
                        random_num = random.nextInt(6);
                    }
                    try
                    {
                        Thread.sleep(10);//Value inside it changes the speed of game
                        //Can be a variable to be able to change the game's speed in the game
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

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        background.drawBackground(g2d);

        if(fuel_item.carImg != null)
            fuel_item.drawCar(g2d);

        if((System.currentTimeMillis() - speedUpGetTime)/1000 > UPARROWSPAWNTIME)
            upward_arrow.drawCar(g2d);

        if((System.currentTimeMillis() - speedDownGetTime )/1000 > DOWNARROWSPAWNTIME)
            downward_arrow.drawCar(g2d);

        playerCar.drawCar(g2d);

        random_car[random_num].drawCar(g2d);

        g.setColor(Color.yellow);

        g.drawString("Score " + hud.score, 10, 20);

        g.drawString("Elapsed Time " + hud.elapsedTime / 1000, 490, 20);

        g.fillRect(200, 5, hud.fuel, 20);

        g.setColor(Color.black);

        g.drawString("FUEL  %" + hud.fuel / 2, 290, 20);

        g.setColor(Color.YELLOW);

        g.setFont(new Font(null, Font.BOLD,16));

        g.drawString("SPEED  " + playerCar.speedX * 15 + "kmh", 450, 700);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_C:
                game_sound.closeSound();
                game_sound.soundSeconds = 0;
                break;
            case KeyEvent.VK_O:
                game_sound.closeSound();
                game_sound.playSound();
                break;
            case KeyEvent.VK_ESCAPE:
                thread.stop();
                JOptionPane.showMessageDialog(null, "GAME WILL BE CLOSED");
                System.exit(0);
                break;

            }
        }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
        }
    }
}