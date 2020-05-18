<style>

</style>
<div class="layui-fluid layui-anim febs-anim" id="febs-${entity?uncap_first}-add" lay-title="${table.comment!}">
    <form class="layui-form" action="" lay-filter="${entity?uncap_first}-add-form">
        <#list table.fields as field>
            <#if field.name?contains("id") || "mod_date" == field.name ||  "creator" == field.name || "modifier" == field.name || "create_date" == field.name || "owner" == field.name>
            <#elseif "remarks"== field.name>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">备注：</label>
                    <div class="layui-input-block">
                        <textarea name="remarks" maxlength="100" class="layui-textarea"></textarea>
                    </div>
                </div>
            <#else>
                <div class="layui-form-item">
                    <label class="layui-form-label febs-form-item-require">${field.comment}：</label>
                    <div class="layui-input-block">
                        <input type="text" name="${field.propertyName}" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </#if>
        </#list>
        <div class="layui-form-item febs-hide">
            <button class="layui-btn" lay-submit="" lay-filter="${entity?uncap_first}-add-form-submit"
                    id="submit"></button>
            <button type="reset" class="layui-btn" id="reset"></button>
        </div>
    </form>
</div>

<script data-th-inline="javascript" type="text/javascript">
    layui.use(['dropdown', 'jquery', 'layer', 'form', 'table', 'febs', 'util', 'xmSelect'], function () {
        var $ = layui.jquery,
            layer = layui.layer,
            febs = layui.febs,
            form = layui.form,
            table = layui.table,
            xmSelect = layui.xmSelect,
            $view = $('#febs-${entity?uncap_first}-add');

        form.render();


        form.on('submit(${entity?uncap_first}-add-form-submit)', function (data) {
            febs.post(ctx + '${package.ModuleName}/${entity?uncap_first}', data.field, function () {
                layer.closeAll();
                $('#febs-${entity?uncap_first}').find('#query').click();
            });
            return false;
        });

    })
</script>