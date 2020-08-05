package com.ohyoung.generator.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.json.JSONUtil;
import com.ohyoung.generator.domain.GeneratorColumn;
import com.ohyoung.generator.domain.GeneratorSetting;
import com.ohyoung.generator.domain.TemplateSetting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成相关工具类
 *
 * @author ohYoung
 * @date 2020/7/28 9:54
 */
public class GeneratorUtil {

    /**
     * 模板所在路径
     */
    private static final String TEMPLATE_CONFIG_PATH = "freemarker/ou-admin/Config.json";

    /**
     * 包名前缀，一般为src/main/java
     */
    private static final String PACKAGE_PREFIX = "src"+ File.separator + "main" + File.separator + "java";

    public static void generateCode(List<GeneratorColumn> columns, GeneratorSetting setting) throws IOException {
        // 将数据库字段类型转为java类型, 将字段名转为小驼峰
        for (GeneratorColumn column : columns) {
            column.setJavaDataType(dbType2JavaType(column.getColumnType()));
            column.setColumnHumpName(name2Hump(column.getColumnName(), false));
        }
        Map<String, Object> generatorMap = getGeneratorMap(columns, setting);
        List<TemplateSetting> settings = getConfig();
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("freemarker", TemplateConfig.ResourceMode.CLASSPATH));
        for (TemplateSetting templateSetting : settings) {
            Template template = engine.getTemplate("ou-admin" + File.separator + templateSetting.getTemplateName());
            String outPath;
            String fileName = generatorMap.get("entityName").toString();
            // 是否为前端模板
            // 例: D:\Work\WebProjects\Git\ou-admin\src\pages
            //      \test
            //      \index.tsx
            if (templateSetting.getView()) {
                if (StrUtil.isNotEmpty(templateSetting.getFilename())) {
                    String servicePath = setting.getWebApiPath().replace(".", "/")
                                            + "/" + fileName;
                    generatorMap.put("servicePath", servicePath);
                    fileName = templateSetting.getFilename();
                }
                outPath = setting.getWebRootPath() + File.separator + templateSetting.getPackageName().replace(".", File.separator)
                        + File.separator + setting.getWebApiPath().replace(".", File.separator)
                        + File.separator + fileName + "." + templateSetting.getFileType();
            } else {
                // 后端模板
                // 例: D:\Work\IdeaProjects\Git\ohyoung-base\ohyoung-system
                //      \src\main\java\com\ohyoung\test\dto
                //      \test.java
                outPath = System.getProperty("user.dir") + File.separator + setting.getModuleName()
                        + File.separator + PACKAGE_PREFIX + File.separator + setting.getPackageName().replace(".", File.separator)
                        + File.separator + templateSetting.getPackageName().replace(".", File.separator)
                        + File.separator + fileName + templateSetting.getSuffix() + "." + templateSetting.getFileType();
            }
            File file = new File(outPath);
            generateFile(file, template, generatorMap);
        }
    }

    private static void generateFile(File file, Template template, Map<String, Object> generatorMap) throws IOException {
        // 生成目标文件
        Writer writer = null;
        try {
            FileUtil.touch(file);
            writer = new FileWriter(file);
            template.render(generatorMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            assert writer != null;
            writer.close();
        }

    }

    /**
     * 根据字段信息和配置信息生成模板变量的map
     *
     * @param columns 字段信息
     * @param setting 配置信息
     * @return 模板变量
     */
    private static Map<String, Object> getGeneratorMap(List<GeneratorColumn> columns, GeneratorSetting setting) {
        Map<String, Object> generatorMap = new HashMap<>(11);
        String tableName = columns.get(0).getTableName();
        String packageName = setting.getPackageName();
        String interfacePrefix = setting.getInterfacePrefix();
        String interfaceName = setting.getInterfaceName();
        generatorMap.put("useSwagger", setting.getUseSwagger());
        generatorMap.put("author", setting.getAuthorName());
        generatorMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        generatorMap.put("package", packageName);
        // 接口前缀, 一般位于controller上方
        generatorMap.put("interfacePrefix", interfacePrefix);
        // 接口名, 一般位于Controller中接口方法上方
        generatorMap.put("interfaceName", interfaceName);
        // 完整路径, 用于生成前端请求路径
        generatorMap.put("requestUrl", interfacePrefix + File.separator + interfaceName);
        // 表名
        generatorMap.put("tableName", tableName);
        // 字段信息
        generatorMap.put("fields", columns);
        // 实体类名
        generatorMap.put("entityName", name2Hump(tableName, true));
        // 实体类中文名
        generatorMap.put("entityCnName", name2Hump(tableName, true));
        return generatorMap;
    }


    private static List<TemplateSetting> getConfig() {
        URL resource = ClassLoader.getSystemResource(TEMPLATE_CONFIG_PATH);
        assert resource != null;
        String configPath = resource.getPath();
        File configFile = new File(configPath);
        String jsonContent = FileUtil.readUtf8String(configFile);
        return Convert.convert(new TypeReference<List<TemplateSetting>>() {
        }, JSONUtil.parseArray(jsonContent));
    }

    /**
     * 数据库类型转Java类型
     *
     * @param type 数据库类型
     * @return 包装类型
     */
    private static String dbType2JavaType(String type) {
        String javaType;
        if (type.contains("bigint")) {
            javaType = "Long";
        } else if (type.contains("char") || type.contains("text")) {
            javaType = "String";
        } else if (type.contains("time")) {
            javaType = "LocalDateTime";
        } else if (type.contains("tinyint(1)")) {
            javaType = "Boolean";
        } else {
            javaType = "Integer";
        }
        return javaType;
    }

    /**
     * 下划线命名转驼峰 xx_yy -> XxYy /xxYy
     *
     * @param name             源值
     * @param isUpperCamelCase 是否转为大驼峰, 否则为小驼峰
     * @return 转换后的值
     */
    private static String name2Hump(String name, Boolean isUpperCamelCase) {
        StringBuilder entityName = new StringBuilder();
        String[] strings = name.split("_");
        for (int i = 0; i < strings.length; i++) {
            String single = strings[i].toLowerCase();
            single = (i == 0 && !isUpperCamelCase) ? single : captureName(single);
            entityName.append(single);
        }
        return entityName.toString();
    }

    /**
     * 首字母大写
     *
     * @param name 原值
     * @return 转换后的值
     */
    private static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
