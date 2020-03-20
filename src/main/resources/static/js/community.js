/**
 * 提交回复问题
 */
function post() {
    var question_id = $("#question_id").val();
    //console.log(question_id);
    var content = $("#comment_content").val();
    //console.log(content);
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": question_id,
            "type": 1,
            "content": content
        }),
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                alert(data.message);
                window.location.reload();
                $("#comment_section").hide();
            } else if (data.code == 400) {
                alert(data.message);
            } else if (data.code == 2002) {
                var isAccept = confirm(data.message);
                if (isAccept) {
                    window.open("https://github.com/login/oauth/authorize?client_id=462469297c326eb4020a&redirect_uri=http://localhost:8888/callback&scope=user&state=1");
                    window.localStorage.setItem("closable", true);
                }
            } else if (data.code == 2007) {
                alert(data.message);
            }
        }

    });
}
/*
*
* 展开二级评论
* */
function collapseComment(e) {
    var id = e.getAttribute("data-id");
    //console.log(id+" id");
    var comments = $("#comment-" + id);
    //获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
        $("p").empty();
    } else {

        //将后端拿到的二级评论的数据写到页面上
        $.getJSON("/getsubcomment/"+id,"",function (data) {
            //debugger;
            //console.log(data);
            //拼接到页面上
            $.each(data.comments,function (index,item) {
                var mediaImg = $("<div class='media-left'>" +
                    "<a href='#'><img src='"+item.avatarUrl+"' class='media-object img-rounded'></a>" +
                    "</div>");
                var mediaBody = $("<div class='media-body'>" +
                    "<h5 class='media-heading'><span>'"+item.commentName+"'</span></h5>" +
                    "<div><div id='"+item.id+"'>'"+item.comment.content+"'</div></div>" +
                    "<div class='menu'><span class='pull-right'>'"+moment(item.gmtCreate).format('YYYY-MM-DD')+"'</span></div>" +
                    "</div>");
                var imgAndBody = $("<div class='media comments'></div>").append(mediaImg).append(mediaBody).appendTo($("p"));
            });

        });

        //展开二级评论
        comments.addClass("in");
        //标记二级评论展开状态
        e.setAttribute("data-collapse", "in");
        e.classList.add("active");


    }
}

/**
 * 添加二级评论
 * @param e
 */
function subcomment(e) {
    var id = e.getAttribute("data-id");
    var content = $("#input-"+id).val();
    $.ajax({
        type: "post",
        url: "/subcomment/"+id,
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": id,
            "type": 2,
            "content": content
        }),
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                alert(data.message);
                window.location.reload();
            } else if (data.code == 400) {
                alert(data.message);
            } else if (data.code == 2002) {
                var isAccept = confirm(data.message);
                if (isAccept) {
                    window.open("https://github.com/login/oauth/authorize?client_id=462469297c326eb4020a&redirect_uri=http://localhost:8888/callback&scope=user&state=1");
                    window.localStorage.setItem("closable", true);
                }
            } else if (data.code == 2007) {
                alert(data.message);
            }
        }

    });
}

/**
 * 点击标签输入框,显示标签列表
 */
function showSelectTag() {
    $("#select-tag").show();
}

/**
 * 选中问题标签,写入到标签框
 */
function selectTag(value) {
    var previous = $("#tag").val();

    if (previous.split(',').indexOf(value)==-1){
        if (previous){
            $("#tag").val(previous+","+value);
        }else {
            $("#tag").val(value);
        }
    }
}