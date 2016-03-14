
fis.set('project.files', ['*', 'map.json']);

fis.match('*.js', {
  isMod: true
});

fis.match('/static/sea.js', {
  isMod: false
});

fis.match('/lib/**.js', {
  isMod: false
});

fis.hook('cmd', {
  baseUrl: './sea-modules/',
  paths: {
    "bootstrap": "bootstrap/bootstrap.js",
    "datetimepicker": "bootstrap/datetimepicker/bootstrap-datetimepicker.js",
    "datetimepickerCN": "bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN.js",
    "jQbootstrap": "jquery/bootstrap/jquery.bootstrap.min.js",
    "dialog":"jquery/dialog/jquery.dialog.js",
    "md5":"jquery/md5/jQuery.md5.js",
    "ztree":"jquery/ztree/jquery.ztree.core.3.5.min.js",
    "excheck":"jquery/ztree/jquery.ztree.excheck-3.5.min.js",
    "cookie":"jquery/cookie/jquery.cookie.js",
    "uploadify":"jquery/uploadify/jquery.uploadify.js",
    "dataTables":"jquery/dataTables/jquery.dataTables.js",
    "mCustomScrollbar":"jquery/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"
  }
});

// fis-optimizer-clean-css 插件进行压缩
fis.match('*.css', {
  optimizer: fis.plugin('clean-css')
});

fis.match('*.png', {
  // fis-optimizer-png-compressor 插件进行压缩
  optimizer: fis.plugin('png-compressor')
});

fis.match('::packager', {
  postpackager: fis.plugin('loader')
});

fis.match('/static/**.js', {
  optimizer: fis.plugin('uglify-js')
})

fis.match('/lib/jquery/*.js', {
  optimizer: fis.plugin('uglify-js')
})

fis.match('/lib/uploadify/*.js', {
  optimizer: fis.plugin('uglify-js')
})

fis.match('/sea-modules/**.js', {
  optimizer: fis.plugin('uglify-js')
})

fis.match('*', {
  release: '/dist/$0'
});


