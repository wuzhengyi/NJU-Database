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
     * дһ���������ݿ�ķ���
     */
    public Connection getConnection(){
        String url="jdbc:mysql://localhost:3306/lab3";
        String userName="root";
        String password="wuzhengyi";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("�Ҳ���������");
            e.printStackTrace();
        }
        try {
            conn=DriverManager.getConnection(url, userName, password);
            if(conn!=null){
                System.out.println("connection successful");
                statement = conn.createStatement();       //����Statement����
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
                System.out.println( "connection fail");
            e.printStackTrace();
        }
        return conn;
    }

    /*
     * ����sql��ѯ���ݿ⣬����һ�������
     * ��    ��:SQL���
     * ����ֵ:ResultSet ��ѯ���
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
 * ��ӡUserInfo�������
 * ��    ��:�����(���ݱ�)
 * ����ֵ:��
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
   * ִ�����ݲ���
   * ��    ��:SQL���
   * ����ֵ:��
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
//        System.out.println("\n��ʾStaff����������:");
//        String sql = "SELECT * FROM Staff;";
//        ResultSet result = j.querySql(sql);//�ڿ���̨�@ʾ�����ҷ���
//        j.printStaff(result);
//
//        System.out.println("\nɾ��SNOΪ128����һ�:");
//        j.executeSql("DELETE FROM Staff WHERE SNO = 128;");
//        result = j.querySql(sql);
//        j.printStaff(result);
//
//        System.out.println("\n���('��һ�',128,21,3):");
//        j.executeSql("INSERT INTO Staff(SNAME,SNO,AGE,DNO) VALUES ('��һ�',128,21,3);");
//        result = j.querySql(sql);
//        j.printStaff(result);
//
//        System.out.println("\n���('������',110,1,100000,2):");
//        j.executeSql("INSERT INTO Staff VALUES ('������',110,1,100000,2);");
//        result = j.querySql(sql);
//        j.printStaff(result);
//
//        System.out.println("\n��SNO=129�ĸ�Ϊ127:");
//        j.executeSql("update Staff set SNO = 127 where SNO=129;");
//        result = j.querySql(sql);
//        j.printStaff(result);
//        System.out.println();
//
//    }
}