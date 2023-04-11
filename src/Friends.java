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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;

public class Friends {


    private static JFrame f;
    private static Connection connection;
    private static ResultSet rs;
    private static JButton close;
    private static JScrollPane jsp,jsp2;
    private static Cb jp,jp2;
    private static JButton fr[],fr2[];
    private static JTextField jt;
    private static JLabel friendsLabel,userLabel;

    private static boolean verifiyF(int id, int idu)
    {
    	String f1="";
    	 try {

             Statement selectStatement = connection.createStatement();
             rs = selectStatement.executeQuery("select friends from users where iduser=" + id);
             rs.next();
             f1 = rs.getString("friends");
         } catch (Exception e) {};
         System.out.println(f1);
         if(f1.contains(","+idu+",")) return false;
         else return true;
    }
    private static String getName(int id) {
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select username from users where iduser=" + id);
            rs.next();
            return rs.getString("username");
        } catch (Exception e) {};
        return "";
    }
    private static void addFriend(int id, int fId) {
        String f1 = "";
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select friends from users where iduser=" + id);
            rs.next();
            f1 = rs.getString("friends");
        } catch (Exception e) {};
        
   f1 += fId + ",";
        try {
            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("call gamification.EditFriends('" + f1 + "', " + id + ")");
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
    private static void deletefr(int friendId, int id) {
        String frl = "";
        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select friends from users where iduser=" + id);
            rs.next();
            frl = rs.getString("friends");
        } catch (Exception e) {};

        String friends = frl.replace(friendId + ",", "");

        try {
            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("call gamification.EditFriends('" + friends + "', " + id + ")");
        } catch (Exception e) {};

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
        friendsLabel=new JLabel();
        friendsLabel.setText("Friends");
        friendsLabel.setBounds(0, 50, 200, 50);
        friendsLabel.setVisible(true);
        friendsLabel.setFont(new Font("Jokerman", Font.PLAIN, 20));
        friendsLabel.setForeground(Color.RED);
        f.add( friendsLabel);
       userLabel=new JLabel();
       userLabel.setText("Users");
       userLabel.setBounds(500, 50, 200, 50);
       userLabel.setVisible(true);
       userLabel.setFont(new Font("Jokerman", Font.PLAIN, 20));
       userLabel.setForeground(Color.RED);
        f.add( userLabel);
        jp2 = new Cb();
        jp2.setBounds(0, 0, 100, 100);
        jp2.setVisible(true);
        jp2.setLayout(new BoxLayout(jp2, BoxLayout.Y_AXIS));
        int v2 = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
        int h2 = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
        jsp2 = new JScrollPane(jp2, v2, h2);
        jsp2.setBorder(null);
        jsp2.setPreferredSize(new Dimension(600, 600));
        jsp2.setBounds(500, 100, 400, 544);
        f.add(jsp2);
        SwingUtilities.updateComponentTreeUI(f);
        int nr2 = 0;
        fr2 = new JButton[150];
        for (int i = 0; i < 150; i++)
            fr2[i] = new JButton();
        
        jp = new Cb();
        jp.setBounds(0, 0, 100, 100);
        jp.setVisible(true);
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
        jsp = new JScrollPane(jp, v, h);
        jsp.setBorder(null);
        jsp.setPreferredSize(new Dimension(600, 600));
        jsp.setBounds(0, 100, 400, 800);
        f.add(jsp);
        SwingUtilities.updateComponentTreeUI(f);
        int nr = 0;
        fr = new JButton[150];
        for (int i = 0; i < 150; i++)
            {
        	fr[i] = new JButton();
        	fr2[i] = new JButton();
            }
jt=new JTextField();


        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select friends from users where iduser=" + id);
            rs.next();
            int i = 0;
            String friends = rs.getString("friends");
           
            while (i<friends.length()-1) {
            	
                int fId = 0;
                while (friends.charAt(i) != ',') {

                    fId = 10 * fId + Integer.parseInt(friends.charAt(i) + "");
                    i++;
                }
                
if(fId!=0)
{
                int friendId = fId;
              
                String name = (getName(fId));

                ImageIcon iconnr = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\pp.png");
                fr[nr].setIcon(iconnr);
                fr[nr].setBounds(0, 0, 50, 100);
                fr[nr].setText(name);
                fr[nr].setToolTipText("Move Ahead");
                fr[nr].setVerticalTextPosition(AbstractButton.CENTER);
                fr[nr].setHorizontalTextPosition(AbstractButton.LEADING);
                fr[nr].setMnemonic(KeyEvent.VK_I);
                fr[nr].setBorder(null);
                fr[nr].setContentAreaFilled(false);
                jp.add(fr[nr]);
                fr[nr].setVisible(true);

                int n1 = nr;
                fr[nr].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                       
                        int n = JOptionPane.showConfirmDialog(f, "Do you want to delete " + name + " from friends?");
                        if (n == JOptionPane.YES_OPTION) {
                            deletefr(friendId, id);
                            jp.remove(fr[n1]);
                            jp.revalidate();
                            jp.repaint();
                        }
                    }

                });
                nr++;
                i++;
            }
else i++;
            }
            
        } catch (Exception e) {};

      
        

        try {

            Statement selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("select*  from users ");
           while (rs.next())
        		   {
        	
       if(Integer.parseInt(rs.getString("iduser"))!=id)
       {
            String name = rs.getString("username");
            int idu=Integer.parseInt(rs.getString("iduser"));
                ImageIcon iconnr = new ImageIcon("C:\\Users\\razva\\eclipse-workspace\\Gamific\\data\\pp.png");
                fr2[nr].setIcon(iconnr);
                fr2[nr].setBounds(0, 0, 50, 100);
                fr2[nr].setText(name);
                fr2[nr].setToolTipText("Move Ahead");
                fr2[nr].setVerticalTextPosition(AbstractButton.CENTER);
                fr2[nr].setHorizontalTextPosition(AbstractButton.LEADING);
                fr2[nr].setMnemonic(KeyEvent.VK_I);
                fr2[nr].setBorder(null);
                fr2[nr].setContentAreaFilled(false);
                jp2.add(fr2[nr]);
                fr2[nr].setVisible(true);
                
                int n1 = nr;
                fr2[nr].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                   
                        int n = JOptionPane.showConfirmDialog(f, "Do you want to add " + name + " to friends?");
                        if (n == JOptionPane.YES_OPTION) {                       	
                        }
                        if(verifiyF(id,idu))
                        {
                        	JOptionPane.showMessageDialog(f,"Friend added!");
                        	if(!Badges.haveBadge("make1f", id))
                        		{
                        		String badges="";
                           	 try {

                                    Statement selectStatement = connection.createStatement();
                                    rs = selectStatement.executeQuery("select badges from users where iduser=" + id);
                                    rs.next();
                                    badges =rs.getString("badges");
                                    badges+="make1f,";
                                    rs = selectStatement.executeQuery("call gamification.insert_badges('"+badges+"', "+id+");");
                                } catch (Exception e) {};
                        		}
                       addFriend(id, idu);         
                       f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                       prev.show();
                      
                        }
                        else JOptionPane.showMessageDialog(f,"Aleardy a friend!");
                       
                    }
                     });
                
                nr++;
              
        		   }
        		   }
            
        } catch (Exception e) {};
        
        
        
       
      
        f.setLayout(null);
        prev.hide();
        f.setVisible(true);

    }

}