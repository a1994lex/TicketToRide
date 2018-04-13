/**
 * Created by clairescout on 4/10/18.
 */

import com.groupryan.shared.JavaSerializer;
import com.groupryan.shared.commands.ServerCommand;
import com.groupryan.shared.commands.ServerCommandFactory;
import com.groupryan.shared.models.Game;
import com.groupryan.shared.models.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.groupryan.shared.utils.BLACK;
import static com.groupryan.shared.utils.RED;
import static org.junit.Assert.*;

public class JavaSerializerTest {

    ServerCommand serverEndTurnCommand;
    ServerCommand serverCreateGameCommand;


    @Before
    public void setup(){
        ServerCommandFactory scf = new ServerCommandFactory();
        serverEndTurnCommand = scf.createEndTurnCommand("clairescout");
        User u5 = new User("kate", "gammon");
        User u6 = new User("grace", "gammon");

        Game game3 = new Game("game3", "gameID3", 3);
        game3.addUser(u5, BLACK);
        game3.addUser(u6, RED);
        serverCreateGameCommand = scf.createCreateGameCommand(game3);

    }

    @After
    public void tearDown(){

    }

    @Test
    public void testSerializeAndDeserializeEndTurnCommand(){
        byte[] stream = JavaSerializer.getInstance().serializeObject(serverEndTurnCommand);
        ServerCommand afterCommand = (ServerCommand) JavaSerializer.getInstance().toObject(stream);
        Assert.assertEquals(serverEndTurnCommand.getClass(), afterCommand.getClass());

    }

    @Test
    public void testSerializeAndDeserializeCreateGameCommand(){
        byte[] stream = JavaSerializer.getInstance().serializeObject(serverCreateGameCommand);
        ServerCommand afterCommand = (ServerCommand) JavaSerializer.getInstance().toObject(stream);
        Assert.assertEquals(serverCreateGameCommand.getClass(), afterCommand.getClass());
    }


}
