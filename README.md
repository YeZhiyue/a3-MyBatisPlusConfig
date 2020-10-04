<font color=#ca0c16 size=8> SpringSecurity

<a id="_top"></a>

@[TOC](文章目录)

# 前言

<font color=#999AAA > 官方文档学习

[https://docs.spring.io/spring-security/site/docs/5.4.0/reference/html5/#servlet-hello](https://docs.spring.io/spring-security/site/docs/5.4.0/reference/html5/#servlet-hello)

---

# 第一节 HelloWorld

## 依赖

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## SpringBoot中对于SpringSecurity的自动装箱配置

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

**1. 自动注册过滤器链 springSecurityFilterChain**

- 这些过滤器用于对每一个访问我们服务的请求进行安全处理，如用户名和密码校验。

启动我们的SpringSecurity项目的时候我们可以看到控制台的打印

![](http://qhif39llc.hn-bkt.clouddn.com/picgo/20201004101344.png)

```java
2020-10-04 10:03:29.375  INFO 9632 --- [  restartedMain] o.s.s.web.DefaultSecurityFilterChain     : Creating filter chain: any request,
[org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@5f7237ac,
org.springframework.security.web.context.SecurityContextPersistenceFilter@3656cd5d,
org.springframework.security.web.header.HeaderWriterFilter@1ba3e9b5,
org.springframework.security.web.csrf.CsrfFilter@109af914,
org.springframework.security.web.authentication.logout.LogoutFilter@6e43540b,
org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@a25792a,
org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter@450c4a3c,
org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter@6a8a277,
org.springframework.security.web.authentication.www.BasicAuthenticationFilter@a29c8b6,
org.springframework.security.web.savedrequest.RequestCacheAwareFilter@3563491d,
org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@4e2947ab,
org.springframework.security.web.authentication.AnonymousAuthenticationFilter@5a4b8296,
org.springframework.security.web.session.SessionManagementFilter@1ddcad86,
org.springframework.security.web.access.ExceptionTranslationFilter@8efd8ea,
org.springframework.security.web.access.intercept.FilterSecurityInterceptor@21eb0f07]
```

**2. 创建一个UserDetailsService组件，名称是user，密码会自动生成并且在控制台打印**

控制台打印自动生成的密码

```java
2020-10-04 10:03:29.291  INFO 9632 --- [  restartedMain] .s.s.UserDetailsServiceAutoConfiguration : 
Using generated security password: d1083c8a-00b5-4bda-99b6-99434ed2ffc4
```

通过这个密码我们可以登录我们的服务

http://qhif39llc.hn-bkt.clouddn.com/picgo/20201004101915.png

### 注入SpringSecurity之后SpringBoot为你自动配置的安全总结

- 每一个用户都需要进行权限校验时候才可以进行服务资源的获取
- SpringSecurity会自带一个默认的登录界面给你
- SpringSecurity自带了BCrypt的加密服务

---

# SpringSecurity的高级体系结构

## 过滤器链条

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

![](http://qhif39llc.hn-bkt.clouddn.com/picgo/20201004111622.png)

## ...许多的过滤器构成了复杂的校验体系结构

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

---

# Spring Security提供的身份验证的支持

**身份验证组件**

- SecurityContextHolder 存储已经通过权限校验的用户的信息 
- SecurityContext 存储已经通过权限校验的当前用户的信息 
- Authentication 用户凭据信息

## SecurityContextHolder

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

![](http://qhif39llc.hn-bkt.clouddn.com/picgo/20201004115249.png)

### 设置一个用户

```java
SecurityContext context = SecurityContextHolder.createEmptyContext(); 
Authentication authentication =
    new TestingAuthenticationToken("username", "password", "ROLE_USER"); 
context.setAuthentication(authentication);

SecurityContextHolder.setContext(context); 
```

### 获取当前用户

```java
SecurityContext context = SecurityContextHolder.getContext();
Authentication authentication = context.getAuthentication();
String username = authentication.getName();
Object principal = authentication.getPrincipal();
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
```



