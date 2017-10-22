package site.kenz.db.api.kenzdbapi.services;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 *  * User: kenzhao
 *  * Date: 2017/10/22
 *  * Time: 11:47
 *  * PROJECT_NAME: kenz
 *  * PACKAGE_NAME: site.kenz.db.api.kenzdbapi
 *  * DESC:
 *  * Version: v1.0.0
 *  
 */
public interface IMongoService {

    /**
     *
     * 高级查询
     * 可指定显示字段，查询条件，排序
     * 采用原生mongo的查询方式获取数据
     * @param showFields 显示字段
     * @param paramMap 参数
     * @param orderList 排序
     * @param clazz 文档
     * @return 查询对象
     */
    List findFieldsByClazz(Map<String, Object> showFields, Map<String, Object> paramMap, List<Sort.Order> orderList, Class<?> clazz);
}
