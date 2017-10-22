package site.kenz.db.api.kenzdbapi.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.kenz.db.api.kenzdbapi.bean.SysUser;
import site.kenz.db.api.kenzdbapi.dao.UserDao;

import io.swagger.annotations.Api;
import javax.annotation.Resource;

/**
 *  * User: kenzhao
 *  * Date: 2017/10/21
 *  * Time: 18:24
 *  * PROJECT_NAME: kenz
 *  * PACKAGE_NAME: site.kenz.db.api.kenzdbapi.controller
 *  * DESC:
 *  * Version: v1.0.0
 *  
 */
@RestController
@RequestMapping(value = "/user")
@Api(description = "用户")
public class SysUserController {

    @Resource
    UserDao userDAO;


    @ApiOperation(value = "添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name" , value = "name" , paramType = "query" , required = true ),
            @ApiImplicitParam(name = "age" , value = "age" , paramType = "query" , required = true )
    })
    @RequestMapping(value = "/addUser" , method = RequestMethod.POST)
    public String addUser(@RequestParam(value = "name") String name, @RequestParam(value = "age") int age){

        SysUser user = new SysUser();
        user.setuName(name);
        user.setuStatus(name);
        user.setuPassword(name);

        userDAO.save(user);

        return "add user success !";
    }


    @ApiOperation(value = "查找用户")
    @ApiImplicitParam(name = "id" , value = "id" , paramType = "query" , required = true , dataType = "int")
    @RequestMapping(value = "/findById" , method = RequestMethod.POST)
    public String findById(@RequestParam(value = "id") Long id){

        SysUser user = userDAO.findById(id);

        if(user == null){
            return "error";
        }else{
            return "name:" + user.getuName() + " , age:" + user.getuStatus();
        }
    }


    @ApiOperation(value = "查询所有用户")
    @RequestMapping(value = "/findAll" , method = RequestMethod.POST)
    public Iterable findAll(){

        Iterable<SysUser> userList = userDAO.findAll();

        return userList;

    }

    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "id" , value = "id" , paramType = "query" , required = true , dataType = "int")
    @RequestMapping(value = "/deleteById" , method = RequestMethod.POST)
    public String deleteById(@RequestParam(value = "id") Long id){

        userDAO.delete(id);
        return "delete success !";

    }

}
