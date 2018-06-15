/*
 * Copyright (c) 2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.shanshu.ai.common;

/**
 * @author Frank Huang (runping@shanshu.ai)
 * @date 2018/06/14
 */

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfUtils {
    private static Logger logger = Logger.getLogger("my.log");
    private Properties properties;
    private static final String FILENAME = "/my.conf";
    public ConfUtils(){
        properties = new Properties();
        InputStream inputStream = this.getClass().getResourceAsStream(FILENAME);
        if (inputStream==null){
            logger.error(String.format("%s not found!", FILENAME));
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
