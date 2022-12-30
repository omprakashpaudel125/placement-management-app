import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class App extends JFrame implements ActionListener {

    static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    static final String USER = "root";
    static final String PASS = "123456";

    JPanel p = new JPanel();
    JPanel q = new JPanel();
    JTextField tname, tage, textra, tusr, tpwd;
    JLabel lname, lage, lsex, ledu, lextra, lexpect, lsal, Blank, Blank1, Blank2, Blank3, Blank4, lusr, lpwd;
    JRadioButton rbml, rbfml;
    JComboBox cbedu, cbsal;
    JRadioButton it, civil, mech;
    JButton bSub, bRes;
    String sex = new String();
    int field;

    public static void main(String args[]) {
        new App();
    }

    public App() {

        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(q, BorderLayout.SOUTH);

        setTitle("Placement Office");

        lusr = new JLabel("Login Name: ");
        lpwd = new JLabel("Password: ");
        lname = new JLabel("Name: ");
        lage = new JLabel("Age: ");
        lsex = new JLabel("Sex: ");
        ledu = new JLabel("Education: ");
        lextra = new JLabel("Extra: ");
        lexpect = new JLabel("Field: ");
        lsal = new JLabel("Minimum Expected Salary: ");

        tusr = new JTextField(10);
        tpwd = new JTextField(10);
        tname = new JTextField(20);
        tage = new JTextField(2);
        textra = new JTextField(15);

        rbml = new JRadioButton("Male");
        rbfml = new JRadioButton("Female");
        ButtonGroup bgrp = new ButtonGroup();
        bgrp.add(rbml);
        bgrp.add(rbfml);

        cbedu = new JComboBox();
        cbedu.addItem("Diploma");
        cbedu.addItem("Degree");
        cbedu.addItem("Post-Graduate");

        cbsal = new JComboBox();
        cbsal.addItem("5,000");
        cbsal.addItem("10,000");
        cbsal.addItem("15,000");
        cbsal.addItem("20,000");

        it = new JRadioButton("Software Engg.");
        civil = new JRadioButton("Civil Engg.");
        mech = new JRadioButton("Mechanical Engg.");
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(it);
        bg2.add(civil);
        bg2.add(mech);

        bSub = new JButton("Submit");
        bRes = new JButton("Reset");

        p.setLayout(new GridLayout(12, 2));
        Blank = new JLabel("");
        Blank1 = new JLabel("");
        Blank2 = new JLabel("");
        Blank3 = new JLabel("");
        Blank4 = new JLabel("");

        p.add(lusr);
        p.add(tusr);

        p.add(lpwd);
        p.add(tpwd);

        p.add(lname);
        p.add(tname);

        p.add(lage);
        p.add(tage);

        p.add(lsex);
        p.add(rbml);

        p.add(Blank);
        p.add(rbfml);

        p.add(ledu);
        p.add(cbedu);

        p.add(lextra);
        p.add(textra);

        p.add(lexpect);
        p.add(it);

        p.add(Blank4);
        p.add(civil);

        p.add(Blank1);
        p.add(mech);

        p.add(lsal);
        p.add(cbsal);

        q.add(bSub);
        q.add(bRes);

        bSub.addActionListener(this);
        bRes.addActionListener(this);
        setVisible(true);
        setBounds(200, 200, 500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == bRes) {
            tname.setText("");
            tage.setText("");
            cbedu.setSelectedItem("Diploma");
            textra.setText("");
            tusr.setText("");
            tpwd.setText("");
        }

        else {

            if (it.isSelected())
                field = 1;
            else if (civil.isSelected())
                field = 2;
            else if (mech.isSelected())
                field = 3;

            if (rbml.isSelected())
                sex = "MALE";
            else if (rbfml.isSelected())
                sex = "FEMALE";

            try {
                int age = Integer.parseInt(tage.getText());
            } catch (Exception ex) {
                tage.setText("");
            }

            if (tname.getText().equals("") | textra.getText().equals("") | tage.getText().equals("") | sex.equals("")
                    | field == 0)
                JOptionPane.showMessageDialog(null, "Please Fill in All Valid Entries!!");
            else {

                String sal = (String) cbsal.getSelectedItem();
                String edu = (String) cbedu.getSelectedItem();

                try {

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement st = conn.createStatement();

                    st.executeUpdate("insert into applicant (Name, Age, Sex, Education, Extra, Salary, Field, Login, Pwd) values('" + tname.getText() + "','" + tage.getText() + "','"
                            + sex + "','" + edu + "','" + textra.getText() + "','" + sal + "','" + field + "','"
                            + tusr.getText() + "','" + tpwd.getText() + "')");
                    conn.close();
                    String msg = "Your Details are Stored. Login again to View Related Companies!  Thank You!!";
                    JOptionPane.showMessageDialog(null, msg);
                    new Login();
                    setVisible(false);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, tname.getText() + " : " + exc);
                    System.exit(0);
                }
            }
        }
    }
}
