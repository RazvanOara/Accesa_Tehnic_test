import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.*;

public class InfoEdit {
    private static JFrame f;
    private static JButton close, name, pass, save, cancel;
    private static Connection connection;
    private static ResultSet rs;
    private static JLabel newn, newp;
    private static JTextField newname, newpass;
    static int ok;
    private static boolean checkpass(String text, int id) {
        try {
            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select* from users where iduser=" + id);
            rs.next();
            if (text.equals(rs.getString("userpassword"))) return true;
        } catch (Exception e) {};

        return false;
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
    private static boolean verif(String text) {

        int nr = 0, nr1 = 0;
        if (text.length() < 5) {
            JOptionPane.showMessageDialog(f, "Password must be at least 5 characters long, contain a upercase and a digit!");
            return false;
        }
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
    public static void main(JFrame prev, String nume, String parola, int id) {


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

        name = new JButton("Username");
        name.setBorder(null);
        name.setContentAreaFilled(false);
        name.setBounds(100, 100, 200, 50);
        name.setFont(new Font("Jokerman", Font.PLAIN, 40));
        name.setVisible(true);
        name.setForeground(Color.RED);
        f.add(name);
        pass = new JButton("Password");
        pass.setBorder(null);
        pass.setContentAreaFilled(false);
        pass.setBounds(100, 300, 200, 50);
        pass.setFont(new Font("Jokerman", Font.PLAIN, 40));
        pass.setVisible(true);
        pass.setForeground(Color.RED);
        f.add(pass);

        newn = new JLabel("New username");
        newp = new JLabel("New password");
        newn.setFont(new Font("Arial", Font.PLAIN, 20));
        newn.setForeground(Color.GREEN);
        newn.setBounds(330, 108, 200, 50);
        newn.setVisible(false);
        f.add(newn);
        newp.setFont(new Font("Arial", Font.PLAIN, 20));
        newp.setForeground(Color.GREEN);
        newp.setBounds(330, 305, 200, 50);
        newp.setVisible(false);
        f.add(newp);

        newname = new JTextField();
        newpass = new JTextField("");

        newname.setBounds(500, 118, 200, 20);
        newname.setVisible(false);
        f.add(newname);

        newpass.setBounds(500, 315, 200, 20);
        newpass.setVisible(false);
        f.add(newpass);

        cancel = new JButton();
        cancel.setBorder(null);
        cancel.setContentAreaFilled(false);
        ImageIcon icon2 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\button_cancel.png");
        cancel.setIcon(icon2);
        cancel.setBounds(250, 500, 146, 62);
        cancel.setVisible(false);
        f.add(cancel);

        save = new JButton();
        save.setBorder(null);
        save.setContentAreaFilled(false);
        ImageIcon icon1 = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\button_update.png");
        save.setIcon(icon1);
        save.setBounds(450, 500, 146, 62);
        save.setVisible(false);
        f.add(save);
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newname.setText(nume);
                newname.setVisible(true);
                newn.setVisible(true);
                save.setVisible(true);
                pass.setEnabled(false);
                cancel.setVisible(true);
                ok = 1;
            }
        });
        pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newpass.setVisible(true);
                newpass.setText("");
                newp.setVisible(true);
                save.setVisible(true);
                ok = 2;
                name.setEnabled(false);
                cancel.setVisible(true);
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String n = "", p = "";
                int okCxl = 0;
                boolean v1 = false;;
                if (ok == 1)
                    if (verif2(newname.getText())) {
                        n = newname.getText();
                        p = parola;
                        v1 = true;
                    }
                System.out.println(newpass.getText());
                if (ok == 2)
                    if (verif(newpass.getText())) {
                        n = nume;
                        p = newpass.getText();
                        v1 = true;
                    }

                if (v1 == true) {
                    JPasswordField pf = new JPasswordField();

                    okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    String checkp = "";
                    if (okCxl == JOptionPane.OK_OPTION)
                        checkp = new String(pf.getPassword());
                    if (checkpass(checkp, id)) {
                        try {
                            Statement selectStatement = connection.createStatement();
                            rs = selectStatement.executeQuery("call gamification.edit_user('" + n + "', '" + p + "', " + id + ");");
                            JOptionPane.showMessageDialog(f, "Succesfull update!");
                            pass.setEnabled(true);
                            name.setEnabled(true);
                            v1 = false;
                            newname.setVisible(false);
                            newpass.setVisible(false);
                            newn.setVisible(false);
                            newp.setVisible(false);
                            cancel.setVisible(false);
                            save.setVisible(false);


                        } catch (Exception e) {};
                    } else JOptionPane.showMessageDialog(f, "Incorect Password");
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pass.setEnabled(true);
                name.setEnabled(true);
                newname.setVisible(false);
                newpass.setVisible(false);
                newn.setVisible(false);
                newp.setVisible(false);
                cancel.setVisible(false);
                save.setVisible(false);
            }
        });

        f.setLayout(null);
        prev.hide();
        f.setVisible(true);


    }

}