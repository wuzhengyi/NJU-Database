import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBCP {

    private Connection conn = null;
    protected Statement stmt;
    private BasicDataSource pool;

    public DBCP(){
        initDBCP();
    }

    public void getParameter(){
        System.out.println("最大空闲时间: " + pool.getMaxIdle());// 最大空闲时间。如果一个用户获取一个连接，不用，多长时间会被强行收回
        System.out.println("持等待回收时间: " + pool.getMaxWaitMillis());// 在抛出异常之前,池等待连接被回收的最长时间（当没有可用连接时）。设置为-1表示无限等待。
        System.out.println("初始化Connection数: " + pool.getInitialSize());// 初始化时有几个Connection
        System.out.println("最大Connection数: " + pool.getMaxTotal());// 最多能有多少个Connection
        System.out.println("----------------");
    }

    public ResultSet testDBCP(){
        ResultSet resultSet = null;
        synchronized (stmt){
            try {
                while(stmt.isClosed())
                    getConnection();
                resultSet = stmt.executeQuery("select * from staff;");//执行查询
            }
            catch (SQLException e ){
                e.printStackTrace();
            }
        }

        return resultSet;
    }

    // 纯Java方式设置参数，使用dbcp池
    public void initDBCP() {

        pool = new BasicDataSource();// 连接池
        pool.setUsername("root");
        pool.setPassword("wuzhengyi");
        pool.setDriverClassName("com.mysql.jdbc.Driver");
        pool.setUrl("jdbc:mysql://localhost:3306/lab3?useSSL=false");

        //可以我们自己设置池的相关参数，如最大连接数
//        pool.setMaxTotal(6);

        getConnection();
    }

    public void getConnection(){
        try {
            // 从它的池中获取连接
            conn = pool.getConnection();
            //语句
            if(!conn.isClosed())
                stmt = conn.createStatement();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public synchronized void endDBCP(){
        try {
            if(!conn.isClosed())
                conn.close();//关闭链接
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}