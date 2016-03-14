/**
 * Created by kepeng on 15/9/9.
 */
define(function(require, exports, module) {

	function GetQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
	/**
	 *
	 * @param succ  有数据处理方式
	 * @param err  没有数据和错误处理方式
	 */
	module.exports = function(options) {

		require('cookie');
		var token = $.cookie('bizData');

		var Tree = null;
		/**
		 * 通过选中树节点的ID获取每个节点下的数据表格
		 * @param selectedId
		 */
		var getGridByTree = function(selectedId) {
			require.async('./menu_list', function(module) {
				module(selectedId, function(options) {
					//增加节点
					if ('add' === options.type) {
						var parentNode = Tree.treeObj.getNodeByParam('id', selectedId, null);
						Tree.treeObj.addNodes(parentNode, options.obj);
					} else if ('update' === options.type) {
						//修改节点
						var updateNode = Tree.treeObj.getNodeByParam('id', options.obj.id, null);
						updateNode.name = options.obj.name;
						Tree.treeObj.updateNode(updateNode);
					} else if ('delete' === options.type) {
						//删除节点
						var deleteNode = Tree.treeObj.getNodeByParam('id', options.id, null);
						Tree.treeObj.removeNode(deleteNode);
					}
				});
			});
		};
		/**
		 * 获取所有菜单
		 */
		var systemCode = GetQueryString('systemCode');
		$.get('/system/menu/getMenuTree?token=' + token + '&systemCode=' + systemCode, function(data) {
			if ('0000000' === data.rtnCode) {
				var nodes = data.bizData.treeBeanList;
				nodes.sort(function(a, b) {
					return a.id - b.id;
				});
				var rootNodes = [{
					name:'根菜单',
					id:'-1',
					children:[]
				}];

				rootNodes[0].children.push.apply(rootNodes[0].children, nodes);
				if (rootNodes.length <= 0) {
					options.err(data);
					return;
				}
				options.succ();
				require.async('../tree', function(Tree) {
					Tree.init({
						id: 'tree_menu',
						url: '',
						beforeClick: function(treeId, parentNode, childNodes) {
							getGridByTree(parentNode.id);
						},
						onAsyncSuccess: function(treeId, treeNode) {

						},
						nodes:rootNodes
					});
					var node = rootNodes[0];
					var selectNode = Tree.treeObj.getNodeByParam('id', node.id, null);
					Tree.treeObj.selectNode(selectNode);//默认选中第一个节点
					Tree.treeObj.expandNode(selectNode, true, false, true);//默认打开第一个节点
					getGridByTree(node.id);
				});
			} else {
				options.err(data);
			}
		})
	};
});