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
    Connection connection;
    public SqlDatabase(){}
    public SqlDatabase(int maxCommands){this.maxCommands=maxCommands;}

    @Override
    public void startTransaction() {
        Connection dbconnection=getConnection();
        try {
            dbconnection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.connection=dbconnection;
    }

    @Override
    public void endTransaction() {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException fe) {
                fe.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    @Override
    public IUserDao getUserDao() {
        return new SQLUserDAO(connection);
    }

    @Override
    public IGameDao getGameDao() {
        return new SQLGameDAO(maxCommands, connection);
    }

    @Override
    public void setMaxCommands(int maxCommands) {
        this.maxCommands=maxCommands;

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