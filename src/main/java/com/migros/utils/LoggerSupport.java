package com.migros.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface LoggerSupport {
    /**
     * @return Logger
     */
    default Logger getLogger() {
        return LogManager.getLogger(getClass().getName());
    }
}
