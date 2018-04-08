/**
 * Created by clairescout on 4/7/18.
 */

public interface IDataBase {

    void startTransaction();
    void endTransaction();
    IUserDao getUserDao();
    IGameDao getGameDao();
    void setUp();
}
