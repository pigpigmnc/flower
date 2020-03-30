package com.zsc.flower.dao;

import com.zsc.flower.domain.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//UserDao类,封装对user对象CRUD操作
@Repository
public interface UsersDao extends BaseMapper<Users> {
    public Users selectUser(@Param("username") String username, @Param("password") String password);
    public List<Users> selectAllUsers();
    public int selectAddUser(Users user);
    public Users selectUserById(long id);
    public int selectModifyItself(Users user);
    public int selectUserStatusById(@Param("id") long id);
    public void selectChangeUserStatus(@Param("id") long id, @Param("status") int status);
    public List<Users> selectUserByRegisterDate();
    public Users selectUserByUsername(@Param("username") String username);
}
