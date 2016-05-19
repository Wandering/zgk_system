define("static/scripts/index/datatable",["sea-modules/jquery/dataTables/jquery.dataTables","sea-modules/bootstrap/bootstrap","static/scripts/index/message","sea-modules/jquery/mCustomScrollbar/jquery.mCustomScrollbar.concat.min"],function(e,t,a){e("sea-modules/jquery/dataTables/jquery.dataTables"),e("sea-modules/bootstrap/bootstrap");var s=e("static/scripts/index/message");$.fn.dataTableExt.oStdClasses.sPaging="dataTables_paginate paging_bootstrap paging_custom",jQuery.fn.dataTableExt.oSort["string-case-asc"]=function(e,t){return t>e?-1:e>t?1:0},jQuery.fn.dataTableExt.oSort["string-case-desc"]=function(e,t){return t>e?1:e>t?-1:0};var n={dataTable:null,searchHandle:null,buttonHandle:null,resourceListHandle:null,resourceContentId:null,initTable:function(t){this.searchHandle=t.searchHandle||null,this.buttonHandle=t.buttonHandle||null,this.resourceListHandle=t.resourceListHandle||null,this.tableId=t.tableId,this.resourceContentId=t.resourceContentId,$("#"+t.tableContentId).html('<table cellpadding="0" cellspacing="0" border="0" class="display" id="'+t.tableId+'"></table>'),this.dataTable=$("#"+t.tableId).dataTable({searching:!1,ordering:!1,targets:0,language:{sLengthMenu:"每页显示 _MENU_ 条记录",sZeroRecords:"抱歉， 没有找到相关数据",sInfo:"从 _START_ 到 _END_ / 共 _TOTAL_ 条数据",sInfoEmpty:"没有数据",sInfoFiltered:"(从 _MAX_ 条数据中检索)",sProcessing:"正在加载数据...",sProcessing:"<img src='/dist/static/images/loader.gif' />",oPaginate:{sFirst:"首页",sPrevious:"前一页",sNext:"后一页",sLast:"尾页"}},columns:t.columns,columnDefs:t.columnDefs,sZeroRecords:"没有检索到数据",bProcessing:!0,bServerSide:!0,sAjaxSource:t.sAjaxSource,fnServerData:this.retrieveData,fnInitComplete:function(){e("sea-modules/jquery/mCustomScrollbar/jquery.mCustomScrollbar.concat.min"),$("#content").mCustomScrollbar({theme:"minimal"})},order:[[1,"asc"]]}).removeClass("display").addClass("table table-datatable table-custom"),this.eventHandle()},retrieveData:function(e,t,a){for(var o={},l=0,r=t.length;r>l;l++)("sEcho"===t[l].name||"iDisplayStart"===t[l].name||"iDisplayLength"===t[l].name)&&(o[t[l].name]=t[l].value);o.currentPageNo=o.iDisplayStart/o.iDisplayLength+1,o.pageSize=o.iDisplayLength,n.searchHandle&&"function"==typeof n.searchHandle&&n.searchHandle(o),$.ajax({type:"get",contentType:"application/json",url:e,dataType:"json",data:o,success:function(e){var t=e;if("0000000"===t.rtnCode){var l={sEcho:o.sEcho,iTotalRecords:t.bizData.count,iTotalDisplayRecords:t.bizData.count,aaData:t.bizData.list};a(l),n.eventHandle()}else if("0100014"===t.rtnCode||"0100015"===t.rtnCode)s({title:"温馨提示",msg:t.msg,type:"alert",clickHandle:function(){window.location.href="login.html"}});else{var l={sEcho:o.sEcho,iTotalRecords:0,iTotalDisplayRecords:0,aaData:[]};a(l)}n.resourceContentId&&n.resourceListHandle&&"function"==typeof n.resourceListHandle&&n.resourceListHandle()}})},eventHandle:function(){$("#"+this.tableId+" tbody tr").off("click"),$("#"+this.tableId+" tbody tr").on("click",function(e){$(this).hasClass("row_selected")?$(this).removeClass("row_selected"):(n.dataTable.$("tr.row_selected").removeClass("row_selected"),$(this).addClass("row_selected")),e.stopPropagation()}),n.buttonHandle&&"function"==typeof n.buttonHandle&&($(".td-button").off("click"),$(".td-button").on("click",function(e){n.buttonHandle(),e.stopPropagation()}))},fnGetSelected:function(e){return e.$("tr.row_selected")},selectedRecord:function(){var e=this.fnGetSelected(this.dataTable),t=[];if(e.length>0)for(var a=0,s=e.length;s>a;a++)t.push(this.dataTable.fnGetData(e[a]));return t}};a.exports=n});