package com.cx.system.controller;

import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.exception.CommonException;
import com.cx.monitor.service.ILoginLogService;
import com.cx.system.entity.User;
import com.cx.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator·
 */
@Validated
@RestController
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ILoginLogService loginLogService;



//    @PostMapping("login")
//    @Limit(key = "login", period = 60, count = 20, name = "登录接口", prefix = "limit")
//    public CommonResponse login(
//            @NotBlank(message = "{required}") String username,
//            @NotBlank(message = "{required}") String password,
//            // @NotBlank(message = "{required}") String verifyCode,
//            boolean rememberMe, HttpServletRequest request) {
//        //验证码相关 if (!CaptchaUtil.verify(verifyCode, request)) { throw new CommonException("验证码错误！"); }
//        password = Md5Util.encrypt(username.toLowerCase(), password);
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
//        super.login(token);
//        // 保存登录日志
//        LoginLog loginLog = new LoginLog();
//        loginLog.setUsername(username);
//        loginLog.setSystemBrowserInfo();
//        this.loginLogService.saveLoginLog(loginLog);
//
//        return new CommonResponse().success();
//    }

    @PostMapping("regist")
    public CommonResponse regist(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws CommonException {
        User user = userService.findByName(username);
        if (user != null) {
            throw new CommonException("该用户名已存在");
        }
        this.userService.regist(username, password);
        return new CommonResponse().success();
    }

    @GetMapping("index/{username}")
    public CommonResponse index(@NotBlank(message = "{required}") @PathVariable String username) {

        // 更新登录时间
        this.userService.updateLoginTime(username);
        Map<String, Object> data = new HashMap<>(16);
        // 获取系统访问记录
        Long totalVisitCount = this.loginLogService.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = this.loginLogService.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = this.loginLogService.findTodayIp();
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        List<Map<String, Object>> lastSevenVisitCount = this.loginLogService.findLastSevenDaysVisitCount(null);
        data.put("lastSevenVisitCount", lastSevenVisitCount);
        User param = new User();
        param.setUsername(username);
        List<Map<String, Object>> lastSevenUserVisitCount = this.loginLogService.findLastSevenDaysVisitCount(param);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
        return new CommonResponse().success().data(data);
    }

}
