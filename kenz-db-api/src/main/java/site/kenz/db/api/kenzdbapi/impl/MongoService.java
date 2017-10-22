package site.kenz.db.api.kenzdbapi.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.kenz.db.api.kenzdbapi.mongo.MongoUtils;
import site.kenz.db.api.kenzdbapi.services.IMongoService;

import java.util.List;
import java.util.Map;

/**
 *  * User: kenzhao
 *  * Date: 2017/10/22
 *  * Time: 11:50
 *  * PROJECT_NAME: kenz
 *  * PACKAGE_NAME: site.kenz.db.api.kenzdbapi.impl
 *  * DESC:
 *  * Version: v1.0.0
 *  
 */
@Service
public class MongoService implements IMongoService {

    @Autowired
    private MongoUtils mongoUtils;
    /**
     * 高级查询
     * 可指定显示字段，查询条件，排序
     * 采用原生mongo的查询方式获取数据
     *
     * @param showFields 显示字段
     * @param paramMap   参数
     * @param orderList  排序
     * @param clazz      文档
     * R@return 查询对象
     */
    @Override
    public List findFieldsByClazz(Map<String, Object> showFields, Map<String, Object> paramMap, List<Sort.Order> orderList, Class<?> clazz) {
        return mongoUtils.findFieldsByClazz(showFields,paramMap,orderList,clazz);
    }
}
