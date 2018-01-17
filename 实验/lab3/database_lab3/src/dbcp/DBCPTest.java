package dbcp;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dbcp.DBConn;
import org.junit.Test;

public class DBCPTest {

    //测试,每写一条数据前,就新建一个连接
    @Test
    public void testWriteDBByEveryConn() throws Exception{
        for(int i = 0; i < 2000; i++){
            writeDBByEveryConn(i);
        }
        System.out.println("DONE");

    }

    //测试,使用连接池,每写一条数据前,从连接池中获取一个连接
    @Test
    public void testWriteDBByDBCP() throws Exception{
        for(int i = 0; i < 2000; i++){
            writeDBByDBCP(i);
        }
        System.out.println("DONE");
    }

    //测试,只建一条连接,写入所有数据
    @Test
    public void testWriteDBByOneConn() throws Exception{
        Connection conn = DBConn.getConnection();
        Statement stat = conn.createStatement();
        for(int i = 0; i < 2000; i++){
            writeDBByOneConn(i, stat);
        }
        conn.close();
        System.out.println("DONE");
    }

    //不使用连接池写数据库,每写一条数据创建一个连接
    public void writeDBByEveryConn(int data){
        String sql = "insert into dbcp values (" + data + ")";
        Connection conn = DBConn.getConnection();
        try{
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace() ;
        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    //不使用连接池写数据库,只用一个连接,写所有数据
    public void writeDBByOneConn(int data, Statement stat){
        String sql = "insert into dbcp values (" + data + ")";
        try{
            stat.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace() ;
        }
    }

    //通过DBCP连接池写数据库
    public void writeDBByDBCP(int data){
        String sql = "insert into dbcp values (" + data + ")";
        try {
            Connection conn = dbcp.KCYDBCPUtil.getConnection();
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}