package Dao;

import Bean.Production;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductDao {
    //查询所有商品
    List<Production> findAllProduction() throws SQLException;

    List<Production> findByPage(int page, int size, Map<String,Object> paramMap) throws SQLException;
    long findCount(Map<String, Object> paramMap) throws Exception;
    int addProduction(Production production) throws SQLException;
    int updateProduction(Production production) throws SQLException;
    Production findById(String id) throws SQLException;
    void deleteById(long id) throws SQLException;
}
