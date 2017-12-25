import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.UserInfo;

public class JDBC {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    /**
     * 写一个连接数据库的方法
     */
    public Connection getConnection(){
        String url="jdbc:mysql://localhost:7797/lab3";
        String userName="root";
        String password="wuzhengyi00";
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
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
                System.out.println( "connection fail");
            e.printStackTrace();
        }
        return conn;
    }
    /**
     * 写一个查询数据库语句的方法
     */
    public void QuerySql(){
        //1、执行静态SQL语句。通常通过Statement实例实现。   
        // 2、执行动态SQL语句。通常通过PreparedStatement实例实现。   
        // 3、执行数据库存储过程。通常通过CallableStatement实例实现。
        System.out.println("query");
        UserInfo u;
//        j.Connection();
        String sql="select * from userInfo";
        try {
            conn=getConnection();//连接数据库
            ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
            rs=ps.executeQuery(sql);  // 3.ִ执行SQL语句
            // 4.处理结果集
            while(rs.next()){
                u=new UserInfo();
                u.setUserId(rs.getInt("userId"));
                u.setUserName(rs.getString("userName"));
                u.setPassword(rs.getString("password"));
                u.setRemark(rs.getString("remark"));
                System.out.println("uesrName"+u.getUserName());
            }
            System.out.println(rs.next());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //释放资源
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    @SuppressWarnings("null")
    public int add(UserInfo user){
        UserInfo u =new UserInfo();
        int row=0;
//        j.Connection();
        String sql="insert into userInfo(userName,password) values(?,?)";
        try {
            conn=getConnection();//连接数据库
            ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
//            rs=ps.executeQuery();  // 3.ִ执行SQL语句,緊緊用于查找語句
            //sql語句中寫了幾個字段，下面就必須要有幾個字段
             ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            // 4.处理结果集
            row=ps.executeUpdate();
            System.out.println(row+user.getUserName()+user.getPassword());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        return row;
    }
    /**
     * @return修改数据
     */
    public int update(UserInfo user){
        UserInfo u;
        int row=0;
//        j.Connection();
        String sql="update userInfo set userName=?,password=? where userId=?";
        try {
            conn=getConnection();//连接数据库
            ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
//            rs=ps.executeQuery(sql);  // 3.ִ执行SQL语句,緊緊用于查找語句
            //sql語句中寫了幾個字段，下面就必須要有幾個字段
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getUserId());
            // 4.处理结果集
            row=ps.executeUpdate();
            System.out.println(row);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //释放资源
            try {
//                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return row;
    }
    /**
     * @return删除操作
     */
    public int delete(UserInfo user){
        UserInfo u;
        int row=0;
//        j.Connection();
        String sql="delete from userInfo where userId=?";
        try {
            conn=getConnection();//连接数据库
            ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
//            rs=ps.executeQuery(sql);  // 3.ִ执行SQL语句,緊緊用于查找語句
            //sql語句中寫了幾個字段，下面就必須要有幾個字段
            ps.setInt(1, user.getUserId());
            // 4.处理结果集
            row=ps.executeUpdate();
            System.out.println(row);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //释放资源【執行完sql要記得釋放資源】
            try {
//                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return row;
    }
    public static void main(String[] args) {
        JDBC j=new JDBC();
        j.getConnection();
//        j.QuerySql();//在控制台顯示出查找方法
//        UserInfo u=new UserInfo();
//        u.setUserId(5);
//        u.setUserName("cool");
//        u.setPassword("123abc");
//        j.update(u);////在控制台顯示出修改方法
    }
}