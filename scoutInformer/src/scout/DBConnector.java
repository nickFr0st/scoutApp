package scout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by nmalloch on 6/5/2014.
 */
public class DBConnector {
    public void connectToDB() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:test2";
            Connection connection = DriverManager.getConnection(url);

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("Select * FROM scout");

            Scout scout = new Scout();

            while(rs.next()) {
                scout.setFirstName(rs.getString(Scout.FIRST_NAME));
                scout.setLastName(rs.getString(Scout.LAST_NAME));
                scout.setSuffix(rs.getString(Scout.SUFFIX));
                scout.setAge(rs.getInt(Scout.AGE));
            }

            connection.close();

            System.out.println("Success");
        } catch (Exception e) {
            System.err.println("not good");
            System.err.println(e.getMessage());
        }
    }
}
