package com.cx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cx.common.entity.DeptTree;
import com.cx.common.entity.QueryRequest;
import com.cx.system.entity.Dept;

import java.util.List;

/**
 * @author Administrator·
 */
public interface IDeptService extends IService<Dept> {

    /**
     * 获取部门树（下拉选使用）
     *
     * @param levelFlag 部门对象（传递查询参数）
     * @return 部门树集合
     */
    List<DeptTree<Dept>> findDepts(Integer levelFlag);

    /**
     * 只获取机构（不含下属部门）树（下拉选使用）
     *
     * @return 部门树集合
     */
    List<DeptTree<Dept>> findOneDepts();

    /**
     * 获取部门列表（树形列表）
     *
     * @param dept 部门对象（传递查询参数）
     * @return 部门树
     */
    List<DeptTree<Dept>> findDepts(Dept dept);

    /**
     * 获取部门树（供Excel导出）
     *
     * @param dept    部门对象（传递查询参数）
     * @param request QueryRequest
     * @return List<Dept>
     */
    List<Dept> findDepts(Dept dept, QueryRequest request);

    /**
     * 新增部门
     *
     * @param dept 部门对象
     */
    void createDept(Dept dept);

    /**
     * 修改部门
     *
     * @param dept 部门对象
     */
    void updateDept(Dept dept);

    /**
     * 删除部门
     *
     * @param deptIds 部门 ID集合
     */
    void deleteDepts(String[] deptIds);

    /**
     * 根据sourceId查部门
     *
     * @param sourceId 部门 ID集合
     * @return
     */
    Dept seleteBySourceId(String sourceId);
}
