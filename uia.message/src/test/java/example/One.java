package example;

import java.util.Date;

public class One {

    private String name;

    private int sex;

    private Date birthday;

    public One() {
        this.name = "Kyle";
        this.sex = 1;
        this.birthday = new Date();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
