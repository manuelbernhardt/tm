package notifiers;

/**
 * @author nikola
 */

import play.*;
import play.mvc.*;
import java.util.*;

public class TMMails extends Mailer{

    public static void feedbackEmail(String name, String message, String location, String headers){
        setSubject("[Oxiras][Feedback] Page: %s", location);
        addRecipient("feedback@oxiras.com");
        setFrom("info@oxiras.com");
        send(name, message, location, headers);
    }
}
