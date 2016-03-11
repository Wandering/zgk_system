define("sea-modules/jquery/ztree/jquery.ztree.core-3.5",[],function(require,exports,module){!function($){var settings={},roots={},caches={},_consts={className:{BUTTON:"button",LEVEL:"level",ICO_LOADING:"ico_loading",SWITCH:"switch"},event:{NODECREATED:"ztree_nodeCreated",CLICK:"ztree_click",EXPAND:"ztree_expand",COLLAPSE:"ztree_collapse",ASYNC_SUCCESS:"ztree_async_success",ASYNC_ERROR:"ztree_async_error",REMOVE:"ztree_remove",SELECTED:"ztree_selected",UNSELECTED:"ztree_unselected"},id:{A:"_a",ICON:"_ico",SPAN:"_span",SWITCH:"_switch",UL:"_ul"},line:{ROOT:"root",ROOTS:"roots",CENTER:"center",BOTTOM:"bottom",NOLINE:"noline",LINE:"line"},folder:{OPEN:"open",CLOSE:"close",DOCU:"docu"},node:{CURSELECTED:"curSelectedNode"}},_setting={treeId:"",treeObj:null,view:{addDiyDom:null,autoCancelSelected:!0,dblClickExpand:!0,expandSpeed:"fast",fontCss:{},nameIsHTML:!1,selectedMulti:!0,showIcon:!0,showLine:!0,showTitle:!0,txtSelectedEnable:!1},data:{key:{children:"children",name:"name",title:"",url:"url",icon:"icon"},simpleData:{enable:!1,idKey:"id",pIdKey:"pId",rootPId:null},keep:{parent:!1,leaf:!1}},async:{enable:!1,contentType:"application/x-www-form-urlencoded",type:"post",dataType:"text",url:"",autoParam:[],otherParam:[],dataFilter:null},callback:{beforeAsync:null,beforeClick:null,beforeDblClick:null,beforeRightClick:null,beforeMouseDown:null,beforeMouseUp:null,beforeExpand:null,beforeCollapse:null,beforeRemove:null,onAsyncError:null,onAsyncSuccess:null,onNodeCreated:null,onClick:null,onDblClick:null,onRightClick:null,onMouseDown:null,onMouseUp:null,onExpand:null,onCollapse:null,onRemove:null}},_initRoot=function(e){var t=data.getRoot(e);t||(t={},data.setRoot(e,t)),t[e.data.key.children]=[],t.expandTriggerFlag=!1,t.curSelectedList=[],t.noSelection=!0,t.createdNodes=[],t.zId=0,t._ver=(new Date).getTime()},_initCache=function(e){var t=data.getCache(e);t||(t={},data.setCache(e,t)),t.nodes=[],t.doms=[]},_bindEvent=function(e){var t=e.treeObj,n=consts.event;t.bind(n.NODECREATED,function(t,n,o){tools.apply(e.callback.onNodeCreated,[t,n,o])}),t.bind(n.CLICK,function(t,n,o,a,r){tools.apply(e.callback.onClick,[n,o,a,r])}),t.bind(n.EXPAND,function(t,n,o){tools.apply(e.callback.onExpand,[t,n,o])}),t.bind(n.COLLAPSE,function(t,n,o){tools.apply(e.callback.onCollapse,[t,n,o])}),t.bind(n.ASYNC_SUCCESS,function(t,n,o,a){tools.apply(e.callback.onAsyncSuccess,[t,n,o,a])}),t.bind(n.ASYNC_ERROR,function(t,n,o,a,r,i){tools.apply(e.callback.onAsyncError,[t,n,o,a,r,i])}),t.bind(n.REMOVE,function(t,n,o){tools.apply(e.callback.onRemove,[t,n,o])}),t.bind(n.SELECTED,function(t,n,o){tools.apply(e.callback.onSelected,[n,o])}),t.bind(n.UNSELECTED,function(t,n,o){tools.apply(e.callback.onUnSelected,[n,o])})},_unbindEvent=function(e){var t=e.treeObj,n=consts.event;t.unbind(n.NODECREATED).unbind(n.CLICK).unbind(n.EXPAND).unbind(n.COLLAPSE).unbind(n.ASYNC_SUCCESS).unbind(n.ASYNC_ERROR).unbind(n.REMOVE).unbind(n.SELECTED).unbind(n.UNSELECTED)},_eventProxy=function(e){var t=e.target,n=data.getSetting(e.data.treeId),o="",a=null,r="",i="",s=null,d=null,l=null;if(tools.eqs(e.type,"mousedown")?i="mousedown":tools.eqs(e.type,"mouseup")?i="mouseup":tools.eqs(e.type,"contextmenu")?i="contextmenu":tools.eqs(e.type,"click")?tools.eqs(t.tagName,"span")&&null!==t.getAttribute("treeNode"+consts.id.SWITCH)?(o=tools.getNodeMainDom(t).id,r="switchNode"):(l=tools.getMDom(n,t,[{tagName:"a",attrName:"treeNode"+consts.id.A}]),l&&(o=tools.getNodeMainDom(l).id,r="clickNode")):tools.eqs(e.type,"dblclick")&&(i="dblclick",l=tools.getMDom(n,t,[{tagName:"a",attrName:"treeNode"+consts.id.A}]),l&&(o=tools.getNodeMainDom(l).id,r="switchNode")),i.length>0&&0==o.length&&(l=tools.getMDom(n,t,[{tagName:"a",attrName:"treeNode"+consts.id.A}]),l&&(o=tools.getNodeMainDom(l).id)),o.length>0)switch(a=data.getNodeCache(n,o),r){case"switchNode":a.isParent&&(tools.eqs(e.type,"click")||tools.eqs(e.type,"dblclick")&&tools.apply(n.view.dblClickExpand,[n.treeId,a],n.view.dblClickExpand))?s=handler.onSwitchNode:r="";break;case"clickNode":s=handler.onClickNode}switch(i){case"mousedown":d=handler.onZTreeMousedown;break;case"mouseup":d=handler.onZTreeMouseup;break;case"dblclick":d=handler.onZTreeDblclick;break;case"contextmenu":d=handler.onZTreeContextmenu}var c={stop:!1,node:a,nodeEventType:r,nodeEventCallback:s,treeEventType:i,treeEventCallback:d};return c},_initNode=function(e,t,n,o,a,r){if(n){var i=data.getRoot(e),s=e.data.key.children;n.level=t,n.tId=e.treeId+"_"+ ++i.zId,n.parentTId=o?o.tId:null,n.open="string"==typeof n.open?tools.eqs(n.open,"true"):!!n.open,n[s]&&n[s].length>0?(n.isParent=!0,n.zAsync=!0):(n.isParent="string"==typeof n.isParent?tools.eqs(n.isParent,"true"):!!n.isParent,n.open=n.isParent&&!e.async.enable?n.open:!1,n.zAsync=!n.isParent),n.isFirstNode=a,n.isLastNode=r,n.getParentNode=function(){return data.getNodeCache(e,n.parentTId)},n.getPreNode=function(){return data.getPreNode(e,n)},n.getNextNode=function(){return data.getNextNode(e,n)},n.isAjaxing=!1,data.fixPIdKeyValue(e,n)}},_init={bind:[_bindEvent],unbind:[_unbindEvent],caches:[_initCache],nodes:[_initNode],proxys:[_eventProxy],roots:[_initRoot],beforeA:[],afterA:[],innerBeforeA:[],innerAfterA:[],zTreeTools:[]},data={addNodeCache:function(e,t){data.getCache(e).nodes[data.getNodeCacheId(t.tId)]=t},getNodeCacheId:function(e){return e.substring(e.lastIndexOf("_")+1)},addAfterA:function(e){_init.afterA.push(e)},addBeforeA:function(e){_init.beforeA.push(e)},addInnerAfterA:function(e){_init.innerAfterA.push(e)},addInnerBeforeA:function(e){_init.innerBeforeA.push(e)},addInitBind:function(e){_init.bind.push(e)},addInitUnBind:function(e){_init.unbind.push(e)},addInitCache:function(e){_init.caches.push(e)},addInitNode:function(e){_init.nodes.push(e)},addInitProxy:function(e,t){t?_init.proxys.splice(0,0,e):_init.proxys.push(e)},addInitRoot:function(e){_init.roots.push(e)},addNodesData:function(e,t,n){var o=e.data.key.children;t[o]||(t[o]=[]),t[o].length>0&&(t[o][t[o].length-1].isLastNode=!1,view.setNodeLineIcos(e,t[o][t[o].length-1])),t.isParent=!0,t[o]=t[o].concat(n)},addSelectedNode:function(e,t){var n=data.getRoot(e);data.isSelectedNode(e,t)||n.curSelectedList.push(t)},addCreatedNode:function(e,t){if(e.callback.onNodeCreated||e.view.addDiyDom){var n=data.getRoot(e);n.createdNodes.push(t)}},addZTreeTools:function(e){_init.zTreeTools.push(e)},exSetting:function(e){$.extend(!0,_setting,e)},fixPIdKeyValue:function(e,t){e.data.simpleData.enable&&(t[e.data.simpleData.pIdKey]=t.parentTId?t.getParentNode()[e.data.simpleData.idKey]:e.data.simpleData.rootPId)},getAfterA:function(){for(var e=0,t=_init.afterA.length;t>e;e++)_init.afterA[e].apply(this,arguments)},getBeforeA:function(){for(var e=0,t=_init.beforeA.length;t>e;e++)_init.beforeA[e].apply(this,arguments)},getInnerAfterA:function(){for(var e=0,t=_init.innerAfterA.length;t>e;e++)_init.innerAfterA[e].apply(this,arguments)},getInnerBeforeA:function(){for(var e=0,t=_init.innerBeforeA.length;t>e;e++)_init.innerBeforeA[e].apply(this,arguments)},getCache:function(e){return caches[e.treeId]},getNextNode:function(e,t){if(!t)return null;for(var n=e.data.key.children,o=t.parentTId?t.getParentNode():data.getRoot(e),a=0,r=o[n].length-1;r>=a;a++)if(o[n][a]===t)return a==r?null:o[n][a+1];return null},getNodeByParam:function(e,t,n,o){if(!t||!n)return null;for(var a=e.data.key.children,r=0,i=t.length;i>r;r++){if(t[r][n]==o)return t[r];var s=data.getNodeByParam(e,t[r][a],n,o);if(s)return s}return null},getNodeCache:function(e,t){if(!t)return null;var n=caches[e.treeId].nodes[data.getNodeCacheId(t)];return n?n:null},getNodeName:function(e,t){var n=e.data.key.name;return""+t[n]},getNodeTitle:function(e,t){var n=""===e.data.key.title?e.data.key.name:e.data.key.title;return""+t[n]},getNodes:function(e){return data.getRoot(e)[e.data.key.children]},getNodesByParam:function(e,t,n,o){if(!t||!n)return[];for(var a=e.data.key.children,r=[],i=0,s=t.length;s>i;i++)t[i][n]==o&&r.push(t[i]),r=r.concat(data.getNodesByParam(e,t[i][a],n,o));return r},getNodesByParamFuzzy:function(e,t,n,o){if(!t||!n)return[];var a=e.data.key.children,r=[];o=o.toLowerCase();for(var i=0,s=t.length;s>i;i++)"string"==typeof t[i][n]&&t[i][n].toLowerCase().indexOf(o)>-1&&r.push(t[i]),r=r.concat(data.getNodesByParamFuzzy(e,t[i][a],n,o));return r},getNodesByFilter:function(e,t,n,o,a){if(!t)return o?null:[];for(var r=e.data.key.children,i=o?null:[],s=0,d=t.length;d>s;s++){if(tools.apply(n,[t[s],a],!1)){if(o)return t[s];i.push(t[s])}var l=data.getNodesByFilter(e,t[s][r],n,o,a);if(o&&l)return l;i=o?l:i.concat(l)}return i},getPreNode:function(e,t){if(!t)return null;for(var n=e.data.key.children,o=t.parentTId?t.getParentNode():data.getRoot(e),a=0,r=o[n].length;r>a;a++)if(o[n][a]===t)return 0==a?null:o[n][a-1];return null},getRoot:function(e){return e?roots[e.treeId]:null},getRoots:function(){return roots},getSetting:function(e){return settings[e]},getSettings:function(){return settings},getZTreeTools:function(e){var t=this.getRoot(this.getSetting(e));return t?t.treeTools:null},initCache:function(){for(var e=0,t=_init.caches.length;t>e;e++)_init.caches[e].apply(this,arguments)},initNode:function(){for(var e=0,t=_init.nodes.length;t>e;e++)_init.nodes[e].apply(this,arguments)},initRoot:function(){for(var e=0,t=_init.roots.length;t>e;e++)_init.roots[e].apply(this,arguments)},isSelectedNode:function(e,t){for(var n=data.getRoot(e),o=0,a=n.curSelectedList.length;a>o;o++)if(t===n.curSelectedList[o])return!0;return!1},removeNodeCache:function(e,t){var n=e.data.key.children;if(t[n])for(var o=0,a=t[n].length;a>o;o++)arguments.callee(e,t[n][o]);data.getCache(e).nodes[data.getNodeCacheId(t.tId)]=null},removeSelectedNode:function(e,t){for(var n=data.getRoot(e),o=0,a=n.curSelectedList.length;a>o;o++)t!==n.curSelectedList[o]&&data.getNodeCache(e,n.curSelectedList[o].tId)||(n.curSelectedList.splice(o,1),e.treeObj.trigger(consts.event.UNSELECTED,[e.treeId,t]),o--,a--)},setCache:function(e,t){caches[e.treeId]=t},setRoot:function(e,t){roots[e.treeId]=t},setZTreeTools:function(){for(var e=0,t=_init.zTreeTools.length;t>e;e++)_init.zTreeTools[e].apply(this,arguments)},transformToArrayFormat:function(e,t){if(!t)return[];var n=e.data.key.children,o=[];if(tools.isArray(t))for(var a=0,r=t.length;r>a;a++)o.push(t[a]),t[a][n]&&(o=o.concat(data.transformToArrayFormat(e,t[a][n])));else o.push(t),t[n]&&(o=o.concat(data.transformToArrayFormat(e,t[n])));return o},transformTozTreeFormat:function(e,t){var n,o,a=e.data.simpleData.idKey,r=e.data.simpleData.pIdKey,i=e.data.key.children;if(!a||""==a||!t)return[];if(tools.isArray(t)){var s=[],d=[];for(n=0,o=t.length;o>n;n++)d[t[n][a]]=t[n];for(n=0,o=t.length;o>n;n++)d[t[n][r]]&&t[n][a]!=t[n][r]?(d[t[n][r]][i]||(d[t[n][r]][i]=[]),d[t[n][r]][i].push(t[n])):s.push(t[n]);return s}return[t]}},event={bindEvent:function(){for(var e=0,t=_init.bind.length;t>e;e++)_init.bind[e].apply(this,arguments)},unbindEvent:function(){for(var e=0,t=_init.unbind.length;t>e;e++)_init.unbind[e].apply(this,arguments)},bindTree:function(e){var t={treeId:e.treeId},n=e.treeObj;e.view.txtSelectedEnable||n.bind("selectstart",function(e){var t=e.originalEvent.srcElement.nodeName.toLowerCase();return"input"===t||"textarea"===t}).css({"-moz-user-select":"-moz-none"}),n.bind("click",t,event.proxy),n.bind("dblclick",t,event.proxy),n.bind("mouseover",t,event.proxy),n.bind("mouseout",t,event.proxy),n.bind("mousedown",t,event.proxy),n.bind("mouseup",t,event.proxy),n.bind("contextmenu",t,event.proxy)},unbindTree:function(e){var t=e.treeObj;t.unbind("click",event.proxy).unbind("dblclick",event.proxy).unbind("mouseover",event.proxy).unbind("mouseout",event.proxy).unbind("mousedown",event.proxy).unbind("mouseup",event.proxy).unbind("contextmenu",event.proxy)},doProxy:function(){for(var e=[],t=0,n=_init.proxys.length;n>t;t++){var o=_init.proxys[t].apply(this,arguments);if(e.push(o),o.stop)break}return e},proxy:function(e){var t=data.getSetting(e.data.treeId);if(!tools.uCanDo(t,e))return!0;for(var n=event.doProxy(e),o=!0,a=!1,r=0,i=n.length;i>r;r++){var s=n[r];s.nodeEventCallback&&(a=!0,o=s.nodeEventCallback.apply(s,[e,s.node])&&o),s.treeEventCallback&&(a=!0,o=s.treeEventCallback.apply(s,[e,s.node])&&o)}return o}},handler={onSwitchNode:function(e,t){var n=data.getSetting(e.data.treeId);if(t.open){if(0==tools.apply(n.callback.beforeCollapse,[n.treeId,t],!0))return!0;data.getRoot(n).expandTriggerFlag=!0,view.switchNode(n,t)}else{if(0==tools.apply(n.callback.beforeExpand,[n.treeId,t],!0))return!0;data.getRoot(n).expandTriggerFlag=!0,view.switchNode(n,t)}return!0},onClickNode:function(e,t){var n=data.getSetting(e.data.treeId),o=n.view.autoCancelSelected&&(e.ctrlKey||e.metaKey)&&data.isSelectedNode(n,t)?0:n.view.autoCancelSelected&&(e.ctrlKey||e.metaKey)&&n.view.selectedMulti?2:1;return 0==tools.apply(n.callback.beforeClick,[n.treeId,t,o],!0)?!0:(0===o?view.cancelPreSelectedNode(n,t):view.selectNode(n,t,2===o),n.treeObj.trigger(consts.event.CLICK,[e,n.treeId,t,o]),!0)},onZTreeMousedown:function(e,t){var n=data.getSetting(e.data.treeId);return tools.apply(n.callback.beforeMouseDown,[n.treeId,t],!0)&&tools.apply(n.callback.onMouseDown,[e,n.treeId,t]),!0},onZTreeMouseup:function(e,t){var n=data.getSetting(e.data.treeId);return tools.apply(n.callback.beforeMouseUp,[n.treeId,t],!0)&&tools.apply(n.callback.onMouseUp,[e,n.treeId,t]),!0},onZTreeDblclick:function(e,t){var n=data.getSetting(e.data.treeId);return tools.apply(n.callback.beforeDblClick,[n.treeId,t],!0)&&tools.apply(n.callback.onDblClick,[e,n.treeId,t]),!0},onZTreeContextmenu:function(e,t){var n=data.getSetting(e.data.treeId);return tools.apply(n.callback.beforeRightClick,[n.treeId,t],!0)&&tools.apply(n.callback.onRightClick,[e,n.treeId,t]),"function"!=typeof n.callback.onRightClick}},tools={apply:function(e,t,n){return"function"==typeof e?e.apply(zt,t?t:[]):n},canAsync:function(e,t){var n=e.data.key.children;return e.async.enable&&t&&t.isParent&&!(t.zAsync||t[n]&&t[n].length>0)},clone:function(e){if(null===e)return null;var t=tools.isArray(e)?[]:{};for(var n in e)t[n]=e[n]instanceof Date?new Date(e[n].getTime()):"object"==typeof e[n]?arguments.callee(e[n]):e[n];return t},eqs:function(e,t){return e.toLowerCase()===t.toLowerCase()},isArray:function(e){return"[object Array]"===Object.prototype.toString.apply(e)},$:function(e,t,n){return t&&"string"!=typeof t&&(n=t,t=""),"string"==typeof e?$(e,n?n.treeObj.get(0).ownerDocument:null):$("#"+e.tId+t,n?n.treeObj:null)},getMDom:function(e,t,n){if(!t)return null;for(;t&&t.id!==e.treeId;){for(var o=0,a=n.length;t.tagName&&a>o;o++)if(tools.eqs(t.tagName,n[o].tagName)&&null!==t.getAttribute(n[o].attrName))return t;t=t.parentNode}return null},getNodeMainDom:function(e){return $(e).parent("li").get(0)||$(e).parentsUntil("li").parent().get(0)},isChildOrSelf:function(e,t){return $(e).closest("#"+t).length>0},uCanDo:function(){return!0}},view={addNodes:function(e,t,n,o){if(!e.data.keep.leaf||!t||t.isParent)if(tools.isArray(n)||(n=[n]),e.data.simpleData.enable&&(n=data.transformTozTreeFormat(e,n)),t){var a=$$(t,consts.id.SWITCH,e),r=$$(t,consts.id.ICON,e),i=$$(t,consts.id.UL,e);t.open||(view.replaceSwitchClass(t,a,consts.folder.CLOSE),view.replaceIcoClass(t,r,consts.folder.CLOSE),t.open=!1,i.css({display:"none"})),data.addNodesData(e,t,n),view.createNodes(e,t.level+1,n,t),o||view.expandCollapseParentNode(e,t,!0)}else data.addNodesData(e,data.getRoot(e),n),view.createNodes(e,0,n,null)},appendNodes:function(e,t,n,o,a,r){if(!n)return[];for(var i=[],s=e.data.key.children,d=0,l=n.length;l>d;d++){var c=n[d];if(a){var u=o?o:data.getRoot(e),p=u[s],f=p.length==n.length&&0==d,g=d==n.length-1;data.initNode(e,t,c,o,f,g,r),data.addNodeCache(e,c)}var v=[];c[s]&&c[s].length>0&&(v=view.appendNodes(e,t+1,c[s],c,a,r&&c.open)),r&&(view.makeDOMNodeMainBefore(i,e,c),view.makeDOMNodeLine(i,e,c),data.getBeforeA(e,c,i),view.makeDOMNodeNameBefore(i,e,c),data.getInnerBeforeA(e,c,i),view.makeDOMNodeIcon(i,e,c),data.getInnerAfterA(e,c,i),view.makeDOMNodeNameAfter(i,e,c),data.getAfterA(e,c,i),c.isParent&&c.open&&view.makeUlHtml(e,c,i,v.join("")),view.makeDOMNodeMainAfter(i,e,c),data.addCreatedNode(e,c))}return i},appendParentULDom:function(e,t){var n=[],o=$$(t,e);!o.get(0)&&t.parentTId&&(view.appendParentULDom(e,t.getParentNode()),o=$$(t,e));var a=$$(t,consts.id.UL,e);a.get(0)&&a.remove();var r=e.data.key.children,i=view.appendNodes(e,t.level+1,t[r],t,!1,!0);view.makeUlHtml(e,t,n,i.join("")),o.append(n.join(""))},asyncNode:function(setting,node,isSilent,callback){var i,l;if(node&&!node.isParent)return tools.apply(callback),!1;if(node&&node.isAjaxing)return!1;if(0==tools.apply(setting.callback.beforeAsync,[setting.treeId,node],!0))return tools.apply(callback),!1;if(node){node.isAjaxing=!0;var icoObj=$$(node,consts.id.ICON,setting);icoObj.attr({style:"","class":consts.className.BUTTON+" "+consts.className.ICO_LOADING})}var tmpParam={};for(i=0,l=setting.async.autoParam.length;node&&l>i;i++){var pKey=setting.async.autoParam[i].split("="),spKey=pKey;pKey.length>1&&(spKey=pKey[1],pKey=pKey[0]),tmpParam[spKey]=node[pKey]}if(tools.isArray(setting.async.otherParam))for(i=0,l=setting.async.otherParam.length;l>i;i+=2)tmpParam[setting.async.otherParam[i]]=setting.async.otherParam[i+1];else for(var p in setting.async.otherParam)tmpParam[p]=setting.async.otherParam[p];var _tmpV=data.getRoot(setting)._ver;return $.ajax({contentType:setting.async.contentType,cache:!1,type:setting.async.type,url:tools.apply(setting.async.url,[setting.treeId,node],setting.async.url),data:tmpParam,dataType:setting.async.dataType,success:function(msg){if(_tmpV==data.getRoot(setting)._ver){var newNodes=[];try{newNodes=msg&&0!=msg.length?"string"==typeof msg?eval("("+msg+")"):msg:[]}catch(err){newNodes=msg}node&&(node.isAjaxing=null,node.zAsync=!0),view.setNodeLineIcos(setting,node),newNodes&&""!==newNodes?(newNodes=tools.apply(setting.async.dataFilter,[setting.treeId,node,newNodes],newNodes),view.addNodes(setting,node,newNodes?tools.clone(newNodes):[],!!isSilent)):view.addNodes(setting,node,[],!!isSilent),setting.treeObj.trigger(consts.event.ASYNC_SUCCESS,[setting.treeId,node,msg]),tools.apply(callback)}},error:function(e,t,n){_tmpV==data.getRoot(setting)._ver&&(node&&(node.isAjaxing=null),view.setNodeLineIcos(setting,node),setting.treeObj.trigger(consts.event.ASYNC_ERROR,[setting.treeId,node,e,t,n]))}}),!0},cancelPreSelectedNode:function(e,t,n){var o,a,r=data.getRoot(e).curSelectedList;for(o=r.length-1;o>=0;o--)if(a=r[o],t===a||!t&&(!n||n!==a)){if($$(a,consts.id.A,e).removeClass(consts.node.CURSELECTED),t){data.removeSelectedNode(e,t);break}r.splice(o,1),e.treeObj.trigger(consts.event.UNSELECTED,[e.treeId,a])}},createNodeCallback:function(e){if(e.callback.onNodeCreated||e.view.addDiyDom)for(var t=data.getRoot(e);t.createdNodes.length>0;){var n=t.createdNodes.shift();tools.apply(e.view.addDiyDom,[e.treeId,n]),e.callback.onNodeCreated&&e.treeObj.trigger(consts.event.NODECREATED,[e.treeId,n])}},createNodes:function(e,t,n,o){if(n&&0!=n.length){var a=data.getRoot(e),r=e.data.key.children,i=!o||o.open||!!$$(o[r][0],e).get(0);a.createdNodes=[];var s=view.appendNodes(e,t,n,o,!0,i);if(o){var d=$$(o,consts.id.UL,e);d.get(0)&&d.append(s.join(""))}else e.treeObj.append(s.join(""));view.createNodeCallback(e)}},destroy:function(e){e&&(data.initCache(e),data.initRoot(e),event.unbindTree(e),event.unbindEvent(e),e.treeObj.empty(),delete settings[e.treeId])},expandCollapseNode:function(e,t,n,o,a){var r=data.getRoot(e),i=e.data.key.children;if(!t)return void tools.apply(a,[]);if(r.expandTriggerFlag){var s=a;a=function(){s&&s(),t.open?e.treeObj.trigger(consts.event.EXPAND,[e.treeId,t]):e.treeObj.trigger(consts.event.COLLAPSE,[e.treeId,t])},r.expandTriggerFlag=!1}if(!t.open&&t.isParent&&(!$$(t,consts.id.UL,e).get(0)||t[i]&&t[i].length>0&&!$$(t[i][0],e).get(0))&&(view.appendParentULDom(e,t),view.createNodeCallback(e)),t.open==n)return void tools.apply(a,[]);var d=$$(t,consts.id.UL,e),l=$$(t,consts.id.SWITCH,e),c=$$(t,consts.id.ICON,e);t.isParent?(t.open=!t.open,t.iconOpen&&t.iconClose&&c.attr("style",view.makeNodeIcoStyle(e,t)),t.open?(view.replaceSwitchClass(t,l,consts.folder.OPEN),view.replaceIcoClass(t,c,consts.folder.OPEN),0==o||""==e.view.expandSpeed?(d.show(),tools.apply(a,[])):t[i]&&t[i].length>0?d.slideDown(e.view.expandSpeed,a):(d.show(),tools.apply(a,[]))):(view.replaceSwitchClass(t,l,consts.folder.CLOSE),view.replaceIcoClass(t,c,consts.folder.CLOSE),0!=o&&""!=e.view.expandSpeed&&t[i]&&t[i].length>0?d.slideUp(e.view.expandSpeed,a):(d.hide(),tools.apply(a,[])))):tools.apply(a,[])},expandCollapseParentNode:function(e,t,n,o,a){if(t){if(!t.parentTId)return void view.expandCollapseNode(e,t,n,o,a);view.expandCollapseNode(e,t,n,o),t.parentTId&&view.expandCollapseParentNode(e,t.getParentNode(),n,o,a)}},expandCollapseSonNode:function(e,t,n,o,a){var r=data.getRoot(e),i=e.data.key.children,s=t?t[i]:r[i],d=t?!1:o,l=data.getRoot(e).expandTriggerFlag;if(data.getRoot(e).expandTriggerFlag=!1,s)for(var c=0,u=s.length;u>c;c++)s[c]&&view.expandCollapseSonNode(e,s[c],n,d);data.getRoot(e).expandTriggerFlag=l,view.expandCollapseNode(e,t,n,o,a)},isSelectedNode:function(e,t){if(!t)return!1;var n,o=data.getRoot(e).curSelectedList;for(n=o.length-1;n>=0;n--)if(t===o[n])return!0;return!1},makeDOMNodeIcon:function(e,t,n){var o=data.getNodeName(t,n),a=t.view.nameIsHTML?o:o.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;");e.push("<span id='",n.tId,consts.id.ICON,"' title='' treeNode",consts.id.ICON," class='",view.makeNodeIcoClass(t,n),"' style='",view.makeNodeIcoStyle(t,n),"'></span><span id='",n.tId,consts.id.SPAN,"'>",a,"</span>")},makeDOMNodeLine:function(e,t,n){e.push("<span id='",n.tId,consts.id.SWITCH,"' title='' class='",view.makeNodeLineClass(t,n),"' treeNode",consts.id.SWITCH,"></span>")},makeDOMNodeMainAfter:function(e){e.push("</li>")},makeDOMNodeMainBefore:function(e,t,n){e.push("<li id='",n.tId,"' class='",consts.className.LEVEL,n.level,"' tabindex='0' hidefocus='true' treenode>")},makeDOMNodeNameAfter:function(e){e.push("</a>")},makeDOMNodeNameBefore:function(e,t,n){var o=data.getNodeTitle(t,n),a=view.makeNodeUrl(t,n),r=view.makeNodeFontCss(t,n),i=[];for(var s in r)i.push(s,":",r[s],";");e.push("<a id='",n.tId,consts.id.A,"' class='",consts.className.LEVEL,n.level,"' treeNode",consts.id.A,' onclick="',n.click||"",'" ',null!=a&&a.length>0?"href='"+a+"'":""," target='",view.makeNodeTarget(n),"' style='",i.join(""),"'"),tools.apply(t.view.showTitle,[t.treeId,n],t.view.showTitle)&&o&&e.push("title='",o.replace(/'/g,"&#39;").replace(/</g,"&lt;").replace(/>/g,"&gt;"),"'"),e.push(">")},makeNodeFontCss:function(e,t){var n=tools.apply(e.view.fontCss,[e.treeId,t],e.view.fontCss);return n&&"function"!=typeof n?n:{}},makeNodeIcoClass:function(e,t){var n=["ico"];return t.isAjaxing||(n[0]=(t.iconSkin?t.iconSkin+"_":"")+n[0],n.push(t.isParent?t.open?consts.folder.OPEN:consts.folder.CLOSE:consts.folder.DOCU)),consts.className.BUTTON+" "+n.join("_")},makeNodeIcoStyle:function(e,t){var n=[];if(!t.isAjaxing){var o=t.isParent&&t.iconOpen&&t.iconClose?t.open?t.iconOpen:t.iconClose:t[e.data.key.icon];o&&n.push("background:url(",o,") 0 0 no-repeat;"),0!=e.view.showIcon&&tools.apply(e.view.showIcon,[e.treeId,t],!0)||n.push("width:0px;height:0px;")}return n.join("")},makeNodeLineClass:function(e,t){var n=[];return n.push(e.view.showLine?0==t.level&&t.isFirstNode&&t.isLastNode?consts.line.ROOT:0==t.level&&t.isFirstNode?consts.line.ROOTS:t.isLastNode?consts.line.BOTTOM:consts.line.CENTER:consts.line.NOLINE),n.push(t.isParent?t.open?consts.folder.OPEN:consts.folder.CLOSE:consts.folder.DOCU),view.makeNodeLineClassEx(t)+n.join("_")},makeNodeLineClassEx:function(e){return consts.className.BUTTON+" "+consts.className.LEVEL+e.level+" "+consts.className.SWITCH+" "},makeNodeTarget:function(e){return e.target||"_blank"},makeNodeUrl:function(e,t){var n=e.data.key.url;return t[n]?t[n]:null},makeUlHtml:function(e,t,n,o){n.push("<ul id='",t.tId,consts.id.UL,"' class='",consts.className.LEVEL,t.level," ",view.makeUlLineClass(e,t),"' style='display:",t.open?"block":"none","'>"),n.push(o),n.push("</ul>")},makeUlLineClass:function(e,t){return e.view.showLine&&!t.isLastNode?consts.line.LINE:""},removeChildNodes:function(e,t){if(t){var n=e.data.key.children,o=t[n];if(o){for(var a=0,r=o.length;r>a;a++)data.removeNodeCache(e,o[a]);if(data.removeSelectedNode(e),delete t[n],e.data.keep.parent)$$(t,consts.id.UL,e).empty();else{t.isParent=!1,t.open=!1;var i=$$(t,consts.id.SWITCH,e),s=$$(t,consts.id.ICON,e);view.replaceSwitchClass(t,i,consts.folder.DOCU),view.replaceIcoClass(t,s,consts.folder.DOCU),$$(t,consts.id.UL,e).remove()}}}},setFirstNode:function(e,t){var n=e.data.key.children,o=t[n].length;o>0&&(t[n][0].isFirstNode=!0)},setLastNode:function(e,t){var n=e.data.key.children,o=t[n].length;o>0&&(t[n][o-1].isLastNode=!0)},removeNode:function(e,t){var n=data.getRoot(e),o=e.data.key.children,a=t.parentTId?t.getParentNode():n;if(t.isFirstNode=!1,t.isLastNode=!1,t.getPreNode=function(){return null},t.getNextNode=function(){return null},data.getNodeCache(e,t.tId)){$$(t,e).remove(),data.removeNodeCache(e,t),data.removeSelectedNode(e,t);for(var r=0,i=a[o].length;i>r;r++)if(a[o][r].tId==t.tId){a[o].splice(r,1);break}view.setFirstNode(e,a),view.setLastNode(e,a);var s,d,l,c=a[o].length;if(e.data.keep.parent||0!=c){if(e.view.showLine&&c>0){var u=a[o][c-1];if(s=$$(u,consts.id.UL,e),d=$$(u,consts.id.SWITCH,e),l=$$(u,consts.id.ICON,e),a==n)if(1==a[o].length)view.replaceSwitchClass(u,d,consts.line.ROOT);else{var p=$$(a[o][0],consts.id.SWITCH,e);view.replaceSwitchClass(a[o][0],p,consts.line.ROOTS),view.replaceSwitchClass(u,d,consts.line.BOTTOM)}else view.replaceSwitchClass(u,d,consts.line.BOTTOM);s.removeClass(consts.line.LINE)}}else a.isParent=!1,a.open=!1,s=$$(a,consts.id.UL,e),d=$$(a,consts.id.SWITCH,e),l=$$(a,consts.id.ICON,e),view.replaceSwitchClass(a,d,consts.folder.DOCU),view.replaceIcoClass(a,l,consts.folder.DOCU),s.css("display","none")}},replaceIcoClass:function(e,t,n){if(t&&!e.isAjaxing){var o=t.attr("class");if(void 0!=o){var a=o.split("_");switch(n){case consts.folder.OPEN:case consts.folder.CLOSE:case consts.folder.DOCU:a[a.length-1]=n}t.attr("class",a.join("_"))}}},replaceSwitchClass:function(e,t,n){if(t){var o=t.attr("class");if(void 0!=o){var a=o.split("_");switch(n){case consts.line.ROOT:case consts.line.ROOTS:case consts.line.CENTER:case consts.line.BOTTOM:case consts.line.NOLINE:a[0]=view.makeNodeLineClassEx(e)+n;break;case consts.folder.OPEN:case consts.folder.CLOSE:case consts.folder.DOCU:a[1]=n}t.attr("class",a.join("_")),n!==consts.folder.DOCU?t.removeAttr("disabled"):t.attr("disabled","disabled")}}},selectNode:function(e,t,n){n||view.cancelPreSelectedNode(e,null,t),$$(t,consts.id.A,e).addClass(consts.node.CURSELECTED),data.addSelectedNode(e,t),e.treeObj.trigger(consts.event.SELECTED,[e.treeId,t])},setNodeFontCss:function(e,t){var n=$$(t,consts.id.A,e),o=view.makeNodeFontCss(e,t);o&&n.css(o)},setNodeLineIcos:function(e,t){if(t){var n=$$(t,consts.id.SWITCH,e),o=$$(t,consts.id.UL,e),a=$$(t,consts.id.ICON,e),r=view.makeUlLineClass(e,t);0==r.length?o.removeClass(consts.line.LINE):o.addClass(r),n.attr("class",view.makeNodeLineClass(e,t)),t.isParent?n.removeAttr("disabled"):n.attr("disabled","disabled"),a.removeAttr("style"),a.attr("style",view.makeNodeIcoStyle(e,t)),a.attr("class",view.makeNodeIcoClass(e,t))}},setNodeName:function(e,t){var n=data.getNodeTitle(e,t),o=$$(t,consts.id.SPAN,e);if(o.empty(),e.view.nameIsHTML?o.html(data.getNodeName(e,t)):o.text(data.getNodeName(e,t)),tools.apply(e.view.showTitle,[e.treeId,t],e.view.showTitle)){var a=$$(t,consts.id.A,e);a.attr("title",n?n:"")}},setNodeTarget:function(e,t){var n=$$(t,consts.id.A,e);n.attr("target",view.makeNodeTarget(t))},setNodeUrl:function(e,t){var n=$$(t,consts.id.A,e),o=view.makeNodeUrl(e,t);null==o||0==o.length?n.removeAttr("href"):n.attr("href",o)},switchNode:function(e,t){if(t.open||!tools.canAsync(e,t))view.expandCollapseNode(e,t,!t.open);else if(e.async.enable){if(!view.asyncNode(e,t))return void view.expandCollapseNode(e,t,!t.open)}else t&&view.expandCollapseNode(e,t,!t.open)}};$.fn.zTree={consts:_consts,_z:{tools:tools,view:view,event:event,data:data},getZTreeObj:function(e){var t=data.getZTreeTools(e);return t?t:null},destroy:function(e){if(e&&e.length>0)view.destroy(data.getSetting(e));else for(var t in settings)view.destroy(settings[t])},init:function(e,t,n){var o=tools.clone(_setting);$.extend(!0,o,t),o.treeId=e.attr("id"),o.treeObj=e,o.treeObj.empty(),settings[o.treeId]=o,"undefined"==typeof document.body.style.maxHeight&&(o.view.expandSpeed=""),data.initRoot(o);var a=data.getRoot(o),r=o.data.key.children;n=n?tools.clone(tools.isArray(n)?n:[n]):[],a[r]=o.data.simpleData.enable?data.transformTozTreeFormat(o,n):n,data.initCache(o),event.unbindTree(o),event.bindTree(o),event.unbindEvent(o),event.bindEvent(o);var i={setting:o,addNodes:function(e,t,n){function a(){view.addNodes(o,e,r,1==n)}if(!t)return null;if(e||(e=null),e&&!e.isParent&&o.data.keep.leaf)return null;var r=tools.clone(tools.isArray(t)?t:[t]);return tools.canAsync(o,e)?view.asyncNode(o,e,n,a):a(),r},cancelSelectedNode:function(e){view.cancelPreSelectedNode(o,e)},destroy:function(){view.destroy(o)},expandAll:function(e){return e=!!e,view.expandCollapseSonNode(o,null,e,!0),e},expandNode:function(e,t,n,a,r){if(!e||!e.isParent)return null;if(t!==!0&&t!==!1&&(t=!e.open),r=!!r,r&&t&&0==tools.apply(o.callback.beforeExpand,[o.treeId,e],!0))return null;if(r&&!t&&0==tools.apply(o.callback.beforeCollapse,[o.treeId,e],!0))return null;if(t&&e.parentTId&&view.expandCollapseParentNode(o,e.getParentNode(),t,!1),t===e.open&&!n)return null;if(data.getRoot(o).expandTriggerFlag=r,!tools.canAsync(o,e)&&n)view.expandCollapseSonNode(o,e,t,!0,function(){if(a!==!1)try{$$(e,o).focus().blur()}catch(t){}});else if(e.open=!t,view.switchNode(this.setting,e),a!==!1)try{$$(e,o).focus().blur()}catch(i){}return t},getNodes:function(){return data.getNodes(o)},getNodeByParam:function(e,t,n){return e?data.getNodeByParam(o,n?n[o.data.key.children]:data.getNodes(o),e,t):null},getNodeByTId:function(e){return data.getNodeCache(o,e)},getNodesByParam:function(e,t,n){return e?data.getNodesByParam(o,n?n[o.data.key.children]:data.getNodes(o),e,t):null},getNodesByParamFuzzy:function(e,t,n){return e?data.getNodesByParamFuzzy(o,n?n[o.data.key.children]:data.getNodes(o),e,t):null},getNodesByFilter:function(e,t,n,a){return t=!!t,e&&"function"==typeof e?data.getNodesByFilter(o,n?n[o.data.key.children]:data.getNodes(o),e,t,a):t?null:[]},getNodeIndex:function(e){if(!e)return null;for(var t=o.data.key.children,n=e.parentTId?e.getParentNode():data.getRoot(o),a=0,r=n[t].length;r>a;a++)if(n[t][a]==e)return a;return-1},getSelectedNodes:function(){for(var e=[],t=data.getRoot(o).curSelectedList,n=0,a=t.length;a>n;n++)e.push(t[n]);return e},isSelectedNode:function(e){return data.isSelectedNode(o,e)},reAsyncChildNodes:function(e,t,n){if(this.setting.async.enable){var a=!e;if(a&&(e=data.getRoot(o)),"refresh"==t){for(var r=this.setting.data.key.children,i=0,s=e[r]?e[r].length:0;s>i;i++)data.removeNodeCache(o,e[r][i]);if(data.removeSelectedNode(o),e[r]=[],a)this.setting.treeObj.empty();else{var d=$$(e,consts.id.UL,o);d.empty()}}view.asyncNode(this.setting,a?null:e,!!n)}},refresh:function(){this.setting.treeObj.empty();var e=data.getRoot(o),t=e[o.data.key.children];data.initRoot(o),e[o.data.key.children]=t,data.initCache(o),view.createNodes(o,0,e[o.data.key.children])},removeChildNodes:function(e){if(!e)return null;var t=o.data.key.children,n=e[t];return view.removeChildNodes(o,e),n?n:null},removeNode:function(e,t){e&&(t=!!t,t&&0==tools.apply(o.callback.beforeRemove,[o.treeId,e],!0)||(view.removeNode(o,e),t&&this.setting.treeObj.trigger(consts.event.REMOVE,[o.treeId,e])))},selectNode:function(e,t){if(e&&tools.uCanDo(o)){if(t=o.view.selectedMulti&&t,e.parentTId)view.expandCollapseParentNode(o,e.getParentNode(),!0,!1,function(){try{$$(e,o).focus().blur()}catch(t){}});else try{$$(e,o).focus().blur()}catch(n){}view.selectNode(o,e,t)}},transformTozTreeNodes:function(e){return data.transformTozTreeFormat(o,e)
},transformToArray:function(e){return data.transformToArrayFormat(o,e)},updateNode:function(e){if(e){var t=$$(e,o);t.get(0)&&tools.uCanDo(o)&&(view.setNodeName(o,e),view.setNodeTarget(o,e),view.setNodeUrl(o,e),view.setNodeLineIcos(o,e),view.setNodeFontCss(o,e))}}};return a.treeTools=i,data.setZTreeTools(o,i),a[r]&&a[r].length>0?view.createNodes(o,0,a[r]):o.async.enable&&o.async.url&&""!==o.async.url&&view.asyncNode(o),i}};var zt=$.fn.zTree,$$=tools.$,consts=zt.consts}(jQuery)});