package com.wan.tool.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.wan.tool.common.enums.ErrorCode;
import com.wan.tool.common.enums.Message;
import com.wan.tool.exception.BaseException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author wan
 * @version 1.0.0
 * @Description excel工具类
 * @createTime 2021年11月24日 09:52:08
 */
@Slf4j
@Component
public class ExcelUtil {

    @Autowired
    private HttpServletResponse response;

    /**
     * 功能描述：
     * -- 获取PhotoUtil对象
     *
     * @param : []
     * @return : com.wan.util.utils.ExcelUtil.PhotoUtil
     * @author : wan
     * @date : 2021/12/3 18:11
     */
    public PhotoUtil getPhotoUtil() {
        return new PhotoUtil();
    }

    /**
     * 功能描述：
     * -- excel导入
     *
     * @param : [file]
     * @return : com.example.demo.common.vo.BaseResponse<java.lang.Boolean>
     * @author : wan
     * @date : 2021/11/24 18:26
     */
    public void excelImport(MultipartFile file, Class<T> aClass) {
        if (null == file) {
            throw new BaseException(5005, "文件为空");
        }
        try {
            InputStream inputStream = file.getInputStream();
            excelRead(inputStream, aClass);
        } catch (IOException e) {
            log.error("excel解析错误：", e);
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：
     * -- excel读取
     *
     * @param : [inputStream]
     * @return : java.lang.Boolean
     * @author : wan
     * @date : 2021/11/24 18:35
     */
    public <T> Object excelRead(InputStream inputStream, Class<T> aClass) {
        XSSFWorkbook xWorkBook = null;
        XSSFSheet sheet = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        //各列
        //内容提取
        try {
            xWorkBook = new XSSFWorkbook(inputStream);
            sheet = xWorkBook.getSheet("Sheet1");
            //表格最后一行
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i < lastRowNum; i++) {
                row = sheet.getRow(i);
                //一行总共多少列
                short lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    String cellValue = row.getCell(j).getStringCellValue();
                }
            }
            log.info("读取成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aClass;
    }

    /**
     * 功能描述：
     * -- excel导出到指定路径
     *
     * @param : [path, t]
     * @return : void
     * @author : wan
     * @date : 2021/12/6 14:44
     */
    public void excelOut(String path, Object t) {
        excelOut(path, null, t);
    }

    /**
     * 功能描述：
     * -- excel导出到指定路径
     *
     * @param : [path, t, sheetName]
     * @return : void
     * @author : wan
     * @date : 2021/12/6 14:44
     */
    @SneakyThrows
    public void excelOut(String path, String excelName, Object t) {
        File file = new File(path);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            log.error("excel创建输出流异常：{}", e.getMessage());
            e.printStackTrace();
            return;
        }
        excelOut(bos, excelName, t);
    }

    /**
     * 功能描述：
     * -- excel导出
     *
     * @param :
     * @return :
     * @author : wan
     * @date : 2021/11/24 10:00
     */
    public void excelOut(OutputStream outputStream, String excelName, Object t) {
        boolean isCollection = false;
        Class<? extends Object> aClass = null;
        Iterator iterator = null;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = null;
        try {
            if (null == t) {
                throw new BaseException(ErrorCode.PARAM_NOT_FOUND.value(), Message.PARAM_NOT_FOUND.value());
            }
            if (t instanceof Collection) {
                if (0 == ((Collection<?>) t).size()) {
                    log.info("输入对象为空，不需要导出!!!");
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                isCollection = true;
                iterator = ((Collection<?>) t).iterator();
                //iterator在此处被消费了一条，所以，下面遍历时会少一条数据，所以改为重新获取iterator进行消费
//            aClass = iterator.next().getClass();
                aClass = ((Collection<?>) t).iterator().next().getClass();
            } else {
                aClass = t.getClass();
            }
            //反射得到类的属性
            Field[] declaredFields = aClass.getDeclaredFields();
            //列数
            int columns = declaredFields.length;
            //sheet名称
            sheet = workbook.createSheet("Sheet1");
            XSSFRow r0 = sheet.createRow(0);
            //存放属性名
            Map<Integer, String> columnMap = new HashMap<>(columns);
            //创建列名
            for (int i = 0; i < columns; i++) {
                String name = declaredFields[i].getName();
                r0.createCell(i).setCellValue(name);
                //自适应列宽
                //中文可能会失效,所以换下面那种
                sheet.autoSizeColumn(i, true);
                columnMap.put(i, name);
            }
            log.info("=======>>>>>>{}开始导出=======>>>>>", excelName);
            if (isCollection) {
                int rowNum = 0;
                while (iterator.hasNext()) {
                    //集合中的当前对象
                    Object o3 = iterator.next();
                    log.info("开始导出的对象：{}", o3);
                    rowNum += 1;
                    XSSFRow row = sheet.createRow(rowNum);
                    for (Map.Entry<Integer, String> map : columnMap.entrySet()
                    ) {
                        Integer key = map.getKey();
                        String value = getGetMethod(map.getValue());
                        //通过反射获取属性的value
                        try {
                            Method method = aClass.getMethod(value, null);
                            Object o = method.invoke(o3);
                            if (null != o) {
                                value = o.toString();
                                row.createCell(key).setCellValue(value);
                            }
                        } catch (Exception e) {
                            log.error("反射获取方法错误:{}", e.getMessage());
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            } else {
                XSSFRow row = sheet.createRow(1);
                for (Map.Entry<Integer, String> map : columnMap.entrySet()
                ) {
                    Integer key = map.getKey();
                    String value = getGetMethod(map.getValue());
                    try {
                        log.info("即将获取的方法是：{}", value);
                        Method method = aClass.getMethod(value, null);
                        Object object = method.invoke(t);
                        if (null != object) {
                            value = object.toString();
                            row.createCell(key).setCellValue(value);
                        }
                    } catch (Exception e) {
                        log.error("反射获取方法错误:{}", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e) {

        }finally {
            try {
                workbook.write(outputStream);
                workbook.close();
                outputStream.close();
                log.info(">>>>>>>>>>>>>>>>>>>>导出完成》》》》》》》》》》》》》》》》》");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 功能描述：
     * -- 获取属性的get方法名
     *
     * @param : [value]
     * @return : java.lang.String
     * @author : wan
     * @date : 2021/12/6 13:59
     */
    private static String getGetMethod(String value) {
        String v1 = value.substring(0, 1).toUpperCase();
        String v2 = value.substring(1);
        return "get" + v1 + v2;
    }

    /**
     * 功能描述：
     * -- 浏览器下载，excel导出
     *
     * @param : []
     * @return : void
     * @author : wan
     * @date : 2021/11/30 19:42
     */
    public void excelLoad(Object t) {
        /*
         * 代码里面使用Content-Disposition来确保浏览器弹出下载对话框的时候。
         * response.addHeader("Content-Disposition","attachment");一定要确保没有做过关于禁止浏览器缓存的操作
         */
        //attachment为以附件方式下载
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(DateUtil.parse(DateUtil.date().toString(), DatePattern.NORM_DATETIME_MINUTE_PATTERN) + ".xlsx", "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：
     * -- 图片工具类
     *
     * @author : wan
     * @date : 2021/11/25 14:07
     */
    public class PhotoUtil {

        /**
         * 功能描述：
         * -- 图片上传
         *
         * @param : [file, format]
         * @return : void
         * @author : wan
         * @date : 2021/12/3 17:47
         */
        public void photoImport(MultipartFile file, String format) {
            try {
                photoImportAndOut(file.getInputStream(), format);
            } catch (IOException e) {
                log.error("图片读取异常：{}", e.getMessage());
                e.printStackTrace();
            }
        }

        /**
         * 功能描述：
         * -- 图片读取并导出
         *
         * @param : [is, formt]
         * @return :
         * @author : wan
         * @date : 2021/12/3 17:46
         */
        public void photoImportAndOut(InputStream is, String formt) {
            //图片要上传的目的地
            byte[] bytes = new byte[9999999];
            try {
                is.read(bytes);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:\\Users\\edy\\Desktop\\" + DateUtil.date().toDateStr() + "." + formt));
                bos.write(bytes);
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 功能描述：
         * -- 图片下载
         *
         * @param : [path, format]
         * @return : void
         * @author : wan
         * @date : 2021/12/3 17:46
         */
        public void photoLoad(String path, String format) {
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
                //图片要上传的目的地
                byte[] bytes = new byte[9999999];
                bis.read(bytes);
                bis.close();
                response.setHeader("Content-Disposition", "attachment; filename = " + DateUtil.date().toDateStr() + format);
                response.flushBuffer();
                ServletOutputStream os = response.getOutputStream();
                os.write(bytes);
                os.close();
            } catch (IOException e) {
                log.error("图片下载异常：{}", e.getMessage());
                e.printStackTrace();
            }
        }

    }

}
