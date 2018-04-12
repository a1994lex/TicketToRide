import java.sql.Connection;

/**
 * Created by clairescout on 4/7/18.
 */

public interface IDataBase {

    Connection startTransaction();
    void endTransaction(Connection dbconnection);
    IUserDao getUserDao();
    IGameDao getGameDao();
    void setUp();
}
