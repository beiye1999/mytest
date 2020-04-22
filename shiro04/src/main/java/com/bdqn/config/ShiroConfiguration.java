package com.bdqn.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.bdqn.realm.UserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfiguration {

    private static final String SHIRO_DIALECT = "shiroDialect";
    private static final String SHIRO_FILTER = "shiroFilter";
    private String hashAlgorithmName = "md5";//加密算法
    private Integer hashIterations = 2;//散列次数

    /**
     * 注入凭证器
     * @return
     */
    @Bean("credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);//设置加密方式
        credentialsMatcher.setHashIterations(hashIterations);//散列次数
        return credentialsMatcher;
    }

    /**
     * 注入UserRealm
     * @param credentialsMatcher    凭证器对象
     * @return
     */
    @Bean
    public UserRealm getUserRealm(CredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm();
        //注入凭证器
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    /**
     * 配置SecurityManager
     * @param userRealm     自定义Realm对象
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //引用UserRealm
        securityManager.setRealm(userRealm);
        //返回DefaultWebSecurityManager对象
        return securityManager;
    }

    /**
     * 注入拦截器链
     * @param securityManager
     * @return
     */
    @Bean(SHIRO_FILTER)
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        //创建过滤器链对象
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //注入安全管理器
        factoryBean.setSecurityManager(securityManager);
        //注入未登陆的跳转页面 默认的是webapp/login.jsp
        factoryBean.setLoginUrl("/user/tologin");
        //注入权限不足时跳转的页面
        factoryBean.setUnauthorizedUrl("/unauthorized");
        //设置过滤器链
        Map<String,String> map = new LinkedHashMap<String,String>();
        //设置匿名访问
        map.put("/res/**","anon");//静态资源放行
        map.put("/user/tologin","anon");//放行去到登录页面
        map.put("/user/login","anon");//登录请求
        map.put("/logout","logout");//退出

        //其他请求一律拦截
        map.put("/**","authc");
        //将过滤器链集合设置到ShiroFilterFactoryBean对象中
        factoryBean.setFilterChainDefinitionMap(map);
        //返回对象
        return factoryBean;
    }


    /**
     * 注册shiro的委托过滤器，相当于之前在web.xml里面配置的
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName(SHIRO_FILTER);
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }
    /* 加入注解的使用，不加入这个注解不生效--开始 */
    /**
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /* 加入注解的使用，不加入这个注解不生效--结束 */

    /**
     * 为了能在html页面引用shiro标签，上面两个函数必须添加，不然会报错
     * @return
     */
    @Bean(name = SHIRO_DIALECT)
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
}
