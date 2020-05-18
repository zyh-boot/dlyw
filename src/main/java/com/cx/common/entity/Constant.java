package com.cx.common.entity;

import com.cx.common.controller.BaseController;

/**
 * 常量
 *
 * @author Administrator·
 */
public class Constant {

    /**
     * 排序规则：降序
     */
    public static final String ROOT = "ROOT";
    /**
     * 排序规则：降序
     */
    public static final String ORDER_DESC = "desc";
    /**
     * 排序规则：升序
     */
    public static final String ORDER_ASC = "asc";
    /**
     * limit限制规则
     */
    public static final String LIMIT_NUM = "limit 50";
    /**
     * 前端页面路径前缀
     */
    public static final String VIEW_PREFIX = "views/";
    /**
     * 验证码 Session Key
     */
    public static final String CODE_PREFIX = "aps_captcha_";
    /**
     * 允许下载的文件类型，根据需求自己添加（小写）
     */
    public static final String[] VALID_FILE_TYPE = {"xlsx", "zip"};
    /**
     * 允许下载的文件类型，根据需求自己添加（小写）
     */
    public static final String XLSX = ".xlsx";
    /**
     * redis  超时
     */
    public final static String REDIS_EXPIRE_TIME_KEY = "#expire_time";
    /**
     * {@link BaseController} getDataTable 中 HashMap
     * 默认的初始化容量
     */
    public static final int DATA_MAP_INITIAL_CAPACITY = 4;
    /**
     * 数据库列表   base主库   quartz定时任务库   CAD cad数据
     */
    public static final String DATASOURCE_BASE = "base";
    public static final String DATASOURCE_QUARTZ = "quartz";
    public static final String DATASOURCE_CAD = "CAD";
    /**
     * 是否有效   1:有效 0失效  2数据补充中禁止修改  3数据补充完成禁止修改
     */
    public static final int STATE_3 = 3;
    public static final int STATE_2 = 2;
    public static final int STATE_1 = 1;
    public static final int STATE_0 = 0;
    /**
     * true/false
     */
    public static final boolean TRUE = true;
    public static final boolean FALSE = false;

    /**
     * 功能 0添加  1删除  2修改   3批量删除   4启用
     */
    public static final int ADD = 0;
    public static final int DEL = 1;
    public static final int MOD = 2;
    public static final int BATCHDEL = 3;
    public static final int START = 4;

    /**
     * 机构数据
     */
    public static final String REDIS_DEPT_INFO = "aps.dept.info.tree::1.";
    public static final  String REDIS_DEPT_INFO_MAP ="aps.dept.info.map::1.";
    public static final  String REDIS_DEPT_NAME_MAP ="aps.dept.name.map::1.";
    public static final  String REDIS_CLIENT_INFO_MAP ="aps.client.info.map::1.";
    public static final  String REDIS_CLIENT_NAME_MAP ="aps.client.name.map::1.";
    public static final  String REDIS_PRODUCT_CLASSIFY_INFO_MAP ="aps.product.classify.info.map::1.";
    public static final  String REDIS_PRODUCT_CLASSIFY_NAME_MAP ="aps.product.classify.name.map::1.";
    /**
     * 机构数据
     */
    public static final String REDIS_SF_INFO = "aps.sflow.info::1";
    /**
     * 人员数据
     */
    public static final String REDIS_STAFF_INFO = "aps.staff.info::1";

    public static final String REDIS_STAFF_ID_INFO = "aps.staff.id.info::1";
    /**
     * 登录用户
     */
    public static final String REDIS_LOGIN_INFO = "aps.login.user::1";
    /**
     * 登录用户
     */
    public static final String REDIS_LOGIN_SESSION_INFO = "aps.login.session::1";
    /**
     * 技能信息
     */
    public static final String REDIS_SKILL_INFO = "aps.skill.map::1";
    /**
     * 工站流详情
     */
    public static final String REDIS_SFLOW_INFO = "aps.sflow.map::1";

    /**
     * 时间 时秒  天秒
     */
    public static final int HOURS_SEC_HALF = 1800;
    public static final int HOURS_SEC = 3600;
    public static final  int DAY_SEC =86400;
    public static final long MINUTE_SEC_HALF =3600 ;


    private void Constant() {
    }

    /**
     * 设备状态
     */
    public static final String TYPE_ZERO = "ONLINE";
    public static final String TYPE_ONE = "OFFLINE";
    public static final String TYPE_TWO ="UNACTIVE";
    public static final String TYPE_THREE ="DISABLE";

}
