package tables;

public class Reader {
    private String id;      // 读者编号
    private String name;    // 读者姓名
    private String phone;   // 读者电话
    private String sex;     // 读者性别
    private double own;     // 读者欠款

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getOwn() {
        return own;
    }
    public void setOwn(double own) {
        this.own = own;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
}

