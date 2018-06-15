/*
 * Copyright (c) 2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.shanshu.ai.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Frank Huang (runping@shanshu.ai)
 * @date 2018/06/14
 */
public class PSqlUtils {
    private static Connection conn = null;
    private static String user;

    /**
     * default initialization
     * */
    public static void initConn(){
        initConn("postgres");
    }

    /**
     * should be called before changing database
     * */
    public static void initConn(String database) {
        if (conn!=null){
            closeConn();
        }
        try{
            Class.forName("org.postgresql.Driver");
            ConfUtils conf = new ConfUtils();
            String url = conf.getProperty("URL") + database;
            user = conf.getProperty("USER");
            String password = conf.getProperty("PASSWORD");
            PSqlUtils.setConn(DriverManager.getConnection(url, user, password));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setConn(Connection conn) {
        PSqlUtils.conn = conn;
    }

    /**
     * show all databases in current database
     * */
    public static List<String> showDatabases() throws SQLException{
        if (conn==null){
            initConn();
        }
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM pg_database where datistemplate=false");
        List<String> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(resultSet.getString("datname"));
        }
        st.close();
        return result;
    }

    public static List<String> showTables(String database) throws SQLException {
        initConn(database);
        return showTables();
    }

    /**
     * show all tables in the current database of postgresql
     * */
    public static List<String> showTables() throws SQLException {
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'");
        List<String> list = new ArrayList<>();

        while (result.next()){
            list.add(result.getString("table_name"));
        }
        st.close();
        return list;
    }

    /**
     * sample data for 50 entry
     * */
    public static List sample(String database, String tableName, String rows) throws SQLException {
        // think of how to optimize not to connect everytime
        if (conn==null){
            initConn(database);
        }
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(String.format("SELECT * FROM %s LIMIT %s", tableName, rows));
//        int columns = resultSet.getMetaData().getColumnCount();
        Set columnNames = getColumns(tableName).keySet();
        List<Map> result = new ArrayList<>();
        resultSet.getMetaData();
        while (resultSet.next()){
            Map<Object, String> row = new HashMap<>();
            for (Object columnName : columnNames){
                row.put(columnName, resultSet.getString(columnName.toString()));
            }
            result.add(row);
        }
        return result;
    }


    /**
     * show table's columns
     * */
    public static Map<String, String> getColumns(String tablename) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rsColumns =  metaData.getColumns(null, null, tablename, null);
        Map<String, String> result = new HashMap<>();
        while (rsColumns.next()) {
            result.put(rsColumns.getString("COLUMN_NAME")
                            ,rsColumns.getString("TYPE_NAME"));
        }
        return result;
    }


    public static Map<String, String> getColumns(String database, String tablename) throws SQLException {
        initConn(database);
        return getColumns(tablename);
    }

}
