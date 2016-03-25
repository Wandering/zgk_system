/**
 * Created by kepeng on 15/9/10.
 */
define(function(require, exports, module) {
    /**
     *
     * @param getTreeDataURL  获取树的数据
     * @param listGridModule  每个节点的列表数据
     * @param succ  有数据处理方式
     * @param err  没有数据和错误处理方式
     */
    module.exports = function(options) {
        $.get(options.getTreeDataURL, function(data) {
            if ('0000000' === data.rtnCode) {
                var nodes = [];
                if (Object.prototype.toString.call(data.bizData) === '[object Array]') {
                    nodes = data.bizData;
                } else {
                    nodes = data.bizData.treeBeanList;
                }
                if (nodes.length <= 0) {
                    if (!data.msg) {
                        data.msg = '请添加相关信息';
                    }
                    options.err(data);
                    return;
                }
                options.succ();
                var Tree = require('../tree.js');
                Tree.init({
                    id: 'tree_menu',
                    url: '',
                    beforeClick: function(treeId, parentNode, childNodes) {
                        if (options.listGridModule && typeof options.listGridModule === 'function') {
                            options.listGridModule(parentNode, Tree.treeObj);
                        }
                    },
                    onAsyncSuccess: function(treeId, treeNode) {

                    },
                    nodes:nodes
                });
                var node = nodes[0];
                var selectNode = Tree.treeObj.getNodeByParam('id', node.id, null);
                Tree.treeObj.selectNode(selectNode);//默认选中第一个节点
                //Tree.treeObj.expandNode(selectNode, true, false, true);//默认打开第一个节点
                options.listGridModule(node, Tree.treeObj);
            } else {
                options.err(data);
            }
        })
    };
});
