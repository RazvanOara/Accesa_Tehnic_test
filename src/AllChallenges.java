import java.awt.Dimension;
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

public class AllChallenges {
    private static JFrame f;
    private static JButton close;
    private static Connection connection;
    private static ResultSet rs;
    private static JScrollPane jsp;
    private static Cb jp;
    private static JButton chs[];
    private static boolean participate(int id, int cid) {
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select* from users where iduser=" + id);
            rs.next();
            if (rs.getString("challenges").contains("," + cid + ","))
                return true;

        } catch (Exception e) {};
        return false;
    }
    private static boolean verifyIfUserParicip(int cid, int id) {
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select* from challenges where idchallenge=" + cid);
            rs.next();
            if (rs.getString("idaccept").contains("," + id + ","))
                return true;

        } catch (Exception e) {};
        return false;
    }
    private static void adauga(int cid, int id) {
        String initialIds;
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select* from challenges where idchallenge=" + cid);
            rs.next();
            initialIds = rs.getString("idaccept");
            initialIds += id + ",";
            rs = selectStatement.executeQuery("call gamification.insert_user_in_challenge('" + initialIds + "', " + cid + ");");
        } catch (Exception e) {};

        String initialIdsC;
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select* from users where iduser=" + id);
            rs.next();
            initialIdsC = rs.getString("challenges");
            initialIdsC += cid + ",";
            rs = selectStatement.executeQuery("call gamification.insertChallengeforUser('" + initialIdsC + "'," + id + ");");
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

    public static void show(JFrame menu, JFrame prev, int id) {
        /**
         *
         *
         *
         */
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
        chs = new JButton[150];
        for (int i = 0; i < 150; i++)
            chs[i] = new JButton();
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select*  from challenges");
            while (rs.next()) {

                ImageIcon iconnr = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\ch.png");
                chs[nr].setIcon(iconnr);
                chs[nr].setToolTipText("Move Ahead");
                chs[nr].setText(rs.getString("title"));
                chs[nr].setVerticalTextPosition(AbstractButton.CENTER);
                chs[nr].setHorizontalTextPosition(AbstractButton.LEADING);
                chs[nr].setMnemonic(KeyEvent.VK_I);
                chs[nr].setBorder(null);
                chs[nr].setContentAreaFilled(false);
                chs[nr].setBounds(0, 0, 50, 50);
                chs[nr].setVisible(true);
                jp.add(chs[nr]);
                int cid = Integer.parseInt(rs.getString("idchallenge"));
                String style = rs.getString("style");
                String dist = rs.getString("distance");
                String timp = rs.getString("timp");
                String tokens = rs.getString("tokens");
                chs[nr].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        int answ = JOptionPane.showConfirmDialog(f, dist + " " + style + "on " + timp, "Do you accept this challenge and win " + tokens + "tokens ?", JOptionPane.YES_NO_OPTION);
                        if (answ == JOptionPane.YES_OPTION) {
                            if (!participate(id, cid)) {
                                adauga(cid, id);
                                JOptionPane.showMessageDialog(f, "Challenge accepted!", "", JOptionPane.OK_OPTION);
                                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                                prev.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                                menu.show();

                            } else JOptionPane.showMessageDialog(f, "You aleardy accepted this challenge!", "", JOptionPane.OK_OPTION);
                        }
                    }

                });

                nr++;



            }
        } catch (Exception e) {};

        f.setLayout(null);
        prev.hide();
        f.setVisible(true);
    }
}