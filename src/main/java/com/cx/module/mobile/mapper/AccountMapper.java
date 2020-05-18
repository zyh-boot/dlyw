package com.cx.module.mobile.mapper;

import com.cx.module.mobile.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * Mapper接口
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
public interface AccountMapper extends BaseMapper<Account> {
    Account  findAccountByAccandPwd(Account map) ;
}
