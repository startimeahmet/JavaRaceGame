import javafx.scene.text.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.IOException;


public class OptionsPanel extends JPanel {
    public JButton helpButton = new JButton("HELP");

    public JButton backButton = new JButton("BACK");

    public JComboBox carColorBox = new JComboBox(new String[]{"Red","Blue","Pink","Green","Purple","Dark Green"});

    public BufferedImage backgroundImg;

    private Sound click_sound = new Sound("click.wav",1);

    public OptionsPanel(MainClass mainClass)
    {
        setSize(MainClass.SCREEN_WIDTH, MainClass.SCREEN_HEIGHT);

        setFocusable(true);

        setLayout(null);

        carColorBox.setBounds((MainClass.SCREEN_WIDTH - MainClass.BUTTON_WIDTH) / 2, (MainClass.SCREEN_HEIGHT - MainClass.BUTTON_HEIGHT) / 2 - 2 * MainClass.BUTTON_HEIGHT, MainClass.BUTTON_WIDTH, MainClass.BUTTON_HEIGHT);

        helpButton.setBounds((MainClass.SCREEN_WIDTH - MainClass.BUTTON_WIDTH) / 2, (MainClass.SCREEN_HEIGHT - MainClass.BUTTON_HEIGHT) / 2, MainClass.BUTTON_WIDTH, MainClass.BUTTON_HEIGHT);

        backButton.setBounds((MainClass.SCREEN_WIDTH - MainClass.BUTTON_WIDTH) / 2, (MainClass.SCREEN_HEIGHT - MainClass.BUTTON_HEIGHT) / 2 + 2 * MainClass.BUTTON_HEIGHT, MainClass.BUTTON_WIDTH, MainClass.BUTTON_HEIGHT);

        add(backButton);

        add(helpButton);

        add(carColorBox);

        carColorBox.setSelectedIndex(Car.car_selected);

        carColorBox.setAction(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {
            }

            @Override
            public void setEnabled(boolean b) {
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {
            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Car.car_selected = carColorBox.getSelectedIndex();
            }
        });

       try
        {
            backgroundImg = ImageIO.read(this.getClass().getResource("Main_img.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        backButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                click_sound.playSound();
                mainClass.mainPanel = new MainPanel(mainClass);
                mainClass.getContentPane().removeAll();
                mainClass.getContentPane().add(mainClass.mainPanel);
                mainClass.mainPanel.requestFocusInWindow();
                mainClass.revalidate();
            }
        });
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                click_sound.playSound();
                JOptionPane.showMessageDialog(null,"                   " + "GAME RULES\n" + "-COLLECT FUELS\n\n-AVOID CARS\n\n-GREEN ARROW SPEEDS UP\n\n-RED ARROW SPEEDS DOWN\n\n" +
                        "                " + "DURING THE GAME\n'ESC' BUTTON = EXIT THE APPLICATION\n\n" +
                        "'C' BUTTON      = CLOSE THE SOUND\n\n" + "'O' BUTTON     = OPEN THE SOUND AGAIN\n");
            }
        });
    }

    @Override
    public void paint(Graphics g)
    {
        g.drawImage(backgroundImg, 0, 0, null);
        paintComponents(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 24));
        g.drawString("Select Car", (MainClass.SCREEN_WIDTH / 2 - 50), (MainClass.SCREEN_HEIGHT - MainClass.BUTTON_HEIGHT) / 2 - 2 * MainClass.BUTTON_HEIGHT - 15);
        g.setColor(Color.YELLOW);
        Font font = new Font("Serif",Font.BOLD,16);
        g.setFont(font);
        g.drawString("High Score " + GamePanel.highscore, 10, 20);
    }

}
