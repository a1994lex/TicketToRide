import com.groupryan.shared.models.User;

import java.util.List;

/**
 * Created by clairescout on 4/7/18.
 */

public interface IUserDao {
    void loginUser(User user);
    void registerUser(User user);
    List<User> getUsersList();
}
