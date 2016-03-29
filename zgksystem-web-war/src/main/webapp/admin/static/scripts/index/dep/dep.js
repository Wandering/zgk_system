/**
 * Created by kepeng on 15/9/9.
 */
define(function(require, exports, module) {
    /**
     *
     * @param succ  有数据处理方式
     * @param err  没有数据和错误处理方式
     */
    module.exports = function(options) {

        var Tree = null;
        require('cookie');
        var token = $.cookie('bizData');
        /**
         * 通过选中树节点的ID获取每个节点下的数据表格
         * @param selectedId
         */
        var getGridByTree = function(selectedId) {
            var module = require('./dep_list.js');
            module(selectedId, function(options) {
                //增加节点
                if ('add' === options.type) {
                    var parentNode = Tree.treeObj.getNodeByParam('id', selectedId, null);
                    Tree.treeObj.addNodes(parentNode, options.obj);
                } else if ('update' === options.type) {
                    //修改节点
                    var updateNode = Tree.treeObj.getNodeByParam('id', options.obj.id, null);
                    console.log(options.obj)
                    updateNode.name = options.obj.name;
                    Tree.treeObj.updateNode(updateNode);
                } else if ('delete' === options.type) {
                    //删除节点
                    var deleteNode = Tree.treeObj.getNodeByParam('id', options.id, null);
                    Tree.treeObj.removeNode(deleteNode);
                }
            });
        };
        /**
         * 获取所有一级代理商
         */
        $.get('/system/department/queryTreeDepartmentAll?token=' + token, function(data) {
            if ('0000000' === data.rtnCode) {
                var nodes = data.bizData;
                if (nodes.length <= 0) {
                    if (!data.msg) {
                        data.msg = '请联系管理员添加代理公司';
                    }
                    options.err(data);
                    return;
                }
                options.succ();
                Tree = require('../tree.js');
                Tree.init({
                    id: 'tree_menu',
                    url: '',
                    beforeClick: function(treeId, parentNode, childNodes) {
                        getGridByTree(parentNode.id);
                    },
                    onAsyncSuccess: function(treeId, treeNode) {

                    },
                    nodes:nodes
                });
                var node = nodes[0];
                var selectNode = Tree.treeObj.getNodeByParam('id', node.id, null);
                Tree.treeObj.selectNode(selectNode);//默认选中第一个节点
                Tree.treeObj.expandNode(selectNode, true, false, true);//默认打开第一个节点
                getGridByTree(node.id);
            } else {
                options.err(data);
            }
        })
    };
});