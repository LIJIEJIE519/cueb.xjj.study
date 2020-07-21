<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
    <html>
        <head>
            <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
            <meta name="viewport" content="width=device-width, initial-scale=1"/>
            <title>贵州方言乡情</title>
            <link href="css/bootstrap.min.css" rel="stylesheet"/>
            <link href="css/main01.css" rel="stylesheet"/>

            <script type="text/javascript" src="js/jquery-1.12.4.js"/>
            <script type="text/javascript" src="js/bootstrap.min.js"/>
            <script type="text/javascript" src="js/main01.js"/>
            <script type="text/javascript" src="js/BDmap.js"/>
            <script type="text/javascript" src="http://api.map.baidu.com/api?key=&amp;v=1.1&amp;services=true"/>
        </head>
        <body>
            <!-- 导航栏 -->
            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class="container">
                    <div class="navbar-header">
                        <!-- 折叠之后的按钮 -->
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only"></span>
                            <!-- 三横 -->
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>

                        <a><img class="logo img-circle">
                            <xsl:attribute name="src"><xsl:value-of select="方言/头像"/></xsl:attribute>
                        </img>
                        </a>
                        <a class="navbar-brand" href="#"><xsl:value-of select="方言/昵称"/></a>
                    </div>

                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="#Overview">综述<span class="sr-only">(current)</span></a></li>
                            <li><a href="#BJ">大北京</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" >特点
                                </a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a href="#food">美食</a></li>
                                    <li><a href="#more">日常对话</a></li>
                                </ul>
                            </li>
                            <li><a href="#about">关于</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!-- 图片滑动 -->
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- 小点导航 -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="4"></li>
                </ol>
                <!-- 轮播主件图片的内容 -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img src="images/BC01.jpg" alt="1 slide"/>
                        <div class="carousel-caption">
                            欢迎来到贵州
                        </div>
                    </div>
                    <div class="item">
                        <img src="images/title02.jpg" alt="..."/>
                        <div class="carousel-caption">
                            百里杜鹃
                        </div>
                    </div>
                    <div class="item">
                        <img src="images/title03.jpg" alt="..."/>
                        <div class="carousel-caption">
                            黄果树瀑布
                        </div>
                    </div>
                    <div class="item">
                        <img src="images/title04.jpg" alt="..."/>
                        <div class="carousel-caption">
                            织金洞
                        </div>
                    </div>
                    <div class="item">
                        <img src="images/title05.jpg" alt="..."/>
                        <div class="carousel-caption">
                            国酒茅台
                        </div>
                    </div>
                </div>
                <!-- 轮播主件向左向右的内容 -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev"/>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next"/>
            </div>
            <!-- 综述 -->
            <div class="main_desc" id="Overview"><h2 class="main_desc_text">我嘞家乡--贵州</h2></div>
            <div class="main">
                <div class="main_all">
                    <hr></hr>
                    <div class="main_left">
                        <h2>综述:</h2>
                        <p class="main_text"><big><b>贵州省</b></big>，简称“黔”或“贵”，地处中国西南腹地，与重庆、四川、湖南、云南、广西接壤，
                            是西南交通枢纽。世界知名山地旅游目的地和山地旅游大省，全国首个国家级大数据综合试验区，
                            国家生态文明试验区，内陆开放型经济试验区。
                            辖贵阳市、遵义市、安顺市、毕节市、铜仁市、六盘水市、黔西南布依族苗族自治州、
                            黔东南苗族侗族自治州、黔南布依族苗族自治州。</p>
                        <br></br>
                        <h3>详细地址:</h3>
                        <table class="main_address">
                            <tr bgcolor="#CDC1C5">
                                <th>省</th>
                                <th>市</th>
                                <th>区</th>
                                <th>乡镇</th>
                                <th>村街道</th>
                                <th>经度</th>
                                <th>纬度</th>
                            </tr>
                            <xsl:for-each select="方言/地理位置">
                                <tr>
                                    <td><xsl:value-of select="省"></xsl:value-of></td>
                                    <td><xsl:value-of select="市"></xsl:value-of></td>
                                    <td><xsl:value-of select="区"></xsl:value-of></td>
                                    <td><xsl:value-of select="乡镇"></xsl:value-of></td>
                                    <td><xsl:value-of select="村街道"></xsl:value-of></td>
                                    <td><xsl:value-of select="经度"></xsl:value-of></td>
                                    <td><xsl:value-of select="纬度"></xsl:value-of></td>
                                </tr>
                            </xsl:for-each>
                        </table>
                    </div>
                    <table class="line" border="1">
                        <td class="line"></td>
                    </table>
                    <!--百度地图容器-->
                    <div id="dituContent"/>
                </div>

            </div>
            <!-- 北京生活 嵌套-->
            <xsl:apply-templates select="方言"/>
            <!-- 食物蒙版 -->
            <div class="mask">
                <img class="food img-circle"><xsl:attribute name="src"><xsl:value-of select="方言/词条[@class='food01']/插图"/></xsl:attribute></img>
                <img class="food img-circle"><xsl:attribute name="src"><xsl:value-of select="方言/词条[@class='food02']/插图"/></xsl:attribute></img>
            </div>
            <!-- 关于 -->
            <div class="about">
                <p style="color:#CCC">适用浏览器：IE.</p>
                <a href="http://cueb.edu.cn/">来源：信息学院</a><br/>
                <a href="#" target="_blank">by: 小杰杰</a>
            </div>
        </body>

    </html>
</xsl:template>

<xsl:template match="方言">
    <!-- 北京生活 -->
    <div class="life_BJ" id="BJ">
        <div class="BJ_title"><h2 class="title_BJtext">一个贵州人嘞北京</h2></div>
        <!-- 对话一 -->
        <div class="topic_one">
            <h3>寝室日常:</h3>
            <!-- 我的方言 -->
            <div class="one_left">
                <img class="left_img" src="images/me/01.jpg"/>
                <div class="left_text">
                    <i class="dialect"><xsl:if test="词条[@class='01']"><xsl:value-of select="词条/例句"/></xsl:if></i>
                    <br/><br/>
                    <i><xsl:if test="词条[@class='01']"><xsl:value-of select="词条/词义"/>。。。</xsl:if></i>
                </div>

                <img class="left_img" src="images/me/01.jpg"/>
                <div class="left_text">
                    <i><xsl:if test="词条[@class='01']"><xsl:value-of select="词条/音调"/>，</xsl:if></i>
                    <br/>
                    <i><xsl:if test="词条[@class='01']"><xsl:value-of select="词条/英语词义"/>.</xsl:if></i>
                    <br/>
                    <i><xsl:if test="词条[@class='01']"><xsl:value-of select="词条/拓展/@类型"/> :</xsl:if></i>
                    <i class="dialect"><xsl:if test="词条[@class='01']"><xsl:value-of select="词条/拓展/词汇"/></xsl:if></i>
                </div>

                <img class="left_img" src="images/me/02.jpg"/>
                <div class="left_text">
                    喊~<i class="dialect">
                    <xsl:value-of select="词条[@class='02']/词汇"/>
                    <xsl:value-of select="词条[@class='03']/词汇"/>
                </i>都的同学过来，趁我得闲好好和你们普普
                    <!--<i>喊阿个卡卡果果都的同学过来，趁我得闲好好和你们摆摆</i>-->
                    <br/>
                </div>

                <img class="left_img" src="images/me/06.jpg"/>
                <div class="left_text">
                    <i class="dialect">
                        <xsl:value-of select="词条[@class='02']/词汇"/></i>在贵州话是指<xsl:value-of select="词条[@class='02']/词义"/>，
                    发音为<xsl:value-of select="词条[@class='02']/音调"/>，<br/>
                    <i class="dialect"><xsl:value-of select="词条[@class='03']/词汇"/></i>
                    是<xsl:value-of select="词条[@class='03']/词义"/>的意思，
                    <xsl:value-of select="词条[@class='03']/拓展/备注"/>
                    <br/>
                </div>

                <img class="left_img" src="images/me/08.jpg"/>
                <div class="left_text">
                    <i class="dialect">
                        <xsl:value-of select="词条[@class='04']/拓展/例句"/>
                        <br/><br/>
                    </i>
                    <xsl:value-of select="词条[@class='04']/英语词义"/>
                    <br/>
                </div>
            </div>

            <!-- 对方方言 -->
            <div class="one_right">
                <div class="right_text">
                    <i>恩恩？</i>
                    <br/><br/>
                    <i>噢噢...怎么读？</i>
                </div>
                <img class="right_img" src="images/them/T101.jpg"/>

                <div class="right_text">
                    <i>可以可以，还有没好玩的？</i>
                </div>
                <img class="right_img" src="images/them/T102.jpg"/>

                <div class="right_text">
                    <i>哈哈哈？？</i>
                </div>
                <img class="right_img" src="images/them/T103.jpg"/>

                <div class="right_text">
                    <i class="dialect"><xsl:value-of select="词条[@class='03']/音调"/>？？</i>
                </div>
                <img class="right_img" src="images/them/T104.jpg"/>
                <img style="margin-top:-30px;margin-left:5px" src="images/them/T105.jpg">走走..出去普。</img>

            </div>

        </div>
        <!-- 对话二 -->
        <div class="topic_two">
            <h3>校园日常:</h3>
            <div class="two_left">
                <img class="left_img" src="images/me/06.jpg"/>
                <div class="left_text">
                    <i class="dialect"><xsl:value-of select="词条[@class='06']/例句"/></i>
                    <!--走走走，喊几拉们几个逛该克-->
                </div>

                <img class="left_img" src="images/me/14.jpg"/>
                <div class="left_text">
                    ooemem，叫他们几个逛街...<br/>
                    <i class="dialect"><xsl:value-of select="词条[@class='06']/词汇"/></i>在我们那是指：<xsl:value-of select="词条[@class='06']/词义"/>

                </div>

                <img class="left_img" src="images/me/05.jpg"/>
                <div class="left_text">
                    我们去吃~
                    <i class="dialect"><xsl:value-of select="词条[@class='food02']/词汇"/></i>
                    ，我也想吃
                    <i class="dialect"><xsl:value-of select="词条[@class='food01']/词汇"/>。。。</i>
                </div>

                <img class="left_img" src="images/me/04.jpg"/>
                <div class="left_text">
                    哈哈哈嘎嘎是~
                    <i class="dialect pork"><xsl:value-of select="词条[@class='food01']/词义"/><xsl:value-of select="词条[@class='07']/英语词义"/></i>
                    ，然后活菜是
                    <i class="dialect hot"><xsl:value-of select="词条[@class='food02']/词义"/>了。</i>
                    <br/>悬浮菜名看看...
                    <br/>怎么样？？
                </div>

                <img class="left_img" src="images/me/03.jpg"/>
                <div class="left_text"><br/>
                    <i class="dialect"><xsl:value-of select="词条[@class='07']/例句"/></i>
                </div>

            </div>
            <div class="two_right">
                <div class="right_text">
                    <i>你说啥子呢？</i>
                </div>
                <img class="right_img" src="images/them/T201.jpg"/>

                <div class="right_text">
                    <i>噢噢，去干嘛呢？</i>
                </div>
                <img class="right_img" src="images/them/T202.jpg"/>

                <div class="right_text">
                    <i><xsl:value-of select="词条[@class='food01']/音调"/>？是什么玩意？</i>
                </div>
                <img class="right_img" src="images/them/T203.jpg"/>

                <div class="right_text">
                    不错不错，还有没有更多？
                </div>
                <img class="right_img" src="images/them/T204.jpg"/>

                <img class="right_img" src="images/them/T205.jpg"/>
                <div class="right_text">
                    <i class="dialect"><xsl:value-of select="词条[@class='11']/音调"></xsl:value-of>？？</i>
                    <br/>好吃的。
                </div>
            </div>
        </div>
    </div>
    <!-- 贵州美食 -->
    <div class="food_GZ" id="food">
        <div class="food_title"><h2 class="title_text">贵州美食</h2></div>
        <br/>
        <div class="food_left">
            <span class="food_one">
                <img class="foodQ" src="images/me/Ah05.png"></img>
                <div class="food_text">
                    第一道美食：
                    <table class="food_table">
                        <tr bgcolor="">
                            <th>菜名</th>
                            <th>读音</th>
                            <th>词义</th>
                        </tr>
                        <tr>
                            <td><xsl:value-of select="词条[@class='food03']/词汇"></xsl:value-of></td>
                            <td><xsl:value-of select="词条[@class='food03']/音调"></xsl:value-of></td>
                            <td><xsl:value-of select="词条[@class='food03']/词义"></xsl:value-of></td>
                        </tr>
                    </table>
                </div>
                <div class="food_img_desc">
                    <img class="img-circle food_one_img" style="width:90%;height:80%;display:none;">
                        <xsl:attribute name="src"><xsl:value-of select="词条[@class='food03']/插图"/></xsl:attribute>
                    </img>
                    <div class="food_desc">
                        <xsl:value-of select="词条[@class='food03']/释义"></xsl:value-of>
                    </div>
                </div>
            </span>
            <span class="food_two">
                <img class="foodQ" src="images/me/Ah01.png"></img>
                <div class="food_text">
                    第二道美食：
                    <table class="food_table">
                        <tr bgcolor="">
                            <th>菜名</th>
                            <th>读音</th>
                            <th>词义</th>
                        </tr>
                        <tr>
                            <td><xsl:value-of select="词条[@class='food04']/词汇"></xsl:value-of></td>
                            <td><xsl:value-of select="词条[@class='food04']/音调"></xsl:value-of></td>
                            <td><xsl:value-of select="词条[@class='food04']/词义"></xsl:value-of></td>
                        </tr>
                    </table>
                </div>
                <div class="food_img_desc">
                    <img class="img-circle food_two_img" style="width:83%;height:42%;margin-left:10px;display:none;">
                        <xsl:attribute name="src"><xsl:value-of select="词条[@class='food04']/插图"/></xsl:attribute>
                    </img>
                    <div class="food_desc">
                        <xsl:value-of select="词条[@class='food04']/释义"></xsl:value-of>
                    </div>
                </div>
            </span>
        </div>
        <div class="food_right">
            <span class="food_three">
                <div class="food_textR">
                    第三道美食：
                    <table class="food_table">
                        <tr bgcolor="">
                            <th>菜名</th>
                            <th>读音</th>
                            <th>词义</th>
                        </tr>
                        <tr>
                            <td><xsl:value-of select="词条[@class='food05']/词汇"></xsl:value-of></td>
                            <td><xsl:value-of select="词条[@class='food05']/音调"></xsl:value-of></td>
                            <td><xsl:value-of select="词条[@class='food05']/词义"></xsl:value-of></td>
                        </tr>
                    </table>
                </div>
                <img class="foodQ" src="images/me/Ah03.png"></img>
                <div class="food_img_desc">
                    <img class="img-circle food_three_img" style="width:80%;height:42%;display:none;">
                        <xsl:attribute name="src"><xsl:value-of select="词条[@class='food05']/插图"/></xsl:attribute>
                    </img>
                    <div class="food_desc">
                        <xsl:value-of select="词条[@class='food05']/释义"></xsl:value-of>
                    </div>
                </div>
            </span>
            <span class="food_four">
                <div class="food_textR">
                    第四道美食：
                    <table class="food_table">
                        <tr bgcolor="">
                            <th>菜名</th>
                            <th>读音</th>
                            <th>词义</th>
                        </tr>
                        <tr>
                            <td><xsl:value-of select="词条[@class='food06']/词汇"></xsl:value-of></td>
                            <td><xsl:value-of select="词条[@class='food06']/音调"></xsl:value-of></td>
                            <td><xsl:value-of select="词条[@class='food06']/词义"></xsl:value-of></td>
                        </tr>
                    </table>
                </div>
                <img class="foodQ" src="images/me/Ah04.png"/>
                <div class="food_img_desc">
                    <img class="img-circle food_four_img" style="width:80%;height:42%;display:none;">
                        <xsl:attribute name="src"><xsl:value-of select="词条[@class='food06']/插图"/></xsl:attribute>
                    </img>
                    <div class="food_desc">
                        <xsl:value-of select="词条[@class='food06']/释义"></xsl:value-of>
                    </div>
                </div>
            </span>
        </div>
    </div>
    <!-- 更多信息 -->
    <div class="life_more" id="more">
        <div class="life_title"><h2 class="life_title_text">日常嗨</h2></div>
        <div class="more">
            <table class="table table-hover more_left">
                <tr class="more_tname active">
                    <th>方言</th>
                    <th>读音</th>
                    <th>词义</th>
                </tr>
                <tbody>
                    <tr class="">
                        <td><xsl:value-of select="词条[@class='d01']/词汇"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d01']/音调"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d01']/词义"></xsl:value-of></td>
                    </tr>
                    <tr class="">
                        <td><xsl:value-of select="词条[@class='d02']/词汇"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d02']/音调"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d02']/词义"></xsl:value-of></td>
                    </tr>
                    <tr class="">
                        <td><xsl:value-of select="词条[@class='d03']/词汇"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d03']/音调"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d03']/词义"></xsl:value-of></td>
                    </tr>
                    <tr class="">
                        <td><xsl:value-of select="词条[@class='d04']/词汇"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d04']/音调"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d04']/词义"></xsl:value-of></td>
                    </tr>
                    <tr class="">
                        <td><xsl:value-of select="词条[@class='d05']/词汇"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d05']/音调"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d05']/词义"></xsl:value-of></td>
                    </tr>
                </tbody>
            </table>
            <table class="table table-hover more_right">
                <tr class="more_tname active">
                    <th>方言</th>
                    <th>读音</th>
                    <th>词义</th>
                </tr>
                <tbody>
                    <tr class="">
                        <td><xsl:value-of select="词条[@class='d06']/词汇"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d06']/音调"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d06']/词义"></xsl:value-of></td>
                    </tr>
                    <tr class="">
                        <td><xsl:value-of select="词条[@class='d07']/词汇"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d07']/音调"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d07']/词义"></xsl:value-of></td>
                    </tr>
                    <tr class="">
                        <td><xsl:value-of select="词条[@class='d08']/词汇"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d08']/音调"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d08']/词义"></xsl:value-of></td>
                    </tr>
                    <tr class="">
                        <td><xsl:value-of select="词条[@class='d09']/词汇"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d09']/音调"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d09']/词义"></xsl:value-of></td>
                    </tr>
                    <tr class="">
                        <td><xsl:value-of select="词条[@class='d10']/词汇"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d10']/音调"></xsl:value-of></td>
                        <td><xsl:value-of select="词条[@class='d10']/词义"></xsl:value-of></td>
                    </tr>
                </tbody>
            </table>
            <div class="more_center">
                <div class="row">
                    <div class="col-sm-6 col-md-12">
                        <div class="thumbnail">
                            <img src="images/me/Ah02.png" class="more_me"/>
                            <div class="caption">
                                <p class="more_me_desc">怎么样？看起来是不是好吃又好玩？很硬火？<br/><br/>
                                    再来一波好玩的吧！
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 更多好玩的 -->
        <div class="more_desc">
            <img src="images/me/18.jpg" class="more_desc_img img-circle"/>
            <div>
                <ul class="pics_3d">
                    <li>
                        <div class="more_desc_right">
                            <div class="left_desc">
                                <img class="img-thumbnail" >
                                    <xsl:attribute name="src"><xsl:value-of select="词条[@class='d02']/插图"/></xsl:attribute>
                                </img>
                            </div>
                            <div class="right_desc">
                                <h3>词义：<xsl:value-of select="词条[@class='d02']/词义"/></h3>
                                <p class="more_me_desc">
                                    词性：<xsl:value-of select="词条[@class='d02']/词性"/><br/>
                                    例句：<xsl:value-of select="词条[@class='d02']/例句"/><br/>
                                </p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="more_desc_right">
                            <div class="left_desc">
                                <img class="img-thumbnail" >
                                    <xsl:attribute name="src"><xsl:value-of select="词条[@class='d01']/插图"/></xsl:attribute>
                                </img>
                            </div>
                            <div class="right_desc">
                                <h3>词义：<xsl:value-of select="词条[@class='d01']/词义"/></h3>
                                <p class="more_me_desc">
                                    词性：<xsl:value-of select="词条[@class='d01']/词性"/><br/>
                                    例句：<xsl:value-of select="词条[@class='d01']/例句"/><br/>
                                </p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="more_desc_right">
                            <div class="left_desc">
                                <img class="img-thumbnail" >
                                    <xsl:attribute name="src"><xsl:value-of select="词条[@class='d03']/插图"/></xsl:attribute>
                                </img>
                            </div>
                            <div class="right_desc">
                                <h3>词义：<xsl:value-of select="词条[@class='d03']/词义"/></h3>
                                <p class="more_me_desc">
                                    词性：<xsl:value-of select="词条[@class='d03']/词性"/><br/>
                                    例句：<xsl:value-of select="词条[@class='d03']/例句"/><br/>
                                </p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="more_desc_right">
                            <div class="left_desc">
                                <img class="img-thumbnail" >
                                    <xsl:attribute name="src"><xsl:value-of select="词条[@class='d04']/插图"/></xsl:attribute>
                                </img>
                            </div>
                            <div class="right_desc">
                                <h3>词义：<xsl:value-of select="词条[@class='d04']/词义"/></h3>
                                <p class="more_me_desc">
                                    词性：<xsl:value-of select="词条[@class='d04']/词性"/><br/>
                                    例句：<xsl:value-of select="词条[@class='d04']/例句"/><br/>
                                </p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="more_desc_right">
                            <div class="left_desc">
                                <img class="img-thumbnail" >
                                    <xsl:attribute name="src"><xsl:value-of select="词条[@class='d05']/插图"/></xsl:attribute>
                                </img>
                            </div>
                            <div class="right_desc">
                                <h3>词义：<xsl:value-of select="词条[@class='d05']/词义"/></h3>
                                <p class="more_me_desc">
                                    词性：<xsl:value-of select="词条[@class='d05']/词性"/><br/>
                                    例句：<xsl:value-of select="词条[@class='d05']/例句"/><br/>
                                </p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="more_desc_right">
                            <div class="left_desc">
                                <img class="img-thumbnail" >
                                    <xsl:attribute name="src"><xsl:value-of select="词条[@class='d06']/插图"/></xsl:attribute>
                                </img>
                            </div>
                            <div class="right_desc">
                                <h3>词义：<xsl:value-of select="词条[@class='d06']/词义"/></h3>
                                <p class="more_me_desc">
                                    词性：<xsl:value-of select="词条[@class='d06']/词性"/><br/>
                                    例句：<xsl:value-of select="词条[@class='d06']/例句"/><br/>
                                </p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="more_desc_right">
                            <div class="left_desc">
                                <img class="img-thumbnail" >
                                    <xsl:attribute name="src"><xsl:value-of select="词条[@class='d07']/插图"/></xsl:attribute>
                                </img>
                            </div>
                            <div class="right_desc">
                                <h3>词义：<xsl:value-of select="词条[@class='d07']/词义"/></h3>
                                <p class="more_me_desc">
                                    词性：<xsl:value-of select="词条[@class='d07']/词性"/><br/>
                                    例句：<xsl:value-of select="词条[@class='d07']/例句"/><br/>
                                </p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="more_desc_right">
                            <div class="left_desc">
                                <img class="img-thumbnail" >
                                    <xsl:attribute name="src"><xsl:value-of select="词条[@class='d08']/插图"/></xsl:attribute>
                                </img>
                            </div>
                            <div class="right_desc">
                                <h3>词义：<xsl:value-of select="词条[@class='d08']/词义"/></h3>
                                <p class="more_me_desc">
                                    词性：<xsl:value-of select="词条[@class='d08']/词性"/><br/>
                                    例句：<xsl:value-of select="词条[@class='d08']/例句"/><br/>
                                </p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="more_desc_right">
                            <div class="left_desc">
                                <img class="img-thumbnail" >
                                    <xsl:attribute name="src"><xsl:value-of select="词条[@class='d09']/插图"/></xsl:attribute>
                                </img>
                            </div>
                            <div class="right_desc">
                                <h3>词义：<xsl:value-of select="词条[@class='d09']/词义"/></h3>
                                <p class="more_me_desc">
                                    词性：<xsl:value-of select="词条[@class='d09']/词性"/><br/>
                                    例句：<xsl:value-of select="词条[@class='d09']/例句"/><br/>
                                </p>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <!--间隔线-->
            <table class="more_desc_line">
                <td class="more_desc_line"></td>
            </table>
        </div>
    </div>
    <div class="music" id="about">
        <h2 class="title_music">无名之辈</h2>
        <!-- 音乐 -->
        <div class="music_lyric">
            秋天的蝉在叫<br/>
            　　我在亭子边<br/>
            　　刚刚下过雨<br/>
            　　我难在们我喝不倒酒<br/>
            　　我扎实嘞舍不得<br/>
            　　斗是们船家喊快点走<br/>
            　　我拉起你嘞手看你眼泪淌出来<br/>
            我曰拉坟讲不出话来<br/>
            　　我难在们我讲不出话来<br/>
            　　我要说走喽<br/>
            　　之千里的烟雾波浪嘞<br/>
            　　啊黑巴巴嘞天好大哦<br/>
            　　拉们讲是那家嘞<br/>
            　　离别是最难在嘞<br/>
            　　更其表讲现在是秋天嘞<br/>
            　　我一哈酒醒来我在哪点<br/>
            　　杨柳嘞岸边风吹一个小月亮嘞<br/>
            　　我一提要克好多年<br/>
            　　漂亮的小姑娘些嘞都不在我边边喽嘞<br/>
            　　斗算之日子些再唱安逸<br/>
            　　我也找不倒人来讲喽<br/>
        </div>
        <div class="music_desc">
            <div class="row">
                <div class="col-sm-2 col-md-12">
                    <div class="thumbnail">
                        <img id="music_img" class="unplay" src="images/music_unplay.png"/>
                        <audio class="music" src="music/尧十三 - 瞎子.mp3"/>
                        <div class="caption">
                            <h3>瞎子：</h3>
                            <p class="desc_music">
                                贵州民谣；尧十三作词曲演唱。<br/>
                                据词人柳永的《雨霖铃》改编的。<br/>
                                电影无名之辈插曲。<br/>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <h2 class="ending">未完待续...</h2>
</xsl:template>
</xsl:stylesheet>