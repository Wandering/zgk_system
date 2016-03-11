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
        require('cookie');
        var token = $.cookie('bizData');
        /**
         * 获取所有产品
         */
        $.get('/system/product/queryTreeProduct?token=' + token, function(data) {

            if ('0000000' === data.rtnCode) {
                var nodes = data.bizData;
                if (nodes.length <= 0) {
                    options.err(data);
                    return;
                }
                options.succ();
                var Tree = require('../tree.js');
                Tree.init({
                    id: 'tree_menu',
                    url: '',
                    beforeClick: function(treeId, parentNode, childNodes) {
                        var module = require('./company_list.js');
                        module(parentNode.id);
                    },
                    onAsyncSuccess: function(treeId, treeNode) {

                    },
                    nodes:nodes
                });

                var node = nodes[0];
                var selectNode = Tree.treeObj.getNodeByParam('id', node.id, null);
                Tree.treeObj.selectNode(selectNode);//默认选中第一个节点
                var module = require('./company_list.js');
                module(node.id);
            } else {
                options.err(data);
            }
        });
    };
});
