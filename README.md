#typz（后台管理框架 之 权限管理）
基于 spring + springmvc + hibernate + springsecurity + bootstrap3.3+mysql 后台管理框架。

模块如下：

1、用户管理

2、机构管理

3、角色管理

4、功能管理

5、资源管理

通过给用户分配角色，角色拥有 功能和资源权限，双重认证后才有权限访问某资源路径。

其中数据库脚本采用 flyway 管理版本，

目前仅支持 mysql 的脚本处理，

#交流群号：462788325
欢迎加入，

#运行方式

1、用git克隆，或者直接下载zip

2、在IDE等开发工具中 作为maven工程导入

3、配置 type-web 中的 application.properties 数据库信息

4、根据3的配置 新建一个空的数据库 

5、install type-all 然后 tomcat:run type-web

6、http://localhost:8080/type-web


demo地址：http://www.izhbg.com/typz-web     (yuewuxing/123456   test/123456)

实例地址：http://www.izhbg.com/izhbg-test   （admin/123456      test/123456）

注：系统部分功能参考 lemon 开源OA。该程序开源 纯属技术交流与学习，如有涉及侵权等操作麻烦告知,会及时处理掉。