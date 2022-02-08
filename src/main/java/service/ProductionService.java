package service;

import Bean.Production;
import common.PageBean;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductionService {
    List<Production> findAllProduction();
    PageBean<Production> findByPage(int page, int size, Map<String,Object> paramMap) throws Exception;
    int addProduction(Production production) throws SQLException;
    int updateProduction(Production production) throws SQLException;
    Production findById(String id) throws SQLException;
    void deleteById(long id) throws SQLException;
}
