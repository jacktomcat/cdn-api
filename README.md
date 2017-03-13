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
3、基于cookie的国际化操作，cdn-api/index -> index.jsp 中英文切换
4、spring bean scope(singleton,request,prototype,session) 区别 -> IndexController 可以使用ab测试


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

### TODO
```
ServletRequestListener request 模式监控 通过浏览器request的创建和销毁
```
####1、spring shiro cas单点登录
####