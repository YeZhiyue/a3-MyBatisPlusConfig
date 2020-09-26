<font color=#ca0c16 size=8> 代码生成器的配置

<a id="_top"></a>

# 前言

<font color=#999AAA > 如果你在使用SpringBoot框架进行Web开发的话，那么就可以使用MybatisPlus的代码生成器来进行代码的生成。这里会告诉你基本的代码生成器的配置，可以满足代码大多数人的代码生成需求。

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

---

# 基本环境搭建(SpringBoot+Maven)

## 源码示例地址



## MybatisPlus的关键包

```java
<!--mysql驱动包-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
<!--核心包-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.3.0</version>
</dependency>
<!--代码生成依赖包-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.3.0</version>
</dependency>
<dependency>
    <groupId>org.apache.velocity</groupId>
    <artifactId>velocity-engine-core</artifactId>
    <version>2.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<!--mysql驱动包-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

## yml的配置

```java
spring:
  datasource:
    password: root
    username: root
    url: jdbc:mysql://59.110.213.92:3307/park?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver
server:
  port: 8150
```

---

# 配置代码生成流程

## 全局配置

这里配置比较简单

- 设置是否在代码生成完毕后打开生成代码的目录
- 设置第二次生成时如果有同名文件的话是否要覆盖源文件
- 设置XML中是否要生成ResultMap映射(就是MybatisPlus默认给你当前表所有字段的ResultMap)
- 设置是否生成基本的表字段的视图(就是<sql> id,...</sql>)
- 设置是否需要Swagger注解(就是@ApiOperation注解，用来生成文档)
- 设置作者名，用于类注释和方法注释
- 设置生成类的别名(如%sDao表示原来的类名后面跟上Dao)

```java
new GlobalConfig()
    // 打开文件
    .setOpen(false)
    // 文件覆盖
    .setFileOverride(true)
    // 开启activeRecord模式
    .setActiveRecord(true)
    // XML ResultMap: mapper.xml生成查询映射结果
    .setBaseResultMap(true)
    // XML ColumnList: mapper.xml生成查询结果列
    .setBaseColumnList(true)
    // swagger注解; 须添加swagger依赖
    .setSwagger2(true)
    // 作者
    .setAuthor(AUTHOR)
    // 设置实体类名称
    // .setEntityName("%sDao")
    ;
```

## 数据源配置

这个就只要设置根据数据库来设置即可，表的数据来源。也就是从数据库读出表的信息然后生成代码文件。

```java
new DataSourceConfig()
    // 数据库类型
    .setDbType(DB_TYPE)
    // 连接驱动
    .setDriverName(driverClassName)
    // 地址
    .setUrl(url)
    // 用户名
    .setUsername(username)
    // 密码
    .setPassword(password);
```

## 输出文件路径配置(重要)

- 设置基本项目路径：System.getProperty("user.dir")，由于每台电脑项目目录是会变动的，所以通过这个就可以动态获取你项目的根目录。
- 接着设置你各个类的输出路径(如果你的项目是分布式项目，那么这里的配置就会比较麻烦，因为entity类和其他业务层类的通常不是在同一个模块下面的)
    - 示例：projectPath + ENTITY_OUTPUT_PATH + tableInfo.getEntityName() + StringPool.DOT_JAVA;
    - 说明：项目根路径 + entity包路径 + entity的名称 + .java后缀
    - 总结：其实就是字符串的拼接

```java
List<FileOutConfig> list = new ArrayList<>();
// 当前项目路径
String projectPath = System.getProperty("user.dir");

// 实体类文件输出
list.add(new FileOutConfig(ENTITY_TEMPLATE) {
    @Override
    public String outputFile(TableInfo tableInfo) {
        return projectPath + ENTITY_OUTPUT_PATH + tableInfo.getEntityName() + StringPool.DOT_JAVA;
    }
});
// mapper xml文件输出
list.add(new FileOutConfig(XML_TEMPLATE) {
    @Override
    public String outputFile(TableInfo tableInfo) {
        return projectPath + XML_OUTPUT_PATH + tableInfo.getMapperName() + StringPool.DOT_XML;
    }
});
// mapper文件输出
list.add(new FileOutConfig(MAPPER_TEMPLATE) {
    @Override
    public String outputFile(TableInfo tableInfo) {
        return projectPath + MAPPER_OUTPUT_PATH + tableInfo.getMapperName() + StringPool.DOT_JAVA;
    }
});
// service文件输出
list.add(new FileOutConfig(SERVICE_TEMPLATE) {
    @Override
    public String outputFile(TableInfo tableInfo) {
        return projectPath + SERVICE_OUTPUT_PATH + tableInfo.getServiceName() + StringPool.DOT_JAVA;
    }
});
// service impl文件输出
list.add(new FileOutConfig(SERVICE_IMPL_TEMPLATE) {
    @Override
    public String outputFile(TableInfo tableInfo) {
        return projectPath + SERVICE_IMPL_OUTPUT_PATH + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
    }
});
// controller文件输出
list.add(new FileOutConfig(CONTROLLER_TEMPLATE) {
    @Override
    public String outputFile(TableInfo tableInfo) {
        return projectPath + CONTROLLER_OUTPUT_PATH + tableInfo.getControllerName() + StringPool.DOT_JAVA;
    }
});
```

## 设置你的包路径

- 和上面的文件路径配置相似，就是需要将其中的斜杠 '/' 替换成java中导包用的 '.'
    - 示例：com/example/entity
    - 转换：com.example.entity

```java
new PackageConfig()
    // 父包名
    .setModuleName(AFTER_MODULE)
    .setParent(PARENT_PACKAGE_PATH.replace('/', '.').substring(1))
    .setEntity(ENTITY_PATH.replace('/', '.').substring(1, ENTITY_PATH.length() - 1))
    .setMapper(MAPPER_PATH.replace('/', '.').substring(1, MAPPER_PATH.length() - 1))
    .setXml(XML_PATH.replace('/', '.').substring(1, XML_PATH.length() - 1))
    .setService(SERVICE_PATH.replace('/', '.').substring(1, SERVICE_PATH.length() - 1))
    .setServiceImpl(SERVICE_IMPL_PATH.replace('/', '.').substring(1, SERVICE_IMPL_PATH.length() - 1))
    .setController(CONTROLLER_PATH.replace('/', '.').substring(1, CONTROLLER_PATH.length() - 1));
```

## 配置文件策略(重要)

这里的配置就非常多了，而且非常重要，下面就介绍几个关键的配置

- 设置表名和表字段转换为Java文件时命名策略
    - 由于我们数据库中通常都是下划线命名，但是Java中通常是驼峰命名，所以我们通常会配置下划线转驼峰的配置
- 设置你需要生成的表，不用多说，必需的配置，设置你需要生成目标数据库中的那几张表
- 设置生成代码时需要去掉的前缀
    - 因为我们的表中常常会有前缀 info_ dict_ relation_ 之类特殊含义的前缀(表示普通信息表，中间关联表，字典表)
- 设置是否需要Lombok注解
- 设置是否需要构建者模式
- 设置是否需要生成表信息注解
- 设置特殊意义字段
    - 逻辑删除
    - 自动更新插入
    - 版本迭代字段
- 设置父类中字段
    - 如 id version deleted类似的字段几乎是每一张表都会有的字段，这样就可以设置一个共同的父类来保存这些字段
- 设置父类，也就是某个类需要继承的类

```java
private static StrategyConfig strategyConfig() {
    StrategyConfig strategyConfig = new StrategyConfig()
            // 表名生成策略：下划线连转驼峰
            .setNaming(NamingStrategy.underline_to_camel)
            // 表字段生成策略：下划线连转驼峰
            .setColumnNaming(NamingStrategy.underline_to_camel)
            // 需要生成的表
            .setInclude(TABLES)
            // 生成controller
            .setRestControllerStyle(true)
            // 去除表前缀
            .setTablePrefix(ENTITY_IGNORE_PREFIX)
            // controller映射地址：驼峰转连字符
            .setControllerMappingHyphenStyle(false)
            // 是否启用builder 模式
            .setEntityBuilderModel(true)
            // 是否为lombok模型; 需要lombok依赖
            .setEntityLombokModel(true)
            // 生成实体类字段注解
            .setEntityTableFieldAnnotationEnable(true)
            // 乐观锁、逻辑删除、表填充
            .setVersionFieldName("version")
            .setLogicDeleteFieldName("deleted")
            .setTableFillList(Arrays.asList(
                    new TableFill("update_time", FieldFill.UPDATE),
                    new TableFill("create_time", FieldFill.INSERT)
            ))
            // 生成类的时候排除的字符串
            .setSuperEntityColumns(
                    SUPER_ENTITY_COLUMNS
            );

    if ("".equals(SUPER_ENTITY_CLASS) || Objects.isNull(SUPER_ENTITY_CLASS)) {
        return strategyConfig;
    }
    strategyConfig.setSuperEntityClass(SUPER_ENTITY_CLASS);
    return strategyConfig;
}
```    

## 自定义配置(高级)

- 配置你的模板参数(其实我们代码模板中的参数来源是MybatisPlus的一个AbstractTemplateEngine类中的Map，稍后具体说明)
- 配置你的模板路径
- 配置你的文件夹动态生成

1.1 配置你的模板参数和文件夹动态添加

```java
/**
 * 自定义配置
 */
private static InjectionConfig injectionConfig() {
    return new InjectionConfig() {
        @Override
        public void initMap() {
            System.out.println(this.toString());
            // 注入配置
            Map<String, Object> map = new HashMap<>();
            String dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(System.currentTimeMillis()));
            String[] split = dateTime.split(" ");
            map.put("email", EMAIL);
            map.put("date", split[0]);
            map.put("time", split[1]);
            this.setMap(map);
        }
    }
            // 判断是否创建文件
            .setFileCreate(new IFileCreate() {
                @Override
                public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                    // 检查文件目录，不存在自动递归创建
                    File file = new File(filePath);
                    boolean exist = file.exists();
                    if (!exist) {
                        file.getParentFile().mkdirs();
                    }
                    return true;
                }
            })
            // 自定义输出文件
            .setFileOutConfigList(fileOutConfigList());
}
```

1.2 配置模板路径

```java
private static List<FileOutConfig> fileOutConfigList() {
    List<FileOutConfig> list = new ArrayList<>();
    // 当前项目路径
    String projectPath = System.getProperty("user.dir");

    // 实体类文件输出
    list.add(new FileOutConfig(ENTITY_TEMPLATE) {
        @Override
        public String outputFile(TableInfo tableInfo) {
            return projectPath + ENTITY_OUTPUT_PATH + tableInfo.getEntityName() + StringPool.DOT_JAVA;
        }
    });
    // mapper xml文件输出
    list.add(new FileOutConfig(XML_TEMPLATE) {
        @Override
        public String outputFile(TableInfo tableInfo) {
            return projectPath + XML_OUTPUT_PATH + tableInfo.getMapperName() + StringPool.DOT_XML;
        }
    });
    // mapper文件输出
    list.add(new FileOutConfig(MAPPER_TEMPLATE) {
        @Override
        public String outputFile(TableInfo tableInfo) {
            return projectPath + MAPPER_OUTPUT_PATH + tableInfo.getMapperName() + StringPool.DOT_JAVA;
        }
    });
    // service文件输出
    list.add(new FileOutConfig(SERVICE_TEMPLATE) {
        @Override
        public String outputFile(TableInfo tableInfo) {
            return projectPath + SERVICE_OUTPUT_PATH + tableInfo.getServiceName() + StringPool.DOT_JAVA;
        }
    });
    // service impl文件输出
    list.add(new FileOutConfig(SERVICE_IMPL_TEMPLATE) {
        @Override
        public String outputFile(TableInfo tableInfo) {
            return projectPath + SERVICE_IMPL_OUTPUT_PATH + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
        }
    });
    // controller文件输出
    list.add(new FileOutConfig(CONTROLLER_TEMPLATE) {
        @Override
        public String outputFile(TableInfo tableInfo) {
            return projectPath + CONTROLLER_OUTPUT_PATH + tableInfo.getControllerName() + StringPool.DOT_JAVA;
        }
    });
    return list;
}
```

## 模板配置(忽略)

- 这里是配置代码生成模板，使用的是MybatisPlus自带的模板文件，但是这里我们自己写模板就不需要配置这个配置

```java
new TemplateConfig()
    // 置空后方便使用自定义输出位置
    .setEntity(null)
    .setXml(null)
    .setMapper(null)
    .setService(null)
    .setServiceImpl(null)
    .setController(null);
```

---

# 我的特殊配置

## 我配置了我的公共父类

注意这里需要带上@Date注解，否则会在数据库操作时报错

```java
@Data
public class BaseModel<T extends Model<?>> extends Model<T> {

    // 参数校验的分组使用参数
    public interface Update {
    }

    @NotNull(groups = {Update.class},message = "更新的对象id不能为空")
    @ApiModelProperty(value = "id", reference = "int(11)")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "权重", reference = "int(11)")
    @TableField(value = "weight")
    private Integer weight;

    @ApiModelProperty(value = "创建时间", reference = "bigint(20)")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Long createTime;

    @ApiModelProperty(value = "更新时间", reference = "bigint(20)")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Long updateTime;

    @ApiModelProperty(value = "创建者", reference = "varchar(36)")
    @TableField(value = "create_by")
    private String createBy;

    @ApiModelProperty(value = "更新者", reference = "varchar(36)")
    @TableField(value = "update_by")
    private String updateBy;

    @ApiModelProperty(value = "版本", reference = "int(11)")
    @TableField(value = "version")
    @Version
    private Integer version;

    @ApiModelProperty(value = "是否有效 0-未删除 1-已删除", reference = "int(11)")
    @TableField(value = "deleted")
    @TableLogic
    private Integer deleted;


    @ApiModelProperty(value = "额外信息", reference = "varchar(1000)")
    @TableField(value = "extra")
    private String extra;

    @ApiModelProperty(value = "租户id", reference = "varchar(255)")
    @TableField(value = "tenant_id")
    private String tenantId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
```

## 代码生成器的配置(我配置在了几个接口中)

### 必要的基本配置

```java
public interface BasicParamConstant {
    /**
     * 作者
     */
    String AUTHOR = "YeZhiyue";
    String EMAIL = "739153436@qq.com";
    /**
     * 生成的实体类忽略表前缀: 不需要则置空
     */
    String ENTITY_IGNORE_PREFIX = "info_";
    /**
     * 表名
     */
    String[] TABLES = {
            "dict_bed",
            "dict_building"
    };

    /**
     * 实体类的父类Entity
     */
    String SUPER_ENTITY_CLASS = "com.example.mybatispluscodegeneral.entity.basic.BaseModel";

    /**
     * 基础父类继承字段
     */
    String SUPER_ENTITY_COLUMNS[] = {
            "id",
            "weight",
            "create_time",
            "update_time",
            "create_by",
            "update_by",
            "deleted",
            "version",
            "extra",
            "tenant_id"
    };

    /**
     * 输出基础路径
     */
    String PARENT_PACKAGE_PATH = "/com/example/mybatispluscodegeneral";
    String PRE_MODULE = "";
    // 配置你包路径下的子模块(例如：com.example.mybatispluscodegeneral.test)
    // 示例："test";
    String AFTER_MODULE = "";

    /**
     * 数据库
     */
    String username = "root";
    String password = "root";
    String url = "jdbc:mysql://59.110.213.92:3307/park";
    DbType DB_TYPE = DbType.MYSQL;
    String driverClassName = "com.mysql.cj.jdbc.Driver";
}
```

### 包路径配置

```java
public interface PackagePathConstant  extends BasicParamConstant {

    /**
     * 父包名路径(文件输出路径,也是导包的路径)
     */
    // 各层包名 实体类、Dao、XML、Service、Controller
    String ENTITY_PATH = "/entity/";
    String MAPPER_PATH = "/mapper/";
    String XML_PATH = "/resources/mapper/";
    String SERVICE_PATH = "/service/";
    String SERVICE_IMPL_PATH = "/service/impl/";
    String CONTROLLER_PATH = "/controller/";

    /**
     * mapper.xml输出模块路径(需要注意放置的位置:默认从模块/src/main下开始)
     */
    String XML_OUTPUT_MODULE = "/"+AFTER_MODULE;
    String XML_OUTPUT_PATH = "/src/main" + XML_PATH;
    /**
     * IService.java, serviceImpl.java输出模块路径
     */
    String SERVICE_OUTPUT_MODULE = "/"+AFTER_MODULE;
    String SERVICE_OUTPUT_PATH = "/src/main/java" + PARENT_PACKAGE_PATH + SERVICE_OUTPUT_MODULE + SERVICE_PATH;
    String SERVICE_IMPL_OUTPUT_PATH = "/src/main/java" + PARENT_PACKAGE_PATH + SERVICE_OUTPUT_MODULE + SERVICE_IMPL_PATH;
    /**
     * Controller.java输出模块路径
     */
    String CONTROLLER_OUTPUT_MODULE = "/"+AFTER_MODULE;
    String CONTROLLER_OUTPUT_PATH = "/src/main/java" + PARENT_PACKAGE_PATH + CONTROLLER_OUTPUT_MODULE + CONTROLLER_PATH;
    /**
     * Entity.java, Mapper.java, Mapper.xml输出模块路径
     */
    String DAO_OUTPUT_MODULE = "/"+AFTER_MODULE;
    String ENTITY_OUTPUT_PATH = "/src/main/java" + PARENT_PACKAGE_PATH + DAO_OUTPUT_MODULE + ENTITY_PATH;
    String MAPPER_OUTPUT_PATH = "/src/main/java" + PARENT_PACKAGE_PATH + DAO_OUTPUT_MODULE + MAPPER_PATH;
}
```

### 代码模板路径配置

```java
public interface TemplatePathConstant  extends BasicParamConstant {

    /**
     * entity输出模板
     */
    String ENTITY_TEMPLATE = "templates/entity.java.vm";
    /**
     * mapper.xml输出模板
     */
    String XML_TEMPLATE = "templates/mapper.xml.vm";
    /**
     * mapper.java输出模板
     */
    String MAPPER_TEMPLATE = "templates/mapper.java.vm";
    /**
     * service输出模板
     */
    String SERVICE_TEMPLATE = "templates/service.java.vm";
    /**
     * serviceImpl输出模板
     */
    String SERVICE_IMPL_TEMPLATE = "templates/serviceImpl.java.vm";
    /**
     * controller输出模板
     */
    String CONTROLLER_TEMPLATE = "templates/controller.java.vm";
}
```

## 添加的工具包(ResultBean用于Controller层的代码返回)

其他的代码直接参考示例代码地址，里面都有

```java
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Builder.Default
    private int code = HttpStatus.HTTP_OK;

    @Getter
    @Setter
    @Builder.Default
    private String msg = "success";

    @Getter
    @Setter
    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = HttpStatus.HTTP_INTERNAL_ERROR;
    }

    public static <T> ResultBean<T> resultBean(T data, int code, String msg) {
        ResultBean<T> resultBean = new ResultBean<>();
        resultBean.setCode(code);
        resultBean.setData(data);
        resultBean.setMsg(msg);
        return resultBean;
    }

    public static <T> ResultBean<T> restResult(T data, ErrorCodeInfo errorCode) {
        return resultBean(data, errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> ResultBean<T> ok() {
        return resultBean(null, CommonConstants.SUCCESS, null);
    }

    public static <T> ResultBean<T> ok(T data) {
        return resultBean(data, CommonConstants.SUCCESS, null);
    }

    public static <T> ResultBean<T> ok(T data, String msg) {
        return resultBean(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> ResultBean<T> failed() {
        return resultBean(null, CommonConstants.FAIL, null);
    }

    public static <T> ResultBean<T> failed(String msg) {
        return resultBean(null, CommonConstants.FAIL, msg);
    }

    public static <T> ResultBean<T> failed(T data) {
        return resultBean(data, CommonConstants.FAIL, null);
    }

    public static <T> ResultBean<T> failed(T data, String msg) {
        return resultBean(data, CommonConstants.FAIL, msg);
    }
}
```

## 异常捕获器处理器(适配SpringBoot的参数校验方法)

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 用来处理bean validation异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResultBean<String> resolveConstraintViolationException(ConstraintViolationException ex){
        ResultBean<String> stringResultBean = new ResultBean<>();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if(!CollectionUtils.isEmpty(constraintViolations)){
            StringBuilder msgBuilder = new StringBuilder();
            for(ConstraintViolation constraintViolation :constraintViolations){
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if(errorMessage.length()>1){
                errorMessage = errorMessage.substring(0,errorMessage.length()-1);
            }
            // 非法参数code
            stringResultBean.setCode(100);
            stringResultBean.setMsg(errorMessage);
            return stringResultBean;
        }
        // 非法参数code
        stringResultBean.setCode(100);
        stringResultBean.setMsg(ex.getMessage());
        return stringResultBean;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBean<String> resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ResultBean<String> stringResultBean = new ResultBean<>();
        List<ObjectError>  objectErrors = ex.getBindingResult().getAllErrors();
        if(!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            // 非法参数code
            stringResultBean.setCode(100);
            stringResultBean.setMsg(errorMessage);
            return stringResultBean;
        }
        // 非法参数code
        stringResultBean.setCode(100);
        stringResultBean.setMsg(ex.getMessage());
        return stringResultBean;
    }
}
```

# 代码模板(VM)

## 首先学习一下基本的VM语法

可以看看博客，通常也就是用用if或者foreach。也就是对象操作

[博客地址](http://www.mamicode.com/info-detail-1635137.html)

## 接着我们需要知道在哪里获取参数

也就是在Mybatisplus中的一个模板类中传递的参数

```java
/**
 * 模板引擎抽象类
 *
 * @author hubin
 * @since 2018-01-10
 */
public abstract class AbstractTemplateEngine {
    ...
    /**
     * 渲染对象 MAP 信息
     *
     * @param tableInfo 表信息对象
     * @return ignore
     */
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap<>(30);
        ConfigBuilder config = getConfigBuilder();
        if (config.getStrategyConfig().isControllerMappingHyphenStyle()) {
            objectMap.put("controllerMappingHyphenStyle", config.getStrategyConfig().isControllerMappingHyphenStyle());
            objectMap.put("controllerMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
        }
        objectMap.put("restControllerStyle", config.getStrategyConfig().isRestControllerStyle());
        objectMap.put("config", config);
        objectMap.put("package", config.getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("idType", globalConfig.getIdType() == null ? null : globalConfig.getIdType().toString());
        objectMap.put("logicDeleteFieldName", config.getStrategyConfig().getLogicDeleteFieldName());
        objectMap.put("versionFieldName", config.getStrategyConfig().getVersionFieldName());
        objectMap.put("activeRecord", globalConfig.isActiveRecord());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger2", globalConfig.isSwagger2());
        objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        objectMap.put("table", tableInfo);
        objectMap.put("enableCache", globalConfig.isEnableCache());
        objectMap.put("baseResultMap", globalConfig.isBaseResultMap());
        objectMap.put("baseColumnList", globalConfig.isBaseColumnList());
        objectMap.put("entity", tableInfo.getEntityName());
        objectMap.put("entitySerialVersionUID", config.getStrategyConfig().isEntitySerialVersionUID());
        objectMap.put("entityColumnConstant", config.getStrategyConfig().isEntityColumnConstant());
        objectMap.put("entityBuilderModel", config.getStrategyConfig().isEntityBuilderModel());
        objectMap.put("entityLombokModel", config.getStrategyConfig().isEntityLombokModel());
        objectMap.put("entityBooleanColumnRemoveIsPrefix", config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix());
        objectMap.put("superEntityClass", getSuperClassName(config.getSuperEntityClass()));
        objectMap.put("superMapperClassPackage", config.getSuperMapperClass());
        objectMap.put("superMapperClass", getSuperClassName(config.getSuperMapperClass()));
        objectMap.put("superServiceClassPackage", config.getSuperServiceClass());
        objectMap.put("superServiceClass", getSuperClassName(config.getSuperServiceClass()));
        objectMap.put("superServiceImplClassPackage", config.getSuperServiceImplClass());
        objectMap.put("superServiceImplClass", getSuperClassName(config.getSuperServiceImplClass()));
        objectMap.put("superControllerClassPackage", verifyClassPacket(config.getSuperControllerClass()));
        objectMap.put("superControllerClass", getSuperClassName(config.getSuperControllerClass()));
        return Objects.isNull(config.getInjectionConfig()) ? objectMap : config.getInjectionConfig().prepareObjectMap(objectMap);
    }
    /**
     * 输出 java xml 文件
     */
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                TemplateConfig template = getConfigBuilder().getTemplate();
                // 自定义内容
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initTableMap(tableInfo);
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        for (FileOutConfig foc : focList) {
                            if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                                writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                            }
                        }
                    }
                }
                // Mp.java
                String entityName = tableInfo.getEntityName();
                if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                    String entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.ENTITY, entityFile)) {
                        writer(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                    }
                }
                // MpMapper.java
                if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                    String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.MAPPER, mapperFile)) {
                        writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
                    }
                }
                // MpMapper.xml
                if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
                    String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                    if (isCreate(FileType.XML, xmlFile)) {
                        writer(objectMap, templateFilePath(template.getXml()), xmlFile);
                    }
                }
                // IMpService.java
                if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                    String serviceFile = String.format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.SERVICE, serviceFile)) {
                        writer(objectMap, templateFilePath(template.getService()), serviceFile);
                    }
                }
                // MpServiceImpl.java
                if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                    String implFile = String.format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                        writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                    }
                }
                // MpController.java
                if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                    String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.CONTROLLER, controllerFile)) {
                        writer(objectMap, templateFilePath(template.getController()), controllerFile);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }


}
```

## 看看将这个map中的参数打印出来的结果

```java
{
	date = 2020 - 09 - 26
	superControllerClassPackage = null
	superServiceImplClassPackage = com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
	baseResultMap = true
	sEntityName = dictBuilding
	superMapperClass = BaseMapper
	superControllerClass = null
	activeRecord = true
	superServiceClass = IService
	superServiceImplClass = ServiceImpl
	table = TableInfo(importPackages = [com.baomidou.mybatisplus.annotation.TableName
		com.example.mybatispluscodegeneral.entity.basic.BaseModel
		com.baomidou.mybatisplus.extension.activerecord.Model
		com.baomidou.mybatisplus.annotation.Version
		com.baomidou.mybatisplus.annotation.TableField
		java.io.Serializable
	] convert = true name = dict_building comment = 楼房类型字典表 entityName = DictBuilding mapperName = DictBuildingMapper xmlName = DictBuildingMapper serviceName = IDictBuildingService serviceImplName = DictBuildingServiceImpl controllerName = DictBuildingController fields = [TableField(convert = true keyFlag = false keyIdentityFlag = false name = cn_name type = varchar(64) propertyName = cnName columnType = STRING comment = 楼房类型名称（ 中文） fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = en_name type = varchar(64) propertyName = enName columnType = STRING comment = 楼房类型名称（ 英文） fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = description type = varchar(255) propertyName = description columnType = STRING comment = 描述 fill = null customMap = null)
	] commonFields = [TableField(convert = true keyFlag = true keyIdentityFlag = true name = id type = int(11) propertyName = id columnType = INTEGER comment = id fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = weight type = int(11) propertyName = weight columnType = INTEGER comment = 权重 fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = create_time type = bigint(20) propertyName = createTime columnType = LONG comment = 创建时间 fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = update_time type = bigint(20) propertyName = updateTime columnType = LONG comment = 更新时间 fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = create_by type = varchar(36) propertyName = createBy columnType = STRING comment = 创建者 fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = update_by type = varchar(36) propertyName = updateBy columnType = STRING comment = 更新者 fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = version type = int(11) propertyName = version columnType = INTEGER comment = 版本 fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = deleted type = int(11) propertyName = deleted columnType = INTEGER comment = 是否有效 0 - 未删除 1 - 已删除 fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = extra type = varchar(1000) propertyName = extra columnType = STRING comment = 额外信息 fill = null customMap = null)
		TableField(convert = true keyFlag = false keyIdentityFlag = false name = tenant_id type = varchar(255) propertyName = tenantId columnType = STRING comment = 租户id fill = null customMap = null)
	] fieldNames = cn_name en_name description)
	package = {
		Entity = com.example.mybatispluscodegeneral.entity
		Mapper = com.example.mybatispluscodegeneral.mapper
		ModuleName =
		Xml = com.example.mybatispluscodegeneral.resources.mapper
		ServiceImpl = com.example.mybatispluscodegeneral.service.impl
		Service = com.example.mybatispluscodegeneral.service
		Controller = com.example.mybatispluscodegeneral.controller
	}
	idType = null
	author = YeZhiyue
	swagger2 = true
	baseColumnList = true
	kotlin = false
	entityLombokModel = true
	superMapperClassPackage = com.baomidou.mybatisplus.core.mapper.BaseMapper
	restControllerStyle = true
	sServiceName = iDictBuildingService
	propertyNameToType = [StrengthenVelocityTemplateEngine.PropertyNameToType(propertyName = cnName type = Object)
		StrengthenVelocityTemplateEngine.PropertyNameToType(propertyName = enName type = Object)
		StrengthenVelocityTemplateEngine.PropertyNameToType(propertyName = description type = Object)
	]
	entityBuilderModel = true
	superServiceClassPackage = com.baomidou.mybatisplus.extension.service.IService
	entitySerialVersionUID = true
	versionFieldName = version
	entityBooleanColumnRemoveIsPrefix = false
	logicDeleteFieldName = deleted
	entityColumnConstant = false
	sMapperName = dictBuildingMapper
	config = com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder @f99f5e0
	enableCache = false
	entity = DictBuilding
	superEntityClass = BaseModel
}
```

