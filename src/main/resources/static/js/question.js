// index页面问题发布的验证
function check() {
    // debugger;
    //提交的时候检测文本框
    if ($("#title").val() == "" || $("#description").val() == "" || $("#tag").val() == "") {
        $("#error").text("Something incomplete Here,check it out!");
        $("#error").css("display", "");
        return false;
    }else{
        var tagValid = tag_valid();
        console.log("服务器端返回的tags:"+tagValid);
        if(tagValid == "" || tagValid == null){
            return true;
        }else{
            $("#error").css("display","block");
            $("#error").text("非法标签 '"+tagValid+"' ,只能选择我们提供的标签~~");
            return false;
        }
    }

}

// 验证标签是否含有提示中不存在的
function tag_valid(){
    var tags = $("#tag").val().replace("，",",");
    console.log("客户端的tags："+tags);
    var tagValid;
    $.ajax({
        type: "POST",
        async: false, // 默认为true，如果要有返回值，设为false
        cache: false,
        url: "/tagValid.action",
        data: {
            "tags":tags,
        },
        success: function (data, status) {
            // alert(data);
            if (status == "success") {
                tagValid = data;
            }
             else {
                alert("发生了某种未知的错误~~~");
            }
        },
        error: function () {
            alert("出错了~~~");
        }
    });
    return tagValid;

}