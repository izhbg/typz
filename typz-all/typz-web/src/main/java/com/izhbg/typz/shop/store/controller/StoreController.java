package com.izhbg.typz.shop.store.controller;

import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.goods.dto.TShGoods;
import com.izhbg.typz.shop.store.dto.TShStore;
import com.izhbg.typz.shop.store.dto.TShStoreAttachefile;
import com.izhbg.typz.shop.store.service.TShStoreAttacheFileService;
import com.izhbg.typz.shop.store.service.TShStoreService;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/store")
public class StoreController {
	
	@Autowired
	private TShStoreService tShStoreService;
	@Autowired
	private TShStoreAttacheFileService tShStoreAttacheFileService;
	/**
	 * 店铺列表
	 * @param page
	 * @param tShStore
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("store_list")
	public String list(@ModelAttribute  Page page,
	           		@ModelAttribute TShStore tShStore, Model model) throws Exception {
		Page page_ = tShStoreService.pageList(page,tShStore);
		model.addAttribute("page", page_);
		model.addAttribute("tShStore", tShStore);
		return "shop/store/store_list";
	}
	/**
	 * 编辑店铺
	 * @param tShStore
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("store_edit")
	public String edit(@ModelAttribute TShStore tShStore,Model model) throws Exception{
		if(tShStore!=null&&StringHelper.isNotEmpty(tShStore.getId())){
			tShStore = tShStoreService.getById(tShStore.getId());
			TShStoreAttachefile logoAttache = tShStoreAttacheFileService.getIndexAttacheFile(tShStore.getId());
			if(logoAttache!=null)
				tShStore.setLogoAttache(logoAttache);
			List<TShStoreAttachefile> tShStoreAttachefiles = tShStoreAttacheFileService.getStoreAttacheFile(tShStore.getId());
			if(tShStoreAttachefiles!=null)
				tShStore.settShStoreAttachefiles(tShStoreAttachefiles);
			model.addAttribute("isUpdate", true);
		}else{
			tShStore = new TShStore();
			tShStore.setId(IdGenerator.getInstance().getUniqTime()+"");
		}
		model.addAttribute("tShStore", tShStore);
		return  "shop/store/store_edit";
	}
	/**
	 * 删除店铺
	 * @param checkdel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("store_delete")
	@ResponseBody
	@POST
	public String delete(String[] checkdel) throws Exception{
		String result="";
		if(checkdel == null || checkdel.length < 1){
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
		}
		tShStoreService.deleteBatche(checkdel);
		result = "sucess";
		return result;
	}
	/**
	 * 保存店铺信息
	 * @param tShStore
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("store_save")
	public String save(@ModelAttribute TShStore tShStore) throws Exception{
		if(StringHelper.isEmpty(tShStore.getId())){
			tShStore.setId(IdGenerator.getInstance().getUniqTime()+"");
			tShStore.setAddTime(new Date());
			tShStoreService.add(tShStore);
		}else{
			if(tShStoreService.getById(tShStore.getId())==null){
				tShStore.setAddTime(new Date());
				tShStore.setSqTime(new Date());
				tShStoreService.add(tShStore);
			}
			else{
				tShStore.setSqTime(new Date());
				tShStoreService.update(tShStore);
			}
		}
		
		return "redirect:/store/store_list.izhbg";
	}
	/**
	 * 批量审核通过
	 * @param checkdel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("store_batchPassStore")
	@ResponseBody
	public String BatchPassStore(String[] checkdel) throws Exception{
		String result="";
		if(checkdel == null || checkdel.length < 1){
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
		}
		tShStoreService.verifyPassOn(checkdel);
		result = "sucess";
		return result;
	}
	/**
	 * 批量审核通过
	 * @param checkdel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("store_batchNoPassStore")
	@ResponseBody
	public String BatchNoPassStore(String[] checkdel) throws Exception{
		String result="";
		if(checkdel == null || checkdel.length < 1){
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
		}
		tShStoreService.verifyPassError(checkdel);
		result = "sucess";
		return result;
	}
}
