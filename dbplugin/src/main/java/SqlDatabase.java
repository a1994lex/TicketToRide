import com.groupryan.server.models.ServerGame;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.models.User;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import java.sql.Connection;

/**
 * Created by arctu on 4/11/2018.
 */

public class SqlDatabase implements IDataBase {
    //GameTable
    //GameCommandTable
    public SqlDatabase(){}
    public void loginUser(User user){

    }
    public void registerUser(User user){

    }
    public List<User> getUserList(){
        return null;
    }
    public void addCommandToGame(String gameID){//and a command?

    }
    public void updateGameSnapshot(String gameID ){

    }
    public void clearCommands(String gameID){

    }
    public List<ServerCommand> getCommandsByGameId(String gameID){
        return null;
    }
    public ServerGame getSnapshotByGameId(String gameID){
        return null;
    }
    public Map<String, List<ServerCommand>> getAllCommands(){
        return null;
    }
    public List<ServerGame> getAllSnapshots(){
        return null;
    }

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
        return null;
    }

    @Override
    public IGameDao getGameDao() {
        return null;
    }

    @Override
    public void setUp() {

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