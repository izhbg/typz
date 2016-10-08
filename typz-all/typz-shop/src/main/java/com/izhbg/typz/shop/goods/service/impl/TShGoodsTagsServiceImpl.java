package com.izhbg.typz.shop.goods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.goods.dto.TShGoodsTags;
import com.izhbg.typz.shop.goods.manager.TShGoodsTagsManager;
import com.izhbg.typz.shop.goods.service.TShGoodsTagsService;

@Service("tShGoodsTagsService")
public class TShGoodsTagsServiceImpl implements TShGoodsTagsService{

	@Autowired
	private TShGoodsTagsManager tShGoodsTagsManager;
	@Override
	public void add(TShGoodsTags entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TShGoodsTags entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBatche(String[] ids) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TShGoodsTags getById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page pageList(Page page) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TShGoodsTags> getAll() throws Exception {
		return tShGoodsTagsManager.getAll();
	}

}
