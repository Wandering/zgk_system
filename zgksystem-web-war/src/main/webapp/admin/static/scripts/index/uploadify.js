/**
 * Created by kepeng on 15/9/9.
 */
define(function(require, exports, module) {
    module.exports = function(callback) {
        $("#file_upload").uploadify({
            'swf': "../lib/uploadify/uploadify.swf",
            'fileObjName': 'file',
            'uploader': "http://cs-dev.thinkjoy.com.cn/rest/v1/uploadFile?userId=gk360&dirId=0&productCode=gk360&bizSystem=gk360&spaceName=gk360",
            //'uploader': "http://10.21.67.16:8080/file/upload/savefile.shtml",
            'auto': true,
            'removeTimeout': 0,
            'width': 300,
            'height': 34,
            'multi': false,
            'uploadLimit': 0,
            'fileSizeLimit': "1MB",
            'fileTypeDesc': '图片文件(*.jpg;*.png;*.gif;*.jpeg)',
            'buttonText': '点击上传logo',
            'fileTypeExts': "*.jpg;*.png;*.gif;*.jpeg",
            'progressData': 'percentage',
            'speed': 'percentage',
            'queueSizeLimit': 1,
            'removeCompleted': true,
            'onSelect': function(file) {
                this.queueData.filesErrored = 0;
            },
            'onOpen': function(event, ID, fileObj) {},
            'onSelectError': function(file, errorCode, errorMsg) {
                for (var i = 0; i < errorCodes.length; i++) {
                    if (errorCodes[i] == errorCode) {
                        this.queueData.errorMsg = errorMsgs[i];
                    }
                }
            },

            'onFallback': function() {
                alert("浏览器不能兼容Flash,请下载最新版!");
            },
            'onUploadSuccess': function(file, data, response) {
                var obj = {
                    code: 200,
                    data: {
                        url: JSON.parse(data).bizData.file.fileUrl
                    }
                }
                callback(file, JSON.stringify(obj), response);
            },
            'onUploadError': function(file, errorCode, errorMsg, errorString) {
            }
        });
    };
});
