package com.izhbg.typz.shop.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.order.service.TShOrderService;
import com.izhbg.typz.shop.store.dto.TShStore;

@Controller
@RequestMapping("/store")
public class OrderController {
	@Autowired
	private TShOrderService tShOrderService;
	/**
	 * 所有的订单列表
	 * @param page
	 * @param tShStore
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("store_order_list")
	public String list(@ModelAttribute  Page page,
	           		@ModelAttribute TShStore tShStore, Model model) throws Exception {
		page = tShOrderService.pageList(page);
		model.addAttribute("page", page);
		model.addAttribute("tShStore", tShStore);
		return "shop/store/store-order-list";
	}
	
}
