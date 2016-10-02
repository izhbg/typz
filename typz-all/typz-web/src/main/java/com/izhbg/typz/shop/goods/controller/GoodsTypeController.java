package com.izhbg.typz.shop.goods.controller;

import java.util.List;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.common.service.ControllerException;
import com.izhbg.typz.base.util.JsonUtil;
import com.izhbg.typz.shop.goods.dto.TShGoodsType;
import com.izhbg.typz.shop.goods.service.TShGoodsTypeService;

/**
 * 
* @ClassName: GoodsTypeController 
* @Description: 产品类目controller
* @author caixl 
* @date 2016-6-29 下午1:48:23 
*
 */
@Controller
@RequestMapping("/goods_type")
public class GoodsTypeController {
	
	@Autowired
	private TShGoodsTypeService tShGoodsTypeService;
	
	@RequestMapping(value="/getTypeByPid_json",method=RequestMethod.GET)
	@ResponseBody
	public String getTypeByPid_json(@RequestParam String pid){
		if(StringHelper.isEmpty(pid))
			throw new ControllerException("参数为空,获取类目信息失败");
		String result = "";
		try {
			List<TShGoodsType> tShGoodsTypes = tShGoodsTypeService.getListByPid(pid);
			result = JsonUtil.toJsonResult(tShGoodsTypes, 1, "操作成功");
		} catch (Exception e) {
			throw new ControllerException("获取类目信息失败");
		}
		return result;
	}
}
