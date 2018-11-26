import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.*;
import javax.swing.*;

import java.util.Scanner;



public class Main {
    public static final int select = 1 ;
    public static final int insert = 2;
    public static final int delete = 3;

    public static void main(String[] args) throws Exception {
        //获取连接
//        connect a = new connect();
//        Connection con = a.getConnection();
//
//        String command = "insert into S values ";
//        int operation = transCommand(command);
//        switch (operation){
//            case select :
//                select_test(con,command);
//                break;
//            case insert :
//                insert_test(con,command);
//
//        }


        JFrame f = new JFrame("LoL");
        f.setSize(400, 300);
        f.setLocation(200, 200);

        f.setLayout(new FlowLayout());

        JLabel lName = new JLabel("账号：");
        // 输入框
        JTextField tfName = new JTextField("");
        tfName.setText("请输入账号");
        tfName.setPreferredSize(new Dimension(80, 30));
//        System.out.println(a);

        JLabel lPassword = new JLabel("密码：");
        // 输入框
        JTextField tfPassword = new JTextField("");
        tfPassword.setText("请输入密码");
        tfPassword.setPreferredSize(new Dimension(80, 30));

        f.add(lName);
        f.add(tfName);
        f.add(lPassword);
        f.add(tfPassword);



        JButton b = new JButton("一键秒对方基地挂");
        b.setBounds(50, 50, 280, 30);



        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setVisible(true);
        tfPassword.grabFocus();







    }


    static int transCommand(String s) {

        int operate ;
        String first_word = s.split("\\s+")[0];
        System.out.println(first_word);
        if (first_word.equals("select"))
            operate = select;
        else if(first_word.equals("insert"))
            operate = insert;
        else if(first_word.equals("delete"))
            operate = delete;
        else operate = 0;

        return operate;
    }


    //数据库操作们
    static void select_test(Connection con,String command){

        try {
            int count = 0;

            //we cannot use select ? from...,it will not select out what we want
            String [] operation_list = command.split("\\s+");

            PreparedStatement ps = con.prepareStatement(command);
            ResultSet rs = ps.executeQuery();

            //  select *
            //  select a,b,c
            //  select S.sno,C.cno
            //  select S.sno sno1

            while (rs.next()) {
                System.out.println("第"+count+"行:");
                for(int i = 1 ;i<= operation_list.length;i++){
                    if(operation_list[i].equals("from"))
                        break;
                    if(operation_list[i].equals(","))
                        continue;
                    System.out.println(rs.getString(operation_list[i]));
                }
                System.out.println('\n');
                count += 1;
            }

            rs.close();
            ps.close();

        }catch (Exception e){
            System.out.println("查询失败");
            e.printStackTrace();
        }
    }
    static void insert_test(Connection con,String command){
        try {

//            String s = "insert into S(sno,sname,ssex,sbirth,sdept) " +
//                    "values (004,\'test\',\'test\',\'1998-08-06\',\'test\')";
//            PreparedStatement ps = con.prepareStatement(s);
//            ps.execute();

            String S = "insert into S(sno,sname,ssex,sbirth,sdept) " +
                    "values (007,\'test1\',\'test\',\'1998-08-06\',?)";
            PreparedStatement ps = con.prepareStatement(S);
            //PreparedStatement里面可以传递参数用？表示，然后用setString来赋值，
            //index表示第几个参数，x会在驱动程序中被抓换成sql varchar或longvarchar
            ps.setString(1,"CS");
            ps.execute();

            ps.close();

        }catch (Exception e){
            System.out.println("插入失败");
            e.printStackTrace();
        }
    }
    static void delete_test(Connection con,String command){
        try{
            String s = "delete from S where sname = \'test\'";
            PreparedStatement ps = con.prepareStatement(s);
            ps.execute();
        }catch (Exception e){
            System.out.println("删除失败");
            e.printStackTrace();
        }
    }
    static void udpate_test(Connection con){
        try{
            String s = "update S set sname = ? where sno = ?";
            PreparedStatement ps = con.prepareStatement(s);
            ps.setString(1,"瞎几把");
            ps.setString(2,"0");
            ps.execute();
        }catch (Exception e){
            System.out.println("更新失败了");
            e.printStackTrace();
        }
    }
    //procedure without parameter
    static void procedure_test1(Connection con){

        /*the code of procedure
        *create procedure S_select_all()
        * as
        * begin
        *   select * from S
        * end
        * */
        try {
            String s = "exec S_select_all";
            CallableStatement ps = con.prepareCall(s);

            ResultSet rs = ps.executeQuery();

            int count=0;
            while (rs.next()) {
                System.out.println("第"+count+"行:");
                System.out.println(rs.getString("sno"));
                System.out.println(rs.getString("sname"));
                System.out.println(rs.getString("ssex"));
                System.out.println(rs.getString("sbirth"));
                System.out.println(rs.getString("sdept"));
                System.out.println('\n');
                count += 1;
            }

        }catch (Exception e){
            System.out.println("存储过程执行失败");
            e.printStackTrace();
        }

    }
    //procedure with parameter 'in'
    static void procedure_test2(Connection con){

        /*the code of procedure is:
         *
         *create procedure S_with_in( @s_name varchar(10)) --sql默认参数为In
         * begin
         *   if @s_name = null or @s_name = '' then
         *      select * from S
         *   else
         *      select * from S where sname = @s_name
         * end
         *
         * */
        try {
            String s = "exec S_with_in ? ";
            CallableStatement ps = con.prepareCall(s);
            ps.setString(1,"李勇");
            ResultSet rs = ps.executeQuery();

            int count=0;
            while (rs.next()) {
                System.out.println("第"+count+"行:");
                System.out.println(rs.getString("sno"));
                System.out.println(rs.getString("sname"));
                System.out.println(rs.getString("ssex"));
                System.out.println(rs.getString("sbirth"));
                System.out.println(rs.getString("sdept"));
                System.out.println('\n');
                count += 1;
            }

        }catch (Exception e){
            System.out.println("存储过程执行失败");
            e.printStackTrace();
        }

    }
    //procedure with parameter 'out'
    static void procedure_test3(Connection con){
        /*the code of procedure is:
         *
         *create procedure S_with_out( @s_name varchar(20) out)
         * begin
         *  select @s_name = sname from S where sbirth = '1998-08-06'
         * end
         *
         * */
        try {
            String s = "exec S_with_out ? "; //different from sql which should be 'exec S_with_out ? out'
            CallableStatement ps = con.prepareCall(s);
            ps.registerOutParameter(1,Types.VARCHAR);
            ps.execute();
            String name = ps.getString(1);
            System.out.println(name);

            ps.close();

        }catch (Exception e){
            System.out.println("存储过程执行失败");
            e.printStackTrace();
        }
    }
    static void addbach_test(Connection con){

        try{
            //批处理不能用select语句
            Statement ps = con.createStatement();
            ps.addBatch("insert into S values (008,\'test8\',\'test\',\'1998-08-06\',\'CS\')");
            ps.addBatch("insert into S values (009,\'test9\',\'test\',\'1998-08-06\',\'CS\')");
            ps.addBatch("insert into S values (010,\'test10\',\'test\',\'1998-08-06\',\'CS\')");

            ps.executeBatch();

            ps.close();
        }catch (Exception e){
            System.out.println("批处理出错");
            e.printStackTrace();
        }


    }

}

class connect{
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