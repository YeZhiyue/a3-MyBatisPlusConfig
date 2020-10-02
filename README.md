<font color=#ca0c16 size=8> MyBatisPlus插件集合

<a id="_top"></a>

@[TOC](文章目录)

# 前言

<font color=#999AAA > 

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1"> MybatisPlus中集成了很多方便好用的插件，其中分页插件、自动填充插件、乐观锁插件、SQL执行分析插件...都是我们经常使用的。下面来一一介绍各个插件如何配置和使用。

---

# 配置我们的依赖

```java
<dependencies>
    <!-- 核心Web包 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- ======================== MybatisPlus =========================-->
    <!-- mysql驱动包 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <!-- 核心包 -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>${mybatisplus.version}</version>
    </dependency>
    <!-- 代码生成器 -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-generator</artifactId>
        <version>${mybatisplus.version}</version>
    </dependency>
    <!-- 代码生成模板引擎包 -->
    <dependency>
        <groupId>org.apache.velocity</groupId>
        <artifactId>velocity-engine-core</artifactId>
        <version>2.2</version>
    </dependency>
    <!-- SQL解析器 -->
    <dependency>
        <groupId>p6spy</groupId>
        <artifactId>p6spy</artifactId>
        <version>3.8.5</version>
    </dependency>
    <!-- ======================== MybatisPlus END =========================-->

    <!-- ======================== 单包 =========================-->
    <!-- 参数校验包 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <!-- 文档包 -->
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>2.9.2</version>
    </dependency>
    <!-- JavaBean工具包 -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <!-- 热部署包 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
    <!-- 测试包 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
        <exclusions>
            <exclusion>
                <groupId>org.junit.vintage</groupId>
                <artifactId>junit-vintage-engine</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <!-- ======================== 单包 END =========================-->

</dependencies>
```

---

# 分页插件

## 配置

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*


```java
@Slf4j
@Component
// Mapper包扫描
@MapperScan("com.example")
public class MybatisPlusConfig {
    // 分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(50);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}
```

## 使用

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

1.1 原生使用

```java
public Page<DictBed> adminPage(int pPageNum, int pPageSize, DictBed pDictBed) {
    return page(new Page(pPageNum, pPageSize), Wrappers.lambdaQuery(pDictBed)
            .like(!StringUtils.isBlank(pDictBed.getCnName()), DictBed::getCnName, pDictBed.getCnName())
            .like(!StringUtils.isBlank(pDictBed.getEnName()), DictBed::getEnName, pDictBed.getEnName())
            .like(!StringUtils.isBlank(pDictBed.getDescription()), DictBed::getDescription, pDictBed.getDescription())
            .eq(!StringUtils.isBlank(pDictBed.getId()), DictBed::getId, pDictBed.getId())
            .orderByDesc(DictBed::getCreateTime)
    );
}
```

1.2 Mapper中使用

```java
<select id="page1" resultType="com.example.mybatispluscodegeneral.entity.DictBed">
    SELECT id,cn_name FROM dict_bed WHERE id>#{min}
</select>

<select id="page2" resultType="com.example.mybatispluscodegeneral.entity.DictBed">
    SELECT id,cn_name FROM dict_bed WHERE id>#{min}
</select>
```

```java
Page<DictBed> page1(Page<?> page, @Param("min") Integer min);
// or
List<DictBed> page2(Page<DictBed> page,@Param("min") Integer min);
```

---

# 主键插件

## 情景一:直接Entity实体字段配置主键生成策略

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

我们直接在Entity的主键字段上添加注解，配置主键生成策略，不需要再配置插件。

```java
@TableId(value = "id", type = IdType.ASSIGN_UUID)
private String id;
```

[参考博客](https://www.hangge.com/blog/cache/detail_2904.html)

ASSIGN_UUID（不含中划线的UUID）

ASSIGN_ID（雪花算法）

AUTO（数据库 ID 自增,需要 id 字段是数值型）

## 情景二:自定义主键生成方式，但是只能是数值型

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

```java
@Slf4j
@Component
// Mapper包扫描
@MapperScan("com.example")
public class MybatisPlusConfig {
    // 自定义主键生成规则
    @Bean
    public IdentifierGenerator IdentifierGeneratorConfig() {
        return new IdentifierGenerator() {
            @Override
            public Long nextId(Object entity) {
                //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
                String bizKey = entity.getClass().getName();
                log.info("bizKey:{}", bizKey);
                MetaObject metaObject = SystemMetaObject.forObject(entity);
                String name = (String) metaObject.getValue("version");
                final long id = IdWorker.getId();
                log.info("为{}生成主键值->:{}", name, id);
                return id;
            }
        };
    }
}
```

---

# 逻辑删除插件

```java
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
```

---

# 枚举类插件

1.1 数据库添加字段

```java
alter table park.dict_bed
	add type varchar(10) null comment '种类分组';
```

1.2 创建Java枚举

```java
@Getter
@AllArgsConstructor
public enum TypeEnum {

    ONE("one"),
    TWO("two");

    @EnumValue // 表示和数据库字段匹配的枚举值
    private final String value;
}
```

1.3 yml配置扫描包

```java
mybatis-plus:
  configuration:
    default-enum-type-handler: org.apache.ibatis.type.EnumOrdinalTypeHandler
  # 枚举包扫描
  type-enums-package: com.example.mybatispluscodegeneral.*
```

1.4 使用(注意你的枚举对字段进行限制)

1.4.1 添加

```java
[
    {
        "cnName": "测试床",
        "enName": "single bed",
        "description": "测试摇号",
        "location": "28号楼",
        "type":"one" // 只能为"one","tow"中的一种(你的枚举对其做了限制)
    }
]
```

1.4.2 查询(会返回你枚举的字段名而不是其中的值)

```java
{
    "id": "1311840615755771906",
    "cnName": "测试床",
    "enName": "single bed",
    "type": "ONE", // 枚举字段名
    "description": "测试摇号"
}
```

---

# 乐观锁

```java
@Slf4j
@Component
// Mapper包扫描
@MapperScan("com.example")
public class MybatisPlusConfig {
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
```

```java
@Version
private Integer version;
```

---

# 自动填充插件

通常一些更新或者插入的时间戳会需要我们进行自动填充，用了官方配置好像并没有实现效果，下面是个人的配置。

```java
@Slf4j
@Component
// Mapper包扫描
@MapperScan("com.example")
public class MybatisPlusConfig {
    // 自动填充
    @Bean
    public MetaObjectHandler autoFillInterceptor() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                log.info("start insert fill ....");
                if (Objects.isNull(metaObject.getValue("createTime"))) {
                    metaObject.setValue("createTime", System.currentTimeMillis());
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                log.info("start update fill ....");
                if (Objects.isNull(metaObject.getValue("updateTime"))) {
                    metaObject.setValue("updateTime", System.currentTimeMillis());
                }
            }
        };
    }
}
```

---

# Sql打印分析插件

1.1 配置插件

```java
@Slf4j
@Component
// Mapper包扫描
@MapperScan("com.example")
public class MybatisPlusConfig {
    // sql分析日志
    @Bean
    public SqlExplainInterceptor sqlExplainInterceptor() {
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);
        return sqlExplainInterceptor;
    }
}
```

1.2 日志打印配置

```java
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

1.3 配置类 spy.properties

```java
#3.2.1以上使用
modulelist=com.baomidou.mybatisplus.extension.p6spy.MybatisPlusLogFactory,com.p6spy.engine.outage.P6OutageFactory
#3.2.1以下使用或者不配置
#modulelist=com.p6spy.engine.logging.P6LogFactory,com.p6spy.engine.outage.P6OutageFactory
# 自定义日志打印
logMessageFormat=com.baomidou.mybatisplus.extension.p6spy.P6SpyLogger
#日志输出到控制台
appender=com.baomidou.mybatisplus.extension.p6spy.StdoutLogger
# 使用日志系统记录 sql
#appender=com.p6spy.engine.spy.appender.Slf4JLogger
# 设置 p6spy driver 代理
deregisterdrivers=true
# 取消JDBC URL前缀
useprefix=true
# 配置记录 Log 例外,可去掉的结果集有error,info,batch,debug,statement,commit,rollback,result,resultset.
excludecategories=info,debug,result,commit,resultset
# 日期格式
dateformat=yyyy-MM-dd HH:mm:ss
# 实际驱动可多个
#driverlist=org.h2.Driver
# 是否开启慢SQL记录
outagedetection=true
# 慢SQL记录标准 2 秒
outagedetectioninterval=2
```
