/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.demo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * dubbo启动涉及技术
 * 1. spring 可扩展Schema的支持
 *  提供了基于Schema风格的XML扩展机制，允许开发者扩展最基本的spring配置文件，这样我们就可以编写自定义的xml bean解析器然后集成到Spring IoC容器中。
        也就是说利用这个机制就可以把我们在xml文件中配置的东西实例化成对象。
 * 2. spring 事件机制
 *  继承了ApplicationListener.这个就是spring的事件机制
 *
 *
 * spring启动过程中会去扫描META-INF/spring.schemas，拿到dubbo的扩展配置，然后根据配置找到META-INF/dubbo.xsd文件
 * http\://dubbo.apache.org/schema/dubbo/dubbo.xsd=META-INF/dubbo.xsd(为什么会加载META-INF/spring.schemas，因为spring中的PluggableSchemaResolver.java)
 * (1) dubbo.xsd文件中定义了我们Bean的标签，和Bean中定义的字段一一对应的(这一步spring会把dubbo.xsd文件解析成 Dom 树，在解析的自定义标签的时候， spring 会根据标签的命名空间和标签名找到一个解析器。)
 * (2) 根据targetNamespace的值的指向 META-INF/spring.handlers。拿到dubbo配置的handler路径
 * (3) DubboNamespaceHandler,由该解析器来完成对该标签内容的解析，并返回一个 BeanDefinition 。
 *
 * 以上即将配置文件解析到spring的容器中
 *
 * spring容器初始化完成之后就会触发ServiceBean的onApplicationEvent方法。这个就是整个dubbo服务启动的入口
 *
 * 3. 服务暴露
 *  ServiceConfig一系列的操作
 * 4. netty服务启动
 *  DubboProtocol.export()方法，接着会调用openServer(url) -> createServer(url)
 *      在createServer方法中利用dubbo SPI机制找到NettyTransporter，new NettyServer（）->doOpen().最终我们就看到boss 线程，worker 线程，和 ServerBootstrap。
 *
 *
 * 问题、
 * 那么为什么会有本地暴露呢?
 * 因为在dubbo中我们一个服务可能既是Provider,又是Consumer,因此就存在他自己调用自己服务的情况,如果再通过网络去访问，就会白白增加一层网络开销。所以本地暴露和远程暴露的区别如下
    -- 本地暴露是暴露在JVM中,不需要网络通信.
    -- 远程暴露是将ip,端口等信息暴露给远程客户端,调用时需要网络通信.
     * 暴露远程服务：<br>
     * 1. 协议在接收请求时，应记录请求来源方地址信息：RpcContext.getContext().setRemoteAddress();<br>
     * 2. export()必须是幂等的，也就是暴露同一个URL的Invoker两次，和暴露一次没有区别。<br>
     * 3. export()传入的Invoker由框架实现并传入，协议不需要关心。<br>
 */
public class Application {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-provider.xml");
        context.start();
        System.in.read();
    }
}
