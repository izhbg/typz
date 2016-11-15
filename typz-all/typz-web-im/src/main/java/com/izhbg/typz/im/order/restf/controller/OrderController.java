package com.izhbg.typz.im.order.restf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.order.dto.OrderAddress;
import com.izhbg.typz.shop.order.dto.TShOrder;
import com.izhbg.typz.shop.order.service.TShOrderService;
import com.izhbg.typz.shop.store.dto.TShStore;
import com.izhbg.typz.shop.store.service.TShStoreService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/order/")
public class OrderController {
	
	@Autowired
	private TShOrderService tShOrderService;
	@Autowired
	private TShStoreService tShStoreService;
	private BeanMapper beanMapper = new BeanMapper();
	
	/**
	 * 添加订单
	 * @param order
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(@ModelAttribute TShOrder order){
		String result = null;
		if(order==null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, Constants.SYSTEMMSG_EMPTYFILED);
		}else{
			try {
				String id = IdGenerator.getInstance().getUniqTime()+"";
				order.setId(id);
				tShOrderService.add(order);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,id);
			} catch (Exception e) {
				e.printStackTrace();
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
			}
		}
		return result;
	}
	/**
	 * 订单详情
	 * @param orderId
	 * @return
	 */
	@RequestMapping("orderDetail")
	@ResponseBody
	public String orderDetail(@RequestParam(name="orderId",required=true) String orderId){
		String result = null;
		if(StringHelper.isEmpty(orderId)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, Constants.SYSTEMMSG_EMPTYFILED);
		}else
			try {
				TShOrder order = tShOrderService.getById(orderId);
				List<TShOrder> tShOrders = new ArrayList<>();
				tShOrders.add(order);
				List<com.izhbg.typz.im.order.reponse.entity.TShOrder> tShOrders_ = beanMapper.copyList(tShOrders, com.izhbg.typz.im.order.reponse.entity.TShOrder.class);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,tShOrders_==null?null:tShOrders_.get(0));
			} catch (Exception e) {
				e.printStackTrace();
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
			}
		return result;
	}
	/**
	 * 订单确认
	 * @param orderIds
	 * @return
	 */
	@RequestMapping("orderConfirm")
	@ResponseBody
	public String orderConfig(@RequestParam(name="orderIds",required=true) String[] orderIds){
		String result = null;
		try {
			List<TShOrder> orders = tShOrderService.orderConfirm(orderIds);
			List<com.izhbg.typz.im.order.reponse.entity.TShOrder> orders_ = beanMapper.copyList(orders, com.izhbg.typz.im.order.reponse.entity.TShOrder.class);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,orders_);
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}	
		return result;
	}
	/**
	 * 添加或者更新订单收货地址
	 * @param orderAddress
	 * @return
	 */
	@RequestMapping("addOrUpdateOrderAddress")
	@ResponseBody
	public String addOrUpdateOrderAddress(@ModelAttribute OrderAddress orderAddress){
		String result = null;
		try {
			tShOrderService.addOrUpdateOrderAddress(orderAddress);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	/**
	 * 获取消费者订单
	 * @param page
	 * @param memberId
	 * @param status
	 * @return
	 */
	@RequestMapping("getMemberOrder")
	@ResponseBody
	public String getMemberOrder(@ModelAttribute Page page,
								 @RequestParam(name="memberId" ,required=true) String memberId,
								 @RequestParam(name="status" ,required=false) Integer status){
		String result = null;
		try {
			page = tShOrderService.getMemberOrder(page, memberId, status);
			List<TShOrder> tShOrders = (List<TShOrder>) page.getResult();
			List<com.izhbg.typz.im.order.reponse.entity.TShOrder> tShOrders_ = beanMapper.copyList(tShOrders,com.izhbg.typz.im.order.reponse.entity.TShOrder.class);
			page.setResult(tShOrders_);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,page);
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	/**
	 * 获取店铺订单
	 * @param page
	 * @param memberId
	 * @param status
	 * @return
	 */
	@RequestMapping("getStoreOrder")
	@ResponseBody
	public String getStoreOrder(@ModelAttribute Page page,
			 @RequestParam(name="memberId" ,required=true) String memberId,
			 @RequestParam(name="status" ,required=false) Integer status){
		String result = null;
		try {
			TShStore store = tShStoreService.getByMemberId(memberId);
			if(store==null)
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
			else{
				page = tShOrderService.getStoreOrder(page, store.getId(), status);
				List<TShOrder> tShOrders = (List<TShOrder>) page.getResult();
				List<com.izhbg.typz.im.order.reponse.entity.TShOrder> tShOrders_ = beanMapper.copyList(tShOrders,com.izhbg.typz.im.order.reponse.entity.TShOrder.class);
				page.setResult(tShOrders_);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,page);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	/**
	 * 变更订单状态
	 * @param memberId
	 * @param status
	 * @return
	 */
	@RequestMapping("setOrderStatus")
	@ResponseBody
	public String setOrderStatus(@RequestParam(name="memberId" ,required=true) String memberId,
			 					 @RequestParam(name="status" ,required=true) Integer status){
		String result = null;
		try {
			TShStore store = tShStoreService.getByMemberId(memberId);
			if(store==null)
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
			else{
				tShOrderService.setOrderStatus(store.getId(), status);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
	
}
