package com.gary.search.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * properties util
 */
public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * load prop file
     * @param propertyFileName
     * @return
     */
    public static Properties loadProperties(String propertyFileName) {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            logger.info(PropertiesUtil.class.getResource("/").getPath());
            URL url = PropertiesUtil.class.getResource(propertyFileName);
            if (url != null) {
                in = new FileInputStream(url.getPath());
                if (in != null) {
                    prop.load(in);
                }
            }
        } catch (IOException e) {
            logger.error("load {} error!", propertyFileName);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close {} error!", propertyFileName);
                }
            }
        }
        return prop;
    }
}