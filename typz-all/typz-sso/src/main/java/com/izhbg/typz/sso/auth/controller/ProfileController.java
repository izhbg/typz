package com.izhbg.typz.sso.auth.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.spring.MessageHelper;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.dto.TXtJgYh;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtJgYhManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Controller
@RequestMapping("user")
public class ProfileController {
    private MessageHelper messageHelper;
    private BeanMapper beanMapper = new BeanMapper();
    
    private TXtYhManager txtYhManager;
    private TXtJgService tXtJgService;
    private TXtJgYhManager tXtJgYhManager;
	private TXtJgManager tXtJgManager;

    @RequestMapping("profile-list")
    public String list(Model model) {
    	TXtYh userInfo = txtYhManager.findUniqueBy("yhDm", SpringSecurityUtils.getCurrentUsername());
    	 model.addAttribute("user", userInfo);
    	 List<TXtJgYh> list = tXtJgYhManager.findBy("yhId", SpringSecurityUtils.getCurrentUserId());
			if(list!=null&&list.size()>0){
				model.addAttribute("jgMc",list.get(0)==null?"":tXtJgService.getOrgan(list.get(0).getJgId()).getJgMc());
				userInfo.setJgId(list.get(0).getJgId());
			}
        return "admin/guser/profile-list";
    }

    @RequestMapping("profile-save")
    public String save(@ModelAttribute TXtYh userBase) throws Exception {
        String id= userBase.getYhId();

        // 再进行数据复制
        TXtYh dest = null;
        if (id != null) {
            dest = txtYhManager.get(id);
            beanMapper.copy(userBase, dest);
            txtYhManager.update(dest);
            //userService.updateUser(dest, userRepoId, parameters);
        } 
      /*  messageHelper.addFlashMessage(null, "core.success.save",
                "保存成功");*/

        return "redirect:/user/profile-list.izhbg";
    }
    @RequestMapping("profile-sysconfig")
    @SystemControllerLog(description = "个人权限设置")
    public String sysconfig(Model model) {
    	StringBuffer sb = new StringBuffer();
    	//读取日志的配置
    	
    	sb.append("select userId,shareToUserId from t_worklog_relation where userId='"+SpringSecurityUtils.getCurrentUserId()+"'");
    	//sb.append("select DISTINCT yh_id from t_xt_yh ,t_worklog_relation where userId='"+SpringSecurityUtils.getCurrentUserId()+"' and shareToUserId=YH_DM");
    	List<Map<String,Object>> list = tXtJgManager.getJdbcTemplate().queryForList(sb.toString());
    	model.addAttribute("userlog", list);
    	
    	
    	sb.setLength(0);
    	sb.append("select userId,shareToUserId from t_worklog_schedule_relation where userId='"+SpringSecurityUtils.getCurrentUserId()+"'");
    	//sb.append("select DISTINCT yh_id from t_xt_yh ,t_worklog_relation where userId='"+SpringSecurityUtils.getCurrentUserId()+"' and shareToUserId=YH_DM");
    	list = tXtJgManager.getJdbcTemplate().queryForList(sb.toString());
    	model.addAttribute("schedulelog", list);
    	
    	
    	
        return "admin/guser/profile-sysconfig";
    }
    
    //saveConfig
    @RequestMapping(value="profile-saveConfig", method={RequestMethod.GET, RequestMethod.POST})
	public  String  saveConfig(HttpServletRequest request) throws Exception {
    	String rzUserid = request.getParameter("rzUserid");
    	//String rzUsername = request.getParameter("rzUsername");
    	
    	String jhUserid = request.getParameter("jhUserid");
    	//String jhUsername = request.getParameter("jhUsername");
    	
    	String rcUserid = request.getParameter("rcUserid");
    	//String rcUsername = request.getParameter("rcUsername");
    	
    	
    	StringBuffer sql = new StringBuffer("");
    	try {
    		if(StringHelper.isNotEmpty(rzUserid)){
    			sql.setLength(0);
    			sql.append("delete from t_worklog_relation where userId='"+SpringSecurityUtils.getCurrentUserId()+"'");
    			txtYhManager.getJdbcTemplate().execute(sql.toString());
    			sql.setLength(0);
    			
    			String[] vdatas = rzUserid.split(",");
    			for(String tem:vdatas){
					sql.setLength(0);
					sql.append(" insert into t_worklog_relation values('"+IdGenerator.getInstance().getUniqTime()+"','"+SpringSecurityUtils.getCurrentUserId()+"','"+tem+"')");
					txtYhManager.getJdbcTemplate().execute(sql.toString());
				}
    		}
    		if(StringHelper.isNotEmpty(rcUserid)){
    			sql.setLength(0);
    			sql.append("delete from t_worklog_schedule_relation where userId='"+SpringSecurityUtils.getCurrentUserId()+"'");
    			txtYhManager.getJdbcTemplate().execute(sql.toString());
    			sql.setLength(0);
    			
    			String[] vdatas = rcUserid.split(",");
    			for(String tem:vdatas){
    				sql.setLength(0);
    				sql.append(" insert into t_worklog_schedule_relation values('"+IdGenerator.getInstance().getUniqTime()+"','"+SpringSecurityUtils.getCurrentUserId()+"','"+tem+"')");
    				txtYhManager.getJdbcTemplate().execute(sql.toString());
    			}
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/user/profile-sysconfig.izhbg";
	}
    // ~ ======================================================================

    
    @Resource
    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Resource
    public void setTXtJgManager(TXtJgManager xtJgManager) {
		tXtJgManager = xtJgManager;
	}

	@Resource
	public void setTxtYhManager(TXtYhManager txtYhManager) {
		this.txtYhManager = txtYhManager;
	}
    @Resource
	public void setTXtJgService(TXtJgService xtJgService) {
		tXtJgService = xtJgService;
	}
    @Resource
	public void setTXtJgYhManager(TXtJgYhManager xtJgYhManager) {
		tXtJgYhManager = xtJgYhManager;
	}
    
}
