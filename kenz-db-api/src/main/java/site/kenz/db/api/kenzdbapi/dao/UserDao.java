package site.kenz.db.api.kenzdbapi.dao;

import org.springframework.data.repository.CrudRepository;
import site.kenz.db.api.kenzdbapi.bean.SysUser;

/**
 *  * User: kenzhao
 *  * Date: 2017/10/21
 *  * Time: 18:28
 *  * PROJECT_NAME: kenz
 *  * PACKAGE_NAME: site.kenz.db.api.kenzdbapi.dao
 *  * DESC:
 *  * Version: v1.0.0
 *  
 */
public interface UserDao extends CrudRepository<SysUser, Long> {


    public SysUser findById(Long id);
}
