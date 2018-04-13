import com.groupryan.dbplugin.IDatabase;
import com.groupryan.dbplugin.IGameDao;
import com.groupryan.dbplugin.IUserDao;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

/**
 * Created by arctu on 4/11/2018.
 */

public class SqlDatabase implements IDatabase {
/**
 *
    public void loginUser(User user){
        getUserDao().loginUser(user);
    }

    public void registerUser(User user){
        getUserDao().registerUser(user);
    }

    public List<User> getUserList(){
        return getUserDao().getUsersList();
    }

    public void addCommandToGame(String gameID, byte[]command){//and a command?
        getGameDao().addCommandToGame(gameID, command);
    }

    public void updateGameSnapshot(String gameID ){
        //where IS IT COMING FROM
    }

    public void clearCommands(String gameID){
        getGameDao().clearCommands(gameID);//IS THIS EVEN RIGHT?
    }

    public List<byte[]> getCommandsByGameId(String gameID){
        return getGameDao().getCommandsByGamdId(gameID);
    }

    public byte[] getSnapshotByGameId(String gameID){
        return getGameDao().getSnapshotByGameId(gameID);
    }

    public Map<String, List<byte[]>> getAllCommands(){
        return getGameDao().getAllCommands();
    }

    public List<byte[]> getAllSnapshots(){
        return getGameDao().getAllSnapshots();
    }
*/

    int maxCommands;
    public SqlDatabase(int maxCommands){this.maxCommands=maxCommands;}

    @Override
    public Connection startTransaction() {
        Connection dbconnection=getConnection();
        try {
            dbconnection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbconnection;
    }

    @Override
    public void endTransaction(Connection dbconnection) {
        try {
            dbconnection.commit();
            dbconnection.close();
        } catch (SQLException e) {
            try {
                dbconnection.rollback();
                dbconnection.close();
            } catch (SQLException fe) {
                fe.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    @Override
    public IUserDao getUserDao() {
        return new SQLUserDAO();
    }

    @Override
    public IGameDao getGameDao() {
        return new SQLGameDAO();
    }

    @Override
    public void setUp() {
//what are we setting up

    }

    private Connection getConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection= null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}