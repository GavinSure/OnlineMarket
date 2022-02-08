package Dao.impl;

import Bean.Production;
import Dao.ProductDao;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DBHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class ProductDaoImpl implements ProductDao {

    @Override
    public List<Production> findAllProduction() throws SQLException {
        String sql="select * from production";
        return new QueryRunner(DBHelper.getDataSource()).query(sql,new BeanListHandler<>(Production.class,new BasicRowProcessor(new GenerousBeanProcessor())));
    }

    @Override
    public List<Production> findByPage(int page, int size, Map<String, Object> paramMap) throws SQLException {
        StringBuilder builder=new StringBuilder("select * from production");
        if (!paramMap.isEmpty()){
            getSql(paramMap,builder);
        }
        builder.append(" limit ?,?");
        return new QueryRunner(DBHelper.getDataSource())
                .query(builder.toString(),
                        new BeanListHandler<>(Production.class, new BasicRowProcessor(new GenerousBeanProcessor()))
                        , (page - 1) * size, size
                );
    }

    @Override
    public long findCount(Map<String, Object> paramMap) throws Exception {
        StringBuilder builder=new StringBuilder(" select count(1) from production");
        if (!paramMap.isEmpty()){
            getSql(paramMap,builder);
        }
        return new QueryRunner(DBHelper.getDataSource()).query(builder.toString(), new ScalarHandler<>());
    }

    @Override
    public int addProduction(Production production) throws SQLException {
        String sql = "insert into production (prodName,prodPrice,prodImg,prodDesc) values (?,?,?,?)";
        return new QueryRunner(DBHelper.getDataSource()).update(sql,
                production.getProdName(),
                production.getProdPrice(),
                production.getProdImg(),
                production.getProdDesc()
        );
    }

    @Override
    public int updateProduction(Production production) throws SQLException {
        String sql="UPDATE production SET prodName=?,prodPrice=?,prodImg=?,prodDesc=? WHERE id =?";
        return new QueryRunner(DBHelper.getDataSource()).update(sql,
                production.getProdName(),
                production.getProdPrice(),
                production.getProdImg(),
                production.getProdDesc(),
                production.getId()
                );
    }

    @Override
    public Production findById(String id) throws SQLException {
        String sql="select * from production where id = ?";
        return new QueryRunner(DBHelper.getDataSource()).query(sql,
                new BeanHandler<>(Production.class,new BasicRowProcessor(new GenerousBeanProcessor())),
                id
        );
    }

    @Override
    public void deleteById(long id) throws SQLException {
        String sql="delete from production where id = ?";
        new QueryRunner(DBHelper.getDataSource()).update(sql,id);
    }

    private void getSql(Map<String,Object> paramMap,StringBuilder builder) {
        builder.append(" where ");
        Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();//列名
            Object value = entry.getValue();//模糊查询的数据
            builder.append(key).append(" like '%").append(value).append("%' or ");
        }
        //builder.append("1=1");
        //因为会多一个and 所以要删除
        builder.delete(builder.lastIndexOf("or"), builder.length());
    }
}
