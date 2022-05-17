package com.wan.tool.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wan
 * @version 1.0.0
 * @Description 文件格式枚举
 * @createTime 2021年11月25日 10:20:52
 */
public enum FileFormatEnum {

    /**
     * 新excel
     */
    XLSX("xlsx", "新excel"),

    /**
     * 旧excel
     */
    XLS("xls", "旧excel"),

    /**
     * 图片
     */
    JPG("jpg", "图片"),

    /**
     * 图片
     */
    PNG("png", "图片"),

    /**
     * 图片
     */
    JPEG("jpeg", "图片"),

    /**
     * 文本文件
     */
    TXT("txt", "文本文件"),

    /**
     * 压缩文件
     */
    ZIP("zip", "压缩文件");

    /**
     * 列
     */
    private String column;
    /**
     * 列描述
     */
    private String columnDescriptin;

    FileFormatEnum(String column, String columnDescriptin) {
        this.column = column;
        this.columnDescriptin = columnDescriptin;
    }

    public String getColumn() {
        return this.column;
    }

    /**
     * 获取枚举的所有对象的列
     *
     * @return
     */
    public static List<String> getEnumColumns() {
        FileFormatEnum[] values = FileFormatEnum.values();
        List<String> list = new ArrayList<>(values.length);
        for (FileFormatEnum en : values
        ) {
            list.add(en.getColumn());
        }
        return list;
    }

}
