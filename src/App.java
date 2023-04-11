import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;


public class App {
    private static JButton logOff, info, badges, friends, sss;
    private static Connection connection;
    private static ResultSet rs;
    private static JFrame f;
    private static JButton close, createC, viewC, viewR;
    private static JLabel tokens;
    public static JLabel tokensC;
    private static Clip clip;
    private static boolean soundO;
    private static boolean top10(int id) {
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("call gamification.ranks();");
            int i = 0;
            while (i < 10 && rs.next()) {
                if (rs.getString("iduser").equals(id + "")) return true;
                i++;
            }


        } catch (Exception e) {};
        return false;
    }
    public static void playMusic() {
        try {

            File musicPath = new File("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\sound.wav");
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("fail music path");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void setT(int id) {
        int k = 0;
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select tokens from users where iduser=" + id);
            rs.next();
            k = Integer.parseInt(rs.getString("tokens"));

        } catch (Exception e) {};
        tokensC.setText(k + "");

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
    public static void main(int userId, JFrame logIn, String name, String pass, int id) {

        playMusic();
        f = new JFrame("Swimmification");
        f.setUndecorated(true);
        f.setBounds(320, 20, 900, 629);
        getConnToSql();
        close = new JButton();
        close.setBorder(null);
        close.setContentAreaFilled(false);
        ImageIcon icon = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\rc.png");
        close.setIcon(icon);
        close.setBounds(830, 5, 35, 35);
        close.setVisible(true);
        f.add(close);

        info = new JButton();
        info.setBorder(null);
        info.setContentAreaFilled(false);
        ImageIcon icon2 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\pngwing.com.png");
        info.setIcon(icon2);
        info.setBounds(820, 70, 50, 50);
        info.setVisible(true);
        f.add(info);

        logOff = new JButton();
        logOff.setBorder(null);
        logOff.setContentAreaFilled(false);
        ImageIcon icon3 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\logO.png");
        logOff.setIcon(icon3);
        logOff.setBounds(825, 148, 50, 50);
        logOff.setVisible(true);
        f.add(logOff);

        badges = new JButton();
        badges.setBorder(null);
        badges.setContentAreaFilled(false);
        ImageIcon icon4 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\badges.png");
        badges.setIcon(icon4);
        badges.setBounds(820, 225, 50, 90);
        badges.setVisible(true);
        f.add(badges);

        friends = new JButton();
        friends.setBorder(null);
        friends.setContentAreaFilled(false);
        ImageIcon icon5 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\friends.png");
        friends.setIcon(icon5);
        friends.setBounds(820, 340, 50, 50);
        friends.setVisible(true);
        f.add(friends);

        logOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(logIn, "Are u sure?");
                if (n == JOptionPane.OK_OPTION) {
                	  clip.stop();
                      clip.close();
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    logIn.show();

                }

            }
        });

        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InfoEdit.main(f, name, pass, id);

            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int n = JOptionPane.showConfirmDialog(f, "Are u sure you want to exit?");
                if (n == JOptionPane.YES_OPTION)
                    logIn.dispatchEvent(new WindowEvent(logIn, WindowEvent.WINDOW_CLOSING));
            }
        });


        badges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Badges.show(f, id);
            }
        });


        friends.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Friends.show(f, id);
            }
        });
        ImageIcon icon6 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\tokens.png");
        tokens = new JLabel(icon6);
        tokens.setBounds(70, 10, 50, 50);
        tokens.setVisible(true);
        f.add(tokens);


        tokensC = new JLabel();
        setT(id);
        tokensC.setBounds(20, 10, 50, 50);
        tokensC.setFont(new Font("Jokerman", Font.PLAIN, 20));
        tokensC.setVisible(true);
        f.add(tokensC);

        createC = new JButton();
        createC.setBorder(null);
        createC.setContentAreaFilled(false);
        ImageIcon icon7 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\create.png");
        createC.setIcon(icon7);
        createC.setBounds(50, 100, 328, 118);
        createC.setVisible(true);
        f.add(createC);

        viewC = new JButton();
        viewC.setBorder(null);
        viewC.setContentAreaFilled(false);
        ImageIcon icon8 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\viewC.png");
        viewC.setIcon(icon8);
        viewC.setBounds(50, 280, 328, 118);
        viewC.setVisible(true);
        f.add(viewC);

        viewR = new JButton();
        viewR.setBorder(null);
        viewR.setContentAreaFilled(false);
        ImageIcon icon9 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\viewR.png");
        viewR.setIcon(icon9);
        viewR.setBounds(50, 460, 328, 118);
        viewR.setVisible(true);
        f.add(viewR);

        sss = new JButton(new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\unmute.png"));
        sss.setBorder(null);
        sss.setContentAreaFilled(false);

        sss.setBounds(820, 420, 50, 50);
        sss.setVisible(true);
        f.add(sss);
        sss.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (soundO) {
                    soundO = false;
                    clip.stop();
                    playMusic();
                    sss.setIcon(new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\unmute.png"));
                } else {

                    soundO = true;
                    clip.stop();
                    clip.close();
                    sss.setIcon(new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\mute.png"));
                }

            }
        });

        createC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (top10(id)) CreateC.show(f, id);
                else JOptionPane.showMessageDialog(f, "You must be at least rank 10 to create a Challange!");
            }
        });

        viewC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int k = 0;
                try {

                    Statement selectStatement = connection.createStatement();
                    rs = selectStatement.executeQuery("select tokens from users where iduser=" + id);
                    rs.next();
                    k = Integer.parseInt(rs.getString("tokens"));

                } catch (Exception e2) {};
                ChallengesAccepted.show(k, f, id);
            }
        });
        viewR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ranking.show(f, id);
            }
        });
        f.setLayout(null);
        logIn.hide();
        f.setVisible(true);

    }

}