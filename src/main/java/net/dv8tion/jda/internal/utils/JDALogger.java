package net.dv8tion.jda.internal.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;
import org.spicord.log.SpicordLogger;

public class JDALogger {

    public static final String DISABLE_FALLBACK_PROPERTY_NAME = "net.dv8tion.jda.disableFallbackLogger";
    public static final boolean SLF4J_ENABLED = false;

    private static final Map<String, Logger> LOGS = new HashMap<>();

    public static Logger getLog(String name) {
        if (SpicordLogger.isLoaded()) {
            return LOGS.computeIfAbsent(name, n -> new SpicordLogger(n));
        }
        return NOPLogger.NOP_LOGGER;
    }

    public static Logger getLog(Class<?> clazz) {
        return getLog(clazz.getSimpleName());
    }

    private JDALogger() {}

    public static void setFallbackLoggerEnabled(boolean enabled) {}

    public static Object getLazyString(final LazyEvaluation lazyLambda) {
        return new Object() {

            public String toString() {
                try {
                    return lazyLambda.getString();
                } catch (Exception ex) {
                    StringWriter sw = new StringWriter();
                    ex.printStackTrace(new PrintWriter(sw));
                    return "Error while evaluating lazy String... " + sw;
                }
            }
        };
    }

    @FunctionalInterface
    public static interface LazyEvaluation {
        public String getString() throws Exception;
    }
}
