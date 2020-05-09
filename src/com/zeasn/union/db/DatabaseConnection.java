package com.zeasn.union.db;


import com.zeasn.union.data.DataMgr;
import com.zeasn.union.data.LauncherProject;
import com.zeasn.union.data.TextStyle;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 本类专门用于数据库的连接于关闭，在实例化本对象时，便意味着要进行数据库的开发<br>
 * 所以在本类的构造方法中要进行数据库驱动的加载和数据库连接对象的取得
 */
public class DatabaseConnection {
    private static final String DBS_NAME = "org.sqlite.JDBC";
    private static final String DBS_URL = "jdbc:sqlite:db/launcher.db";
    private static final String DBS_USER = "";
    private static final String DBS_PASSWORD = "";
    private static final String PROJECT_TABLE = "project";
    private static final String TEXT_STYLE_TABLE = "text_style";
    private static DatabaseConnection instance;

    private Connection connection;

    private DatabaseConnection() {
        try {
            Class.forName(DBS_NAME);
            this.connection = DriverManager.getConnection(DBS_URL, DBS_USER, DBS_PASSWORD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
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
        createTable("TEST", new IntType("ID", false, true), new StringType("name", false, false));
    }

    /**
     * CREATE TABLE TEST (ID INTEGER not NULL,name VARCHAR(50) not NULL,PRIMARY KEY (ID))
     * 创建数据库表
     *
     * @param tableName
     * @param fieldNameTypes
     */
    public void createTable(String tableName, TableFieldType... fieldNameTypes) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append(tableName).append(" (");
        TableFieldType primaryKeyField = null;
        for (TableFieldType fieldType : fieldNameTypes) {
            sb.append(fieldType.toString()).append(",");
            if (fieldType.isPrimaryKey()) {
                primaryKeyField = fieldType;
            }
        }
        if (primaryKeyField != null) {
            sb.append("PRIMARY KEY (").append(primaryKeyField.getFieldName()).append(")");
        } else {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        System.out.println("create Table SQL=" + sb.toString());
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sb.toString());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行数据库查询
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public PreparedStatement query(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return statement;
    }

    /**
     * 表是否存在
     *
     * @param tableName
     * @return
     */
    public boolean tableExist(String tableName) {
        String sql = "select * from " + tableName;
        try {
            PreparedStatement statement = query(sql);
            statement.executeQuery();
            statement.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public void createTableByModel(Object object) {
        String tableName = object.getClass().getSimpleName();
        if (!tableExist(tableName)) {
            TableFieldType[] fields = getObjectFieldType(object);
//            createTable(tableName,fields);
        }
    }

    private TableFieldType[] getObjectFieldType(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        TableFieldType[] tableFieldTypes = new TableFieldType[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String type = field.getType().getSimpleName();
            String name = field.getName();
            System.out.println("type=" + type + ",name=" + name);
        }
        return tableFieldTypes;
    }

    //============================================================   创建表  =========================================================================

    private void createLauncherProjectTable() {
        if (!tableExist(PROJECT_TABLE)) {
            createTable(PROJECT_TABLE,
                    new StringType("PROJECT_NAME", false, false),
                    new StringType("PROJECT_DIR", false, false),
                    new StringType("TEMPLATE_DIR", true, false),
                    new IntType("LAST_EDIT_TIME", true, false));
        }
    }

    /**
     * private TypeFace typeFace;
     * private int textSize;
     * private String textColor;
     * private int letterSpacing;
     * private int lineSpacing;
     */
    private void createTextStyleTable() {
        if (!tableExist(TEXT_STYLE_TABLE)) {
            createTable(TEXT_STYLE_TABLE,
                    new StringType("PROJECT_NAME", false, false),
                    new StringType("NAME", false, true),
                    new StringType("TYPE_FACE", true, false),
                    new IntType("TEXT_SIZE", true, false),
                    new IntType("LETTER_SPACING", true, false),
                    new IntType("LINE_SPACING", true, false),
                    new StringType("TEXT_COLOR", true, false));
        }
    }

    //============================================================   插入数据  =========================================================================

    /**
     * 如数据库不存在此工程则加入进去
     *
     * @param project
     */
    public void insertLauncherProject(LauncherProject project) {
        createLauncherProjectTable();
        if (!launcherProjectExist(project)) {
            String sql = "insert into " + PROJECT_TABLE + " values(?,?,?,?)";
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sql);
                statement.setString(1, project.getName());
                statement.setString(2, project.getProjectDir());
                statement.setString(3, project.getTemplateDir());
                statement.setLong(4, System.currentTimeMillis());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(statement!=null){
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void updateLauncherProject(LauncherProject project){
        if(project!=null){
            String sql = "update " + PROJECT_TABLE + " set LAST_EDIT_TIME=? where PROJECT_NAME=? and  PROJECT_DIR=?";
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sql);
                statement.setLong(1, System.currentTimeMillis());
                statement.setString(2, project.getName());
                statement.setString(3, project.getProjectDir());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(statement!=null){
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean insertTextStyle(TextStyle textStyle) {
        createTextStyleTable();
        String sql = "insert into " + TEXT_STYLE_TABLE + " values (?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, DataMgr.getInstance().getProject().getProjectFilePath());
            statement.setString(2, textStyle.getName());
            statement.setString(3, textStyle.getTypeFace() == null ? "" : textStyle.getTypeFace().name());
            statement.setInt(4, textStyle.getTextSize());
            statement.setInt(5, textStyle.getLetterSpacing());
            statement.setInt(6, textStyle.getLineSpacing());
            statement.setString(7, (textStyle.getTextColor()!=null&&textStyle.getTextColor().length()>0)?textStyle.getTextColor():"");
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private boolean launcherProjectExist(LauncherProject project) {
        String sql = "select * from " + PROJECT_TABLE + " where PROJECT_NAME=? and PROJECT_DIR=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getProjectDir());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 从数据库查询工程列表
     *
     * @return
     */
    public List<LauncherProject> getLauncherProjects() {
        String sql = "select * from " + PROJECT_TABLE;
        List<LauncherProject> launcherProjects = new ArrayList<>();
        try {
            PreparedStatement statement = query(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String projectDir = resultSet.getString("PROJECT_DIR");
                String projectName = resultSet.getString("PROJECT_NAME");
                String templateDir = resultSet.getString("TEMPLATE_DIR");
                long lastEditTime = resultSet.getLong("LAST_EDIT_TIME");
                LauncherProject launcherProject = new LauncherProject(projectName);
                launcherProject.setRootDir(projectDir);
                launcherProject.setTemplateDir(templateDir);
                launcherProjects.add(launcherProject);
                launcherProject.setLastEditTime(lastEditTime);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return launcherProjects;
    }

    /**
     * 从数据库查询工程列表
     *createTable(TEXT_STYLE_TABLE, new StringType("NAME", false, true),
     *                     new StringType("TYPE_FACE", true, false),
     *                     new IntType("TEXT_SIZE", true, false),
     *                     new IntType("LETTER_SPACING", true, false),
     *                     new IntType("LINE_SPACING", true, false),
     *                     new StringType("TEXT_COLOR", true, false));
     * @return
     */
    public List<TextStyle> getTextStyles() {
        String sql = "select * from " + TEXT_STYLE_TABLE +" where PROJECT_NAME=?";
        List<TextStyle> textStyles = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, DataMgr.getInstance().getProject().getProjectFilePath());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                TextStyle.TypeFace typeFace = TextStyle.TypeFace.valueOf(resultSet.getString("TYPE_FACE"));
                int textSize = resultSet.getInt("TEXT_SIZE");
                int letterSpacing = resultSet.getInt("LETTER_SPACING");
                int lineSpacing = resultSet.getInt("LINE_SPACING");
                String textColor = resultSet.getString("TEXT_COLOR");
                TextStyle textStyle = new TextStyle(name,typeFace,textSize,textColor,letterSpacing,lineSpacing);
                textStyles.add(textStyle);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return textStyles;
    }


}
