import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class SQLApp {
    static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    static final String USER = "root";
    static final String PASS = "123456";
    static int points = 0;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            String ques = "select * from questions";
            ResultSet rs = stmt.executeQuery(ques);
            Scanner in = new Scanner(System.in);
            System.out.println("Each question will have 3 option. Type the index of correct one.\nQuiz: ");
            int i = 1;
            while (rs.next()) {
                Statement oStatement = conn.createStatement();
                String opts = "select * from opts where q_id = " + rs.getInt("id");
                ResultSet tempR = oStatement.executeQuery(opts);
                System.out.print(String.format("Q%d. %s\n", i, rs.getString("question")));
                int j = 1;
                String correctAnswer = rs.getString("answer");
                HashMap<Integer, String> options = new HashMap<Integer, String>();
                while (tempR.next()) {
                    System.out.print(String.format("\t%d. %s\n", j, tempR.getString("opt")));
                    options.put(j, tempR.getString("opt"));
                    j++;
                }
                System.out.print("Ans: ");
                int ans = in.nextInt();
                if (options.get(ans).equals(correctAnswer)) {
                    System.out.println("Correct!\n");
                    points++;
                } else {
                    System.out.println("Wrong!\n");
                }
                i++;
                tempR.close();
                oStatement.close();
            }
            System.out.println("Your score is: " + points);
            rs.close();
            in.close();
            stmt.close();
            conn.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}