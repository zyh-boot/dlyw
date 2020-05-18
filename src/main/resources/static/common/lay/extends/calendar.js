/*
* @version: 1.2
* @Author:  tomato
* @Date:    2018-4-24 22:56:00
* @Last Modified by:   tomato
* @Last Modified time: 2018-5-26 18:08:43
*/
//无限级下拉框
layui.define(['laydate', 'jquery'], function (exports) {
    var MOD_NAME = 'calendar';
    var laydate = layui.laydate,
        $ = layui.jquery;
    var calendar = function (config) {

        this.config = {
            //选择器id或class
            elem: '',
            type: 'date',
            theme: 'grid',
            max: '2099-06-16 23:59:59',
            position: 'static',
            range: false,
            value: '',
            calendar: false,
            showBottom: false,
            btns: false,
            mark: [],
            week: "",
            rest: []
        }


        //初始化
        this.set = function (selected) {
            var o = this, c = o.config;
            $E = $(c.elem);
            laydate.render(c);
            o.html($E);
            $E.find(".laydate-set-ym span:first").bind('DOMNodeInserted', function (e) {
               // alert('element now contains: ' + $(e.target).html());
                o.change($E);
            });
        };

        this.html = function (elem) {
            var o = this, c = o.config;
            let rest = c.rest;
            let week = $.isEmptyObject(c.week)?[]:c.week.split(",");
            let indexes = 0;
            elem.find("tr td").each(function (index, item) {
                let remarks = rest[o.format($(item).attr("lay-ymd"))]
                $(item).find("br").remove();
                $(item).find("label").remove();
                if (!$.isEmptyObject(remarks) || (week.length>0 && week.indexOf(indexes + "") >= 0 && $.isEmptyObject(remarks))) {
                    $(item).attr("style", "color:red;background-color: #efbbbb;")
                    remarks=$.isEmptyObject(remarks)?"休息":remarks
                    $(item).html($(item).html() + "<br><label style='font-size: 12px;color: #4E5465'>"+remarks+"<label>");
                } else {
                    $(item).attr("style", "")
                   // $(item).html($(item).html());
                }
                indexes = indexes === 6 ? 0 : indexes + 1;
            })
        }

        //下拉事件
        this.change = function (elem) {
            var o = this;
            o.html(elem);
        };
        //下拉事件
        this.format = function (date) {
            let d = date.split("-");
            let curr_date = d[2];
            let curr_month = d[1];
            let curr_year = d[0];
            String(curr_month).length < 2 ? (curr_month = "0" + curr_month): curr_month;
            String(curr_date).length < 2 ? (curr_date = "0" + curr_date): curr_date;
            return curr_year + "-" + curr_month +"-"+ curr_date;
        };

        this.refresh = function (e) {
            var o = this, c = $.extend(o.config, e);
            o.config.rest=o.config.mark;
            o.config.mark=[];
            o.formatWeek();
            o.html($(c.elem))
        };
        this.formatWeek=function () {
            var o = this;
            if(o.config.week.indexOf("7")>=0){
                o.config.week.replace("7","0")
            }
        }

    };

    //渲染一个实例
    calendar.prototype.render = function (e) {
        var o = this, c = $.extend(o.config, e);
        $E = $(c.elem);
        if ($E.length == 0) {
            console.error(MOD_NAME + ' hint：找不到容器 ' + c.elem);
            return false;
        }

        c.filter = c.filter == '' ? c.elem.replace('#', '').replace('.', '') : c.filter;
        c.name = c.name == '' ? c.elem.replace('#', '').replace('.', '') : c.name;
        o.config.rest=o.config.mark;
        o.config.mark=[];
        o.formatWeek();
        //初始化
        o.set();
        return o;
    }

    //输出模块
    var calendar = new calendar();
    exports(MOD_NAME, calendar);
});