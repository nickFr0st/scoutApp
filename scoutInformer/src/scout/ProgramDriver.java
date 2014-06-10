package scout;

import util.DBConnector;
import util.OpeningScene;

/**
 * Created by Malloch on 6/2/14
 */
public class ProgramDriver {
    public static void main(String args[]) {
        DBConnector connector = new DBConnector();
        if (!connector.checkForDBConnection()) {
            OpeningScene scene = new OpeningScene();
            scene.setVisible(true);
        }


//        PnlMain pnlMain = new PnlMain();
//        pnlMain.setVisible(true);
    }
}
