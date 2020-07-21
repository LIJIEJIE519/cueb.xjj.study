$(function () {
    /* 监听嘎嘎图片的显示 */
    $(".mask").hide();
    $(".pork").hover(function(){
        $(".mask").show();
        $(".mask>img:first").show();
        $(".mask>img:last").hide();
    },function () {
        $(".mask").hide();
    });
    /* 监听活菜图片的显示 */
    $(".hot").hover(function(){
        $(".mask").show();
        $(".mask>img:last").show();
        $(".mask>img:first").hide();
    },function () {
        $(".mask").hide();
    });

    /* 图片滑动进入效果 */
    $(window).scroll(function () {
        var offset = $("html,body").scrollTop();
        // 大北京
        if (offset > 600) {
            $(".topic_one").fadeIn(2000);
            $(".topic_two").fadeIn(2000);
        }else {
            $(".topic_one").fadeOut(2000);
            $(".topic_two").fadeOut(2000);
        }
        // 贵州美食
        if (offset > 1000) {
            $(".food_one_img").fadeIn(3000);
            $(".food_two_img").fadeIn(4000);
            $(".food_three_img").fadeIn(5000);
            $(".food_four_img").fadeIn(5000);
        }else {
            $(".food_one_img").fadeOut(3000);
            $(".food_two_img").fadeOut(4000);
            $(".food_three_img").fadeOut(5000);
            $(".food_four_img").fadeOut(5000);
        }
        if (offset > 2000){
            $(".more_left").fadeIn(2000);
            $(".more_right").fadeIn(4000);
        }else {
            $(".more_left").fadeOut(1000);
            $(".more_right").fadeOut(1000);
        }
        //console.log(offset);
    });

    /* logo点击显示大图 */
    $(".logo").hover(function () {
        var $big_logo = $("<div class='big_logo'>" +
                        "        <div class='row'>" +
                            " <div class='col-sm-12 col-md-6'>" +
                            "            <div class='thumbnail'> " +
                            "           <img src='images/logo.jpg'/>" +
                            "            <div class='caption'>" +
                            "            <p>小杰杰</p> " +
                            "           <h3>欢迎来到贵州！！</h3>" +
                            "        </div>" +
                            "        </div> </div> </div> " +
                            "</div>"
                        );
        $("body").append($big_logo);
    },function () {
        $(".big_logo").remove();
    })

    // 日常生活轮播图
    var $imgs = $(".pics_3d > li");
    for (var i=0;i < $imgs.length;i++ ) {
        Nav_3D.add(new Item($imgs[i]));
    }
    //console.log($imgs.length);
    Nav_3D.ready();
    
    // 监听音乐播放
    $("#music_img").click(function () {
        // 获得属性值
        var $value = $(this).attr("class");
        // 获得音乐元素并转换成js对象
        var $audio = $("audio")[0];
        // 如果处于未播放
        if ($value == "unplay"){
            // 则播放
            $(this).attr("class","play");
            $(this).attr("src","images/music_play.png");
            $audio.play();
        }else {
            $(this).attr("class","unplay");
            $(this).attr("src","images/music_unplay.png");
            $audio.pause()
        }
    });
});

    // 定义UI类
Item=function(UI){
    this.angle=0;
    this.UI=UI;
    this.update();
};
Item.ini={
    axle_w:450,     // 里面轮播图的距离
    axle_h:15,     // 高度差距离
    cen_x:500,
    cen_y:260,
};
Item.prototype.update=function(){
    var J=this.UI.style,C=Item.ini,W=C.axle_w,H=C.axle_h,X=C.cen_x,Y=C.cen_y;
    // 翻转角度
    var angle=this.angle/180*Math.PI;
    var left=Math.cos(angle)*W+X;
    var top=Math.sin(angle)*H+Y;
    var A=this.angle>270?this.angle-360:this.angle;
    var size=360-Math.abs(90-A)*3;
    // 改切换图片宽度
    this.UI.width=Math.max(size, 120);
    var opacity=Math.max(10,size-180);
    J.filter='alpha(opacity='+opacity+')';
    J.opacity=opacity/100;
    J.left=(left-this.UI.offsetWidth/2)+'px';
    top=(top-this.UI.offsetHeight)+'px';
    J.top=top;
    //设置优先级
    J.zIndex=parseInt(size);

};

Nav_3D={
    items:[],
    dir:1,
    index:0,
    hover:false,
    add:function(item){
        this.items.push(item);
        item.index=this.items.length-1;
        item.UI.onclick=function (){
            var J=item.angle,M=Nav_3D;
            if(M.uping)return;
            if(J==90){
                return ;
            };
            M.wheel_90(item);
            M.index=item.index;
        };
        item.UI.onmouseover=function (){
            if(item.angle==90){
                Nav_3D.hover=true;
                clearTimeout(Nav_3D.autoTimer);
            };
        };
        item.UI.onmouseout=function (){
            if(item.angle==90){
                Nav_3D.hover=false;
                Nav_3D.auto();
            };
        };
        return this;
    },
    wheel_90:function(hot){
        if(this.uping)return;
        this.uping=true;
        var This=this;
        // 设置滑动时间
        this.timer=setInterval(function (){
            clearTimeout(This.autoTimer);
            var A=hot.angle;
            This.dir=A<270&&A>90?-1:1;
            if(A==90){
                clearInterval(This.timer);
                This.uping=false;
                This.onEnd(hot);
            }
            if(A>270)A-=360;
            var set=Math.ceil(Math.abs((90-A)*0.1));
            for (var i=0;i<This.items.length;i++ ) {
                var J=This.items[i];
                J.angle+= (set*This.dir);
                J.update();
                if(J.angle>360)J.angle-=360;
                if(J.angle<0)J.angle +=360;
            };
        },10);
    },

    ready:function(){
        var J=this.items,step=parseInt(360/J.length);
        for (var i=0;i<J.length;i++) {J[i].angle=i*step+90;}
        this.wheel_90(this.items[0]);
        Nav_3D.prevHot=this.items[0].UI;
        Nav_3D.setHot();
    },

    setHot:function(isHot){
        if(!this.prevHot)return;
        with(this.prevHot.style){
            borderColor=isHot!==false?'#CC0000':'#00CCFF';
            cursor=isHot!==false?'default':"pointer";
        };
        return this;
    },

    auto:function(){
        this.index--;
        if(this.index<0)this.index=this.items.length-1;
        var J=this.items[this.index];
        this.setHot(false).prevHot=J.UI;
        this.setHot();
        this.wheel_90(J);
    },

    onEnd:function(hot){
        // 悬浮轮播时间
        if(this.hover){
            return setTimeout(function(){Nav_3D.onEnd();},1000);
        }
        // 自动轮播时间
        this.autoTimer=setTimeout(function(){Nav_3D.auto();},4000);
    }
};

