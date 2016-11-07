package com.izhbg.typz.shop.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.goods.dto.GoodsCommentCount;
import com.izhbg.typz.shop.goods.dto.TShGoodsComment;
import com.izhbg.typz.shop.goods.manager.TShGoodsCommentManager;
import com.izhbg.typz.shop.goods.service.TShGoodsCommentService;
import com.izhbg.typz.shop.member.dto.TShMember;
import com.izhbg.typz.shop.member.manager.TShMemberManager;
@Service
public class TShGoodsCommentServiceImpl implements TShGoodsCommentService {

	@Autowired
	private TShGoodsCommentManager tShGoodsCommentManager;
	@Autowired
	private TShMemberManager tShMemberManager;
	
	@Override
	public void add(TShGoodsComment entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，操作失败");
		tShGoodsCommentManager.save(entity);
	}

	@Override
	public void update(TShGoodsComment entity) throws Exception {
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
	public TShGoodsComment getById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page pageList(Page page) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TShGoodsComment> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page getListByGoodsId(Page page, String goodsId) throws Exception {
		if(StringHelper.isEmpty(goodsId))
			throw new ServiceException("参数为空，操作失败");
		Map<String, Object> map = new HashMap<>();
		map.put("goodsId", goodsId);
		page = tShGoodsCommentManager.pagedQuery(" from TShGoodsComment where goodsId=:goodsId order by time desc ", page.getPageNo(), page.getPageSize(), map);
		List<TShGoodsComment> tShGoodsComments =  (List<TShGoodsComment>) page.getResult();
		TShMember tShMember = null;
		if(tShGoodsComments!=null)
			for(TShGoodsComment tsgc:tShGoodsComments){
				tShMember = tShMemberManager.findUniqueBy("id", tsgc.getMemberId());
				if(tShMember!=null)
				{
					tShMember.setPhone(tShMember.getPhone().substring(0, 4)+"****"+tShMember.getPhone().substring(tShMember.getPhone().length()-3, tShMember.getPhone().length()));
					tsgc.setMember(tShMember);
				}
			}
		page.setResult(tShGoodsComments);
		return page;
	}

	@Override
	public GoodsCommentCount getCountByGoodsId(String goodsId) throws Exception {
		if(StringHelper.isEmpty(goodsId))
			throw new ServiceException("参数为空，操作失败");
		GoodsCommentCount gcc = new GoodsCommentCount();
		String sql = "select COUNT(id) count from t_sh_goods_comment ";
		List<Map<String,Object>> list = tShGoodsCommentManager.getJdbcTemplate().queryForList(sql);
		int count = 0;
		if(list!=null&&list.size()>0) {
			Map<String, Object> map = list.get(0);
			if(map!=null&&map.get("count")!=null){
				count = Integer.parseInt(map.get("count").toString());
				gcc.setTotal(count);
			}
		}
		
		sql = "select COUNT(id) count from t_sh_goods_comment where grade="+Constants.GOODS_COMMENT_0;
		list = tShGoodsCommentManager.getJdbcTemplate().queryForList(sql);
		if(list!=null&&list.size()>0) {
			Map<String, Object> map = list.get(0);
			if(map!=null&&map.get("count")!=null){
				count = Integer.parseInt(map.get("count").toString());
				gcc.setNegative(count);
			}
		}
		sql = "select COUNT(id) count from t_sh_goods_comment where grade="+Constants.GOODS_COMMENT_1;
		list = tShGoodsCommentManager.getJdbcTemplate().queryForList(sql);
		if(list!=null&&list.size()>0) {
			Map<String, Object> map = list.get(0);
			if(map!=null&&map.get("count")!=null){
				count = Integer.parseInt(map.get("count").toString());
				gcc.setPositive(count);
			}
		}
		sql = "select COUNT(id) count from t_sh_goods_comment where grade="+Constants.GOODS_COMMENT_2;
		list = tShGoodsCommentManager.getJdbcTemplate().queryForList(sql);
		if(list!=null&&list.size()>0) {
			Map<String, Object> map = list.get(0);
			if(map!=null&&map.get("count")!=null){
				count = Integer.parseInt(map.get("count").toString());
				gcc.setFavourable(count);
			}
		}
		return gcc;
	}

	@Override
	public boolean isCommonent(String orderId,String goodsId) throws Exception {
		if(StringHelper.isEmpty(orderId,goodsId))
			throw new ServiceException("参数为空，操作失败");
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		map.put("goodsId", goodsId);
		TShGoodsComment tgc = tShGoodsCommentManager.findUnique(" from TShGoodsComment where goodsId=:goodsId and orderId=:orderId", map);
		if(tgc==null)
			return false;
		else
			return true;
	}

}
