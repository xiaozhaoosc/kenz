package site.kenz.db.api.kenzdbapi.bean;

import com.mongodb.util.JSON;
import org.springframework.data.annotation.Id;

import java.util.Map;

/**
 *  * User: kenzhao
 *  * Date: 2017/10/22
 *  * Time: 11:23
 *  * PROJECT_NAME: kenz
 *  * PACKAGE_NAME: site.kenz.db.api.kenzdbapi.bean
 *  * DESC:
 *  * Version: v1.0.0
 *  
 */
public class DataReport  extends BaseEntity{
    private static final long serialVersionUID = -2742148489606189447L;

    @Id
    private String id;

    private String applicationID;

    private String taskType;

    private JSON jsonInfo;

    private long createTime;

    private String orgName;

    private String isEnd;


    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public JSON getJsonInfo() {
        return jsonInfo;
    }

    public void setJsonInfo(JSON jsonInfo) {
        this.jsonInfo = jsonInfo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DataReport [id=" + id + ", applicationID=" + applicationID + ", taskType=" + taskType + ", jsonInfo="
                + jsonInfo + ", createTime=" + createTime + ", orgName=" + orgName + ", isEnd=" + isEnd + "]";
    }
}
