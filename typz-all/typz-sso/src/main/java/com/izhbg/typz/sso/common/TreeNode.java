package com.izhbg.typz.sso.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.internal.util.StringHelper;

import com.izhbg.typz.sso.auth.dto.TXtGnzy;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtYh;


public class TreeNode implements Serializable, Comparable<TreeNode> {
	private String name;
	private String url;
	private String code;
	private String pcode;
	private Integer xh;
	private String lx;
	private String icon;
	private String isParent = "false";
	private String checked = "false";
	private String open = "true";
	private String other;
	private Integer sqbj;

	private List<TreeNode> childs = new ArrayList<TreeNode>();

	public TreeNode(Object object) {
		if (object instanceof TXtGnzy) {
			TXtGnzy gnzy = (TXtGnzy) object;
			this.code = gnzy.getGnDm();
			this.pcode = gnzy.getSjgnDm();
			this.name = gnzy.getGnMc();
			this.url = gnzy.getNewurl();
			this.xh = gnzy.getGnXh();
			this.icon = gnzy.getGnIcon();
			this.other = gnzy.getBz();
			this.sqbj = gnzy.getSqBj();

		} else if (object instanceof TXtJg) {
			TXtJg jg = (TXtJg) object;
			this.code = jg.getJgId();
			this.pcode = jg.getSjjgId();
			this.name = jg.getJgMc();
			if (jg.getXh()!=null) {
				this.xh = jg.getXh();
			}
		} else if( object instanceof TXtYh )
		{
			TXtYh yh = (TXtYh)object;
			this.code = yh.getYhId();
			this.name = yh.getYhMc();
		}else {
			System.out.println(" ");
		}

	}

	public TreeNode findSubTreeNode(String key) {
		if (this.code.equalsIgnoreCase(key)) {
			return this;
		}
		TreeNode r = null;
		for (TreeNode node : getChildren()) {
			r = node.findSubTreeNode(key);
			if (r != null) {
				return r;
			}
		}
		return null;
	}

	public static TreeNode Bulid(List funcList) {
		TreeNode root = null;
		HashMap<String, TreeNode> todelete = new HashMap<String, TreeNode>();
		HashMap<String, TreeNode> all = new HashMap<String, TreeNode>();
		TreeNode node = null;
		for (int i = 0; i < funcList.size(); i++) {
			if (funcList.get(i) != null) {
				node = new TreeNode(funcList.get(i));
				all.put(node.getCode(), node);
				todelete.put(node.getCode(), node);
			}
		}

		int oldsize = 0;
		Object[] keys = null;
		while (true) {
			keys = todelete.keySet().toArray();
			oldsize = keys.length;
			for (int i = 0; i < keys.length; ++i) {
				node = (TreeNode) todelete.get(keys[i]);
				if (node != null && StringHelper.isNotEmpty(node.getPcode())) {
					TreeNode p = (TreeNode) all.get(node.getPcode());
					if (p != null) {
						p.insertChilds(node);
						todelete.remove(node.getCode());
					}
				}
			}

			if (todelete.size() == oldsize) {
				if (todelete.size() == 1) {
					root = (TreeNode) todelete.get(keys[0]);
				} else {
					root = new TreeNode(null);
					root.setName("top");
					root.setIsParent("true");
					keys = todelete.keySet().toArray();
					for (int i = 0; i < keys.length; ++i) {
						node = (TreeNode) todelete.get(keys[i]);
						root.insertChilds(node);
					}
				}
				all.clear();
				todelete.clear();
				root.Sort();
				
				break;
			}
		}
		return root;
	}

	public int compareTo(TreeNode o) {
		int flag = -1;
		
		if(this.getXh()!=null&&o.getXh()!=null)
		{
			if(this.getXh()>o.getXh()){
				flag = 1;
			}
		}
		if(this.getXh()==null&&o.getXh()!=null)
			flag = 1;
		
		return flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public List<TreeNode> getChildren() {
		return childs;
	}

	public void insertChilds(TreeNode childs) {
		this.childs.add(childs);
	}

	public String getIsParent() {
		return  this.childs.size() == 0 ? "false" : "true" ;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public void Sort() {
		try {
			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
			Collections.sort(childs);
			for (int i = 0; i < childs.size(); ++i) {
				childs.get(i).Sort();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONObject toJson() {
		return new JSONObject().fromObject(this);
	}

	/**
	 * 转换为jsonArray
	 * 
	 * @param list
	 * @return
	 */
	public static JSONArray toJsonArray(List<TreeNode> list) {
		JSONArray jsonArray = new JSONArray();
		for (TreeNode g : list) {
			jsonArray.add(g.toJson());
		}
		return jsonArray;
	}
/*
	public static TreeNode getTreeNodeYhByGw( String gw ){ 
		return getTreeNodeYh(TXtYhGw.getYhListByGwId( gw ));
		
	}*/
	public static List getNotRepeatList(List list){
		List resultList = new ArrayList();
		if(list==null){
			return resultList;
		}
		for(int i=0;i<list.size();i++){
			if(resultList.contains(list.get(i))){
				continue;
			}else{
				resultList.add(list.get(i));
			}
		}
		return resultList;
	}
	
	/*public static TreeNode getTreeNodeYh( List userList){
		TXtYh yh = null;
		List<String> jglist = new ArrayList<String>();
		//排除一下重复的人员
		userList = getNotRepeatList(userList);
		for (int i = 0; i < userList.size(); ++i) {
			yh = QueryCache.get(TXtYh.class, userList.get(i).toString());
			if( yh != null )
			{
				//兼容 yh中有删除标记的人员 取机构时空指针错误
				try{
					jglist.add( yh.getYhJG().getJgId() );
				}catch(Exception e){
					System.out.println("dirty Data:" + yh.getYhDm()+"缺少jgId.....");				
				}
			} 			
		}		
		TXtJg jg = null;
		for( int i=0; i<jglist.size(); ++i )
		{
			try {

				jg = QueryCache.get(TXtJg.class, jglist.get(i).toString());
			} catch (ClassCastException e) {
				System.out.println( jglist.get(i).toString() + " <-id key->" +  QueryCache.Key(TXtJg.class.getName(), jglist.get(i).toString()) + " " + jg  );
			}
			jg.setTopToList( jglist );
		} 

		TreeNode node = TreeNode.Bulid( QueryCache.idToObj(TXtJg.class, jglist));
		TreeNode sub = null;
		TreeNode yhnode = null;
		for( int i=0; i<userList.size(); ++i )
		{
			yh = QueryCache.get(TXtYh.class, userList.get(i).toString());
			if( yh != null )
			{
				//兼容 yh中有删除标记的人员 取机构时空指针错误
				try{
					
					sub = node.findSubTreeNode( yh
							.getYhJG().getJgId());
				}catch(Exception e){
					System.out.println("dirty Data:" + yh.getYhDm()+"缺少jgId.....");
				}
			yhnode = new TreeNode(yh) ;
			yhnode.setPcode(  sub.getCode() );
			sub.insertChilds(yhnode);
			}
		}
		return node;
	}*/
	
	public void setSubNodeChecked( boolean selected )
	{ 
		if(selected && this.childs.size()==0){
			this.checked = Boolean.toString(selected);
		}
		for( int i=0; i<childs.size(); ++i )
		{
			childs.get(i).setSubNodeChecked(selected);
		}
	}
	public void setSubNodeOpen( boolean isOpen )
	{ 
		if(isOpen && this.childs.size()==0){
			this.open = Boolean.toString(isOpen);
		}
		for( int i=0; i<childs.size(); ++i )
		{
			childs.get(i).setSubNodeOpen(isOpen);
		}
	}

	 
	public static void main(String[] args)
	{
//		TreeNode node = getTreeNodeYhByGw( "" );
//		System.out.println( node );
		List list = new ArrayList();
		list.add("111");
		list.add("222");
		list.add("333");
		list.add("111");
		System.out.println(getNotRepeatList(list).size());
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Integer getSqbj() {
		return sqbj;
	}

	public void setSqbj(Integer sqbj) {
		this.sqbj = sqbj;
	}
	
	
}
