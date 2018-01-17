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
}