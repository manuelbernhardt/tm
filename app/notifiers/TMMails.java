package notifiers;

/**
 * @author nikola
 */

import play.*;
import play.mvc.*;
import java.util.*;

public class TMMails extends Mailer{

    public static void feedbackEmail(String message, String location){
        setSubject("[Oxiras][Feedback] Page: %s", location);
        addRecipient("feedback@oxiras.com");
        setFrom("info@oxiras.com");
        send(message, location);
    }
}
