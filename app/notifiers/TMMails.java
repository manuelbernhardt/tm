package notifiers;

/**
 * @author nikola
 */

import play.mvc.Mailer;

public class TMMails extends Mailer {

    public static void feedbackEmail(String name, String message, String location, String userAgent) {
        setSubject("[Oxiras][Feedback] Page: %s", location);
        addRecipient("feedback@oxiras.com");
        setFrom("info@oxiras.com");
        send(name, message, location, userAgent);
    }
}
