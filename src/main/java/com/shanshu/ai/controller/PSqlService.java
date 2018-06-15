/*
 * Copyright (c) 2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.shanshu.ai.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author Frank Huang (runping@shanshu.ai)
 * @date 2018/06/14
 */


public interface PSqlService {
    Map<String, List> showTables(String database);
    List showDatabases();
    Map<String, Map>  showColumns(String database, String table);
    List sample(String database, String tablename, String rows);
}
