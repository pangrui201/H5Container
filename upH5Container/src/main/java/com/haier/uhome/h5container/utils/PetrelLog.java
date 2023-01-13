package com.haier.uhome.h5container.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class PetrelLog {
    private static Logger logger;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);

    public static Logger logger() {
        if (logger == null) {
            initialize();
        }
        return logger;
    }

    private static void initialize() {
        if (!isInitialized.compareAndSet(false, true)) {
            logger.warn("Petrel Logger had been initialized !");
            return;
        }
        logger = LoggerFactory.getLogger("[PetrelLog]");
    }
}