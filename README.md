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


### git分支创建并合并
```
1、Team -> Switch To -> New Brach(输入名称，创建，提交) -> 分支操作（此时master主干也可以操作）并分别提交至各自的分支
2、切换至master -> Team -> Merge -> 选择分支版本 即可进行合并，（Add to Index）如果出现冲突解决冲突
```

### TODO
####1、svn分支项目创建
####2、json、xml同时支持的问题
####3、controller测试单例和多例模式