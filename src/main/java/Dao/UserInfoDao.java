package Dao;

import Bean.UserInfo;

import java.sql.SQLException;

public interface UserInfoDao {
    UserInfo findUserByEmail(String email,String password) throws SQLException;
}
