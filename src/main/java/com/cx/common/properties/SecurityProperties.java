package com.cx.common.properties;

import lombok.Data;

/**
 * @author Administrator·
 */
@Data
public class SecurityProperties {

    private long sessionTimeout;
    private int cookieTimeout;
    private String anonUrl;
    private String loginUrl;
    private String successUrl;
    private String logoutUrl;
    private String unauthorizedUrl;
}
