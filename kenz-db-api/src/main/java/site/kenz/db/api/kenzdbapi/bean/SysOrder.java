package site.kenz.db.api.kenzdbapi.bean;

import javax.persistence.*;
import java.util.Date;

/**
 *  * User: kenzhao
 *  * Date: 2017/10/21
 *  * Time: 18:22
 *  * PROJECT_NAME: kenz
 *  * PACKAGE_NAME: site.kenz.db.api.kenzdbapi.bean
 *  * DESC:
 *  * Version: v1.0.0
 *  
 */
@Entity
@Table(name = "sys_order")
public class SysOrder {

    @Id
    @Column(name = "hsid")
    @GeneratedValue
    private Long id;

    @Column(name = "order_id")
    private String orderId;
    @Column(name = "c_time")
    @Temporal(TemporalType.DATE)
    private Date cTime;
    @Column(name = "e_time")
    @Temporal(TemporalType.DATE)
    private Date eTime;

}
