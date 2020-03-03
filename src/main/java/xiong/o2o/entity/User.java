package xiong.o2o.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    public User() {

    }

    public User(Long id, String name, String password, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    private Long id;
    private String name;
    private String password;
    private Integer age;
    private String email;
    private Date createTime;
    private Date updateTime;
}
