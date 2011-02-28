package mappers;

import controllers.tabularasa.AbstractObjectValueMapper;
import models.tm.User;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class UserMapper extends AbstractObjectValueMapper<User> {

    public Object getByName(User user, String name) {
        if (name.equals("firstName")) {
            return user.authentication.firstName;
        } else if (name.equals("lastName")) {
            return user.authentication.lastName;
        } else throw new RuntimeException("not implemented");
    }
}
