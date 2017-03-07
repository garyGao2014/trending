package com.gary.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * start
 */
public class Main {

    private static ScheduledThreadPoolExecutor singleThread = new ScheduledThreadPoolExecutor(1);
    private static Logger logger  = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        long period = 24 * 60 * 60 * 1000;// A Day
        logger.info("Start A Schedule !");
        singleThread.scheduleAtFixedRate(new TimeJob(), 1000, period, TimeUnit.MILLISECONDS);
    }
}
