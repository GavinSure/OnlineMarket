package Dao.impl;

import Bean.UserInfo;
import Dao.UserInfoDao;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import util.DBHelper;

import java.sql.SQLException;

public class UserInfoDaoImpl implements UserInfoDao {
    @Override
    public UserInfo findUserByEmail(String email, String password) throws SQLException {
        String sql="SELECT * FROM `user` WHERE Email=? AND `password`=?";
        return new QueryRunner(DBHelper.getDataSource()).query(sql, new BeanHandler<>(UserInfo.class,new BasicRowProcessor(new GenerousBeanProcessor())),
                email,
                password);
    }
}
