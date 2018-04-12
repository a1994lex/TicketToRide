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

    public void loginUser(User user){

    }

    public void registerUser(User user){

    }

    public List<User> getUserList(){
        return null;
    }

    public void addCommandToGame(String gameID){//and a command?
//AND A COMMAND?
    }

    public void updateGameSnapshot(String gameID ){
        //where IS IT COMING FROM
    }

    public void clearCommands(String gameID){
        getGameDao().clearCommands(gameID);//IS THIS EVEN RIGHT
    }

    public List<ServerCommand> getCommandsByGameId(String gameID){
        return getGameDao().getCommandsByGamdId(gameID);
    }

    public ServerGame getSnapshotByGameId(String gameID){
        return getGameDao().getSnapshotByGameId(gameID);
    }

    public Map<String, List<ServerCommand>> getAllCommands(){
        return getGameDao().getAllCommands();
    }

    public List<ServerGame> getAllSnapshots(){
        return getGameDao().getAllSnapshots();
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