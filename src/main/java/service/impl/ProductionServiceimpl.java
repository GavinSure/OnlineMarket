package service.impl;

import Bean.Production;
import Dao.ProductDao;
import Dao.impl.ProductDaoImpl;
import common.PageBean;
import lombok.SneakyThrows;
import util.DateUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductionServiceimpl implements service.ProductionService {
    private static ProductDao productDao=new ProductDaoImpl();
    @SneakyThrows
    @Override
    public List<Production> findAllProduction() {
        List<Production> allProduction = productDao.findAllProduction();
        getDate(allProduction);
        return allProduction;
    }

    private void getDate(List<Production> allProduction) {
        allProduction.forEach(production -> production.setTimeStr(DateUtil.format(production.getCreateTime())));
    }

    @Override
    public PageBean<Production> findByPage(int page, int size, Map<String, Object> paramMap) throws Exception {
        List<Production> productionList = productDao.findByPage(page, size, paramMap);//分页+条件查询的数据
        getDate(productionList);
        long count = productDao.findCount(paramMap);
        long totalPage = count / size;
        totalPage = (count % size == 0) ? totalPage : totalPage + 1;
        PageBean<Production> pageBean = new PageBean<>();
        pageBean.setList(productionList);
        pageBean.setSize(size);
        pageBean.setPage(page);
        pageBean.setCount(count);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public int addProduction(Production production) throws SQLException {
        return productDao.addProduction(production);
    }

    @Override
    public int updateProduction(Production production) throws SQLException {
        return productDao.updateProduction(production);
    }

    @Override
    public Production findById(String id) throws SQLException {
        return productDao.findById(id);
    }

    @Override
    public void deleteById(long id) throws SQLException {
        productDao.deleteById(id);
    }


}
