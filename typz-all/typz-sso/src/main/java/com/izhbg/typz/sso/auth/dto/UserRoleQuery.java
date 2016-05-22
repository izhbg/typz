package com.izhbg.typz.sso.auth.dto;

import com.izhbg.typz.base.util.Constants;

public class UserRoleQuery {

    private String yhId,code,gnjsMc,jgId,yyId = Constants.APP_DEFAULT,currentAppId;

    public String getYhId() {
        return yhId;
    }

    public void setYhId(String yhId) {
        this.yhId = yhId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGnjsMc() {
        return gnjsMc;
    }

    public void setGnjsMc(String gnjsMc) {
        this.gnjsMc = gnjsMc;
    }

    public String getJgId() {
        return jgId;
    }

    public void setJgId(String jgId) {
        this.jgId = jgId;
    }

    public String getYyId() {
        return yyId;
    }

    public void setYyId(String yyId) {
        this.yyId = yyId;
    }

    public String getCurrentAppId() {
        return currentAppId;
    }

    public void setCurrentAppId(String currentAppId) {
        this.currentAppId = currentAppId;
    }
    
    

}
