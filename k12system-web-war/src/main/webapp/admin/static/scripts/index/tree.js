define(function(require, exports, module) {

	var Tree = (function() {
		var setOptions = function() {
			return {
				view: {
					selectedMulti: false
				},
				check: {
					enable: false,
					chkboxType: {
						"Y": "ps",
						"N": "s"
					}
				},
				async: {
					enable: true,
					url: '',
					autoParam: ["id", "name=n", "level=lv"],
					otherParam: {
						"otherParam": "zTreeAsyncTest"
					}
				},
				callback: {
					beforeClick: function(treeId, parentNode, childNodes) {},
					onAsyncSuccess: function(treeId, treeNode) {}
				}
			}
		};
		return {
			treeObj: null,
			init: function(options) {
				require('ztree');
				require('excheck');
				var setting = setOptions();
				if (options.url) {
					setting.async.url = options.url;
				} else {
					setting.async.enable = false;
				}
				setting.check.enable = options.check || false;
				if (options.beforeClick) {
					setting.callback.beforeClick = options.beforeClick;
				}

				if (options.onAsyncSuccess) {
					setting.callback.onAsyncSuccess = options.onAsyncSuccess;
				}

				if (!options.url && options.nodes && options.nodes.length > 0) {
					this.treeObj = $.fn.zTree.init($("#" + options.id), setting, options.nodes);
					return;
				};
				this.treeObj = $.fn.zTree.init($("#" + options.id), setting);
			},
			getCheckedNodes: function() {
				var nodes = this.treeObj.getCheckedNodes(true);
				return nodes;
				var checkedNodes = [];
				for (var i = 0, len = nodes.length; i < len; i++) {
					checkedNodes.push({
						id: nodes[i].id,
						pId: nodes[i].pId,
						isParent: nodes[i].isParent
					});
				}
				return this.structureCheckedTree(checkedNodes);
			},
			structureCheckedTree: function(nodes) {
				for (var i = 0; i < nodes.length; i++) {
					var id = nodes[i].id;
					if (nodes[i].isParent) {
						for (var j = 0; j < nodes.length; j++) {
							if (id == nodes[j].pId) {
								nodes[i].children = nodes[i].children || [];
								nodes[i].children.push(nodes[j]);
							}
						}
					}
				}

				var checkedTree = [];
				for (var n = 0; n < nodes.length; n++) {
					if (!nodes[n].pId) {
						checkedTree.push(nodes[n]);
					}
				}
				return checkedTree;
			}
		}
	})();

	module.exports = Tree;
})