package com.wan.tool.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.wan.tool.common.enums.ErrorCode;
import com.wan.tool.common.enums.FileFormatEnum;
import com.wan.tool.common.enums.Message;
import com.wan.tool.exception.BaseException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.Collator;
import java.util.*;

/**
 * @author wan
 * @data 2021/4/13 18:08
 * 工具类
 */
@Slf4j
@Component
public class ToolUtil {

    private static Connection conn = null;
    private static Statement statement = null;

    @Autowired
    ExcelUtil excelUtil;

    /**
     * 功能描述：
     * -- 字符串去重
     *
     * @param : [s, regex]
     * @return : java.lang.String
     * @author : wan
     * @date : 2021/11/25 11:18
     */
    public static String removeRepeat(String s, String regex) {
        if (true) {
            throw new BaseException(5005, "这是个异常");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(s)) {
            throw new BaseException(5005, "传入参数为空");
        }
        String[] split = null;
        List<String> list = new ArrayList<>();
        List<String> repeat = new ArrayList<>();
        split = s.split(regex);
        int max = split.length;
        for (int j = 0; j < max; j++) {
            list.add(split[j]);
        }
        for (int j = 0; j < max - 1; j++) {
            for (int k = j + 1; k < max; k++) {
                if (split[k].equals(split[j])) {
                    int x = 0;
                    for (int l = 0; l < list.size(); l = x) {
                        x = l;
                        if (split[k].equals(list.get(l))) {
                            list.remove(l);
                        } else {
                            x += 1;
                        }
                    }
                    if (!repeat.contains(split[k])) {
                        repeat.add(split[k]);
                    }
                }
            }
        }
        if (repeat.size() != 0) {
            repeat.stream().forEach(r -> list.add(r));
        }
        for (int j = 0; j < list.size(); j++) {
            if (j == 0) {
                s = list.get(0) + regex;
            } else {
                s = s + list.get(j) + regex;
            }
        }
        String s2 = s.substring(0, s.length() - 1);
        log.info("去重后:" + s2);
        System.out.println(s2);
        return s2;
    }

    /**
     * 功能描述：
     * -- 去换行、空白
     *
     * @param : [s]
     * @return : java.lang.String
     * @author : wan
     * @date : 2021/11/25 11:20
     */
    public static String removeBlank(String s) {
        if (StringUtils.isEmpty(s)) {
            return "传入参数为空";
        }
        String replace1 = s.replace(" ", "");
        String replace = replace1.replace("\n", "");
        log.info("去空白后:" + replace);
        return replace;
    }

    /**
     * 功能描述：
     * -- 连接本地数据库
     *
     * @param : []
     * @return : java.sql.Statement
     * @author : wan
     * @date : 2021/11/25 11:21
     */
    public static Statement getConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=GMT%2B8", "root", "123456");
            statement = conn.createStatement();
        } catch (Exception e) {
            log.info("出错：" + e.getMessage());
        }
        return statement;
    }

    /**
     * 功能描述：
     * -- 关闭资源
     *
     * @param : []
     * @return : void
     * @author : wan
     * @date : 2021/11/25 11:22
     */
    @SneakyThrows
    public static void closeSource() {
        conn.close();
        statement.close();
    }

    /**
     * 功能描述：
     * -- 递归函数
     *
     * @param : [list, n]
     * @return : int
     * @author : wan
     * @date : 2021/11/25 11:22
     */
    public static int factorial(List list, int n) {
        int result;
        if (n < 0) {
            log.info("输入有误！！！");
            return 0;
        }
        if (n == 0 || n == 1) {
            result = 1;
        } else {
            list.add(n);
            result = factorial(list, n - 1);
        }
        return result;
    }

    /**
     * 功能描述：
     * -- 汉字排序
     *
     * @param : [collection]
     * @return : java.util.List<org.apache.poi.ss.formula.functions.T>
     * @author : wan
     * @date : 2021/11/25 11:22
     */
    public static List<T> collectionSort(List<T> collection) {
        log.info("排序前");
        collection.forEach(System.out::println);
        Collator compare = Collator.getInstance(Locale.CHINA);
        //第一种
//        Collections.sort(collection, new Comparator<Object>() {
//            @Override
//            public int compare(Object o1, Object o2) {
//                return compare.compare(o1, o2);
//            }
//        });
        //第二种
        collection.sort((c1, c2) -> compare.compare(c1, c2));
        log.info("排序后");
        collection.forEach(System.out::println);
        return collection;
    }

    /**
     * 功能描述：
     * -- 校验对象是否为空
     *
     * @param : object
     * @return : boolean
     */
    public static boolean isBlank(Object... object) {
        if (null == object || object.length == 0) {
            return true;
        }
        for (int i = 0; i < object.length; i++) {
            Object o = object[i];
            if (o == null) {
                return true;
            }
            if (o instanceof String && o == "") {
                return true;
            }
            if (o instanceof Collection && ((Collection<?>) o).size() == 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * 功能描述：
     * -- 文件导入
     *
     * @param : [file]
     * @return : boolean
     * @author : wan
     * @date : 2021/11/25 11:36
     */
    public void fileImport(MultipartFile file) {
        if (null == file) {
            throw new BaseException(ErrorCode.PARAM_NOT_FOUND.value(), Message.PARAM_NOT_FOUND.value());
        }
        //导入文件名称
        String filename = file.getOriginalFilename();
        log.info("输入的文件是{}", filename);
        //由于.在正则有特殊含义，所以替换
        filename = filename.replace(".", ",");
        String[] split = filename.split(",");
        if (split.length == 0) {
            throw new BaseException(ErrorCode.DATA_ERROR.value(), Message.DATA_ERROR.value());
        }
        String tailName = split[split.length - 1];
        if (!FileFormatEnum.getEnumColumns().contains(tailName)) {
            throw new BaseException(ErrorCode.DATA_NOT_FOUND.value(), Message.DATA_NOT_FOUND.value());
        } else {
            //获取PhotoUtil对象
            ExcelUtil.PhotoUtil photoUtil = excelUtil.getPhotoUtil();
            //根据文件不同类型调不同util
            if (Objects.equals(tailName, FileFormatEnum.XLSX.getColumn())) {
//                excelUtil.excelImport(file, Object.class);
            }
            if (Objects.equals(tailName, FileFormatEnum.JPG.getColumn()) || Objects.equals(tailName, FileFormatEnum.PNG.getColumn()) || Objects.equals(tailName, FileFormatEnum.JPEG.getColumn())) {
                photoUtil.photoImport(file, tailName);
            }
        }
    }

    /**
     * 功能描述：
     * -- excel导出
     *
     * @param : [object]
     * @return : void
     * @author : wan
     * @date : 2021/12/6 14:51
     */
    public void excelOut(Object object) {
        excelOut(object, null);
    }

    /**
     * 功能描述：
     * -- excel导出
     *
     * @param : [object, excelName, sheetName]
     * @return : void
     * @author : wan
     * @date : 2021/12/6 14:51
     */
    public void excelOut(Object object, String excelName) {
        excelUtil.excelOut("C:\\Users\\edy\\Desktop\\" + excelName + "." + FileFormatEnum.XLSX.getColumn(), excelName, object);
    }

    /**
     * 功能描述：
     * -- 获取默认写出路径
     *
     * @param : [format: 文件格式]
     * @return : java.lang.String
     * @author : wan
     * @date : 2021/12/6 14:54
     */
    private static String getDefaultPath(String format) {
        return "C:\\Users\\edy\\Desktop\\" + DateUtil.format(DateUtil.date(), DatePattern.CHINESE_DATE_TIME_PATTERN) + "." + format;
    }

}

