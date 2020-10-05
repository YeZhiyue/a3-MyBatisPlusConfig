<font color=#ca0c16 size=8> 个人Demo

<a id="_top"></a>

@[TOC](文章目录)

---

# 参考和项目地址

[https://github.com/Snailclimb/spring-security-jwt-guide 文档地址](https://github.com/Snailclimb/spring-security-jwt-guide)

[https://docs.spring.io/spring-security/site/docs/5.4.0/reference/html5/#jc SpringSecurity官方文档](https://docs.spring.io/spring-security/site/docs/5.4.0/reference/html5/#jc)

个人demo

```java

```

---

# 依赖导入

```java
<!-- ======================== SpringSecurity =========================-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.10.7</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.10.7</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.10.7</version>
    <scope>runtime</scope>
</dependency>
<!-- ======================== END =========================-->
```

---

# 关键权限文件

## public class CorsConfiguration implements WebMvcConfigurer

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

文件配置了跨域请求会返回token

```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("*")
            //暴露header中的其他属性给客户端应用程序
            //如果不设置这个属性前端无法通过response header获取到Authorization也就是token
            .exposedHeaders("Authorization")
            .allowCredentials(true)
            .allowedMethods("GET", "POST", "DELETE", "PUT")
            .maxAge(3600);
}
```

## public class SecurityConfig extends WebSecurityConfigurerAdapter

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

文件主要配置了那些权限需要进行权限校验，权限校验的过滤器

## public class JwtUser implements UserDetails

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

存放登录用户的信息，其中有着这个用户的权限信息

## public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

用户登录时的权限查询，对这个用户授权，生成token

## public class JwtAuthorizationFilter extends BasicAuthenticationFilter

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

对前端传递过来的token进行校验，看这个token中到底有哪些权限

## public class JwtTokenUtils && public class CurrentUserUtils 

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

安全框架工具类，用于token的编解码。另外一个可以获取当前的登录用户

## public class UserDetailsServiceImpl implements UserDetailsService

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

用于获取当前准备登录的用户的信息获取，准备进行权限校验

---

# 校验流程

## 登录校验流程

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

**1. 请求**

{{url}}auth/login

```java
{
    "username": "张三",
    "password": "1234",
    "rememberMe": true
}
```

**2. 配置**

配置请求路径

```java
public class SecurityConstants{
    public static final String AUTH_LOGIN_URL = "/auth/login";
    ...
}
```

过滤器对指定路径进行拦截

```java
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 设置URL，以确定是否需要身份验证(就是需要从数据库获取用户权限的地址)
        super.setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 获取登录的信息
            // 以LoginRequest类为模板来读取Json数据
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            rememberMe.set(loginRequest.getRememberMe());
            // 这部分和attemptAuthentication方法中的源码是一样的，
            // 只不过由于这个方法源码的是把用户名和密码这些参数的名字是死的，所以我们重写了一下
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());
            // 这个会跳转到 UserDetailsService 的 loadUserByUsername() 方法中读取用户信息
            return authenticationManager.authenticate(authentication);
        } catch (IOException | AuthenticationException e) {
            if (e instanceof AuthenticationException) {
                throw new LoginFailedException("登录失败！请检查用户名和密码。");
            }
            throw new LoginFailedException(e.getMessage());
        }
    }
}
```

读取用户信息

```java
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private IUserService iUserService;

    public UserDetailsServiceImpl(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        User user = iUserService.getUserWithRoles(name);
        return new JwtUser(user);
    }
}
```

## 其他访问的权限校验

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

```java
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private static final Logger logger = Logger.getLogger(JwtAuthorizationFilter.class.getName());

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
        } else {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    /**
     * 获取用户认证信息 Authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {
        log.info("get authentication");
        String token = authorization.replace(SecurityConstants.TOKEN_PREFIX, "");
        try {
            String username = JwtTokenUtils.getUsernameByToken(token);
            logger.info("checking username:" + username);
            if (!StringUtils.isEmpty(username)) {
                // 这里我们是又从数据库拿了一遍,避免用户的角色信息有变
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                return userDetails.isEnabled() ? usernamePasswordAuthenticationToken : null;
            }
        } catch (UserNameNotFoundException | SignatureException | ExpiredJwtException | MalformedJwtException | IllegalArgumentException exception) {
            logger.warning("Request to parse JWT with invalid signature . Detail : " + exception.getMessage());
        }
        return null;
    }
}
```




