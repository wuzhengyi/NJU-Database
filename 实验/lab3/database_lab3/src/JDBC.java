<<<<<<< HEAD
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import entity.UserInfo;

public class JDBC {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    private Statement statement;
    /**
     * 写一个连接数据库的方法
     */
    public Connection getConnection(){
        String url="jdbc:mysql://localhost:3306/lab3";
        String userName="root";
        String password="wuzhengyi";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("找不到驱动！");
            e.printStackTrace();
        }
        try {
            conn=DriverManager.getConnection(url, userName, password);
            if(conn!=null){
                System.out.println("connection successful");
                statement = conn.createStatement();       //创建Statement对象
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
                System.out.println( "connection fail");
            e.printStackTrace();
        }
        return conn;
    }

    /*
     * 根据sql查询数据库，返回一个结果集
     * 输    入:SQL语句
     * 返回值:ResultSet 查询结果
     */
    public ResultSet querySql(String sql) {
        ResultSet result = null;

        try
        {
            result = statement.executeQuery(sql);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /*
 * 打印UserInfo表的数据
 * 输    入:结果集(数据表)
 * 返回值:空
 */
    public void printStaff(ResultSet result) {
        try
        {
            while(result.next()) {
                System.out.println("SNAME:" + result.getString(1)
                        + " SNO:" + result.getString(2)
                        + " AGE:" + result.getString(3)
                        + " SALARY:" + result.getString(4)
                        + " DNO:" + result.getString(5)
                );
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
   * 执行数据操作
   * 输    入:SQL语句
   * 返回值:空
   */
    public void executeSql(String sql) {
        try
        {
            statement.execute(sql);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        JDBC j=new JDBC();
//        j.getConnection();
//        /*
//        *   SELECT * FROM Staff;
//        *   SELECT * FROM Project;
//        *   SELECT * FROM Department;
//        *   SELECT * FROM Work;
//        */
//        System.out.println("\n显示Staff表所有数据:");
//        String sql = "SELECT * FROM Staff;";
//        ResultSet result = j.querySql(sql);//在控制台顯示出查找方法
//        j.printStaff(result);
//
//        System.out.println("\n删除SNO为128的吴一楠:");
//        j.executeSql("DELETE FROM Staff WHERE SNO = 128;");
//        result = j.querySql(sql);
//        j.printStaff(result);
//
//        System.out.println("\n添加('吴一楠',128,21,3):");
//        j.executeSql("INSERT INTO Staff(SNAME,SNO,AGE,DNO) VALUES ('吴一楠',128,21,3);");
//        result = j.querySql(sql);
//        j.printStaff(result);
//
//        System.out.println("\n添加('无名字',110,1,100000,2):");
//        j.executeSql("INSERT INTO Staff VALUES ('无名字',110,1,100000,2);");
//        result = j.querySql(sql);
//        j.printStaff(result);
//
//        System.out.println("\n将SNO=129的改为127:");
//        j.executeSql("update Staff set SNO = 127 where SNO=129;");
//        result = j.querySql(sql);
//        j.printStaff(result);
//        System.out.println();
//
//    }
=======
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import entity.UserInfo;

public class JDBC {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    private Statement statement;
    /**
     * connection database
     */
    public Connection getConnection(){
        String url="jdbc:mysql://localhost:3306/lab3?useSSL=false";
        String ssl = "useSSL=false";
        String userName="root";
        String password="wuzhengyi";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("找不到驱动！");
            e.printStackTrace();
        }
        try {
            conn=DriverManager.getConnection(url,userName, password);
            if(conn!=null){
//                System.out.println("connection successful");
                statement = conn.createStatement();       //create Statement
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
                System.out.println( "connection fail");
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection(){
        try {
            conn.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet querySql(String sql) {
        ResultSet result = null;

        try
        {
            result = statement.executeQuery(sql);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public static void printStaff(ResultSet result) {
        try
        {
            while(result.next()) {
                System.out.println("SNAME:" + result.getString(1)
                        + " SNO:" + result.getString(2)
                        + " AGE:" + result.getString(3)
                        + " SALARY:" + result.getString(4)
                        + " DNO:" + result.getString(5)
                );
            }
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void executeSql(String sql) {
        try
        {
            statement.execute(sql);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void testJDBC(){
        System.out.println("\n Create Staff Table:");
        executeSql("CREATE TABLE Staff\n" +
                "(\n" +
                "    SNAME char(8) NOT NULL,\n" +
                "    SNO int NOT NULL,\n" +
                "    AGE int DEFAULT 0, -- 默认值约束\n" +
                "    SALARY int,\n" +
                "    DNO int, -- FOREIGN KEY REFERENCES Department(DNO) --外键约束，之后再加\n" +
                "    PRIMARY KEY (SNO)\n" +
                ");");
        String sql = "SELECT * FROM Staff;";
        ResultSet result = querySql(sql);//在控制台顯示出查找方法
        printStaff(result);

        System.out.println("\n Insert to Staff Table:");
        executeSql("INSERT INTO Staff\n" +
                "VALUES \n" +
                "('谢敏辉',131,21,-5,1),\n" +
                "('吴政亿',129,20,1000,2),\n" +
                "('吴玉明',130,21,100,3);");
        sql = "SELECT * FROM Staff;";
        result = querySql(sql);//在控制台顯示出查找方法
        printStaff(result);

        System.out.println("\n向Staff表中插入数据:");
        executeSql("INSERT INTO Staff(SNAME,SNO,AGE,DNO)\n" +
                "VALUES\n" +
                "('吴一楠',128,21,3),\n" +
                "('许丽君',133,21,2);");
        sql = "SELECT * FROM Staff;";
        result = querySql(sql);//在控制台顯示出查找方法
        printStaff(result);

        System.out.println("\n显示Staff表所有数据:");
        sql = "SELECT * FROM Staff;";
        result = querySql(sql);//在控制台顯示出查找方法
        printStaff(result);

        System.out.println("\n删除SNO为128的吴一楠:");
        executeSql("DELETE FROM Staff WHERE SNO = 128;");
        result = querySql(sql);
        printStaff(result);

        System.out.println("\n添加('吴一楠',128,21,3):");
        executeSql("INSERT INTO Staff(SNAME,SNO,AGE,DNO) VALUES ('吴一楠',128,21,3);");
        result = querySql(sql);
        printStaff(result);

        System.out.println("\n添加('无名字',110,1,100000,2):");
        executeSql("INSERT INTO Staff VALUES ('无名字',110,1,100000,2);");
        result = querySql(sql);
        printStaff(result);

        System.out.println("\n将SNO=129的改为127:");
        executeSql("update Staff set SNO = 127 where SNO=129;");
        result = querySql(sql);
        printStaff(result);
        System.out.println();
    }

//    public static void main(String[] args) {
//        JDBC j=new JDBC();
//        j.getConnection();
//        j.teztJDBC();
//
//        try{
//            DBCP d = new DBCP();
//            d.getParameter();
//            d.testDBCP();
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//    }

>>>>>>> 6a9a379cff17a233d35fd6ccd0cab0152b3a8095
}