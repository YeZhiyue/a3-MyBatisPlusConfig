
# 插件配置介绍

## 分页插件

### 配置分页插件

```java
@Bean
public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
    // paginationInterceptor.setOverflow(false);
    // 设置最大单页限制数量，默认 500 条，-1 不受限制
    // paginationInterceptor.setLimit(500);
    // 开启 count 的 join 优化,只针对部分 left join
    paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
    return paginationInterceptor;
}
```

### 配置Mapper分页语句

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

## 主键策略

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

mybatisplus支持在插入操作之后返回主键值，你可以设置你Entity的id生成策略

### 主键id生成策略

1.1 示例

```java
@TableId(value = "id", type = IdType.ASSIGN_UUID)
private String id;
```

1.2 IdType属性值说明

[参考博客](https://www.hangge.com/blog/cache/detail_2904.html)

ASSIGN_UUID（不含中划线的UUID）

ASSIGN_ID（雪花算法）

AUTO（数据库 ID 自增,需要 id 字段是数值型）

## 逻辑删除

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

```java
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
```

## 枚举类型处理

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

### 设备你的枚举类型

1.1 数据库

```java
enum('枚举值1','枚举值2')
```

1.2 定义Java类

```java
public enum GradeEnum {

    PRIMARY(1, "小学"),  SECONDORY(2, "中学"),  HIGH(3, "高中");

    GradeEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    @EnumValue//标记数据库存的值是code
    private final int code;
    //。。。
}
```

## 乐观锁

*<a href="#_top" rel="nofollow" target="_self">返回目录</a>*

```java
@Bean
public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    return new OptimisticLockerInterceptor();
}
```

```java
@Version
private Integer version;
```

