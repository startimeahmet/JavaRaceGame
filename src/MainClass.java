import javax.swing.*;

public class MainClass extends JFrame
{
    public static int SCREEN_WIDTH = 600;
    public static int SCREEN_HEIGHT = 800;

    public static int BUTTON_WIDTH = 200;
    public static int BUTTON_HEIGHT = 50;

    public MainPanel mainPanel;
    public OptionsPanel optionsPanel;
    public GamePanel gamePanel;

    public MainClass()
    {
        this.setTitle("Java Project");

        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        this.setLocationRelativeTo(null);

        this.setResizable(false);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new MainPanel(this);

        this.getContentPane().add(mainPanel);

        this.setVisible(true);
    }


    public static void main(String [] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainClass();
            }
        });
    }
}
