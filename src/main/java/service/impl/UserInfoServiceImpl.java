package service.impl;

import Bean.UserInfo;
import Dao.UserInfoDao;
import Dao.impl.UserInfoDaoImpl;
import lombok.SneakyThrows;
import service.UserInfoService;

import java.sql.SQLException;

public class UserInfoServiceImpl implements UserInfoService {
    private static UserInfoDao userInfoDao=new UserInfoDaoImpl();
    @SneakyThrows
    @Override
    public UserInfo finduserInfo(String email, String password) {
            return userInfoDao.findUserByEmail(email,password);

    }
}
