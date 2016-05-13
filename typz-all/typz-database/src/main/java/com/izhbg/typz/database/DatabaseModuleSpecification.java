package com.izhbg.typz.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.izhbg.typz.dbmigrate.ModuleSpecification;
/**
 * 通用配置基础
* @ClassName: AuditLogModuleSpecification 
* @Description: 通用配置基础
* @author caixl 
* @date 2016-4-26 下午4:21:14 
*
 */
@Component
public class DatabaseModuleSpecification implements ModuleSpecification 
{
	private static final String MODULE_NAME = "database";
    private static final String MODULE_NAME_UPPER = MODULE_NAME.toUpperCase();
    private String type;
    private boolean enabled;
    private boolean initData;

    public boolean isEnabled() {
        return enabled;
    }

    public String getSchemaTable() {
        return "SCHEMA_VERSION_" + MODULE_NAME_UPPER;
    }

    public String getSchemaLocation() {
        return "dbmigrate." + type + "." + MODULE_NAME;
    }

    public boolean isInitData() {
        return initData;
    }

    public String getDataTable() {
        return "SCHEMA_VERSION_DATA_" + MODULE_NAME_UPPER;
    }

    public String getDataLocation() {
        return "dbmigrate." + type + ".data_" + MODULE_NAME;
    }

    @Value("${application.database.type}")
    public void setType(String type) {
        this.type = type;
    }

    @Value("${" + MODULE_NAME + ".dbmigrate.enabled}")
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Value("${" + MODULE_NAME + ".dbmigrate.initData}")
    public void setInitData(boolean initData) {
        this.initData = initData;
    }
}
