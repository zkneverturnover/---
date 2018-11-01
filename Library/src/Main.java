import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
        // 声明JDBC objects.
        Connection con = null;
        try
        {
            // 建立连接
            System.out.println("准备连接！！！");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=student","sa","123456");
            System.out.println("连接成功！！！");

            //这里可以写sql语句来测试

            //查询
            PreparedStatement ps = con.prepareStatement("select * from S");
            ResultSet result = ps.executeQuery();
            while (result.next()){
                System.out.println(result.getRow());
                System.out.println(result.getString("sno")
                        +result.getString("sname"));
            }

            Statement a = con.createStatement();
            result = a.executeQuery("select * from C");
            while (result.next()){
                System.out.println(result.getRow());
                System.out.println(result.getString("cno")
                        +result.getString("cname"));
            }


        }
        catch (Exception e)
        {
            System.out.println("连接错误："+e);
        }
    }
}


