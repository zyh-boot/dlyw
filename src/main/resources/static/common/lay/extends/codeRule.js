/**
 * 编码生成规则
 * @name:
 * @author:
 * @version: 1.0
 */
layui.extend({
    strpy: 'lay/extends/strpy'
}).define(['strpy'], function (exports) {
    var strpy = layui.strpy,
        obj = (function () {
            codeRule = function (value, ruleType, vElem) {
                if (ruleType == 0) {
                    var arrRslt = makePy(value.trim());
                    return  arrRslt[0];
                } else if (ruleType == 1) {
                    let vale = strpy(value.trim());
                    return vale.replace(/\s+/g,"");
                } else {
                    let d = new Date();
                    return  d.getFullYear() + "" + (d.getMonth() + 1) + "" + d.getDate() + "" + d.getHours() + "" + d.getMinutes() + "" + d.getSeconds();
                }
            }
            return codeRule;
        }());

    exports("codeRule", obj);
})
