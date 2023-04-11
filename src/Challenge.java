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

public class Challenge {

    private static JFrame f;
    private static JButton close, done, cancel;
    private static Connection connection;
    private static ResultSet rs;
    private static JLabel tit, about;
    private static void setT(int id, int tokens) {
        int k = 0;
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select tokens from users where iduser=" + id);
            rs.next();
            k = Integer.parseInt(rs.getString("tokens"));
            k += tokens;
            rs = selectStatement.executeQuery("call gamification.insert_tokens(" + k + "," + id + ");");

        } catch (Exception e) {};

    }
    private static void addPoints(int id) {
        int p;
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select* from users where iduser=" + id);
            rs.next();
            p = Integer.parseInt(rs.getString("points"));
            p += 25;
            rs = selectStatement.executeQuery("call gamification.insert_points(" + p + ", " + id + ");");
        } catch (Exception e) {};
    }

    private static void stergeCfromUser(int cid, int id) {
        String initialIdsC;
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select* from users where iduser=" + id);
            rs.next();
            initialIdsC = rs.getString("challenges");
            initialIdsC = initialIdsC.replace(cid + ",", "");
            rs = selectStatement.executeQuery("call gamification.insertChallengeforUser('" + initialIdsC + "'," + id + ");");
        } catch (Exception e) {};
    }
    private static void sterge(int cid, int id) {
        String initialIds;
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select* from challenges where idchallenge=" + cid);
            rs.next();
            initialIds = rs.getString("idaccept");
            initialIds = initialIds.replace(id + ",", "");
            rs = selectStatement.executeQuery("call gamification.insert_user_in_challenge('" + initialIds + "', " + cid + ");");
        } catch (Exception e) {};

    }
    private static boolean israng1(int id) {
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("call gamification.ranks();");
            rs.next();
            if (Integer.parseInt(rs.getString("iduser")) == id)
                return true;

        } catch (Exception e) {};
        return false;
    }
    private static void checkForBadge(int nr, int id) {

        if (nr == 1) {

            if (!Badges.haveBadge("w1q", id)) { //Badges.winHowManyQuests(id, 1);
                String badges;
                try {

                    Statement selectStatement = connection.createStatement();
                    rs = selectStatement.executeQuery("select badges from users where iduser=" + id);
                    rs.next();
                    badges = rs.getString("badges");
                    badges += "w" + nr + "q,";
                    rs = selectStatement.executeQuery("call gamification.insert_badges('" + badges + "', " + id + ");");
                } catch (Exception e) {};

            }
        }
        if (nr == 5)
            if (!Badges.haveBadge("w5q", id)) {
                //Badges.winHowManyQuests(id, 5);
                String badges;
                try {

                    Statement selectStatement = connection.createStatement();
                    rs = selectStatement.executeQuery("select badges from users where iduser=" + id);
                    rs.next();
                    badges = rs.getString("badges");
                    badges += "w" + nr + "q,";
                    rs = selectStatement.executeQuery("call gamification.insert_badges('" + badges + "', " + id + ");");
                } catch (Exception e) {};
            }
        if (nr == 15)
            if (!Badges.haveBadge("w15q", id)) { //Badges.winHowManyQuests(id, 15);
                String badges;
                try {

                    Statement selectStatement = connection.createStatement();
                    rs = selectStatement.executeQuery("select badges from users where iduser=" + id);
                    rs.next();
                    badges = rs.getString("badges");
                    badges += "w" + nr + "q,";
                    rs = selectStatement.executeQuery("call gamification.insert_badges('" + badges + "', " + id + ");");
                } catch (Exception e) {};
            }
        if (nr == 30)
            if (!Badges.haveBadge("w30q", id)) {
                //Badges.winHowManyQuests(id, 30);
                String badges;
                try {

                    Statement selectStatement = connection.createStatement();
                    rs = selectStatement.executeQuery("select badges from users where iduser=" + id);
                    rs.next();
                    badges = rs.getString("badges");
                    badges += "w" + nr + "q,";
                    rs = selectStatement.executeQuery("call gamification.insert_badges('" + badges + "', " + id + ");");
                } catch (Exception e) {};
            }
       
    }
    private static void incCC(int id) {

        int nr;
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select* from users where iduser=" + id);
            rs.next();
            nr = Integer.parseInt(rs.getString("completedC"));
            nr++;
            checkForBadge(nr, id);
            if (!Badges.haveBadge("rang1", id) && israng1(id)) {
                String badges;
                try {

                    Statement selectStatementt = connection.createStatement();
                   ResultSet rs2 = selectStatement.executeQuery("select badges from users where iduser=" + id);
                    rs2.next();
                    badges = rs2.getString("badges");
                    badges += "rang1,";
                    rs2 = selectStatement.executeQuery("call gamification.insert_badges('" + badges + "', " + id + ");");
                } catch (Exception e) {};
            }
            rs = selectStatement.executeQuery("call gamification.insertC(" + nr + "," + id + ");");
        } catch (Exception e) {};

    }
    public static void getConnToSql() {
        rs = null;
        try {
            f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\bck.jpg")))));
            connection = DriverManager.getConnection("jdbc:mysql://localhost/gamification?user=root&password=12345&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void show(int tok, JFrame menu, JFrame prev, int id, int cid, String title, String style, String dist, String timp, String tokens) {
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


        tit = new JLabel(title + "-" + tokens + " goggels");
        tit.setFont(new Font("Jokerman", Font.PLAIN, 40));
        tit.setBounds(300, 0, 500, 60);
        tit.setForeground(Color.RED);
        f.add(tit);

        about = new JLabel("You have to swim " + dist + " " + style + " on " + timp);
        about.setFont(new Font("Jokerman", Font.PLAIN, 30));
        about.setBounds(100, 200, 800, 50);
        about.setForeground(Color.RED);
        f.add(about);

        done = new JButton(new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\done.png"));
        done.setBorder(null);
        done.setContentAreaFilled(false);
        done.setBounds(200, 300, 100, 100);
        done.setVisible(true);
        f.add(done);

        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int n = JOptionPane.showConfirmDialog(f, "Did you complete this challenge?");
                if (n == JOptionPane.YES_OPTION) {

                    sterge(cid, id);
                    incCC(id);
                    addPoints(id);
                    setT(id, Integer.parseInt(tokens));
                    JOptionPane.showMessageDialog(f, "Challenge completed! You earn 25 points and " + tokens + " goggles!", "", JOptionPane.OK_OPTION);
                    App.tokensC.setText((tok + Integer.parseInt(tokens)) + "");
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    prev.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    menu.show();


                }
            }
        });
        cancel = new JButton(new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\cancel.png"));
        cancel.setBorder(null);
        cancel.setContentAreaFilled(false);
        cancel.setBounds(400, 300, 100, 100);
        cancel.setVisible(true);
        f.add(cancel);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int n = JOptionPane.showConfirmDialog(f, "Do you quit this challenge?");
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(f, "Challenge cancelled!", "", JOptionPane.OK_OPTION);
                    sterge(cid, id);
                    stergeCfromUser(cid, id);
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    prev.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    menu.show();
                }

            }

        });

        f.setLayout(null);
        prev.hide();
        f.setVisible(true);
    }
}