package com.spark.notebackend.util;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author spark
 * @Create 2025-01-13 21:30
 * @Version 1.0
 * @Description Bean对象拷贝工具类，用于DTO、VO、Entity等对象之间的转换
 */
public class BeanCopyUtils {
    
    private BeanCopyUtils() {
        // 私有构造方法，防止实例化
    }

    /**
     * 单个对象拷贝，将源对象的属性拷贝到目标类型的新对象
     *
     * @param source 源对象，可以是任何对象
     * @param clazz  目标类型的Class对象
     * @param <V>    目标对象类型
     * @return 目标类型的对象，包含源对象的属性值
     */
    public static <V> V copyBean(Object source, Class<V> clazz) {
        // 如果源对象为空，直接返回null
        if (source == null) {
            return null;
        }
        V result = null;
        try {
            // 创建目标类型的实例
            result = clazz.newInstance();
            // 使用Hutool的BeanUtil进行属性拷贝
            BeanUtil.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 集合对象拷贝，将源对象列表转换为目标类型的对象列表
     *
     * @param list  源对象列表
     * @param clazz 目标类型的Class对象
     * @param <O>   源对象类型
     * @param <V>   目标对象类型
     * @return 目标类型的对象列表
     */
    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        // 使用Stream API处理列表
        return list.stream()
                // 将每个源对象转换为目标类型对象
                .map(o -> copyBean(o, clazz))
                // 收集转换结果到新列表
                .collect(Collectors.toList());
    }

    /**
     * 分页对象拷贝，将MyBatis-Plus的Page对象转换为包含目标类型对象的Page
     *
     * @param page  源Page对象
     * @param clazz 目标类型的Class对象
     * @param <T>   源对象类型
     * @param <V>   目标对象类型
     * @return 包含目标类型对象的Page
     */
    public static <T, V> Page<V> copyBeanPage(Page<T> page, Class<V> clazz) {
        // 创建新的Page对象
        Page<V> resultPage = new Page<>();
        // 复制Page对象的属性，忽略records属性
        BeanUtil.copyProperties(page, resultPage, "records");
        // 转换并设置records列表
        resultPage.setRecords(copyBeanList(page.getRecords(), clazz));
        return resultPage;
    }
} 