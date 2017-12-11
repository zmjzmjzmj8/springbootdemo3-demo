window.onload = function() {
	var imglist = document.getElementById("imglist")
	var imglist_li = imglist.children
	for(var i = 0; i < imglist_li.length; i++) {
		imglist_li[i].children[0].style.height = document.documentElement.clientHeight * 1.2 + 'px' //图片的高度
		imglist_li[i].children[0].style.width = document.documentElement.clientWidth + 'px' //图片的宽度
		imglist_li[i].style.top = -70 * 1.2 + 'px'
		imglist.style.width = parseInt(imglist_li[0].children[0].style.width) * imglist_li.length + 'px' //ul的宽度    
		imglist_li[0].style.display = 'block'
	}
	
//	function Init(id){
//	 this.imglist = document.getElementById(id)
//	 this.imglist_li = imglist.children
//	}
//	
//	Init.prototype.init = function(){
//		for(var i = 0; i < this.imglist_li.length; i++) {
//		this.imglist_li[i].children[0].style.height = document.documentElement.clientHeight * 1.2 + 'px' //图片的高度
//		this.imglist_li[i].children[0].style.width = document.documentElement.clientWidth + 'px' //图片的宽度
//		this.imglist_li[i].style.top = -70 * 1.2 + 'px'
//		this.imglist_li[0].style.display = 'block'
//	}
//	}
//	var init = new Init('imglist')
//	init.init()

	window.onresize = function() {
		var rel_width = document.documentElement.clientWidth;
		var rel_height = document.documentElement.clientHeight;
		if(rel_width > 1000 && rel_height > 500) {
			for(var i = 0; i < imglist_li.length; i++) {
				imglist_li[i].children[0].style.height = rel_height * 1.2 + 'px' //图片的高度
				imglist_li[i].children[0].style.width = rel_width + 'px' //图片的宽度   
			}
		}
	}
	
	
	//轮播
	var num = 1
	function show() {
		for(var i = 0; i < imglist_li.length; i++) {
			imglist_li[i].style.opacity = 0
			imglist_li[i].id = ''
		}
		imglist_li[num].style.opacity = 1
		imglist_li[num].id = 'move'
		num++
		if(num == imglist_li.length) {
			num = 0
		}
	}
	var timer = null;
	timer = setInterval(function() {
		show()
	}, 5000)
	
	
	
	
	//放大
	var x, y;

	//当需求为获得的坐标值相对于某一对象时，用：
	function positionObj(event, id) {
		//获得对象相对于页面的横坐标值；id为对象的id
		var thisX = document.getElementById(id).offsetLeft;

		//获得对象相对于页面的纵坐标值；
		var thisY = document.getElementById(id).offsetTop;

		//获得页面滚动的距离；
		//注：document.documentElement.scrollTop为支持非谷歌内核；document.body.scrollTop为谷歌内核
		var thisScrollTop = document.documentElement.scrollTop + document.body.scrollTop;

		event = event || window.event;

		//获得相对于对象定位的横标值 = 鼠标当前相对页面的横坐标值 - 对象横坐标值；
		x = (event.clientX - thisX - 760) / 49.999;

		//获得相对于对象定位的纵标值 = 鼠标当前相对页面的纵坐标值 - 对象纵坐标值 + 滚动条滚动的高度；
		y = (event.clientY - thisY + thisScrollTop - 280) / 49.999;

	}

	//应用：

	document.onmousemove = function(event) {
		positionObj(event, 'move');
		var a = document.getElementById("move");
		a.style.transform = "matrix(" + 1.05 + "," + 0 + "," + 0 + "," + 1.05 + "," + x.toFixed(5) + "," + y.toFixed(5) + ")";
		a.style.transaction = "all 500ms linear";

	}
	//清空设置
	document.onmouseout = function(event) {
		var a = document.getElementById("move");
		a.style.transform = "matrix(1,0,0,1,0,0)";
		a.style.transition = "all 500ms linear";
	}
	
	
	
	//切换
	var login_list = document.getElementById('login-list')
	var register_tit = document.getElementById('register-tit')
	var login_tit = document.getElementById('login-tit')
	register_tit.onclick = function() {
		login_list.style.marginTop = '-300px'
	}
	login_tit.onclick = function() {
		login_list.style.marginTop = '0'
	}
	
	
	//记住我
	$('#rememberme').on('click', function(){
//		console.log($('#rememberme').is(':checked'))
		var remember_me = $('#rememberme').is(':checked');
		localStorage.setItem("remember_me", remember_me);
	})
	var remember_storage = localStorage.getItem('remember_me');
	if(remember_storage === 'true'){
		$("#rememberme").prop("checked",true);
	}else{
		$("#rememberme").prop("checked",false);
	}

    $("#logincheck").click(function(){
        $.ajax({
            type: "POST",
            url: "/login",
            data: "",
            success: function(data){
                $('#login-req').html(data)
                if(data==null){
                    return false
                }else{
                    alert(data)
                }
            }
        });

    })

    $('#regform').submit(function(e) {
        if($('#regpwd').val()!=$('#regpwd2').val()){
            alert("两次密码不一致");
            return false;
        }else {
            $.ajax({
                type:'POST',
                url:'register',
                data:$('#regform').serialize(),
                success:function(data){
                    alert(data);
                    if(data=="成功")
                        window.location.reload();
                },
                error:function()
                {
                    alert("服务器错误");
                    window.location.href = "error";
                }
            });
        }
        e.preventDefault();
    });


//	$("#logincheck").click(function(){
//		$.ajax({
//             type: "POST",
//             url: "",
//             data: "",
//             success: function(data){
//                    $('#login-req').html(data)
//                    if(data==){
//                    	return false
//                    }else{
//                    	return true
//                    }
//                }
//          });
//	})
//	
//	$("#regcheck").click(function(){
//		$.ajax({
//             type: "POST",
//             url: "",
//             data: "",
//             success: function(data){
//                   $('#register-req').html(data)
//                    if(data==){
//                    	return false
//                    }else{
//                    	return true
//                    }
//                }
//          });
//	})
}