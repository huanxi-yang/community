// 封装评论和二级评论
function commentTarget(parentId, type, content){
    if (!content){
        alert("别这样,多少说点儿~~");
        return ;
    }
    // debugger;
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": parentId,
            "content": content,
            "type": type
        }),
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            if(response.code == 200){
                // 隐藏回复的输入框,不会刷新页面，但是刚才评论的内容需要刷新才会显示出来
                // $("#comment_div").hide();
                // 重新刷新页面，评论会直接显示出来，但是会刷新页面
                window.location.reload();
            }else{
                if(response.code == 2004){
                    var b = confirm("未登录~~");
                    //打开新页面
                    window.open("https://github.com/login/oauth/authorize?client_id=724df6f6252b3c341a39&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                    window.localStorage.setItem("localS","true");
                    /**
                     * 我想实现的就是不用刷新页面，评论的内容也可以实时的显示在页面
                     * 看到说可以 用append方法追加评论，但是不会
                     */
                }
            }
        }
    });
}

// 发表评论的js
function post(){
    var parentId = $("#questionId").val();
    var content = $("#comment").val();
    commentTarget(parentId, 1, content);
}

// 发表二级评论
function comment(e){
    var commentId = e.getAttribute("data-id");
    var content = $("#replay-"+commentId).val();
    commentTarget(commentId,2, content);
}

// 加载二级评论框
function collageChange(e) {
    var data_id = e.getAttribute("data-id");
    // 第一种方式
    // var comments = $("#comment-"+data_id);
    // comments.toggleClass("in");

    // 第二种方式
    // 通过class属性判断二级评论是否展开，从而确定展开还是关闭
    if ($("#comment-" + data_id).hasClass("in")) {
        // 折叠评论
        $(e).removeClass("active");
        $("#comment-" + data_id).removeClass("in");

    } else {
        // 展开评论
        var subCommentsContrainer = $("#comment-"+data_id);
        if (subCommentsContrainer.children().length > 1){
            $(e).addClass("active");
            $("#comment-" + data_id).addClass("in");
        }else{
            $.getJSON("/comment/"+data_id,function(data){
                console.log(data);
                $.each(data.data.reverse(), function (index, comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-circle",
                        "src": comment.user.avatarUrl,
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body",
                        "style": "padding-top: 5px",
                    }).append(
                        $("<h6/>", {
                            html: comment.user.name
                        })
                    ).append(
                        $("<div/>", {
                            html: comment.content,
                        })
                    ).append(
                        $("<div/>",{
                            "class": "topic-info",
                            "style": "margin-top: 10px",
                        }).append(
                            $("<span/>", {
                                "class": "pull-right",
                                html: moment(comment.gmtCreate).format("MM Do YYYY")
                            })
                        )
                    );

                    var mediaElement = $("<div/>", {
                        "class": "media",
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);

                    var c = $("<div/>", {
                        "div": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                        "style": "margin-bottom: 15px; border-bottom: 1px solid #eee; padding-bottom: 5px",
                        // html: comment.content,
                    }).append(mediaElement);
                    subCommentsContrainer.prepend(c);
                });

                $(e).addClass("active");
                $("#comment-" + data_id).addClass("in");
            });
        }
    }
}

// 选择标签显示到标签输入框
function show(e){
    var value = e.getAttribute("data-tag");
    var preTag = $("#tag").val();
    if (preTag.indexOf(value) == -1){
        if (preTag){
            $("#tag").val(preTag+","+value);
        }else{
            $("#tag").val(value);
        }
    }
}

// 控制标签页的显示和关闭
// function showTag(){
$(document).ready(function(){

    $("#tag").focus(function(){
        $("#tag-show").css("display","block");
    });

});

// }