/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.pleuvoir.mine.chapter01;

import java.util.Arrays;

import org.junit.Test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 入口测试
 * @author pleuvoir
 */
public class ChapterTest01 {
	
	@Test
	public void test(){
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Config01.class);
		Arrays.asList(app.getBeanDefinitionNames()).forEach(name -> {
			System.out.println(name);
		});
		app.close();
	}
	
}
