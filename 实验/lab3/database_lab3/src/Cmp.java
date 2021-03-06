import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class cmpJDBC extends JDBC{
    //覆盖基类的测试方法,此处连接、查询、关闭Cmp.NR_TEST次。
    @Override
    public void testJDBC(){
        for(int i = 0; i< Cmp.NR_TEST; i++) {
            getConnection();
            querySql("select * from staff;");//执行查询
//            executeSql("INSERT INTO Department\n" +
//                    "VALUES \n" +
//                    "('清洁工',1,131);");
            closeConnection();

        }
//        //用一个JDBC连接执行2000语句
//        getConnection();
//        for(int i = 0; i< Cmp.NR_TEST; i++) {
//            querySql("select * from staff;");//执行查询
//        }
//        closeConnection();
    }
}


class cmpDBCP extends DBCP{
    //覆盖基类的测试方法,此处使用10个线程的线程池进行连接、查询、关闭共Cmp.NR_TEST次。
    @Override
    public ResultSet testDBCP() {
        ExecutorService executorService  = Executors.newFixedThreadPool(Cmp.NR_THREADS);//固定的十线程池
        for (int i = 0; i < Cmp.NR_TEST; i++) {
            executorService.execute(() -> {
                getConnection();
                super.testDBCP();
                endDBCP();
            });
        }

//        //用一个连接读写DBCP
//        getConnection();
//        for (int i = 0; i < Cmp.NR_TEST; i++) {
//            executorService.execute(() -> {
//                super.testDBCP();
//            });
//        }
//        endDBCP();

        executorService.shutdown();//不允许再往线程池中添加任务

        try {//等待直到所有任务完成
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

public class Cmp {
    static final int NR_THREADS=8;//多线程线程数
    static final int NR_TEST=2000;//
    private MyTimer timer = new MyTimer();
    public static void main(String[] args) {

        JDBC jdbc = new JDBC();
        jdbc.getConnection();
        jdbc.testJDBC();

        DBCP dbcp = new DBCP();
        ResultSet resultSet = dbcp.testDBCP();
        System.out.println("DBCP连接池执行选择语句显示所有Staff:");
        JDBC.printStaff(resultSet);

        Cmp cmpTwoMethod=new Cmp();
//        cmpTwoMethod.testJdbcTwoThousandTimes();
        cmpTwoMethod.testJdbcTwoThousandTimes();
        cmpTwoMethod.testDbcpTwoThousandTimes();
//        cmpTwoMethod.testDbcpTwoThousandTimes();
//        cmpTwoMethod.testDbcpTwoThousandTimes();
    }

    public void testJdbcTwoThousandTimes(){
        timer.start();
        cmpJDBC jdbc = new cmpJDBC();
        jdbc.testJDBC();
        timer.end();
        System.out.print("Jdbc耗时:");
        System.out.println(timer.getTime() +" ms");
    }
    //DBCP：使用多线程的2000次sql查询
    public void testDbcpTwoThousandTimes() {
        timer.start();
        cmpDBCP dbcp = new cmpDBCP();
        dbcp.testDBCP();
        timer.end();
        System.out.print("Dbcp使用多线程("+NR_THREADS+"个线程)耗时:");
        System.out.println(timer.getTime() +" ms");
        dbcp.getParameter();
    }
}

class MyTimer{
    long startTime;
    long endTime;
    public void start(){
        startTime = System.currentTimeMillis();   //获取开始时间
    }
    public void end() {
        endTime = System.currentTimeMillis();   //获取结束时间]
    }

    public long getTime(){
        return endTime - startTime;
    }
}