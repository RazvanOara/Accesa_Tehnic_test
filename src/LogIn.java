import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class LogIn {

    private static JButton reg;
    private static Connection connection;
    private static ResultSet rs;
    private static JFrame logInPopUp;
    private static JButton logIn, close;
    private static JLabel logInLabelPass;
    private static JLabel logInLabelNick;
    private static JTextField nicusor;
    private static JPasswordField logInPass;
    private static String userName;
    private static String userPassword;
    private static JCheckBox af_parola;
    private static boolean af_p;
    private static int userid;

    public static void getConnToSql() {
        rs = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost/gamification?user=root&password=12345&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public static void main(String[] args) {
        getConnToSql();

        logInPopUp = new JFrame("Swimmtification");
        logInPopUp.setUndecorated(true);
        logInPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInPopUp.setBounds(540, 300, 400, 200);
        try {
            logInPopUp.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\swimming-pool-background-cinoby.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        af_parola = new JCheckBox("<html>Show <br/>password<html>");
        af_parola.setVisible(true);
        af_parola.setBounds(290, 50, 100, 50);
        af_parola.setOpaque(false);
        af_parola.setContentAreaFilled(false);
        af_parola.setBorderPainted(false);
        logInPopUp.getContentPane().add(af_parola);

        logInLabelPass = new JLabel("Username ");
        logInLabelPass.setBounds(50, 20, 100, 50);
        logInLabelPass.setVisible(true);
        logInPopUp.getContentPane().add(logInLabelPass);

        nicusor = new JTextField("");
        nicusor.setBounds(120, 38, 150, 20);
        nicusor.setVisible(true);
        logInPopUp.getContentPane().add(nicusor);

        logInLabelNick = new JLabel("Password");
        logInLabelNick.setBounds(50, 50, 100, 50);
        logInLabelNick.setVisible(true);
        logInPopUp.getContentPane().add(logInLabelNick);

        logInPass = new JPasswordField("");
        logInPass.setEchoChar('\u25CF');
        logInPass.setBounds(120, 70, 150, 20);
        logInPass.setVisible(true);
        logInPopUp.getContentPane().add(logInPass);

        logIn = new JButton();
        logIn.setBorder(null);
        logIn.setContentAreaFilled(false);
        ImageIcon icon3 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\log.png");
        logIn.setIcon(icon3);
        logIn.setBounds(120, 110, 141, 46);
        logIn.setVisible(true);
        logInPopUp.add(logIn);

        close = new JButton();
        close.setBorder(null);
        close.setContentAreaFilled(false);
        ImageIcon icon = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\rc.png");
        close.setIcon(icon);
        close.setBounds(350, 7, 35, 35);
        close.setVisible(true);
        logInPopUp.add(close);

        reg = new JButton("Register");
        reg.setVisible(true);
        reg.setBounds(-10, 130, 120, 50);
        reg.setOpaque(false);
        reg.setContentAreaFilled(false);
        reg.setBorderPainted(false);
        reg.setFont(new Font("Arial", Font.BOLD, 10));
        logInPopUp.getContentPane().add(reg);
        af_p = true;
        af_parola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (af_p) {
                    logInPass.setEchoChar((char) 0);
                    af_p = false;
                } else {
                    logInPass.setEchoChar('\u25CF');
                    af_p = true;

                }
            }

        });
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int n = JOptionPane.showConfirmDialog(logInPopUp, "Are u sure you want to exit?");
                if (n == JOptionPane.YES_OPTION) logInPopUp.dispatchEvent(new WindowEvent(logInPopUp, WindowEvent.WINDOW_CLOSING));
            }

        });
        logIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    Statement selectStatement = connection.createStatement();
                    rs = selectStatement.executeQuery("select* from users");
                    boolean gasit_user = false;
                    boolean gasit_parola = false;
                    while (rs.next()) {
                        gasit_user = false;
                        gasit_parola = false;
                        userName = nicusor.getText();
                        userPassword = logInPass.getText();

                        if (userName.equals(rs.getString("username")))
                            gasit_user = true;
                        if (userPassword.equals(rs.getString("userpassword")))
                            gasit_parola = true;

                        if (gasit_user && gasit_parola) break;



                    }

                    if (gasit_user && gasit_parola) {
                        String name = rs.getString("username");
                        String pass = rs.getString("userpassword");
                        userid = Integer.parseInt(rs.getString("iduser"));


                        App.main(userid, logInPopUp, name, pass, userid);

                    } else {
                        JOptionPane.showMessageDialog(logInPopUp, "Incorect data");
                        nicusor.setText("");
                        logInPass.setText("");
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        });
        reg.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Register.register(logInPopUp);
            }

        });

        logInPopUp.setLayout(null);
        logInPopUp.setVisible(true);

    }

}