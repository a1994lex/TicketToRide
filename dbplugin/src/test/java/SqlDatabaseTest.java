import com.groupryan.dbplugin.SqlDatabase;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by arctu on 4/11/2018.
 */
public class SqlDatabaseTest {
    @Test
    public void startTransaction() throws Exception {
        SqlDatabase s= new SqlDatabase();
        s.startTransaction();
        s.setMaxCommands(4);
       // s.getGameDao().addCommandToGame("test",[a,b,c] ,-1)
    }

}