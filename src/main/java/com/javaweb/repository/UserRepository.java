package com.javaweb.repository;

import com.javaweb.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update UserEntity us set us.nickname=:qNickname, us.password=:qPassword where us.id=:qId")
    public void updateUser(@Param("qNickname") String nickname, @Param("qPassword") String password, @Param("qId") Integer id);


    @Transactional
    @Query("select u from UserEntity u where u.nickname=:qNickname and u.password=:qPassword")
    public UserEntity selectUser(@Param("qNickname") String nickname , @Param("qPassword") String password);

    @Query("select u from UserEntity u where u.id=:qId")
    List<UserEntity> selectUserList(@Param("qId") Integer id);

    @Transactional
    @Query("select u from UserEntity u where u.nickname=:qNickname")
    public UserEntity selectUserName(@Param("qNickname") String nickname);

}

