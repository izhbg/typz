package com.izhbg.typz.sso.auth.service;


public class TXtJgYhService {
	
	
	/*public void buildId() {
		this.duId = MemHelp.MD5Encode( this.yhId + this.jgId );
	}
	
	public static String getJgYhListKey(String jgId )
	{
		return MemHelp.MD5Encode(TXtJgYh.class.getName() + jgId + "getJgYhListKey");
	}
	
	public static String getJgIdByYhIdKey(String yhid )
	{
		return MemHelp.MD5Encode(TXtJgYh.class.getName() + yhid + "getJgIdByYhIdKey");
	}
	
	public static List getYhIdByJgIdFromDb( String jgId, String want )
	{
		List userlist = HibernateUtil.currentSession().find(
				"select a." + want+ " from TXtJgYh a where a.jgId = :jgId")
				.setParameter("jgId", jgId).list(); 
		if(userlist != null && userlist.size()>0){
					QueryCache qc2 = new QueryCache("select a.yhId from TXtYh a where a.yxBj = '1' and a.scBj = '2' and a.yhId in(:userId) order by a.userPriority asc").setParameter("userId", userlist);
					return qc2.list();
		}else{
					return userlist;
		}
		
	}
	
	public static List getJgIdByYhIdFromDb( String yhId, String want )
	{
		return HibernateUtil.currentSession().find(
				"select a." + want+ " from TXtJgYh a where a.yhId = :yhId")
		.setParameter("yhId", yhId).list();
	}
	
	public static List getJgIdByYhId( String yhId )
	{
//		String key = getJgIdByYhIdKey(yhId);
//		List jglist = (List)MemCachedFactory.get( key );
//		if( jglist == null )
//		{
//			jglist = getJgIdByYhIdFromDb(yhId, "jgId");
//			MemCachedFactory.set( key , jglist); 
//		}
//		return jglist;
		return getJgIdByYhIdFromDb(yhId, "jgId");
	}
	
	public static List getYhIdByJgId( String jgId )
	{
		String key = getJgYhListKey(jgId);
		List yhlist = (List) MemCachedFactory.get( key );
		if( yhlist == null )
		{
			yhlist = getYhIdByJgIdFromDb( jgId, "yhId");
			MemCachedFactory.set(key , yhlist);
		}
		return yhlist;
	}

	public static void main(String[] args)
	{
		System.out.println(  getJgIdByYhId("1013") );
	}*/

}
