package com.cx.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 树形结构
 *
 * @author Administrator
 * @date 2019/09/27
 */
@Data
public class ObjTree<T> implements Serializable {

    private static final long serialVersionUID = 7681873362531265829L;

    private String id;
    private String href;
    private String title;
    private String name;
    private String label;
    private String value;
    private String remarks;
    private transient Map<String, Object> state;
    private boolean checked = false;
    private transient Map<String, Object> attributes;
    private List<ObjTree<T>> children = new ArrayList<>();
    private String parentId;
    private boolean hasParent = false;
    private boolean hasChild = false;

    private T data;

    public void setId(String id){
        this.id=id;
        this.value=id;
    }
}
