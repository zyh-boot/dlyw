<div class="layui-fluid layui-anim febs-anim" id="febs-${entity?uncap_first}" lay-title="${table.comment!}">
    <div class="layui-row febs-container">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body febs-table-full">
                    <form class="layui-form layui-table-form" lay-filter="${entity?uncap_first}-table-form">
                        <div class="layui-row">
                            <div class="layui-col-md2 layui-col-sm12 layui-col-xs12 table-action-area">
                                <div class="layui-btn layui-btn-sm layui-btn-primary table-action"
                                     id="add${entity?uncap_first}"   sec:authorize="hasRole('${entity?uncap_first}:add')">
                                    <i class="layui-icon">&#xe891;</i>
                                </div>
                                <div class="layui-btn layui-btn-sm layui-btn-primary table-action" id="delete" sec:authorize="hasRole('${entity?uncap_first}:del')">
                                    <i class="layui-icon">&#xe7f9;</i>
                                </div>
                                <div class="layui-btn layui-btn-sm layui-btn-primary table-action" id="query">
                                    <i class="layui-icon">&#xe848;</i>
                                </div>
                                <div class="layui-btn layui-btn-sm layui-btn-primary table-action" id="reset">
                                    <i class="layui-icon">&#xe79b;</i>
                                </div>
                            </div>
                            <div class="layui-col-md10   layui-col-sm12 layui-col-xs12 table-action-area">
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label layui-form-label-sm">名称</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="name" autocomplete="off" class="layui-input">
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label layui-form-label-sm">创建时间</label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="createDate" id="createTime" class="layui-input">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <table lay-filter="${entity?uncap_first}Table"
                           lay-data="{id: '${entity?uncap_first}Table'}"></table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="${entity?uncap_first}-status">
    {{#
    var status = {
    1: {title: '有效', color: 'green'},
    0: {title: '失效', color: 'volcano'}
    }[d.state];
    }}
    <span class="layui-badge febs-tag-{{status.color}}">{{ status.title }}</span>
</script>

<script type="text/html" id="${entity?uncap_first}-option">
    <a lay-event="edit"><i class="layui-icon febs-edit-area febs-blue"  sec:authorize="hasRole('${entity?uncap_first}:mod')">&#xe7a4;</i></a>
    <a lay-event="del"><i class="layui-icon febs-edit-area febs-red"  sec:authorize="hasRole('${entity?uncap_first}:del')">&#xe7f9;</i></a>
</script>

<script data-th-inline="none" type="text/javascript">
    layui.use([ 'jquery', 'layer', 'laydate', 'form','febs','table', 'util','xmSelect'], function () {
        var $ = layui.jquery,
            layer = layui.layer,
            laydate = layui.laydate,
            febs = layui.febs,
            form = layui.form,
            table=layui.table,
            xmSelect = layui.xmSelect,
            $view = $('#febs-${entity?uncap_first}'),
            $query = $view.find('#query'),
            $reset = $view.find('#reset'),
            $add${entity?uncap_first} = $view.find('#add${entity?uncap_first}'),
            $delete = $view.find('#delete'),
            $searchForm = $view.find('form'),
            sortObject = {field: 'createDate', type: null},
            tableIns;

        form.render();

        initTable();


        laydate.render({
            elem: '#createTime',
            range: true,
            trigger: 'click'
        });


        //监听单元格事件
        table.on('tool(${entity?uncap_first}Table)', function (obj) {
            var data = obj.data,
                layEvent = obj.event;
            console.log("data", data);
            if (layEvent === 'del') {
                febs.modal.confirm('删除${table.comment!}', '确定删除该${table.comment!} ？', function () {
                    delete${entity}(data.id);
                });
            }
            if (layEvent === 'edit') {
                febs.modal.open('修改${table.comment!}', '${package.ModuleName}/${entity?uncap_first}/update/' + data.id, {
                    area: $(window).width() <= 750 ? '90%' : '50%',
                    btn: ['提交', '取消'],
                    yes: function (index, layero) {
                        $('#febs-${entity?uncap_first}-update').find('#submit').trigger('click');
                    },
                    btn2: function () {
                        layer.closeAll();
                    }
                });
            }
        });

        table.on('sort(${entity?uncap_first}Table)', function (obj) {
            sortObject = obj;
            tableIns.reload({
                initSort: obj,
                where: $.extend(getQueryParams(), {
                    field: obj.field,
                    order: obj.type
                })
            });
        });

        $query.on('click', function () {
            var params = $.extend(getQueryParams(), {field: sortObject.field, order: sortObject.type});
            tableIns.reload({where: params, page: {curr: 1}});
        });

        $reset.on('click', function () {
            $searchForm[0].reset();
            sortObject.type = 'null';
            tableIns.reload({where: getQueryParams(), page: {curr: 1}, initSort: sortObject});
        });


        $add${entity?uncap_first}.on('click', function () {
            febs.modal.open('新增${table.comment!}', '${package.ModuleName}/${entity?uncap_first}/add', {
                btn: ['提交', '重置'],
                area: $(window).width() <= 750 ? '95%' : '50%',
                yes: function (index, layero) {
                    $('#febs-${entity?uncap_first}-add').find('#submit').trigger('click');
                },
                btn2: function () {
                    $('#febs-${entity?uncap_first}-add').find('#reset').trigger('click');
                    return false;
                }
            });
        });


        $delete.on('click', function () {
            var checkStatus = table.checkStatus('${entity?uncap_first}Table');
            if (!checkStatus.data.length) {
                febs.alert.warn('请选择需要删除的${table.comment!} ');
            } else {
                febs.modal.confirm('删除${table.comment!}', '确定删除该${table.comment!} ？', function () {
                    var userIds = [];
                    layui.each(checkStatus.data, function (key, item) {
                        userIds.push(item.id)
                    });
                    delete${entity}(userIds.join(','));
                });
            }
        });

        function initTable() {
            tableIns = febs.table.init({
                elem: $view.find('table'),
                id: '${entity?uncap_first}Table',
                url: ctx + '${package.ModuleName}/${entity?uncap_first}/pageList',
                cols: [[
                    {type: 'checkbox'},
                    <#list table.fields as field>
                    <#if "state" == field.name >
                    {title: '状态', templet: '#${entity?uncap_first}-status'},
                    <#elseif "create_date"== field.name>
                    {field: 'createDate', title: '创建时间', minWidth: 180, sort: true},
                    <#elseif  "mod_date" == field.name ||  "creator" == field.name || "modifier" == field.name || "id" == field.name || "owner" == field.name>
                    <#else>
                    {field: '${field.propertyName}', title: '${field.comment}'},
                    </#if>
                    </#list>
                    {title: '操作', toolbar: '#${entity?uncap_first}-option', minWidth: 140}
                ]]
            });
        }

        function getQueryParams() {
            var createTimeFrom = "",
                createTimeTo = "",
                createTime = $searchForm.find('input[name="createDate"]').val();
            if (createTime) {
                createTimeFrom = createTime.split(' - ')[0];
                createTimeTo = createTime.split(' - ')[1];
            }
            return {
                startDate: createTimeFrom,
                endDate: createTimeTo,
                name: $searchForm.find('input[name="name"]').val().trim(),
                invalidate_ie_cache: new Date()
            };
        }


        function delete${entity}(ids){
            febs.delete(ctx + '${package.ModuleName}/${entity?uncap_first}', {"ids": ids}, function () {
                febs.alert.success('删除成功');
                $query.click();
            });
        }
    })
</script>