/*
 * Copyright (c) 2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.shanshu.ai.controller;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.shanshu.ai.common.PSqlUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Frank Huang (runping@shanshu.ai)
 * @date 2018/06/14
 */

@RestController
public class PSqlServiceImpl implements PSqlService {

    @RequestMapping(path = "/showTables", method = RequestMethod.GET)
    @Override
    public @ResponseBody Map<String, List> showTables(@RequestParam("database") String database) {
        Map<String, List>  result = new HashMap<>();
        try {
            List<String> tables = PSqlUtils.showTables(database);
            result.put(database, tables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(path = "/showDatabases", method = RequestMethod.GET)
    @Override
    public @ResponseBody List showDatabases() {
        List result = null;
        try{
            result = PSqlUtils.showDatabases();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(path = "/showColumns", method = RequestMethod.GET)
    @Override
    public @ResponseBody Map<String, Map> showColumns(@RequestParam("database") String database, @RequestParam("tablename") String table) {
//        JSONObject result = new JSONObject();
        Map<String, Map> result = new HashMap<>();
        try{
            Map columns = PSqlUtils.getColumns(database, table);
            result.put(table, columns);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(path = "/sample", method = RequestMethod.GET)
    @Override
    public @ResponseBody List sample(@RequestParam("database") String database, @RequestParam String tablename, @RequestParam String rows){
        List result = null;
        if (rows==null){
            rows = "20";
        }
        try{
            result = PSqlUtils.sample(database, tablename, rows);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
