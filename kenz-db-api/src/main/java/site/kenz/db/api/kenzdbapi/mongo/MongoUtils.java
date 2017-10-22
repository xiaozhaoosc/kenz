package site.kenz.db.api.kenzdbapi.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import site.kenz.db.api.kenzdbapi.bean.BaseEntity;
import site.kenz.db.api.kenzdbapi.bean.PageEntity;
import site.kenz.db.api.kenzdbapi.utils.JacksonUtils;
import site.kenz.db.api.kenzdbapi.utils.ReadJsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class MongoUtils {

    private static Logger logger = Logger.getLogger("mongotest");

	@Autowired
    protected MongoOperations mongoTemplate;

	/**
     * 字符串分隔的标志符号，现在用:表示，在关联查询的时候用。
     ***/
    private final String splitMarker = "::";

    /////////////////////////////////////////////////////////
    ////////////////////////保存/////////////////////////////
    /////////////////////////////////////////////////////////
    /**
     * 根据实体对象保存数据到mongodb中
     * @param clazz
     */
    public void saveByClazz(Class<?> clazz){
    	mongoTemplate.save(clazz);
    }

    public void saveByClazz(BaseEntity clazz){
        mongoTemplate.save(clazz);
    }

	/////////////////////////////////////////////////////////
	////////////////////////删除/////////////////////////////
	/////////////////////////////////////////////////////////
    /**
     * 根据K,V的形式先查找删除一条数据集合
     * @param paramMap K.V 参数
     * @param clazz 对象（mongodb集合名）必传
     * @return
     */
    public void removeByClazz(Map<String, Object> paramMap, Class<?> clazz){
    	if(paramMap == null || paramMap.isEmpty()){
    		throw new RuntimeException("查询参数不能为空");
    	}
    	Query query = this.buildQuery(paramMap, null, clazz);
    	mongoTemplate.findAndRemove(query, clazz);
    }


    /**
     * 根据K,V的形式查找删除所有相关数据集合（慎用）
     * @param paramMap K.V 参数 切记 参数不可以为空
     * @param clazz 对象（mongodb集合名）必传
     * @return
     */
    public void removeAllByClazz(Map<String, Object> paramMap, Class<?> clazz){
    	if(paramMap == null || paramMap.isEmpty()){
    		throw new RuntimeException("查询参数不能为空");
    	}
    	Query query = this.buildQuery(paramMap, null, clazz);
    	mongoTemplate.findAllAndRemove(query, clazz);
    }

	/////////////////////////////////////////////////////////
	////////////////////////修改/////////////////////////////
	/////////////////////////////////////////////////////////

    /**
     * 根据K,V的形式查找修改一条匹配的数据集合
     * @param paramMap K.V 参数 切记 参数不可以为空
     * @param update 修改的对象
     * @param clazz 对象（mongodb集合名）必传
     */
    public WriteResult updateByClazz(Map<String, Object> paramMap, Update update, Class<?> clazz){
    	if(paramMap == null || paramMap.isEmpty()){
    		throw new RuntimeException("查询参数不能为空");
    	}
    	Query query = this.buildQuery(paramMap, null, clazz);
    	return mongoTemplate.updateFirst(query, update, clazz);
    }

    /**
     * 根据K,V的形式查找修改所有匹配的数据集合
     * @param paramMap K.V 参数 切记 参数不可以为空
     * @param update 修改的对象
     * @param clazz 对象（mongodb集合名）必传
     */
    public WriteResult updateAllByClazz(Map<String, Object> paramMap,Update update, Class<?> clazz){
    	if(paramMap == null || paramMap.isEmpty()){
    		throw new RuntimeException("查询参数不能为空");
    	}
    	Query query = this.buildQuery(paramMap, null, clazz);
    	return mongoTemplate.updateMulti(query, update, clazz);
    }

    /////////////////////////////////////////////////////////
    ////////////////////////查询/////////////////////////////
    /////////////////////////////////////////////////////////

    /**
     * 根据K,V的形式查询数据集合
     * @param paramMap K.V 参数(没有参数就传null)
     * @param orderList 排序(如果不排序就传null)
     * @param clazz 对象（mongodb集合名）必传
     * @return 返回数据集合
     */
    @SuppressWarnings("rawtypes")
	public List findListByClazz(Map<String, Object> paramMap, List<Order> orderList, Class<?> clazz) {
	    Query query = this.buildQuery(paramMap, orderList, clazz);
	    return mongoTemplate.find(query, clazz);
	}


    /**
     * 根据K,V的形式查询单个数据对象
     * @param paramMap K.V 参数(没有参数就传null)
     * @param orderList 排序(如果不排序就传null)
     * @param clazz 对象（mongodb集合名）必传
     * @return 返回单个数据对象
     */
    public Object findByClazz(Map<String, Object> paramMap, List<Order> orderList, Class<?> clazz) {
    	long start = System.currentTimeMillis();
	    Query query = this.buildQuery(paramMap, orderList, clazz);
	    Object obj = mongoTemplate.findOne(query, clazz);
	    logger.info("mongo查询是否有结果：" + obj == null ? "否" : "是");
	    long end = System.currentTimeMillis();
	    long time = end - start;
	    logger.info("mongo查询执行时间：" + time +"ms");
	    return obj;
	}


    /**
     * 根据K,V的形式查询匹配的对象数量
     * @param paramMap K.V 参数(没有参数就传null)
     * @param clazz clazz 对象（mongodb集合名）必传
     * @return 返回匹配的数量（type:long）
     */
    public long findCountByClazz(Map<String, Object> paramMap, Class<?> clazz){
    	Query query = this.buildQuery(paramMap, null, clazz);
	    return mongoTemplate.count(query, clazz);
    }

    /**
     * 根据K,V的形式分页查询匹配的对象数量
     * @param paramMap K.V 参数(没有参数就传null)
     * @param orderList 排序(如果不排序就传null)
     * @param clazz clazz 对象（mongodb集合名）必传
     * @param pageEntity 分页实体类
     * @deprecated 在使用这个方法时,先需要实例化一个PageEntity实体对象，并设置你需要查询数据集合的开始条数与查询行数（每页显示数量）
     * 执行完这个方法后，直接调用PageEntity对象中的getResults方法获取结果
     */
	@SuppressWarnings("unchecked")
	public void findPageList(Map<String,Object> paramMap, List<Order> orderList, Class<?> clazz, @SuppressWarnings("rawtypes") PageEntity pageEntity){

    	Query query = this.buildQuery(paramMap, orderList, clazz);
    	int totalRsults = (int)mongoTemplate.count(query, clazz);//总条数
    	int start = pageEntity.getStart();//起始行数
    	int pageSize = pageEntity.getPageSize();//查询行数

    	int pageTotal = totalRsults % pageSize > 0 ? totalRsults / pageSize + 1 : totalRsults / pageSize;

        pageEntity.setTotalResults(totalRsults);// 总记录数
        pageEntity.setTotalPages(pageTotal);// 总页数

        query.skip(start);
        query.limit(pageSize);

        pageEntity.setResults(mongoTemplate.find(query, clazz));// 分页数据
        pageEntity.setCurrentPageLength(pageEntity.getResults().size());// 当前页面记录数
    }

    protected Query buildQuery(Map<String, Object> paramMap, List<Order> orderList, Class<?> clazz) {
        Query query = new Query();
        if (paramMap != null && paramMap.size() > 0) {
            for (String key : paramMap.keySet()) {
                Object value = paramMap.get(key);
                query.addCriteria(Criteria.where(key).is(value));
            }
        }

        if (orderList != null && orderList.size() > 0) {
            Sort sort = new Sort(orderList);
            query.with(sort);
        }

        return query;
    }



    /**
     *
     * 高级查询
     * 可指定显示字段，查询条件，排序，分页
     * 采用原生mongo的查询方式获取数据
     * @param showFields 显示字段
     * @param paramMap 参数
     * @param orderList 排序
     * @param clazz 文档
     * @param pageEntity
     * @return 查询对象
     */
    @SuppressWarnings("rawtypes")
    public List findFieldsByClazz(Map<String, Object> showFields, Map<String, Object> paramMap, List<Order> orderList, Class<?> clazz,PageEntity pageEntity) {
        long beginTime = new Date().getTime();

        //获取查询对象
        Query query = this.buildQuery(showFields, paramMap, orderList, clazz);

        int totalRsults = (int)mongoTemplate.count(query, clazz);//总条数
        int start = pageEntity.getStart();//起始行数
        int pageSize = pageEntity.getPageSize();//查询行数
        int pageTotal = totalRsults % pageSize > 0 ? totalRsults / pageSize + 1 : totalRsults / pageSize;
        pageEntity.setTotalResults(totalRsults);// 总记录数
        pageEntity.setTotalPages(pageTotal);// 总页数
        query.skip(start);
        query.limit(pageSize);
        pageEntity.setResults(mongoTemplate.find(query, clazz));// 分页数据
        pageEntity.setCurrentPageLength(pageEntity.getResults().size());// 当前页面记录数
        List list =  mongoTemplate.find(query, clazz);
        long endTime = new Date().getTime();
        logger.info("分页查询用时：" + (endTime-beginTime));
        return list;
    }

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
    @SuppressWarnings("rawtypes")
    public List findFieldsByClazz(Map<String, Object> showFields, Map<String, Object> paramMap, List<Order> orderList, Class<?> clazz) {
        long beginTime = new Date().getTime();
        //获取查询对象
        Query query = this.buildQuery(showFields, paramMap, orderList, clazz);
        logger.info("查询参数：" + JacksonUtils.ObjectToJson(query));
        List list =  mongoTemplate.find(query, clazz);
        long endTime = new Date().getTime();
        logger.info("查询用时(ms)：" + (endTime-beginTime));
        logger.info("查询个数:" + list.size());
//        logger.info("Gson:" + ReadJsonUtils.toJson(list));
        int count = 0;
        for (Object obj:list
             ) {
            count++;
            logger.info("查询明细" + count +  "："  + JacksonUtils.ObjectToJson(obj).substring(0,10));
        }
        return list;
    }
    /**
     * 设置查询参数
     * @param showFields 显示字段
     * @param paramMap 参数
     * @param orderList 排序
     * @param clazz 文档
     * @return 查询对象
     */
    protected Query buildQuery(Map<String, Object> showFields, Map<String, Object> paramMap, List<Order> orderList, Class<?> clazz) {
//        Query query = new Query();

        //定义查询显示字段
        BasicDBObject fieldsObject = new BasicDBObject();
        //查询参数
        BasicDBObject queryParams = new BasicDBObject();

        boolean isPage = false;
        int pageSize = 10;
        Object lastId = null;

        //设置查询参数
        if (paramMap != null && paramMap.size() > 0) {
            for (String key : paramMap.keySet()) {
//                Object value = paramMap.get(key);
//                queryParams.addCriteria(Criteria.where(key).is(value));
                if ("createTime".equals(key)) {
                    BSONObject bsonObject = new BasicDBObject();
                    bsonObject.put("$lte", paramMap.get(key));
                    queryParams.put(key, bsonObject);
                }else if ("createTime".equals(key)) {
                    BSONObject bsonObject = new BasicDBObject();
                    bsonObject.put("$lte", paramMap.get(key));
                    queryParams.put(key, bsonObject);
                }else if ("lastId".equals(key)) {
                    lastId = paramMap.get(key);
                } else if ("pageSize".equals(key)) {
                    try {
                        pageSize = Integer.parseInt(paramMap.get(key).toString());
                    } catch (Exception e) {
                        logger.info("分页大小非数字，已初始化为10");
                    }
                    isPage = true;
                }   else {
                    queryParams.put(key, paramMap.get(key));
                }
            }
        }

        //设置查询显示的字段
        if (showFields != null && showFields.size() > 0) {
            for (String key: showFields.keySet()
                    ) {
                fieldsObject.put(key, showFields.get(key));
            }

        }
        Query query = new BasicQuery(queryParams, fieldsObject);

        if (isPage) {
            if (lastId != null) {
                ObjectId id = new ObjectId(lastId.toString());
                query.addCriteria(Criteria.where("_id").gte(id));
            }
            query.limit(pageSize);
        }
        //设置排序
        if (orderList != null && orderList.size() > 0) {
            Sort sort = new Sort(orderList);
            query.with(sort);
        }

        return query;
    }
}
