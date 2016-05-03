package com.izhbg.typz.sso.auth.service;


public class TXtYhGnjsService {
	/*public static List<String> getJsListByHyIdFromDb( String yhid, String want)
	{
		List<String> sl = new QueryCache(
				"select a.jsDm from TXtYhGnjs a where a.yhId = :yhId")
				.setParameter("yhId", yhid).list();
		List<String> rl = new QueryCache("select a.gnjsDm from TXtGnjs a where a.gnjsDm in (:sl) and a.yxBj=1")
			.setParameter("sl", sl).list();
		return rl;
	}
	
	
	public static List<String> getUserIdListByRoleIdFromDb( String roleId)
	{
		List<String> sl = new QueryCache(
				"select a.yhId from TXtYhGnjs a where a.jsDm = :roleId")
				.setParameter("roleId", roleId).list();
		return sl;
	}
	public static void removeJsListByHyIdCache( String yhid ) {
		String key = getYhJsListKey(yhid);
		MemCachedFactory.delete( key );
	}
	public static List<String> getJsListByHyId( String yhid )
	{
		String key = getYhJsListKey(yhid);
		List rolelist = (List) MemCachedFactory.get( key );
		if( rolelist == null )
		{
			rolelist = getJsListByHyIdFromDb(yhid, "jsDm");
			MemCachedFactory.set(key , rolelist);
		}
//		List rolelist = getJsListByHyIdFromDb(yhid, "jsDm");
		return rolelist;
	}
	public static List getJsListByYhIdAppId( String yhid, String appId)
	{
		List sl = new QueryCache(
				"select a.jsDm from TXtYhGnjs a where a.yhId = :yhId")
				.setParameter("yhId", yhid).list();
		List rl = new QueryCache("select a.gnjsDm from TXtGnjs a where a.gnjsDm in (:sl) and a.appId=:appId and a.yxBj=1")
			.setParameter("sl", sl).setParameter("appId", appId).list();
		return rl;
	}
	public static void main(String[] args)
	{
		System.out.println(MemHelp.MD5Encode("1" + "bzb") );
			
	}*/
}
