package com.cx.common.authentication.session;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Set;

/**
 * session  管理
 *
 * @author Administrator
 */
public class SessionContext {
    private static SessionContext instance;
    private HashMap<String, HttpSession> sessionMap;
    private HashMap<String, String> userMap;

    private SessionContext() {
        sessionMap = new HashMap<String, HttpSession>();
    }

    public static SessionContext getInstance() {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

    public synchronized void addSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
        }
    }

    public synchronized void delSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }

    public synchronized void refreshSessionId(String oldSessionId, String sessionId) {
        if (StringUtils.isNotBlank(oldSessionId) && StringUtils.isNotBlank(sessionId)) {
            sessionMap.put(sessionId, sessionMap.get(oldSessionId));
            sessionMap.remove(sessionId);
        }
    }

    public synchronized HttpSession getSession(String sessionID) {
        if (sessionID == null) {
            return null;
        }
        return sessionMap.get(sessionID);
    }
    public Set<String> getSessionId() {
        return sessionMap.keySet();
    }

//    @Scheduled(cron = "0/10 * *  * * ?")
//    public void cleanSession() {
//
//    }
}
