package com.izhbg.typz.shop.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.member.dto.TShMember;
import com.izhbg.typz.shop.member.manager.TShMemberManager;
import com.izhbg.typz.shop.member.service.TShMemberService;
/**
 * 
* @author xiaolong.cai@mtime.com
* @date 2016年10月2日 上午11:50:04 
* @version V1.0
 */
@Service
public class TShMemberServiceImpl implements TShMemberService{
	Logger logger = LoggerFactory.getLogger(TShMemberServiceImpl.class);
	private BeanMapper beanMapper = new BeanMapper();
	@Autowired
	private TShMemberManager tShMemberManager;
	
	@Override
	public void add(TShMember entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空,添加会员信息失败");
		tShMemberManager.save(entity);
	}

	@Override
	public void update(TShMember entity) throws Exception {
		if(entity==null||StringHelper.isEmpty(entity.getId()))
			throw new ServiceException("参数为空,更新会员信息失败");
		TShMember tShMember = tShMemberManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, tShMember);
		tShMemberManager.update(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，删除会员信息失败");
		tShMemberManager.removeById(id);
	}

	@Override
	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除会员信息失败");
		for(String id:ids)
			this.delete(id);
	}

	@Override
	public TShMember getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取会员信息失败");
		
		return tShMemberManager.findUniqueBy("id", id);
	}

	@Override
	public Page pageList(Page page) throws Exception {
		Map<String,Object> map = new HashMap<>();
		return tShMemberManager.pagedQuery("from TShMember ", page.getPageNo(), page.getPageSize(), map);
	}

	@Override
	public List<TShMember> getAll() throws Exception {
		return tShMemberManager.getAll();
	}

	@Override
	public boolean existPhone(String phone) throws Exception {
		TShMember tShMember = tShMemberManager.findUniqueBy("phone", phone);
		if(tShMember==null)
			return false;
		else
			return true;
	}

	@Override
	public TShMember getTShMemberByPhone(String phone) throws Exception {
		if(StringHelper.isEmpty(phone))
			throw new ServiceException("参数为空,获取会员信息失败");
		
		TShMember tShMember = tShMemberManager.findUniqueBy("phone", phone);
		return tShMember;
	}

}
