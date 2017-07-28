            var winWidth = 0;
            var winHeight = 0;
            var s1Height = 0;
            var s2Height = 0;
            var s3Height = 0;
        
            function findDimensions() //函数：获取尺寸
            {
            //获取窗口宽度
            if (window.innerWidth)
            winWidth = window.innerWidth;
            else if ((document.body) && (document.body.clientWidth))
            winWidth = document.body.clientWidth;
            //获取窗口高度
            if (window.innerHeight)
            winHeight = window.innerHeight;
            else if ((document.body) && (document.body.clientHeight))
            winHeight = document.body.clientHeight;
            if (document.documentElement  && document.documentElement.clientHeight && document.documentElement.clientWidth)
            {
            winHeight = document.documentElement.clientHeight;
            winWidth = document.documentElement.clientWidth;
            }
            var mydiv=document.getElementById("mydiv");
            var content=document.getElementById("content");
            if(mydiv != null && content != null){
            mydiv.style.height=winHeight-88+"px";
            content.style.height=winHeight-88-30+"px";
            }
     var s1 = document.getElementById("table1");
	if (s1 != null) {
		s1Height = s1.offsetHeight;
		// alert(s1Height);
	}
	var s2 = document.getElementById("table2");
	// window.alert(s2);
	if (s2 != null) {
		s2Height = s2.offsetHeight;
	}
	var s3 = document.getElementById("table3");
	if (s3 != null) {
		s2.style.height = s2Height + 28 + "px";
		s3.style.height = winHeight  - s1Height - s2Height - 40-30 + "px";
	}
	if (s3 == null & s2 != null & s1 != null) {
		s1Height = s1.offsetHeight;
		s2.style.height = winHeight  - s1Height - 40 -30 + "px";
     //alert(s2.style.height);
	}
            }
            
            findDimensions();
            //调用函数，获取数值
            window.onresize=findDimensions;