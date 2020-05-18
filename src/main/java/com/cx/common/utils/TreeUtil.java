package com.cx.common.utils;


import com.cx.common.entity.Constant;
import com.cx.common.entity.DeptTree;
import com.cx.common.entity.MenuTree;
import com.cx.common.entity.ObjTree;
import com.cx.system.entity.User;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator·
 */
public class TreeUtil {

    private static final String ZERO = "0";

    protected TreeUtil() {
    }

    public static <T> MenuTree<T> buildMenuTree(List<MenuTree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<MenuTree<T>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || ZERO.equals(pid)) {
                topNodes.add(children);
                return;
            }
            for (MenuTree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChilds().add(children);
                    children.setHasParent(true);
                    parent.setHasChild(true);
                    return;
                }
            }
        });

        MenuTree<T> root = new MenuTree<>();
        root.setId(ZERO);
        root.setParentId("");
        root.setHasParent(false);
        root.setHasChild(true);
        root.setChecked(true);
        root.setChilds(topNodes);
        Map<String, Object> state = new HashMap<>(16);
        root.setState(state);
        return root;
    }

    public static <T> List<DeptTree<T>> buildDeptTree(List<DeptTree<T>> nodes) {
        if (nodes == null) {
            return new ArrayList<>();
        }
        List<DeptTree<T>> result = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || ZERO.equals(pid)) {
                User user = CommonUtil.getCurrentUser();
                //管理员查看全部
                if (CommonUtil.getRootUser() ||
                        Long.valueOf(children.getId()).equals(user.getDeptId())) {
                    //其他只能查看本机构下属
                    result.add(children);
                }
                return;
            }
            for (DeptTree<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null) {
                        n.initChildren();
                    }
                    n.getChildren().add(children);
                    children.setHasParent(true);
                    n.setHasChild(true);
                    return;
                }
            }
        });

        return result;
    }

    public static <T> List<MenuTree<T>> buildList(List<MenuTree<T>> nodes, String idParam) {
        if (nodes == null) {
            return new ArrayList<>();
        }
        List<MenuTree<T>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);
                return;
            }
            nodes.forEach(parent -> {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChilds().add(children);
                    children.setHasParent(true);
                    parent.setHasChild(true);
                }
            });
        });
        return topNodes;
    }

    public static <T> List<ObjTree<T>> buildObjTree(List<ObjTree<T>> nodes) {
        if (nodes == null) {
            return new ArrayList<>();
        }
        List<ObjTree<T>> result = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (StringUtils.isBlank(pid)  || ZERO.equals(pid) || Constant.ROOT.equals(pid.toUpperCase())) {
                result.add(children);
                return;
            }
            for (ObjTree<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    n.getChildren().add(children);
                    children.setHasParent(true);
                    n.setHasChild(true);
                    return;
                }
            }
        });

        return result;
    }

}