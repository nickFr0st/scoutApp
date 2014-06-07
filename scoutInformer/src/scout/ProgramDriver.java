package scout;

/**
 * Created by Malloch on 6/2/14
 */
public class ProgramDriver {
    public static void main(String args[]) {
//        DBConnector.connectToDB();
//
//        try {
//            Statement st = DBConnector.connection.createStatement();
//            ResultSet rs = st.executeQuery("Select * FROM scout");
//
//            Scout scout = new Scout();
//
//            while (rs.next()) {
//                scout.setFirstName(rs.getString(Scout.FIRST_NAME));
//                scout.setLastName(rs.getString(Scout.LAST_NAME));
//                scout.setSuffix(rs.getString(Scout.SUFFIX));
//                scout.setAge(rs.getInt(Scout.AGE));
//            }
//        } catch (SQLException e) {
//            System.err.println("not good from main");
//            System.err.println(e.getMessage());
//        }
//        DBConnector.closeConnection();

        PnlMain pnlMain = new PnlMain();
        pnlMain.setVisible(true);
    }
}
