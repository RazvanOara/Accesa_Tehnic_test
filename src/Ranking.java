import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class Ranking {

    private static JButton close;
    private static JFrame f;
    private static Connection connection;
    private static ResultSet rs;
    private static JScrollPane jsp;
    private static Cb jp;
    private static JLabel chs[];

    public static void getConnToSql() {
        rs = null;
        try {
            f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\bck.jpg")))));
            connection = DriverManager.getConnection("jdbc:mysql://localhost/gamification?user=root&password=12345&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public static void show(JFrame prev, int id) {
        f = new JFrame("Swimmification");
        f.setUndecorated(true);
        f.setBounds(320, 20, 900, 629);
        getConnToSql();
        close = new JButton();
        close.setBorder(null);
        close.setContentAreaFilled(false);
        ImageIcon icon = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\la.png");
        close.setIcon(icon);
        close.setBounds(10, 10, 62, 50);
        close.setVisible(true);
        f.add(close);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                prev.show();
            }
        });

        jp = new Cb();
        jp.setBounds(0, 0, 100, 100);
        jp.setVisible(true);
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
        jsp = new JScrollPane(jp, v, h);
        jsp.setBorder(null);
        jsp.setPreferredSize(new Dimension(500, 500));
        jsp.setBounds(0, 100, 1000, 550);

        f.add(jsp);
        SwingUtilities.updateComponentTreeUI(f);

        int nr = 0;
        chs = new JLabel[150];
        for (int i = 0; i < 150; i++)
            chs[i] = new JLabel();
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("call gamification.ranks();");
            while (rs.next()) {

                chs[nr].setText(nr + 1 + ". " + rs.getString("username") + "................" + rs.getString("points"));
                chs[nr].setBounds(0, 0, 50, 50);
                chs[nr].setVisible(true);
                chs[nr].setFont(new Font("Jokerman", Font.PLAIN, 20));
                chs[nr].setForeground(Color.RED);
                jp.add(chs[nr]);
                nr++;



            }
        } catch (Exception e) {};

        f.setLayout(null);
        prev.hide();
        f.setVisible(true);
    }

}