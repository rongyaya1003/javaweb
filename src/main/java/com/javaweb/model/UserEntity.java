package com.javaweb.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by shopnc on 2017/5/4.
 */
@Entity
@Table(name = "user", schema = "javawebdemo", catalog = "")
public class UserEntity {
    private int id;
    private String nickname;
    private String password;
    private Collection<BlogEntity> blogById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nickname", nullable = false, length = 45)
    @NotNull(message = "名称不能为空!!")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    @NotNull(message = "密码不能为空!!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<BlogEntity> getBlogById() {
        return blogById;
    }

    public void setBlogById(Collection<BlogEntity> blogById) {
        this.blogById = blogById;
    }
}
