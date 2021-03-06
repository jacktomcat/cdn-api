cdn-api
===============

### 多环境打包项目
mvn clean package -P prod
```
<profiles>
		<!-- 测试环境 -->
		<profile>
			<id>test</id>
			<properties>
				<running.env>test</running.env>
			</properties>
			<activation>
			   <activeByDefault>true</activeByDefault>
			</activation>
		</profile>
<!-- 生产环境 -->
		<profile>
			<id>prod</id>
			<properties>
				<running.env>prod</running.env>
			</properties>
		</profile>
	</profiles>
	
	<build>
		<finalName>cdn-api</finalName>
		<resources>
		   <resource>
		      <directory>src/main/resources</directory>
		      <excludes>
		        <exclude>test/*</exclude>
		        <exclude>prod/*</exclude>
		      </excludes>
		   </resource>
		   <resource>
		     <directory>src/main/resources/${running.env}</directory>
		   </resource>
		</resources>
	</build>
```

### spring要点
1、@Lazy(false) bean的加载时机 <br>
2、mvc:redirect-view-controller redirect-url<br>
3、<br/>
   + 基于cookie的国际化操作，cdn-api/index -> index.jsp 中英文切换<br/>
   + 后台业务代码的方式获取国际化资源文件<br/>
   + Locale 语言设置<br/>
   + TimeZone时区设置<br/>
   
4、spring bean scope(singleton,request,prototype,session) 区别 -> IndexController 可以使用ab测试<br/>
5、spring @Value 在properties文件里面对 ``String```,  ``` Array ```, ```List```, ``` Map``,  的动态解析的支持<br/>
6、抽象类的abstract
```
<bean id="wrapperVideo" abstract="true" class="com.gochinatv.cdn.api.test.service.WrapperVideo" init-method="init"/>
```


### git分支创建并合并
```
1、Team -> Switch To -> New Brach(输入名称，创建，提交) -> 分支操作(此时master主干也可以操作)并分别提交至各自的分支
2、切换至master -> Team -> Merge -> (将分支信息合并到主干，进行merge操作，选择local-->我最后一次提交的分支，点击merge即可)、Add to Index
3、这个时候通常情况下是没有错误的，接下来要进行一步操作，很危险，就是将最新版本的master给pull下来；修改完冲突之后，进行提交，并推送到主干即可
```

###同时支持json、xml输出  
```
需要在spring-servlet.xml中配置消息转换器xml、json
http://localhost:8080/cdn-api/output_format   输出xml
http://localhost:8080/cdn-api/output_format.json  输出json
基于以上访问的方式，有个不好的地方需要切换url地址！！！！！！

那么我们可以，访问：http://localhost:8080/cdn-api/output_format
在header中指定请求的类型：
Accept application/xml    输出xml
Accept application/json   输出json
```

### Guava 
 + LoadingCache 本地缓存的支持  #http://blog.csdn.net/u011012826/article/details/45746505

### 其他
 + ajax 非responseBody形式返回，而是返回jsp页面的形式index.jsp -> ajax_result.jsp
 + hashset 测试对象是否有重复，重写hashcode和equals方法  -> 测试用例TestSetUser.java
 + java深度copy 嵌套对象的hashcode，equals  com.gochinatv.cdn.api.test.maincase.EntityDepthComparison
 + java的 addShutdownHook 现在为止还有一个bug未解决 参见：com.gochinatv.cdn.api.framework.HookThreadService、HookJVMThread


### TODO
```
ServletRequestListener request 模式监控 通过浏览器request的创建和销毁
jvm 堆内存，栈内存溢出解决方案
zookeeper 主从的task任务测试
read write lock synchronized 测试区别
```

####1、spring shiro cas单点登录