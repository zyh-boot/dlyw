package com.cx.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cx.common.utils.CommonUtil;
import com.cx.system.entity.User;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充处理类
 *
 * @author Administrator
 * @date 2019/09/27
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String MODIFIER = "modifier";
    private static final String CREATOR = "creator";
    private static final String CREATEDATE = "createDate";
    private static final String MODDATE = "modDate";

    /**
     * 新增 自动更新 创建和修改时间
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        boolean creator = metaObject.hasSetter(CREATOR);
        boolean modifier = metaObject.hasSetter(MODIFIER);
        if (creator || modifier) {
            User user = CommonUtil.getCurrentUser();
            if (user != null) {
                if (creator) {
                    this.setFieldValByName(CREATOR, user.getId().toString(), metaObject);
                }
                if (modifier) {
                    this.setFieldValByName(MODIFIER, user.getId().toString(), metaObject);
                }
            }
        }
        boolean createDate = metaObject.hasSetter(CREATEDATE);
        boolean modDate = metaObject.hasSetter(MODDATE);
        if (createDate) {
            this.setFieldValByName(CREATEDATE, LocalDateTime.now(), metaObject);
        }
        if (modDate) {
            this.setFieldValByName(MODDATE, LocalDateTime.now(), metaObject);
        }
    }

    /**
     * 修改 自动更新 修改时间
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        boolean modifier = metaObject.hasSetter(MODIFIER);
        if (modifier) {
            User user = CommonUtil.getCurrentUser();
            if (user != null) {
                this.setFieldValByName(MODIFIER, user.getId().toString(), metaObject);
            }
        }
        boolean modDate = metaObject.hasSetter(MODDATE);

        if (modDate) {
            this.setFieldValByName(MODDATE, LocalDateTime.now(), metaObject);
        }
    }
}