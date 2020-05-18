package com.cx.common.entity;

import lombok.Getter;

/**
 * 模块功能
 * @author Administrator
 */

@Getter
public enum Module {
    product("产品","0","product","productDetail"),
    materiel("物料","1","materiel","materielDetail"),
    parts("部件","2","pModel","parts"),
    position("部位","3","pModel","position"),
    taglia("尺码","4","taglia","taglia"),
    color("颜色","5","color","colorDetail"),
    bom("bom","6","pModel","bom"),
    fabricManage("面辅料","7","pModel","fabricManage"),
    cutParts("裁片","8","pModel","cutParts"),
    station("工站","9","work","station"),
    stationFlow("工站流","10","work","stationFlow"),
    technology("工艺库","11","work","technology"),
    seq("工序库","12","work","seq"),
    seqFlow("工序流","13","work","seqFlow"),
    technologySeq("工艺路线","14","work","technologySeq"),
    workflow("标准作业","15","work","workflow");

    //功能模块
    private  String name;
    //类型
    private  String type;
    //模块
    private  String code;
    //编码
    private  String beanName;

    Module(String name, String type, String code,String beanName) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.beanName=beanName;
    }



    public  static Module selectByType(String type){
        Module module=null;
        for (Module m:Module.values()){
            if(type.equals(m.getType())){
                module=m;
                break;
            }
        }
        return module;
    }

    public static  Module selectByBeanName(String beanName){
        Module module=null;
        for (Module m:Module.values()){
            if(m.getBeanName().equals(beanName)){
                module=m;
                break;
            }
        }
        return module;
    }


}
