import $axios from "./request.js";

var url = "http://localhost:9090/"

//test
export function findAll(data) {
    return $axios({
        url: url + "user/findAll/",
        method: "get",
        data: data,
    });
}

//查询所有用户
export function queryAllList(data) {
    return $axios({
        url: url + "user/queryAllList",
        method: "post",
        data: data,
    });
}

//登录
export function login(data) {
    return $axios({
        url: url + "user/login",
        method: "post",
        data: data,
    });
}

//注册
export function register(data) {
    return $axios({
        url: url + "user/register",
        method: "post",
        data: data,
    });
}

//获取用户名
export function getUsername(data) {
    return $axios({
        url: url + "user/username/" + data,
        method: "get",
        data: data,
    });
}

//删除用户
export function deleteUser(data) {
    return $axios({
        url: url + "user/deleteUser",
        method: "post",
        data: data,
    });
}

//删除用户
export function resetPassword(data) {
    return $axios({
        url: url + "user/resetPassword",
        method: "post",
        data: data,
    });
}

//批量删除用户
export function deleteAllUser(data) {
    return $axios({
        url: url + "user/deleteAllUser",
        method: "post",
        data: data,
    });
}

//获取echarts图
export function getExample(data) {
    return $axios({
        url: url + "echarts/example",
        method: "get",
        data: data,
    });
}

//获取echarts图
export function getPieExample(data) {
    return $axios({
        url: url + "echarts/getPieExample",
        method: "get",
        data: data,
    });
}

//新增用户
export function addUser(data) {
    return $axios({
        url: url + "user/addUser",
        method: "post",
        data: data,
    });
}

//查询所有角色
export function queryAllRoleList(data) {
    return $axios({
        url: url + "role/queryAllList",
        method: "post",
        data: data,
    });
}

//新增角色
export function addRole(data) {
    return $axios({
        url: url + "role/addRole",
        method: "post",
        data: data,
    });
}

//修改角色
export function modifyRole(data) {
    return $axios({
        url: url + "role/modifyRole",
        method: "post",
        data: data,
    });
}

//批量删除角色
export function deleteAllRole(data) {
    return $axios({
        url: url + "role/deleteAllRole",
        method: "post",
        data: data,
    });
}

//删除角色
export function deleteRole(data) {
    return $axios({
        url: url + "role/deleteRole",
        method: "post",
        data: data,
    });
}

//查询所有菜单
export function queryAllMenuList(data) {
    return $axios({
        url: url + "menu/queryAllList",
        method: "post",
        data: data,
    });
}

//新增菜单
export function addMenu(data) {
    return $axios({
        url: url + "menu/addMenu",
        method: "post",
        data: data,
    });
}

//批量删除菜单
export function deleteAllMenu(data) {
    return $axios({
        url: url + "menu/deleteAllMenu",
        method: "post",
        data: data,
    });
}

//删除菜单
export function deleteMenu(data) {
    return $axios({
        url: url + "menu/deleteMenu",
        method: "post",
        data: data,
    });
}

//Menu管理获取树结构
export function getMenuTree(data) {
    return $axios({
        url: url + "menu/getMenuTree",
        method: "get",
        data: data,
    });
}

//获取父目录下拉框
export function getPidSelect(data) {
    return $axios({
        url: url + "menu/getPidSelect",
        method: "post",
        data: data,
    });
}

//获取父目录下拉框
export function getIconSelect(data) {
    return $axios({
        url: url + "icon/getIconSelect",
        method: "post",
        data: data,
    });
}

//操作修改用户权限
export function insertRoleMenu(data) {
    return $axios({
        url: url + "roleMenu/insertRoleMenu/",
        method: "post",
        data: data,
    });
}

//获取用户权限
export function getRoleMenu(data) {
    return $axios({
        url: url + "roleMenu/getRoleMenu",
        method: "post",
        data: data,
    });
}

//获取用户角色下拉框
export function getSelectRole(data) {
    return $axios({
        url: url + "role/getSelectRole",
        method: "post",
        data: data,
    });
}

//查询所有角色
export function modifyPassword(data) {
    return $axios({
        url: url + "user/modifyPassword",
        method: "post",
        data: data,
    });
}


//查询所有用户
export function queryAllFileList(data) {
    return $axios({
        url: url + "file/queryAllList",
        method: "post",
        data: data,
    });
}

//下载
export function downloadItem(data) {
    return $axios({
        url: data,
        method: "get",
        data: data,
    });
}

//批量下载
export function downloadAll(data) {
    return $axios({
        url: url + "file/downloadAll",
        method: "post",
        data: data,
    });
}

//删除文件
export function deleteFile(data) {
    return $axios({
        url: url + "file/deleteFile",
        method: "post",
        data: data,
    });
}

//批量删除文件
export function deleteAllFile(data) {
    return $axios({
        url: url + "file/deleteAllFile",
        method: "post",
        data: data,
    });
}

//批量删除文件
export function modifyFile(data) {
    return $axios({
        url: url + "file/modifyFile",
        method: "post",
        data: data,
    });
}

//查询文件类型下拉框
export function selectFileType(data) {
    return $axios({
        url: url + "file/selectFileType",
        method: "post",
        data: data,
    });
}

//查询所有角色
export function queryAllBcatList(data) {
    return $axios({
        url: url + "bcat/queryAllBcatList",
        method: "post",
        data: data,
    });
}

//删除本地喵数据
export function deleteBcat(data) {
    return $axios({
        url: url + "bcat/deleteBcat",
        method: "post",
        data: data,
    });
}

//批量删除本地喵数据
export function deleteAllBcat(data) {
    return $axios({
        url: url + "bcat/deleteAllBcat",
        method: "post",
        data: data,
    });
}

//新增宠物信息
export function addBcat(data) {
    return $axios({
        url: url + "bcat/addBcat",
        method: "post",
        data: data,
    });
}

//派去打工
export function changeToWork(data) {
    return $axios({
        url: url + "bcat/changeToWork",
        method: "post",
        data: data,
    });
}

//查询所有角色
export function queryAllCcatList(data) {
    return $axios({
        url: url + "ccat/queryAllCcatList",
        method: "post",
        data: data,
    });
}

//查询所有角色
export function queryAllMinecatList(data) {
    return $axios({
        url: url + "ccat/queryAllMinecatList",
        method: "post",
        data: data,
    });
}

//删除本地喵数据
export function deleteCcat(data) {
    return $axios({
        url: url + "ccat/deleteCcat",
        method: "post",
        data: data,
    });
}

//批量删除本地喵数据
export function deleteAllCcat(data) {
    return $axios({
        url: url + "ccat/deleteAllCcat",
        method: "post",
        data: data,
    });
}

//新增宠物信息
export function addCcat(data) {
    return $axios({
        url: url + "ccat/addCcat",
        method: "post",
        data: data,
    });
}

//派去打工
export function changeCcatToWork(data) {
    return $axios({
        url: url + "ccat/changeToWork",
        method: "post",
        data: data,
    });
}

//送去摆烂
export function changeCcatToRest(data) {
    return $axios({
        url: url + "ccat/changeCcatToRest",
        method: "post",
        data: data,
    });
}

//获取铲屎官下拉框
export function selectMaster(data) {
    return $axios({
        url: url + "ccat/getSelectMaster",
        method: "post",
        data: data,
    });
}

//查询所有角色
export function queryAllWorkcatList(data) {
    return $axios({
        url: url + "workcat/queryAllWorkcatList",
        method: "post",
        data: data,
    });
}

//删除本地喵数据
export function deleteWorkcat(data) {
    return $axios({
        url: url + "workcat/deleteWorkcat",
        method: "post",
        data: data,
    });
}

//批量删除本地喵数据
export function deleteAllWorkcat(data) {
    return $axios({
        url: url + "workcat/deleteAllWorkcat",
        method: "post",
        data: data,
    });
}

//新增宠物信息
export function addWorkcat(data) {
    return $axios({
        url: url + "workcat/addWorkcat",
        method: "post",
        data: data,
    });
}

//下去休息
export function changeToRest(data) {
    return $axios({
        url: url + "workcat/changeToRest",
        method: "post",
        data: data,
    });
}

//下去休息
export function goHome(data) {
    return $axios({
        url: url + "workcat/goHome",
        method: "post",
        data: data,
    });
}

//下班
export function backHome(data) {
    return $axios({
        url: url + "workcat/backHome",
        method: "post",
        data: data,
    });
}


//下去休息
export function addMinecat(data) {
    return $axios({
        url: url + "ccat/addMinecat",
        method: "post",
        data: data,
    });
}

//查询所有角色
export function queryAllDcatList(data) {
    return $axios({
        url: url + "ccat/queryAllDcatList",
        method: "post",
        data: data,
    });
}

//查询猫咪总数
export function getAllCats(data) {
    return $axios({
        url: url + "echarts/getAllCats",
        method: "post",
        data: data,
    });
}

//查询在店喵数
export function getAtStoreCats(data) {
    return $axios({
        url: url + "echarts/getAtStoreCats",
        method: "post",
        data: data,
    });
}

//查询上岗喵数
export function getWorkCats(data) {
    return $axios({
        url: url + "echarts/getWorkCats",
        method: "post",
        data: data,
    });
}

//查询顾客总数
export function getCustomers(data) {
    return $axios({
        url: url + "echarts/getCustomers",
        method: "post",
        data: data,
    });
}

//查询所有预约
export function queryAllMealList(data) {
    return $axios({
        url: url + "meal/queryAllMealList",
        method: "post",
        data: data,
    });
}

//查询个人所有预约
export function queryMyMealList(data) {
    return $axios({
        url: url + "meal/queryMyMealList",
        method: "post",
        data: data,
    });
}

//导出用户
export function exportUser(data) {
    return $axios({
        url: url + "user/export",
        method: "get",
        data: data,
    });
}

//导入用户
export function uploadUser(data) {
    return $axios({
        url: url + "user/import",
        method: "post",
        data: data,
    });
}

//预约购票
export function addMealList(data) {
    return $axios({
        url: url + "meal/addMealList",
        method: "post",
        data: data,
    });
}

//删除购票
export function delMealList(data) {
    return $axios({
        url: url + "meal/delMealList",
        method: "post",
        data: data,
    });
}

//批量删除购票
export function delAllMeal(data) {
    return $axios({
        url: url + "meal/delAllMealList",
        method: "post",
        data: data,
    });
}

//获取购票详情
export function selectMealInfo(data) {
    return $axios({
        url: url + "meal/selectMealInfo",
        method: "post",
        data: data,
    });
}

