define("static/scripts/index/uploadify",[],function(e,o,i){i.exports=function(e){$("#file_upload").uploadify({swf:"../lib/uploadify/uploadify.swf",fileObjName:"file",uploader:"http://10.21.67.16:8080/file/upload/savefile.shtml",auto:!0,removeTimeout:0,width:300,height:34,multi:!1,uploadLimit:0,fileSizeLimit:"1MB",fileTypeDesc:"图片文件(*.jpg;*.png;*.gif;*.jpeg)",buttonText:"点击上传logo",fileTypeExts:"*.jpg;*.png;*.gif;*.jpeg",progressData:"percentage",speed:"percentage",queueSizeLimit:1,removeCompleted:!0,onSelect:function(){this.queueData.filesErrored=0},onOpen:function(){},onSelectError:function(e,o){for(var i=0;i<errorCodes.length;i++)errorCodes[i]==o&&(this.queueData.errorMsg=errorMsgs[i])},onFallback:function(){alert("浏览器不能兼容Flash,请下载最新版!")},onUploadSuccess:function(o,i,t){e(o,i,t)},onUploadError:function(){}})}});