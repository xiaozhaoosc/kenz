package site.kenz.db.api.kenzdbapi.bean;

import javax.persistence.*;

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
@Table(name = "sys_user")
public class SysUser {

    @Id
    @Column(name = "hsid",columnDefinition="bigint COMMENT '主键，自动生成'")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "u_name")
    private String uName;
    @Column(name = "u_password")
    private String uPassword;
    @Column(name = "u_status")
    private String uStatus;

    @Basic
    @Column(name = "u_email",columnDefinition="varchar(64) COMMENT 'email'",unique = true)
    private String uEmail;


//    @UpdateTimestamp

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuStatus() {
        return uStatus;
    }

    public void setuStatus(String uStatus) {
        this.uStatus = uStatus;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }
}
