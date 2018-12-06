
# spring-source-code-learning

spring 源码学习，使用的版本为 `5.0.6.RELEASE`，对源码进行注释，方便理解及扩展。同时会记录一些常用的工具类，方便开发中使用。


### 环境准备

#### 安装 gradle

由于 `spring` 使用 [gradle](https://gradle.org/install/)，所以需要先安装，[点我下载](https://gradle.org/next-steps/?version=4.10.3&format=all)，完成后解压。添加环境变量`GRADLE_HOME\bin`。配置完成后，命令行测试 `gradle -version` 出现版本则代表安装成功。

#### 下载 spring

使用 `git clone` 速度过慢，所以直接下载 [zip](https://github.com/spring-projects/spring-framework/archive/master.zip)，解压后在 `spring-framework` 目录下执行执行 `gradle cleanidea eclipse` 进行转换。转换完成后会发现各个目录下已经出现了 `Eclipse` 工程所必须的 `.project` 和 `.classpath` 文件。

选择 `spring-context` 导入以后发现依赖了其他 6 个 jar 包，可以选择参考如上方式编译导入或者直接以 jar 包的形式添加进来，取决于你是否想编辑该代码。这里我选择编译了这些源代码。

![](https://i.imgur.com/PCfN69y.png)

导入后依然会报错，提示缺少 `spring-cglib-repack-3.2.9.jar` 和 `spring-objenesis-repack-3.0.1.jar` 。解决方案，在 `spring-framework` 目录下执行 `gradle objenesisRepackJar` 和 `gradle cglibRepackJar` 命令，会在 `Spring-framework\spring-core\build\libs` 目录下生成 jar 包。

依次导入后如下：

![](https://i.imgur.com/B0TphQY.png)

依然报错 `GroovyDynamicElementReader cannot be resolved to a type`，需要安装一个 eclipse 的 groovy 插件，

step 1：检查自己的 eclipse 版本：在 help->About Eclipse 中查看：
step 2：进入 https://github.com/groovy/groovy-eclipse/wiki 查找和自己 eclipse 对应版本的 groovy

在 eclipse的 `Help -> Install New Software` 中，添加 `groovy` 的下载链接: http://dist.springsource.org/milestone/GRECLIPSE/这里不同的 eclipse 对应版本也不同/ 全选后，开始下载，下载完成，重启 eclipse，然后 clean 一下项目就搞定了。

注意：一定要安装对应版本的插件，否则无效。

### 源码解析

// TODO