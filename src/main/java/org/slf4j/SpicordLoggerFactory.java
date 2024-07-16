package org.slf4j;

import org.slf4j.helpers.NOP_FallbackServiceProvider;

import net.dv8tion.jda.internal.utils.JDALogger;

public class SpicordLoggerFactory extends NOP_FallbackServiceProvider implements ILoggerFactory {

    public static void inject() {
        LoggerFactory.INITIALIZATION_STATE = LoggerFactory.SUCCESSFUL_INITIALIZATION;
        LoggerFactory.PROVIDER = new SpicordLoggerFactory();
    }

    @Override
    public Logger getLogger(String name) {
        return JDALogger.getLog(name);
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return this;
    }
}
