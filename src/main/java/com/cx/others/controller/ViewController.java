package com.cx.others.controller;

import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator·
 */
@Controller("others")
@RequestMapping(Constant.VIEW_PREFIX + "others")
public class ViewController {

    @GetMapping("common/form")
    public String commonForm() {
        return CommonUtil.view("others/common/form");
    }

    @GetMapping("common/form/group")
    public String commonFormGroup() {
        return CommonUtil.view("others/common/formGroup");
    }

    @GetMapping("common/tools")
    public String commonTools() {
        return CommonUtil.view("others/common/tools");
    }

    @GetMapping("common/icon")
    public String commonIcon() {
        return CommonUtil.view("others/common/icon");
    }

    @GetMapping("common/others")
    public String commonOthers() {
        return CommonUtil.view("others/common/others");
    }

    @GetMapping("apex/line")
    public String apexLine() {
        return CommonUtil.view("others/apex/line");
    }

    @GetMapping("apex/area")
    public String apexArea() {
        return CommonUtil.view("others/apex/area");
    }

    @GetMapping("apex/column")
    public String apexColumn() {
        return CommonUtil.view("others/apex/column");
    }

    @GetMapping("apex/radar")
    public String apexRadar() {
        return CommonUtil.view("others/apex/radar");
    }

    @GetMapping("apex/bar")
    public String apexBar() {
        return CommonUtil.view("others/apex/bar");
    }

    @GetMapping("apex/mix")
    public String apexMix() {
        return CommonUtil.view("others/apex/mix");
    }

    @GetMapping("map")
    public String map() {
        return CommonUtil.view("others/map/gaodeMap");
    }

    @GetMapping("eximport")
    public String eximport() {
        return CommonUtil.view("others/eximport/eximport");
    }

    @GetMapping("eximport/result")
    public String eximportResult() {
        return CommonUtil.view("others/eximport/eximportResult");
    }
}
