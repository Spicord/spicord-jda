package org.spicord.log;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import org.slf4j.Marker;
import org.slf4j.SpicordLoggerFactory;
import org.slf4j.helpers.LegacyAbstractLogger;

public final class SpicordLogger extends LegacyAbstractLogger {

    private static final long serialVersionUID = 1L;

    private static Logger log;

    static {
        SpicordLoggerFactory.inject();
    }

    public static boolean isLoaded() {
        return log != null;
    }

    public static void setLogger(Logger logger) {
        SpicordLogger.log = logger;
    }

    private final String prefix;

    public SpicordLogger(String name) {
        this.prefix = "[JDA:" + name + "] ";
    }

    @Override
    public boolean isDebugEnabled() {
        return log.getLevel().intValue() <= Level.FINE.intValue();
    }

    @Override
    public boolean isTraceEnabled() {
        return log.getLevel().intValue() <= Level.FINER.intValue();
    }

    @Override
    public boolean isInfoEnabled() {
        return log.getLevel().intValue() <= Level.INFO.intValue();
    }

    @Override
    public boolean isWarnEnabled() {
        return log.getLevel().intValue() <= Level.WARNING.intValue();
    }

    @Override
    public boolean isErrorEnabled() {
        return log.getLevel().intValue() <= Level.SEVERE.intValue();
    }

    @Override
    protected String getFullyQualifiedCallerName() {
        return null;
    }

    @Override
    protected void handleNormalizedLoggingCall(org.slf4j.event.Level level, Marker marker, String format, Object[] arguments, Throwable t) {
        final boolean hasArguments = arguments != null && arguments.length > 0;

        final String message;
        if (hasArguments) {
            message = prefix + format(format, arguments);
        } else {
            message = prefix + format;
        }

        switch (level) {
        case DEBUG:
            log.fine(message);
            break;
        case ERROR:
            log.severe(message);
            break;
        case INFO:
            log.info(message);
            break;
        case TRACE:
            log.finer(message);
            break;
        case WARN:
            log.warning(message);
            break;
        }

        if (t != null) {
            t.printStackTrace(System.err);
        }
    }

    private static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        return object.toString();
    }

    private static String format(String format, Object... arguments) {
        for (int i = 0; i < arguments.length; i++) {
            format = format.replaceFirst("\\{\\}", Matcher.quoteReplacement(toString(arguments[i])));
        }
        return format;
    }
}
