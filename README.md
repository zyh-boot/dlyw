#环境监测 http://127.0.0.1:8099/
## `账户: admin 密码: 1234`

代码逻辑存在 com.cx.module下
## 用户管理 myUser.* 用户处于无部门状态,无任何操作权限
#### `接口: qx 返回角色树,用户前台Select下拉框数据展示`
#### `接口: dept 返回机构树,用户前台Select下拉框数据展示`
#### `接口: bind 设备绑定, 将前台右侧穿梭框数据与当前用户名下的设备进行对比,如果减少就删除掉缺失的设备`
#### `方法: add 添加用户, 用户到期时间默认为一年后`
#### `方法: delete 删除用户, 同步a_user_myequipment表, 将该用户下所有设备全部删除.`


## 设备管理 amyequipment.* 采取分级展示
#### `接口: pageOtherList/{categroy} 根据左侧菜单传递的等级展示不同的设备`
#### `接口: catetory 根据当前用户机构类别,返回下属类别.`

## 机构管理 mydqpt.*
#### `方法: delete 删除机构, 同步用户表和设备表. 将这两张表关于机构的信息更新. dept_id = -1, dept_name= 无部门`

##排名 rank.*
#### `接口: pollutionSearch 污染项排名页面查询操作`
#### `接口: search 机构污染排名页面查询`
#### `接口: detile 返回echarts图表所需数据`
#### `接口: pollution 返回污染项数据,升序排列`
#### `方法: getMaps 查询pm2.5和pm10数据`
