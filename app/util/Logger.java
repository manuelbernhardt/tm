package util;

/**
 * Logger with categories.
 *
 * TODO append category name before each line of a message.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class Logger {

    public enum LogType {SECURITY, AUTHENTICATION, ADMIN, TECHNICAL}

    public static void debug(String message, Object... args) {
        play.Logger.debug(message, args);
    }

    public static void debug(Throwable e, String message, Object... args) {
        play.Logger.debug(e, message, args);
    }

    public static void info(LogType type, String message, Object... args) {
        play.Logger.info(message, args);
    }

    public static void info(LogType type, Throwable e, String message, Object args) {
        play.Logger.info(e, message, args);
    }

    public static void warn(LogType type, String message, Object... args) {
        play.Logger.warn(message, args);
    }

    public static void warn(LogType type, Throwable e, String message, Object... args) {
        play.Logger.warn(e, message, args);
    }

    public static void error(LogType type, String message, Object... args) {
        play.Logger.error(message, args);
    }

    public static void error(LogType type, Throwable e, String message, Object... args) {
        play.Logger.error(e, message, args);
    }

    public static void fatal(String message, Object... args) {
        play.Logger.error(message, args);
    }

    public static void fatal(Throwable e, String message, Object... args) {
        play.Logger.error(e, message, args);
    }

}
