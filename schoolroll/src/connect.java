import java.sql.*;
public class connect {
    static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student";
    static final String name = "sa";
    static final String password = "123456";
    static Connection conn = null;

    static{
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,name,password);
            System.out.println("数据库连接成功");
        }catch (Exception e){
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return conn;
    }
}
