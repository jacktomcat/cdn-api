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
2、mvc:redirect-view-controller redirect-url

### TODO
####1、国际化
####2、分支项目创建
####3、json、xml同时支持的问题