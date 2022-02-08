package util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
    private DBHelper(){}

    private static DataSource dataSource;

    public static Connection getconn() throws SQLException {
        return dataSource.getConnection();
    }
    public static DataSource getDataSource(){
        return dataSource;
    }
    static {
        Properties properties=new Properties();
        try {
            properties.load(DBHelper.class.getClassLoader().getResourceAsStream("druid.properties"));       //要获得编译后路径 所以要使用反射，使用类加载器获得
            dataSource= DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
