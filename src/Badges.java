import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Badges {

    private static JFrame f;
    private static Connection connection;
    private static ResultSet rs;
    private static JButton close;
    private static JScrollPane jsp;
    private static Cb jp;
    private static JButton badges[];
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
        jsp.setPreferredSize(new Dimension(600, 600));
        jsp.setBounds(0, 100, 1000, 650);

        f.add(jsp);
        SwingUtilities.updateComponentTreeUI(f);

        int nr = 0;
        badges = new JButton[150];
        for (int i = 0; i < 150; i++)
            badges[i] = new JButton();

        try {
            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select badges from users where iduser=" + id);
            rs.next();
            String badgesT = rs.getString("badges");
            int i = 0;
            while (i < badgesT.length() - 1) {
                String name = "";
                while (badgesT.charAt(i) != ',') {

                    name += badgesT.charAt(i);
                    i++;
                }
                if (name != "") {
                    String nume = name;
                    ImageIcon iconnr = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\" + name + ".png");
                    badges[nr].setIcon(iconnr);
                    badges[nr].setBounds(0, 0, 50, 50);
                    badges[nr].setVisible(true);
                    jp.add(badges[nr]);
                    badges[nr].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            String text = "";
                            if (nume.equals("c1q")) text = "Create 1 challenge";
                            if (nume.equals("make1f")) text = "Add one friend!";
                            if (nume.equals("rang1")) text = "Reach the top!";
                            if (nume.equals("w1q")) text = "Win 1 challenge!";
                            if (nume.equals("w5q")) text = "Win 5 challenges!";
                            if (nume.equals("w15q")) text = "Win 15 challenges!";
                            if (nume.equals("w30q")) text = "Win 30 challenges!";
                            JOptionPane.showMessageDialog(f, text);

                        }
                    });
                    nr++;
                }
                i++;
            }


        } catch (Exception e) {};

        f.setLayout(null);
        prev.hide();
        f.setVisible(true);
    }


    public static boolean haveBadge(String name, int id) {
        String badges = "";
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select badges from users where iduser=" + id);
            rs.next();
            badges = rs.getString("badges");
            if (badges.contains(name)) return true;
        } catch (Exception e) {};
        return false;
    }

}