import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
   static final String DB_URL = "jdbc:mysql://localhost:3306/test";
   static final String USER = "root";
   static final String PASS = "123456";
   JPanel butpan = new JPanel();
   JPanel logpan = new JPanel();
   JPanel selpan = new JPanel();
   JLabel un = new JLabel("Login-ID");
   JTextField usr = new JTextField(10);
   JLabel pd = new JLabel("Password");
   JTextField pwd = new JTextField(10);
   JButton reset = new JButton("Reset");
   JButton log = new JButton("Login");
   JButton join = new JButton("Join");
   JRadioButton app = new JRadioButton("Applicants");
   JRadioButton emp = new JRadioButton("Employers");
   ButtonGroup bg = new ButtonGroup();
   Connection clog;
   Statement st;
   int flag = 0;
   int i = 0;

   public static void main(String args[]) {
      new Login();
   }

   public Login() {
      getContentPane().add(butpan, BorderLayout.SOUTH);
      getContentPane().add(logpan, BorderLayout.WEST);
      getContentPane().add(selpan, BorderLayout.EAST);

      logpan.add(un);
      logpan.add(usr);
      logpan.add(pd);
      logpan.add(pwd);

      butpan.add(reset);
      butpan.add(log);
      butpan.add(join);

      selpan.add(app);
      selpan.add(emp);

      bg.add(app);
      bg.add(emp);
      setVisible(true);
      setSize(400, 400);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      pack();
      reset.addActionListener(this);
      log.addActionListener(this);
      join.addActionListener(this);

   }

   public void actionPerformed(ActionEvent ae) {
      Object source = ae.getSource();
      if (source == reset) {
         usr.setText("");
         pwd.setText("");
      } else if (source == join) {
         if (app.isSelected()) {
            new SQLApp();
            setVisible(false);
         }

         else if (emp.isSelected()) {
            new Employee();
            setVisible(false);
         }

      } else if (source == log) {

         /* Log in of Applicants start from here */

         if (app.isSelected()) {
            try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               clog = DriverManager.getConnection(DB_URL, USER, PASS);
               st = clog.createStatement();
               ResultSet rsLog1 = st.executeQuery("select * from Applicant");

               while (rsLog1.next()) {
                  boolean b1 = rsLog1.getString(8).equals((String) usr.getText());
                  boolean b2 = rsLog1.getString(9).equals((String) pwd.getText());
                  if (b1 && b2) {
                     JOptionPane.showMessageDialog(null, "done"); // Remove this line
                     new EmployeeDetails(rsLog1.getInt(7));
                     flag = 1;
                     break;
                  }
               }
               if (flag != 1 && i <= 3) {
                  ++i;
                  JOptionPane.showMessageDialog(null, "Invalid Entry");
               } else if (flag != 1 && i > 3) {
                  System.exit(0);
               } else if (flag == 1) {
                  setVisible(false);
               }
            } catch (Exception excp) {
               JOptionPane.showMessageDialog(null, excp);
               System.exit(0);
            }
         }

         /* log in of Employers Start from here */

         else if (emp.isSelected()) {
            try {
               Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
               clog = DriverManager.getConnection("jdbc:odbc:go");
               st = clog.createStatement();
               ResultSet rsLog2 = st.executeQuery("select * from Company");

               while (rsLog2.next()) {
                  boolean b1 = rsLog2.getString(5).equals((String) usr.getText());
                  boolean b2 = rsLog2.getString(6).equals((String) pwd.getText());
                  if (b1 && b2) {
                     JOptionPane.showMessageDialog(null, "done"); // Remove this line
                     new AppDetail(rsLog2.getInt(4));
                     flag = 1;
                     break;
                  }
               }
               if (flag != 1 && i <= 3) {
                  ++i;
                  JOptionPane.showMessageDialog(null, "Invalid Entry");
               } else if (flag != 1 && i > 3) {
                  System.exit(0);
               } else if (flag == 1) {
                  setVisible(false);
               }
            } catch (Exception excp) {
               JOptionPane.showMessageDialog(null, excp);
               System.exit(0);
            }
         }
      }
   }
}
