package com.cx.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.entity.Constant;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * aps工具类
 *
 * @author Administrator·
 */
@Slf4j
public class CommonUtil {
    private static final Pattern CommonUtilS = Pattern.compile("[{}\\[\\]<>|\\^~`#%]");

    private CommonUtil() {
    }

    /**
     * 驼峰转下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String camelToUnderscore(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
        if (arr.length == 0) {
            return value;
        }
        StringBuilder result = new StringBuilder();
        IntStream.range(0, arr.length).forEach(i -> {
            if (i != arr.length - 1) {
                result.append(arr[i]).append(StringPool.UNDERSCORE);
            } else {
                result.append(arr[i]);
            }
        });
        return StringUtils.lowerCase(result.toString());
    }

    /**
     * 下划线转驼峰
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String underscoreToCamel(String value) {
        StringBuilder result = new StringBuilder();
        String[] arr = value.split("_");
        for (String s : arr) {
            result.append((String.valueOf(s.charAt(0))).toUpperCase()).append(s.substring(1));
        }
        return result.toString();
    }

    /**
     * 判断是否为 ajax请求
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }

    /**
     * 正则校验
     *
     * @param regex 正则表达式字符串
     * @param value 要匹配的字符串
     * @return 正则校验结果
     */
    public static boolean match(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 获取当前登录用户
     *
     * @return User
     */
    public static User getCurrentUser() {
        try {
            return SecurityUtils.getUser();
        } catch (Exception e) {
            log.error("获取用户失败" + e);
        }
        return null;
    }

    /**
     * 判断是否超级管理员
     *
     * @return User
     */
    public static boolean getRootUser() {
        User user = getCurrentUser();
        if (user == null) {
            return true;
        } else if (StringUtils.isNotBlank(user.getRoleName()) && user.getRoleName().indexOf(Constant.ROOT) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 清理测试字符
     */
    public static String strClear(String str) {
        if (StringUtils.isNotBlank(str)) {
            return CommonUtilS.matcher(str).replaceAll("");
        }
        return "";
    }

    /**
     * 字符串(逗号分割)转List<Long>
     */
    public static List<Long> strToList(String str) {
        if (StringUtils.isNotBlank(str)) {
            return Arrays.stream(str.split(StringPool.COMMA)).map(s -> Long.valueOf(s)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static String view(String viewName) {
        return Constant.VIEW_PREFIX + viewName;
    }

}
