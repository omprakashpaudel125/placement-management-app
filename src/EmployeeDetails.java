import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class EmployeeDetails extends JFrame{
 public static void main(String args[]){
  new EmployeeDetails(0);
 }


 JTable tab;
 JPanel tpan=new JPanel();
 JPanel bpan=new JPanel();
 String header[]={"Name","Vacancy","Salary"};
 Object data[][]=new String[15][3];
 JButton check=new JButton("TRY");
 int i,j,ki;

 public EmployeeDetails(int k){
  this.ki=k;
  setVisible(true);
  setSize(600,600);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  getContentPane().add(tpan,BorderLayout.CENTER);
  getContentPane().add(bpan,BorderLayout.SOUTH);
  bpan.add(check);
  try{
   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
   Connection conn=DriverManager.getConnection("jdbc:odbc:go");
   Statement st=conn.createStatement();
   ResultSet rs=st.executeQuery("select * from Company where Field="+ki);
   while(rs.next()){
    for(j=0;j<=2;j++)
     data[i][j]=rs.getString(j+1);
    i++;
   }
  }
  catch(Exception e){
   JOptionPane.showMessageDialog(null,data[1][0]);
   System.exit(0);
  }
  tab=new JTable(data,header);
  tab.setPreferredScrollableViewportSize(new Dimension(600,600));
  JScrollPane sp=new JScrollPane(tab);
  tpan.add(sp);
  pack();
  
 }
} 