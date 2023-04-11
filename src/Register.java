import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Register {
    private static JFrame f;
    private static Connection connection;
    private static ResultSet rs;
    private static JButton close;
    private static boolean verif(String text) {
        if (text.length() < 5) return false;
        int nr = 0, nr1 = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) nr++;
            if (Character.isUpperCase(text.charAt(i))) nr1++;
        }

        if (nr == 0 || nr1 == 0) {
            JOptionPane.showMessageDialog(f, "Password must be at least 5 characters long, contain a upercase and a digit!");
            return false;
        }
        return true;
    }
    private static boolean verif2(String text) {
        if (text.length() < 6) {
            JOptionPane.showMessageDialog(f, "Username must be at least 6 charactrs long!");
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                JOptionPane.showMessageDialog(f, "Username contains blank spaces!");
                return false;
            }
        }
        return true;
    }
    public static void register(JFrame prev) {
        prev.hide();
        f = new JFrame("Register");
        f.setUndecorated(true);
        f.setBounds(340, 150, 600, 500);

        rs = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost/gamification?user=root&password=12345&serverTimezone=UTC");
            f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\swimming-pool-background-cinoby.jpg")))));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JLabel username = new JLabel("Username");
        JLabel userPass = new JLabel("Password");
        username.setBounds(100, 30, 80, 50);
        userPass.setBounds(100, 80, 80, 50);
        username.setVisible(true);
        userPass.setVisible(true);
        f.add(userPass);
        f.add(username);
        JLabel userPassR = new JLabel("Repeat Password");
        userPassR.setBounds(80, 130, 110, 50);
        userPassR.setVisible(true);
        f.add(userPassR);

        JPasswordField textpassR = new JPasswordField();
        textpassR.setVisible(true);
        textpassR.setBounds(200, 149, 200, 20);
        textpassR.setEchoChar('\u25CF');
        f.getContentPane().add(textpassR);

        JTextField textuser = new JTextField("");
        textuser.setVisible(true);
        textuser.setBounds(200, 47, 200, 20);
        f.getContentPane().add(textuser);

        JPasswordField textpass = new JPasswordField();
        textpass.setVisible(true);
        textpass.setBounds(200, 98, 200, 20);
        textpass.setEchoChar('\u25CF');
        f.getContentPane().add(textpass);

        JButton r = new JButton(new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\register.png"));
        r.setVisible(true);
        r.setBorder(null);
        r.setContentAreaFilled(false);
        r.setBounds(180, 250, 194, 66);
        f.getContentPane().add(r);
        close = new JButton();
        close.setBorder(null);
        close.setContentAreaFilled(false);
        ImageIcon icon = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\rc.png");
        close.setIcon(icon);
        close.setBounds(550, 15, 25, 25);
        close.setVisible(true);
        f.add(close);
        r.addActionListener(new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String myPass = String.valueOf(textpass.getPassword());
                String myPass2 = String.valueOf(textpassR.getPassword());
                boolean ok = true;
                if (verif2(textuser.getText()))
                    if (verif(myPass))
                        if (!myPass.equals(myPass2)) JOptionPane.showMessageDialog(f, "Passwords does not match!");
                        else {
                            try {
                                Statement selectStatement = connection.createStatement();
                                rs = selectStatement.executeQuery("select* from users");
                                while (rs.next()) {
                                    if (textuser.getText().equals(rs.getString("username"))) {

                                        JOptionPane.showMessageDialog(f, "A user aleardy have this username!");
                                        ok = false;
                                    } else {

                                        rs = selectStatement.executeQuery("call gamification.new_user('" + textuser.getText() + "', '" + myPass + "')");
                                        JOptionPane.showMessageDialog(f, "Utilizator creat cu succes!");
                                        f.dispose();
                                        prev.show();
                                    }
                                }

                            } catch (Exception e) {};
                        }


            }
        });
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f.dispose();
                prev.show();
            }

        });
        SwingUtilities.updateComponentTreeUI(f);
        f.setLayout(null);
        f.setVisible(true);
    }

}