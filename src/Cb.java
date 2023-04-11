import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Cb extends JPanel
{
   Cb()
    {
        
    }
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\bckj.png");
        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }
}

//https://stackoverflow.com/questions/19125707/simplest-way-to-set-image-as-jpanel-background