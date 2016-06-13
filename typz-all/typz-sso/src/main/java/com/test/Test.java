package com.test;

import com.izhbg.typz.base.util.JsonUtil;
import com.izhbg.typz.sso.auth.SpringSecurityUserAuth;

public class Test {
	public static void main(String[] args) {
		String str = "{\"id\":\"2\",\"enabled\":true,\"username\":\"test\",\"appId\":\"1\",\"authorities\":[{\"authority\":\"AUTH_EMPLOYEE\"}],\"treeNode\":{\"icon\":\"\",\"xh\":8,\"other\":\"\",\"children\":[{\"icon\":\"fa-home\",\"xh\":1,\"other\":\"\",\"children\":[],\"isParent\":\"false\",\"code\":\"firstpage\",\"pcode\":\"c78657w5m0vg781mw5m1\",\"sqbj\":1,\"url\":\"main/common.izhbg\",\"lx\":\"\",\"open\":\"true\",\"name\":\"首页\",\"checked\":\"false\"}],\"isParent\":\"true\",\"code\":\"c78657w5m0vg781mw5m1\",\"pcode\":\"-1\",\"sqbj\":1,\"url\":\"\",\"lx\":\"\",\"open\":\"true\",\"name\":\"系统功能\",\"checked\":\"false\"},\"roles\":[{\"role\":\"2\"}],\"permissions\":[{\"JS_DM\":\"2\",\"GNZY_DM\":\"firstpage\",\"uuid\":\"10\",\"is_read\":1,\"is_create\":1,\"is_update\":1,\"is_delete\":1,\"is_all\":1}],\"depName\":\"测试机构\",\"displayName\":\"test\",\"accountNonExpired\":true,\"password\":\"761dee303f4545c5c9db8ff6bd297032\",\"depId\":\"1456209358815\",\"credentialsNonExpired\":true,\"accountNonLocked\":true}";
		
		SpringSecurityUserAuth ssu = JsonUtil.fromJson(str, SpringSecurityUserAuth.class);
		System.out.println(ssu.getAuthorities());
	}
}
