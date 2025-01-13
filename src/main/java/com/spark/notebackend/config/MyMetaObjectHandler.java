package com.spark.notebackend.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author spark
 * @Create 2025-01-13 19:53
 * @Version 1.0
 * @Description MyBatis-Plus 自动填充处理器,用于在插入或更新时自动填充某些字段
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时的填充策略
     * 在执行MyBatis-Plus的insert()方法时被调用
     *
     * @param metaObject 元对象，可以获取和设置到对象的值
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("自动插入创建时间和更新时间");

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 设置创建时间，如果属性存在且为空则填充
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        // 设置更新时间，如果属性存在且为空则填充
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
    }

    /**
     * 更新时的填充策略
     * 在执行MyBatis-Plus的update()方法时被调用
     *
     * @param metaObject 元对象，可以获取和设置到对象的值
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("自动更新更新时间");

        // 设置更新时间，如果属性存在且为空则填充
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
 