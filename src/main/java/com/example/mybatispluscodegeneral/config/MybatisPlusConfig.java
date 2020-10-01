package com.example.mybatispluscodegeneral.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 叶之越
 * Description
 * Date 2020/9/30
 * Time 23:18
 * Mail 739153436@qq.com
 */
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

    // 乐观锁
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

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

    // sql分析日志
    @Bean
    public SqlExplainInterceptor sqlExplainInterceptor() {
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);
        return sqlExplainInterceptor;
    }

    // 设置主键生成策略 需要设置实体类的字段是 type=INPUT
    @Bean
    public H2KeyGenerator h2KeyGenerator() {
        return new H2KeyGenerator();
    }

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
