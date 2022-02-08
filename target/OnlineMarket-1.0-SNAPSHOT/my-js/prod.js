//图片上传操作
$("#prodImage").change(function () {
    let uploadFile=this.files[0];
    let formData=new FormData();
    formData.append("uploadImage",uploadFile);
    //let url = URL.createObjectURL(uploadFile);//图片路径
    //$("#uploadImg").prop("src", url);

    //文件上传  读取文件内容 写入指定文件中
    //1. 单纯使用servlet3.1之后技术实现(推荐)
    //2. 使用第三方技术实现文件上传  commons-fileupload.jar
    //将文件数据提交给服务端
    //1.1 普通文本数据   $.post()  $.get() 需要编码处理
    //1.2 二进制文件 不需要编码处理   $.ajax();

    $.ajax({
        type:"post",
        url:"prod/upload",
        data:formData,
        dataType:"json",
        //二进制文件不能处理前台页面
        processData:false,
        contentType:false,
        success:function (response){
            let {data}=response;
            $("#uploadImg").prop("src",data);
            $("#uploadMsg").text("文件上传成功").css("color","red");
            $("#avatar").val(data);
        }
    })
})
//新增和修改
$("#addOrUpdateBtn").click(function (){
    //请求服务器路径  将商品数据提交 服务器  保存到数据库表里面
    let info=$("#addForm").serialize();
    $.post("prod/addOrUpdate",info,function (response){
        let {code}=response;
        if (code==1000){

            //关闭当前小窗口
            xadmin.close();
            //刷出父窗口
            xadmin.father_reload();
        }
    },"json")
})


