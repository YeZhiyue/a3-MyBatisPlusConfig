package com.example.mybatispluscodegeneral.utils.mybatisplus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 叶之越
 * Description
 * Date 2020/9/23
 * Time 12:42
 * Mail 739153436@qq.com
 */
public class CodeAutoGenerator implements PackagePathConfig, TemplatePathConfig {

    /**
     * 常用配置就直接到对应的常数接口去配置即可
     * <p>
     * BasicParamConstant 接口的基本配置都已经存在了
     */
    public static void main(String[] args) {
        // 代码生成
        new AutoGenerator()
                .setGlobalConfig(globalConfig())
                .setDataSource(dataSourceConfig())
                .setStrategy(strategyConfig())
                .setPackageInfo(packageConfig())
                // 因为使用了自定义模板,所以需要把各项置空否则会多生成一次
                .setTemplate(templateConfig())
                // 使用的模板引擎，如果不是默认模板引擎则需要添加模板依赖到pom
                .setTemplateEngine(new VelocityTemplateEngine())
                // 自定义配置
                .setCfg(injectionConfig())
                .execute();
    }

    /**
     * 自定义配置
     */
    private static InjectionConfig injectionConfig() {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();

                // ========================== 设置Page查询参数 ==========================
                // ==========================2020/9/30 15:48==========================
                List<MyCodeConfig> myCodeConfigList = new ArrayList<>();
                String serviceName = "";
                String mapperName = "";
                for (int i = 0; i < this.getConfig().getTableInfoList().size(); i++) {
                    StringBuilder pageBuilder = new StringBuilder();
                    TableInfo table = this.getConfig().getTableInfoList().get(i);
                    String className = table.getEntityName();
                    for (TableField field : table.getFields()) {
                        // 字段名
                        String fieldName = strFirstToUp(field.getPropertyName());
                        // 对象类型
                        IColumnType columnType = field.getColumnType();
                        if ("Id".equals(fieldName)) {
                            pageBuilder.append(".eq(!StringUtils.isBlank(p" + className + ".get" + fieldName + "()), " + className + "::get" + fieldName + ", p" + className + ".get" + fieldName + "())\n");
                        } else if ("String".equals(columnType.getType())) {
                            pageBuilder.append(".like(!StringUtils.isBlank(p" + className + ".get" + fieldName + "()), " + className + "::get" + fieldName + ", p" + className + ".get" + fieldName + "())\n");
                        } else {
                            pageBuilder.append(".like(!Objects.isNull(p" + className + ".get" + fieldName + "()), " + className + "::get" + fieldName + ", p" + className + ".get" + fieldName + "())\n");
                        }
                    }

                    for (TableField field : table.getCommonFields()) {
                        String fieldName = strFirstToUp(field.getPropertyName());
                        IColumnType columnType = field.getColumnType();
                        // 字段名
                        String strFirstToUp = strFirstToUp(field.getPropertyName());
                        if ("Id".equals(strFirstToUp) && "String".equals(columnType.getType())) {
                            pageBuilder.append(".eq(!StringUtils.isBlank(p" + className + ".get" + strFirstToUp + "()), " + className + "::get" + strFirstToUp + ", p" + className + ".get" + strFirstToUp + "())\n");
                        } else if ("Id".equals(strFirstToUp)) {
                            pageBuilder.append(".eq(!Objects.isNull(p" + className + ".get" + fieldName + "()), " + className + "::get" + fieldName + ", p" + className + ".get" + fieldName + "())\n");
                        }
                    }
                    myCodeConfigList.add(new MyCodeConfig(className, pageBuilder.toString()));
                    /// Service、Mapper字段
                    serviceName = strFirstToLow(table.getServiceName());
                    mapperName = strFirstToUp(table.getMapperName());
                    /// END
                }
                /// END

                /// 时间日期
                String dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(System.currentTimeMillis()));
                String[] split = dateTime.split(" ");
                /// END

                map.put("page", myCodeConfigList);
                map.put("sServiceName", serviceName);
                map.put("sMapperName", mapperName);
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

    /**
     * 全局配置
     */
    private static GlobalConfig globalConfig() {
        return new GlobalConfig()
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
    }

    /**
     * 策略配置
     */
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

    /**
     * 数据源配置
     */
    private static DataSourceConfig dataSourceConfig() {
        return new DataSourceConfig()
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
    }


    /**
     * 自定义输出文件配置
     */
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

    /**
     * 包配置
     * 设置包路径用于导包时使用，路径示例：com.path
     */
    private static PackageConfig packageConfig() {
        return new PackageConfig()
                // 模块名
                .setModuleName(StringUtils.isEmpty(AFTER_MODULE)?"":AFTER_MODULE.substring(1))
                .setParent(PARENT_PACKAGE_PATH.replace('/', '.').substring(1))
                .setEntity(ENTITY_PATH.replace('/', '.').substring(1, ENTITY_PATH.length() - 1))
                .setMapper(MAPPER_PATH.replace('/', '.').substring(1, MAPPER_PATH.length() - 1))
                .setXml(XML_PATH.replace('/', '.').substring(1, XML_PATH.length() - 1))
                .setService(SERVICE_PATH.replace('/', '.').substring(1, SERVICE_PATH.length() - 1))
                .setServiceImpl(SERVICE_IMPL_PATH.replace('/', '.').substring(1, SERVICE_IMPL_PATH.length() - 1))
                .setController(CONTROLLER_PATH.replace('/', '.').substring(1, CONTROLLER_PATH.length() - 1));
    }

    /**
     * 模板配置
     */
    private static TemplateConfig templateConfig() {
        return new TemplateConfig()
                // 置空后方便使用自定义输出位置
                .setEntity(null)
                .setXml(null)
                .setMapper(null)
                .setService(null)
                .setServiceImpl(null)
                .setController(null);
    }


    public static String strFirstToUp(String str) {
        String first = str.substring(0, 1);
        String after = str.substring(1); //substring(1),获取索引位置1后面所有剩余的字符串
        first = first.toUpperCase();
        return first + after;
    }

    public static String strFirstToLow(String str) {
        String first = str.substring(0, 1);
        String after = str.substring(1); //substring(1),获取索引位置1后面所有剩余的字符串
        first = first.toLowerCase();
        return first + after;
    }
}
