package com.cx.module.mobile.mapper;

import com.cx.module.mobile.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.module.mobile.entity.AccountEquipment;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
public interface AccountMapper extends BaseMapper<Account> {
    Account  findAccountByAccandPwd(Account map) ;
    List<Account> pageList(Map<String,Object> map);
}
