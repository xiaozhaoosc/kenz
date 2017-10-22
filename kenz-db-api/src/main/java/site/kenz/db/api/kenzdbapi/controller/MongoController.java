package site.kenz.db.api.kenzdbapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.kenz.db.api.kenzdbapi.bean.DataReport;
import site.kenz.db.api.kenzdbapi.services.IMongoService;
import site.kenz.db.api.kenzdbapi.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@Api(description = "Mongo")
public class MongoController {

	@Autowired
	private IMongoService mongoService;


	/**
	 * 查询指定的一条记录
	 * 
	 * @Title: search
	 * @param id
	 * @return
	 *
	 */

	@ApiOperation(value = "查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id" , value = "id" , paramType = "query" , required = true )
	})
	@RequestMapping(value = "/search" , method = RequestMethod.POST)
	public List search(@RequestParam("id") String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("_id",id);
		List list = mongoService.findFieldsByClazz(null, params, null, DataReport.class);
		return list;
	}

	@ApiOperation(value = "查询分页")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginTime" , value = "开始时间-时间缀", paramType = "query" , required = true ),
			@ApiImplicitParam(name = "endTime" , value = "结束时间-时间缀", paramType = "query" , required = true ),
			@ApiImplicitParam(name = "lastId" , value = "上次查询的最后一条记录" , paramType = "query" , required = true ),
			@ApiImplicitParam(name = "pageSize" , value = "页面大小" , paramType = "query" , required = true )
	})
	@RequestMapping(value = "/searchPage" , method = RequestMethod.POST)
	public List searchPage(Long beginTime,Long endTime,String lastId,int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("lastId",lastId);
		params.put("pageSize",pageSize);
		params.put("createTime",beginTime);
		params.put("createTime",endTime);
		List list = mongoService.findFieldsByClazz(null, params, null, DataReport.class);
		return list;
	}

	@ApiOperation(value = "查询分页")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginTime" , value = "开始时间-时间缀", paramType = "query" , required = true ),
			@ApiImplicitParam(name = "endTime" , value = "结束时间-时间缀", paramType = "query" , required = true ),
			@ApiImplicitParam(name = "lastId" , value = "上次查询的最后一条记录" , paramType = "query" , required = true ),
			@ApiImplicitParam(name = "pageSize" , value = "页面大小" , paramType = "query" , required = true )
	})
	@RequestMapping(value = "/searchPageByDate" , method = RequestMethod.POST)
	public List searchPageByDate(Date beginTime, Date endTime, String lastId, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("lastId",lastId);
		params.put("pageSize",pageSize);
		params.put("createTime",beginTime.getTime());
		params.put("createTime",endTime.getTime());
		List list = mongoService.findFieldsByClazz(null, params, null, DataReport.class);
		return list;
	}

	@ApiOperation(value = "查询分页")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginTime" , value = "开始时间-时间缀", paramType = "query" , required = true,defaultValue = "2017-08-26 13:43:01" ),
			@ApiImplicitParam(name = "endTime" , value = "结束时间-时间缀", paramType = "query" , required = true,defaultValue = "2017-08-26 13:43:01"),
			@ApiImplicitParam(name = "lastId" , value = "上次查询的最后一条记录" , paramType = "query" , required = true ),
			@ApiImplicitParam(name = "pageSize" , value = "页面大小" , paramType = "query" , required = true )
	})
	@RequestMapping(value = "/searchPageByDateStr" , method = RequestMethod.POST)
	public List searchPageByDateStr(String beginTime, String endTime, String lastId, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("lastId",lastId);
		params.put("pageSize",pageSize);


		Date bTime = null;
		Date eTime = null;

		try {
			bTime = DateUtils.convertStringToDate(DateUtils.dateTimePattern,beginTime);
			eTime = DateUtils.convertStringToDate(DateUtils.dateTimePattern,endTime);
		} catch (ParseException e) {
			if (bTime == null) {
				bTime = new Date();
			}
			if (eTime == null) {
				eTime = new Date();
			}
		}

		params.put("createTime",bTime.getTime());
		params.put("createTime",eTime.getTime());
		List list = mongoService.findFieldsByClazz(null, params, null, DataReport.class);
		return list;
	}

	@ApiOperation(value = "查询所有")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize" , value = "页面大小" , paramType = "query" , required = true )
	})
	@RequestMapping(value = "/searchAll" , method = RequestMethod.POST)
	public List searchAll(int pageSize) {
		List list = mongoService.findFieldsByClazz(null, null, null, DataReport.class);
		return list;
	}
}
