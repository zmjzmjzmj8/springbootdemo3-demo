//获取非行间样式的方法
			function getCss(obj, arr) {
				if(obj.currentStyle) {
					return obj.currentStyle[arr];
				} else {
					return getComputedStyle(obj, false)[arr];
				}
			};
			//js动画方法
			function animate(obj, json, fn) {
				clearInterval(obj.timer);
				obj.timer = setInterval(function() {
					var off = true;
					for(var arr in json) { //left:200 top:200   arr=>left
						var cur = 0;
						if(arr == 'opacity') {
							cur = parseFloat(getCss(obj, arr)) * 100; //获取值
						} else {
							cur = parseInt(getCss(obj, arr));
						};

						var speed = (json[arr] - cur) / 8;
						speed = speed > 0 ? Math.ceil(speed) : Math.floor(speed);

						if(cur != json[arr]) {
							off = false;
						};
						if(arr == 'opacity') {
							cur += speed;
							obj.style[arr] = cur / 100;
							obj.style.filter = 'alpha(opacity:' + cur + ')';

						} else {
							obj.style[arr] = cur + speed + 'px';
						}
					}
					if(off) {
						clearInterval(obj.timer);
						if(fn) {
							fn.call(obj)
						}
					}

				}, 30)
			}
			
//获取class的方法  ie8不兼容getelemnetclassName所以要用这个代替
function getClass(parent,name){				//例 getclass(box1,'box2')||('','box2')
	var oParent = parent || document;
	var aEles = oParent.getElementsByTagName("*");
	var result=[];
	for(var i=0; i<aEles.length;i++){
		var arr= aEles[i].className.split(' ');
		for(var j=0; j<arr.length;j++){
			if(arr[j]==name){
				result.push(aEles[i])
			}						
		}
	};
	return result;
};
