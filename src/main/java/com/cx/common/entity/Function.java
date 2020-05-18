package com.cx.common.entity;

import lombok.Getter;

@Getter
public enum Function {

    add("添加",Constant.ADD,"add"),
    update("修改",Constant.MOD,"update"),
    delete("删除",Constant.DEL,"delete"),
    batchDel("批量删除",Constant.BATCHDEL,"batchDel"),
    start("启用",Constant.START,"start");

    //功能模块
    private  String name;
    //类型
    private  int type;
    //编码
    private  String code;

    Function(String name, int type, String code) {
        this.name = name;
        this.type = type;
        this.code = code;
    }


}
