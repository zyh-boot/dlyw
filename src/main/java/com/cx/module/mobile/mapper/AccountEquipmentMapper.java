package com.cx.module.mobile.mapper;

import com.cx.module.mobile.entity.AccountEquipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
*  Mapper接口
*
* @author admin
* @Description Created on 2020-05-08
*/
    public interface AccountEquipmentMapper extends BaseMapper<AccountEquipment> {
        void     delectByKh(String khId);

    }
