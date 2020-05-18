package com.cx.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.annotation.Log;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.common.utils.Md5Util;
import com.cx.common.utils.CommonUtil;
import com.cx.common.utils.SecurityUtils;
import com.cx.system.entity.User;
import com.cx.system.service.IUserService;
import com.cx.common.annotation.Log;
import com.cx.common.controller.BaseController;
import com.cx.common.utils.Md5Util;
import com.cx.common.utils.SecurityUtils;
import com.cx.system.service.IUserService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator·
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sysUser")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @GetMapping("{username}")
    public User getUser(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findUserDetail(username);
    }

    @RequestMapping("me")
    public User user() {
        return SecurityUtils.getUser();
    }


    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username, String userId) {
        return this.userService.findByName(username) == null || StringUtils.isNotBlank(userId);
    }

    @GetMapping("list")
    @PreAuthorize("hasRole('user:view')")
    public CommonResponse userList(User user, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.userService.findUserDetail(user, request));
        return new CommonResponse().success().data(dataTable);
    }

    @Log("新增用户")
    @PostMapping
    @PreAuthorize("hasRole('user:add')")
    public CommonResponse addUser(@Valid User user) throws CommonException {
        try {
            if (user.getDeptId() == null) {
                user.setDeptId(CommonUtil.getCurrentUser().getDeptId());
            }
            if (user.getRoleId() == null) {
                user.setRoleId(CommonUtil.getCurrentUser().getRoleId());
            }
            this.userService.createUser(user);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "新增用户失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("删除用户")
    @GetMapping("delete/{userIds}")
    @PreAuthorize("hasRole('user:delete')")
    public CommonResponse deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws CommonException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "删除用户失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("修改用户")
    @PostMapping("update")
    @PreAuthorize("hasRole('user:update')")
    public CommonResponse updateUser(@Valid User user) throws CommonException {
        try {
            if (user.getUserId() == null) {
                throw new CommonException("用户ID为空");
            }
            this.userService.updateUser(user);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "修改用户失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @PostMapping("password/reset/{usernames}")
    @PreAuthorize("hasRole('user:password:reset')")
    public CommonResponse resetPassword(@NotBlank(message = "{required}") @PathVariable String usernames) throws CommonException {
        try {
            String[] usernameArr = usernames.split(StringPool.COMMA);
            this.userService.resetPassword(usernameArr);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "重置用户密码失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @PostMapping("password/update")
    public CommonResponse updatePassword(
            @NotBlank(message = "{required}") String oldPassword,
            @NotBlank(message = "{required}") String newPassword) throws CommonException {
        try {
            User user = getCurrentUser();

            if (!StringUtils.equals(user.getPassword(), Md5Util.encrypt(user.getUsername(), oldPassword))) {
                throw new CommonException("原密码不正确");
            }
            userService.updatePassword(user.getUsername(), newPassword);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "修改密码失败，" + e.getMessage();
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @GetMapping("avatar/{image}")
    public CommonResponse updateAvatar(@NotBlank(message = "{required}") @PathVariable String image) throws CommonException {
        try {
            User user = getCurrentUser();
            this.userService.updateAvatar(user.getUsername(), image);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "修改头像失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @PostMapping("theme/update")
    public CommonResponse updateTheme(String theme, String isTab) throws CommonException {
        try {
            User user = getCurrentUser();
            this.userService.updateTheme(user.getUsername(), theme, isTab);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "修改系统配置失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @PostMapping("profile/update")
    public CommonResponse updateProfile(User user) throws CommonException {
        try {
            User currentUser = getCurrentUser();
            user.setUserId(currentUser.getUserId());
            this.userService.updateProfile(user);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "修改个人信息失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @GetMapping("excel")
    @PreAuthorize("hasRole('user:export')")
    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) throws CommonException {
        try {
            List<User> users = this.userService.findUserDetail(user, queryRequest).getRecords();
            ExcelKit.$Export(User.class, response).downXlsx(users, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }
}
