package com.zeasn.union.db;


import com.sun.tools.javac.util.Pair;

import java.sql.*;

/**
 * 本类专门用于数据库的连接于关闭，在实例化本对象时，便意味着要进行数据库的开发<br>
 * 所以在本类的构造方法中要进行数据库驱动的加载和数据库连接对象的取得
 */
public class DatabaseConnection {
    private static final String DBS_NAME = "org.sqlite.JDBC";
    private static final String DBS_URL = "jdbc:sqlite:db/launcher.db";
    private static final String DBS_USER = "";
    private static final String DBS_PASSWORD = "";

    private Connection connection;

    public DatabaseConnection() {
        try {
            Class.forName(DBS_NAME);
            this.connection = DriverManager.getConnection(DBS_URL, DBS_USER, DBS_PASSWORD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //获取一个数据库的连接对象
    public Connection getConnection() {
        return this.connection;
    }

    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void test() throws SQLException {
       createTable("TEST",new IntType("ID",false,true),new StringType("name",false,false));
        String sql = "select * from by_speech_cmd";
        query(sql, new QueryCallback() {
            @Override
            public void onResultSet(ResultSet resultSet) {
                try {
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("input"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * CREATE TABLE TEST (ID INTEGER not NULL,name VARCHAR(50) not NULL,PRIMARY KEY (ID))
     * @param tableName
     * @param fieldNameTypes
     */
    public void createTable(String tableName, TableFieldType... fieldNameTypes){
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append(tableName).append(" (");
        TableFieldType primaryKeyField = null;
        for(TableFieldType fieldType:fieldNameTypes){
            sb.append(fieldType.toString()).append(",");
            if(fieldType.isPrimaryKey()){
                primaryKeyField = fieldType;
            }
        }
        if(primaryKeyField!=null){
            sb.append("PRIMARY KEY (").append(primaryKeyField.getFieldName()).append(")");
        }else{
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append(")");
        System.out.println("create Table SQL="+sb.toString());
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sb.toString());
            statement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(statement!=null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void query(String sql, QueryCallback callback) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            callback.onResultSet(resultSet);
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            if (statement != null) {
                statement.close();
            }
        }
    }



    public interface QueryCallback {
        void onResultSet(ResultSet resultSet);
    }
}
