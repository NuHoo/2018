<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>My JSP 'index.jsp' starting page</title>
    <script type="text/javascript" src="jquery-3.2.1.min.js"></script>
    <!--防止谷歌出现icon异常，此异常对运行没有影响 -->
    <link rel="shortcut icon" href="#" />

</head>

<body>
<form method="post" enctype="multipart/form-data" id="upload">
    <input type="file" name="file" class="uploadfile" id="file">
    <p><input type="button" value="上传" class="addbtn"></p>
</form>
</body>
<script type="text/javascript">
    $(".addbtn").click(function () {
        //获取input:type='file'选中文件的内容
        var file = $("#file")[0].files[0];

        if ($("#file").val() == "") {
            alert("请选择上传文件");
        } else {
            var formData = new FormData();
            //此处将所要传递的数据append到formData中
            formData.append("fileinfo", file);
            //ajax传递到后台处理
            $.ajax({
                url: "/u",
                type: "POST",
                data: formData,
                processData: false,       //必不可缺
                contentType: false,       //必不可缺
                success: function (e) {
                    alert("文件上传成功");
                    $("#file").val() == "";
                }
            })
        }
    });
</script>

</html>