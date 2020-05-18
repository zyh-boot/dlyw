package com.cx.common.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mybatis plus 代码生成器配置
 *
 * @author Tellsea
 * @Description Created on 2019/7/18
 * Mybatis plus 官网：https://mp.baomidou.com/guide/generator.html
 */
public class MybatisPlusCodeConfig {

    /**
     * 项目路径
     */
    private static final String PROJECTPATH = System.getProperty("user.dir");
    /**
     * 模板存放位置
     */
    private static final String PATH = "/templates/ftl/";
    private static final String TEMPLATEPATHENTITY = PATH + "entity.java.ftl";
    private static final String TEMPLATEPATHCONTROLLER = PATH + "controller.java.ftl";
    private static final String TEMPLATEPATHVIEWCONTROLLER = PATH + "viewController.java.ftl";
    private static final String TEMPLATEPATHSERVICE = PATH + "service.java.ftl";
    private static final String TEMPLATEPATHSERVICEIMPL = PATH + "serviceImpl.java.ftl";
    private static final String TEMPLATEPATHMAPPER = PATH + "mapper.java.ftl";
    private static final String TEMPLATEPATHMAPPERXML = PATH + "mapper.xml.ftl";
    private static final String TEMPLATEPATHJSP = PATH + "page.html.ftl";
    private static final String TEMPLATEPATHJSPADD = PATH + "add.html.ftl";
    private static final String TEMPLATEPATHJSPUPDATE = PATH + "update.html.ftl";
    /**
     * 基类路径
     */
    private static final String BASEPACKAGE = "com.cx.common.module.base";
    /**
     * 生成文件位置
     */
    private static String JAVALOCATION = PROJECTPATH + "";
    private static String XMLLOCATION = PROJECTPATH + "/src/main/resources/mapper/module/";
    private static String pageLocation = PROJECTPATH + "/src/main/resources/templates/views/";
    private static Pattern pattern = Pattern.compile("[A-Z]");

    /**
     * 代码生成
     *
     * @param model     模块名称
     * @param tableName 表名
     */
    public static void codeGenerator(String model, String tableName) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(PROJECTPATH + "/src/main/java");
        gc.setAuthor("admin");
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setSwagger2(true);
//        gc.setEnableCache(true);
        gc.setServiceName("I%sService");
        /*gc.setServiceName("%sService");*/
        /*gc.setEntityName("%s");
        gc.setMapperName("%sDaoImpl");
        gc.setXmlName("%sDao");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");*/
        gc.setFileOverride(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/cxzn_zg?characterEncoding=utf8&serverTimezone=GMT%2b8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("cxzn2020");
        mpg.setDataSource(dsc);

        // 包配置
        final PackageConfig pc = new PackageConfig();
        pc.setModuleName(model);
        pc.setParent("com.cx.module");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 自定义输出配置，配置会被优先输出
        JAVALOCATION = "/src/main/java/com/cx/" + model;
        pageLocation += model;
        cfg.setFileOutConfigList(getFileOutConfig());
        mpg.setCfg(cfg);

        // 配置模板
        // TemplateConfig templateConfig = new TemplateConfig();
        //templateConfig.setXml(null);

        TemplateConfig config = new TemplateConfig();
        config.setController(TEMPLATEPATHCONTROLLER.replace(".ftl", ""));
        config.setService(TEMPLATEPATHSERVICE.replace(".ftl", ""));
        config.setServiceImpl(TEMPLATEPATHSERVICEIMPL.replace(".ftl", ""));
        config.setMapper(TEMPLATEPATHMAPPER.replace(".ftl", ""));
        config.setEntity(TEMPLATEPATHENTITY.replace(".ftl", ""));
        config.setXml(null);
        mpg.setTemplate(config);

//        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BASEPACKAGE + ".entity.BaseEnity");
        strategy.setSuperControllerClass("com.cx.common.controller.BaseController");
//        strategy.setSuperServiceClass(basePackage + ".service.BaseService");
        strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        strategy.setEntityLombokModel(true);
        strategy.setInclude(tableName);

        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("a_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();

        gc.setControllerName("%sViewController");
        cfg.setFileOutConfigList(getFileOutViewConfig());
        mpg.setCfg(cfg);
        config = new TemplateConfig();
        config.setController(TEMPLATEPATHVIEWCONTROLLER.replace(".ftl", ""));
        config.setXml(null);
        mpg.setTemplate(config);
        mpg.setConfig(null);
        mpg.execute();

    }

    /**
     * 自定义输出配置
     *
     * @return
     */
    private static List<FileOutConfig> getFileOutConfig() {
        List<FileOutConfig> focList = new ArrayList<>();

        // Entity
        focList.add(new FileOutConfig(TEMPLATEPATHENTITY) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return JAVALOCATION + "/entity/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });

        // Controller
        focList.add(new FileOutConfig(TEMPLATEPATHCONTROLLER) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                tableInfo.setXmlName(convertToLowercase(tableInfo.getEntityName()));
                return JAVALOCATION + "/controller/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(TEMPLATEPATHVIEWCONTROLLER) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                tableInfo.setXmlName(convertToLowercase(tableInfo.getEntityName()));
                return JAVALOCATION + "/controller/" + "ViewController" + StringPool.DOT_JAVA;
            }
        });

        // Service
        focList.add(new FileOutConfig(TEMPLATEPATHSERVICE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return JAVALOCATION + "/service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });

        // ServiceImpl
        focList.add(new FileOutConfig(TEMPLATEPATHSERVICEIMPL) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return JAVALOCATION + "/service/impl/" + tableInfo.getEntityName() + "ServiceImpl" + StringPool.DOT_JAVA;
            }
        });

        // Mapper.java
        focList.add(new FileOutConfig(TEMPLATEPATHMAPPER) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return JAVALOCATION + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
            }
        });

        // Mapper.xml
        focList.add(new FileOutConfig(TEMPLATEPATHMAPPERXML) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return XMLLOCATION + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        // 生成页面
        focList.add(new FileOutConfig(TEMPLATEPATHJSP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String fileName = humpTurnUnderscore(new StringBuffer(convertToLowercase(tableInfo.getEntityName()))).toString();
                return pageLocation + "/" + convertToLowercase(tableInfo.getEntityName()) + "/index.html";
            }
        });
        // 生成页面
        focList.add(new FileOutConfig(TEMPLATEPATHJSPADD) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String fileName = humpTurnUnderscore(new StringBuffer(convertToLowercase(tableInfo.getEntityName()))).toString();
                return pageLocation + "/" + convertToLowercase(tableInfo.getEntityName()) + "/add.html";
            }
        });
        // 生成页面
        focList.add(new FileOutConfig(TEMPLATEPATHJSPUPDATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String fileName = humpTurnUnderscore(new StringBuffer(convertToLowercase(tableInfo.getEntityName()))).toString();
                return pageLocation + "/" + convertToLowercase(tableInfo.getEntityName()) + "/update.html";
            }
        });
        return focList;
    }

    /**
     * 自定义输出配置
     *
     * @return
     */
    private static List<FileOutConfig> getFileOutViewConfig() {
        List<FileOutConfig> focList = new ArrayList<>();

        // Controller
        focList.add(new FileOutConfig(TEMPLATEPATHVIEWCONTROLLER) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                tableInfo.setXmlName(convertToLowercase(tableInfo.getEntityName()));
                return JAVALOCATION + "/controller/" + "ViewController" + StringPool.DOT_JAVA;
            }
        });
        return focList;
    }

    /**
     * 全部转为小写
     *
     * @param oldStr
     * @return
     */
    public static String convertToLowercase(String oldStr) {
        char[] chars = oldStr.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static StringBuffer humpTurnUnderscore(StringBuffer str) {
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        } else {
            return sb;
        }
        return humpTurnUnderscore(sb);
    }
}
