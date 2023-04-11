import java.awt.Color;
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
import javax.swing.*;
import javax.swing.event.DocumentEvent;

public class CreateC {

    private static JButton close, create;
    private static JLabel titleLabel, stilLabel, distLabel, timpLabel, tokens;
    private static JTextField titlefield;
    private static JComboBox < Object > styleBox, distBox, ts, tm, th, tBox;
    private static JFrame f;
    private static Connection connection;
    private static ResultSet rs;
    public static void getConnToSql() {
        rs = null;
        try {
            f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\bck.jpg")))));
            connection = DriverManager.getConnection("jdbc:mysql://localhost/gamification?user=root&password=12345&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public static void checkTokensCreateChallenge(int nr, int id) {
        int tokens = -1;
        try {
            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select tokens from users where iduser=" + id);
            rs.next();
            tokens = Integer.parseInt(rs.getString("tokens"));

        } catch (Exception e) {};
        if (tokens > 9) {
            try {
                tokens -= 10;
                Statement selectStatement = connection.createStatement();
                rs = selectStatement.executeQuery("call gamification.insert_tokens(" + tokens + "," + id + ");");
            } catch (Exception e) {};

            String title = titlefield.getText();
            String style = (String) styleBox.getSelectedItem();
            int toks = Integer.parseInt((String) tBox.getSelectedItem());
            int dist = Integer.parseInt((String) distBox.getSelectedItem());
            String time = (String) th.getSelectedItem() + ":" + (String) tm.getSelectedItem() + ":" + (String) ts.getSelectedItem();
            try {

                Statement selectStatement = connection.createStatement();
                System.out.println();
                rs = selectStatement.executeQuery("call gamification.new_challenge('" + title + "', '" + style + "'," + dist + ", '" + time + "', " + toks + ");");
            } catch (Exception e) {};

            JOptionPane.showMessageDialog(f, "Challenge created!", "", JOptionPane.OK_OPTION);
            if (!Badges.haveBadge("c1q", id)) {
                String badges = "";
                try {

                    Statement selectStatement = connection.createStatement();
                    rs = selectStatement.executeQuery("select badges from users where iduser=" + id);
                    rs.next();
                    badges = rs.getString("badges");
                    badges += "c1q,";
                    rs = selectStatement.executeQuery("call gamification.insert_badges('" + badges + "', " + id + ");");
                } catch (Exception e) {};
            }
            titlefield.setText("");
            styleBox.setSelectedIndex(0);
            tBox.setSelectedIndex(0);
            distBox.setSelectedIndex(3);
            tBox.setSelectedIndex(0);
            th.setSelectedIndex(0);
            tm.setSelectedIndex(0);
            ts.setSelectedIndex(0);
            App.tokensC.setText(tokens + "");

        } else
            JOptionPane.showConfirmDialog(f, "You don't have enough goggles!", "", JOptionPane.OK_OPTION);

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

        titleLabel = new JLabel("Title");
        titleLabel.setBounds(100, 100, 200, 50);
        titleLabel.setFont(new Font("Jokerman", Font.PLAIN, 40));
        titleLabel.setVisible(true);
        titleLabel.setForeground(Color.RED);
        f.add(titleLabel);

        stilLabel = new JLabel("Style");
        stilLabel.setBounds(100, 200, 200, 50);
        stilLabel.setFont(new Font("Jokerman", Font.PLAIN, 40));
        stilLabel.setVisible(true);
        stilLabel.setForeground(Color.RED);
        f.add(stilLabel);

        distLabel = new JLabel("Distance");
        distLabel.setBounds(100, 300, 200, 50);
        distLabel.setFont(new Font("Jokerman", Font.PLAIN, 40));
        distLabel.setVisible(true);
        distLabel.setForeground(Color.RED);
        f.add(distLabel);


        timpLabel = new JLabel("Time");
        timpLabel.setBounds(100, 400, 200, 50);
        timpLabel.setFont(new Font("Jokerman", Font.PLAIN, 40));
        timpLabel.setVisible(true);
        timpLabel.setForeground(Color.RED);
        f.add(timpLabel);
        JLabel hms = new JLabel("(h:m:s)");
        hms.setVisible(true);
        hms.setBounds(220, 410, 100, 40);
        f.add(hms);

        titlefield = new JTextField();
        titlefield.setBounds(300, 110, 200, 30);
        titlefield.setVisible(true);
        f.add(titlefield);

        String[] styleString = {
            "Freestyle",
            "Butterfly",
            "Backstroke",
            "Breaststroke"
        };
        styleBox = new JComboBox < Object > (styleString);
        styleBox.setSelectedIndex(0);
        styleBox.setBounds(300, 210, 100, 30);
        styleBox.setVisible(true);
        f.add(styleBox);

        String[] distanceString = new String[200];
        for (int i = 1; i <= 200; i++)
            distanceString[i - 1] = i * 25 + "";

        distBox = new JComboBox < Object > (distanceString);
        distBox.setSelectedIndex(3);
        distBox.setBounds(300, 310, 100, 30);

        f.add(distBox);

        String[] h = new String[4];
        for (int i = 0; i < 4; i++)
            h[i] = i + "";
        th = new JComboBox < Object > (h);
        th.setSelectedIndex(0);
        th.setBounds(300, 410, 50, 30);
        f.add(th);

        String[] m = new String[60];
        for (int i = 0; i < 59; i++)
            m[i] = i + "";
        tm = new JComboBox < Object > (m);
        tm.setSelectedIndex(0);
        tm.setBounds(350, 410, 50, 30);
        f.add(tm);

        String[] s = new String[59];
        for (int i = 0; i < 59; i++)
            s[i] = i + 1 + "";
        ts = new JComboBox < Object > (s);
        ts.setSelectedIndex(0);
        ts.setBounds(400, 410, 50, 30);
        f.add(ts);

        tokens = new JLabel(new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\tokens.png"));
        tokens.setBounds(100, 485, 50, 50);
        tokens.setVisible(true);
        f.add(tokens);
        String[] t = new String[5];
        for (int i = 0; i < 5; i++)
            t[i] = (i + 1) * 5 + "";
        tBox = new JComboBox < Object > (t);
        tBox.setSelectedIndex(0);
        tBox.setBounds(160, 495, 50, 30);
        f.add(tBox);

        create = new JButton(new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\createC.png"));
        create.setBounds(540, 250, 270, 60);
        create.setVisible(true);
        f.add(create);
        create.setEnabled(false);
        create.setBorder(null);
        create.setContentAreaFilled(false);

        titlefield.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                if (titlefield.getText().length() > 5) create.setEnabled(true);
                else create.setEnabled(false);
            }
        });

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int answ = JOptionPane.showConfirmDialog(f, "Creating a challenge will cost you 10 goggels", "", JOptionPane.OK_CANCEL_OPTION);
                if (answ == JOptionPane.OK_OPTION) {
                    checkTokensCreateChallenge(10, id);

                }

            }
        });
        f.setLayout(null);
        prev.hide();
        f.setVisible(true);


    }
}