package com.cx.module.pcController;

import com.cx.common.entity.CommonResponse;
import com.cx.module.mobile.entity.Account;
import com.cx.module.mobile.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("pcAccount")
public class PcAccountController {

    @Autowired
    IAccountService accountService;

    @RequestMapping("map")
    public Object getMap(){

        Account account = new Account();
        account.setKhStatus(1);
        List<Account> accounts = accountService.list(account);
        ArrayList<Account> addresses = new ArrayList<>();
        System.out.println(">>>>>>>>>>>>>>>>>>>>" + addresses.size());
        for(Account tmpAccount : accounts){
            addresses.add(tmpAccount);
        }
        return new CommonResponse().code(HttpStatus.OK).data(addresses);
    }
}
