

### refresh 流程

单元测试的类均在 [此目录](https://github.com/pleuvoir/spring-source-code-learning/blob/master/spring-context/src/test/java/io/github/pleuvoir/mine) 下

该方法为典型的模版方法

```java
public void refresh() throws BeansException, IllegalStateException {
	synchronized (this.startupShutdownMonitor) {
		// 1. 准备刷新的上下文环境
		prepareRefresh();
	
		// 2. 初始化 beanFactory 并进行 XML 文件读取，
		//    这里会在父类构造方法中偷偷创建创建 DefaultListableBeanFactory 对象
		// public GenericApplicationContext() {
		//		this.beanFactory = new DefaultListableBeanFactory();
		// }
		ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
	
		// 3. 对 beanFactory 进行功能填充
		prepareBeanFactory(beanFactory);
	
		try {
			// 4. 子类覆盖方法做额外的处理
			postProcessBeanFactory(beanFactory);
	
			// 5. 激活各种 beanFactory 处理器
			invokeBeanFactoryPostProcessors(beanFactory);
	
			// 6. 注册拦截 bean 创建的 bean 处理器，这里只是注册，真正的调用是在 getBean 的时候
			registerBeanPostProcessors(beanFactory);
	
			// 7. 为上下文初始化 Message 源，即国际化
			initMessageSource();
	
			// 8. 初始化事件派发器，并放入 applicationEventMulticaster bean 中
			initApplicationEventMulticaster();
	
			// 9. 留给子类来初始化其它的 bean
			onRefresh();
	
			// 10. 在所有注册 bean 中查找 Listener bean，并注册到消息派发器中
			registerListeners();
	
			// 11. 初始化剩下的单例 （非 lazy 的）
			finishBeanFactoryInitialization(beanFactory);
	
			// 12. 最后一步，完成刷新，通知生命周期处理器 lifecycleProcessor 刷新过程，
			// 同时发出 contextRefreshEvent 通知别人
			finishRefresh();
		}
	
		catch (BeansException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("Exception encountered during context initialization - " +
						"cancelling refresh attempt: " + ex);
			}
	
			// Destroy already created singletons to avoid dangling resources.
			destroyBeans();
	
			// Reset 'active' flag.
			cancelRefresh(ex);
	
			// Propagate exception to caller.
			throw ex;
		}
	
		finally {
			// Reset common introspection caches in Spring's core, since we
			// might not ever need metadata for singleton beans anymore...
			resetCommonCaches();
		}
	}
}
```

### 1. prepareRefresh

该步骤是对初始化做一些准备工作，例如对系统属性或者环境变量进行准备或者验证。

```java
protected void prepareRefresh() {
	this.startupDate = System.currentTimeMillis();
	this.closed.set(false);
	this.active.set(true);

	if (logger.isDebugEnabled()) {
		if (logger.isTraceEnabled()) {
			logger.trace("Refreshing " + this);
		}
		else {
			logger.debug("Refreshing " + getDisplayName());
		}
	}

	// 空实现，留给子类覆盖
	initPropertySources();

	// 验证需要的属性文件是否已放入环境中
	getEnvironment().validateRequiredProperties();

	// Allow for the collection of early ApplicationEvents,
	// to be published once the multicaster is available...
	this.earlyApplicationEvents = new LinkedHashSet<>();
}
```

这一步基本什么也没有做，因为 `initPropertySources` 是空的，所以 `validateRequiredProperties` 也因为没有需要验证的属性而没有做任何处理。如果我们有需求，工程在运行过程中用的某个设置是从系统变量中取得的，那么我们就可以扩展这个类，重写 `initPropertySources` 方法，增加对该参数的验证，启动时用我们新的入口类启动即可。如下：

```java
public class MyAppBootstrap extends AnnotationConfigApplicationContext {

	public MyAppBootstrap(Class<?>... annotatedClasses) {
		super(annotatedClasses);
	}

	@Override
	protected void initPropertySources() {
		System.out.println("增加必要属性 VAR，当启动参数没有时会抛出异常 。");
		getEnvironment().setRequiredProperties("VAR");
	}

}
```
提示： `eclipse` 中增加启动参数 `run Configuration -> Arguments -> VM`  格式: `-Dargname=argvalue`，此处增加 `-DVAR=argvalue` 即可正常启动。

### 2. obtainFreshBeanFactory

刷新并获得工厂

```java
protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
	// 刷新工厂，其中  GenericApplicationContext 会在构造方法中 new DefaultListableBeanFactory() 
	//  refreshBeanFactory() 修改为已刷新，并设置  serializationId
	refreshBeanFactory();
	// 返回创建的 DefaultListableBeanFactory
	return getBeanFactory();
}
```

// TODO
