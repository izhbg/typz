package com.izhbg.typz.shop.person.service;

import java.util.List;

import com.izhbg.typz.shop.common.service.BaseService;
import com.izhbg.typz.shop.person.dto.TShPersonAddress;
/**
 * 
* @author xiaolong.cai@mtime.com
* @date 2016年9月26日 下午5:17:39 
* @version V1.0
 */
public interface TShPersonAddressService extends BaseService<TShPersonAddress>{
	/**
	 * 获取用户收货地址列表
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<TShPersonAddress> getPersonAddressByYhId(String id) throws Exception;
	/**
	 * 设置默认 收货地址
	 * @param id
	 * @throws Exception
	 */
	public void setDefalutAddress(String id,String yhId) throws Exception;
	
}
