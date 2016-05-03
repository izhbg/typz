package com.izhbg.typz.sso.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.internal.util.StringHelper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.izhbg.typz.sso.auth.dto.TXtGnzy;

public class TreeNodeUtil {
	  private JdbcTemplate jdbcTemplate;
	  private String userid;
	  public TreeNodeUtil(JdbcTemplate jdbcTemplate,String userid){
		  this.jdbcTemplate = jdbcTemplate;
		  this.userid = userid;
	  }
	 //构造左侧树结构
    public List getGNZYList()
	{
		List gnlist = new ArrayList();
		List l = getJsListByHyId(userid);
		if(l != null) {
			for( int i=0; i<l.size(); ++i )
			{
				List ltmp = getGnListByJsDm(l.get(i).toString());
				if(ltmp != null)
					gnlist.addAll(ltmp);
			}
			List needtop = new ArrayList<String>();
			for( int i=0; i<gnlist.size(); ++i)
			{ 
				TXtGnzy gn =  findById(gnlist.get(i).toString());//QueryCache.get(TXtGnzy.class, gnlist.get(i).toString());
				if(gn != null)
					setTopToList(needtop,gn);
			}		
			gnlist.addAll(needtop);
		}
		return gnlist; 
	}
    public List getJsListByHyId(String yhid ){
		List rolelist = getJsListByHyIdFromDb(yhid, "js_dm");
		return rolelist;
	}
	public  List<String> getGnListByJsDm(String jsDm )
	{
		List gnlist = getGnListByJsDmFromDb(jsDm,"gnzy_dm");
//			List gnlist = getGnListByJsDmFromDb(jsDm,"gnzyDm");
		return gnlist;
	}
	public List<String> getGnListByJsDmFromDb( String jsDm, String want)
	{
		List<Map<String, Object>> sl = jdbcTemplate.queryForList("select a.gnzy_dm from t_xt_gnjs_zy a where a.js_dm = ? ", jsDm);//new QueryCache(
				//"select a.gnzyDm from TXtGnjsZy a where a.jsDm = :jsDm")
				//.setParameter("jsDm", jsDm).list();
		StringBuffer sb = new StringBuffer("select a.gn_dm from t_xt_gnzy a where a.yx_bj=?");// a.gnDm in (?)
		String sql = sb.toString();
		if(sl!=null&&sl.size()>0)
		{
			sb.append(" and (");
			for(Map s:sl){
				sb.append(" a.gn_dm='"+s.get("gnzy_dm")+"' "+" or");
			}
			sql = sb.substring(0, sb.length()-2)+")";
		}else{
			sb.append(" and a.gn_dm=''");
			sql = sb.toString();
		}
		List<Map<String, Object>> rl = jdbcTemplate.queryForList(sql+" order by a.gn_xh asc", 1);//new QueryCache("select a.gnDm from TXtGnzy a where a.gnDm in (:param1) and a.yxBj=1")
			//.setParameter("param1", sl).list();
		List list = new ArrayList();
		if(rl!=null&&rl.size()>0)
		for(Map map:rl){
			list.add(map.get("gn_dm"));
		}
		return list;
	}
	public  List<String> getJsListByHyIdFromDb( String yhid, String want)
	{
		List<Map<String, Object>> sl = jdbcTemplate.queryForList("select a.js_dm from t_xt_yh_gnjs a where a.yh_id = ?", yhid);//new QueryCache(
				//"select a.jsDm from TXtYhGnjs a where a.yhId = :yhId")
				//.setParameter("yhId", yhid).list();
		StringBuffer sb = new StringBuffer("select a.gnjs_dm from t_xt_gnjs a where a.yx_bj=? " );
		String sql = sb.toString();
		List list = new ArrayList();
		if(sl!=null&&sl.size()>0)
		{
			sb.append(" and (");
			for(Map  s:sl){
				sb.append(" a.gnjs_dm='"+s.get("js_dm")+"' "+" or");
			}
			sql = sb.substring(0, sb.length()-2)+")";
			
			List<Map<String, Object>> rl = jdbcTemplate.queryForList(sql, 1); //new QueryCache("select a.gnjsDm from TXtGnjs a where a.gnjsDm in (:sl) and a.yxBj=1")
			//.setParameter("sl", sl).list();
			if(rl!=null&&rl.size()>0)
			for(Map map:rl){
				list.add(map.get("gnjs_dm"));
			}
		}
		return list;
	}
	public void setTopToList( List all,TXtGnzy gn )
	{
		TXtGnzy top = gn;
		while( top != null )
		{
			if( !all.contains(top.getGnDm()) )
			{
				all.add( top.getGnDm() );
			}
			if( StringHelper.isEmpty(top.getSjgnDm()) || top.getSjgnDm().equals("-1"))
			{
				top = null;
			}else
			{ 
				try {
					top = findById(top.getSjgnDm());//QueryCache.get(TXtGnzy.class, top.getSjgnDm());
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
				
			}
		}
	} 
	public TreeNode getTreeNode(){
		List<TXtGnzy> list = new ArrayList<TXtGnzy>();
		List list_temp = getGNZYList();
		if(list_temp!=null&&list_temp.size()>0){
			for(Object obj:list_temp){
				if(obj!=null){
					list.add(findById(obj.toString()));
				}
			}
		}
		try {
		ComparatorTXtGnzy comparator=new ComparatorTXtGnzy();
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Collections.sort(list, comparator);
		}catch(Exception e) {}
		return TreeNode.Bulid(list);
	}
	public TXtGnzy findById(String id){
		String sql = "select gn_dm,gn_mc,gn_lx,sjgn_dm,url,gn_xh,yx_bj,sq_bj," +
				"app_id,gn_ls,bz,gn_icon,newurl from t_xt_gnzy where gn_dm=?";
		
		List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql,id);
		TXtGnzy tXtGnzy = null;
		if(list!=null&&list.size()>0)
			tXtGnzy = convertTXtGnzy(list.get(0));
		return tXtGnzy;
	}
	 private String getValue(Object value) {
	        if (value == null) {
	            return null;
	        }

	        return value.toString();
	    }
	 private Integer getIntegerValue(Object value) {
		 if (value == null) {
			 return null;
		 }
		 
		 return Integer.valueOf(value.toString());
	 }
	 
	protected TXtGnzy convertTXtGnzy(Map<String, Object> map) {
        if ((map == null) || map.isEmpty()) {
            return null;
        }
        TXtGnzy tXtGnzy = new TXtGnzy();
        tXtGnzy.setAppId(this.getValue(map.get("app_id")));
        tXtGnzy.setBz(this.getValue(map.get("bz")));
        tXtGnzy.setGnDm(this.getValue(map.get("gn_dm")));
        tXtGnzy.setGnIcon(this.getValue(map.get("gn_icon")));
        tXtGnzy.setGnLs(this.getValue(map.get("gn_ls")));
        tXtGnzy.setGnLx(this.getIntegerValue(map.get("gn_lx")));
        tXtGnzy.setGnMc(this.getValue(map.get("gn_mc")));
        tXtGnzy.setGnXh(this.getIntegerValue(map.get("gn_xh")));
        tXtGnzy.setNewurl(this.getValue(map.get("newurl")));
        tXtGnzy.setSjgnDm(this.getValue(map.get("sjgn_dm")));
        tXtGnzy.setSqBj(this.getIntegerValue(map.get("sq_bj")));
        tXtGnzy.setUrl(this.getValue(map.get("url")));
        tXtGnzy.setYxBj(this.getIntegerValue(map.get("yx_bj")));

        return tXtGnzy;
    }
    

}
