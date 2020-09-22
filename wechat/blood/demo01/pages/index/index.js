//index.js
//获取应用实例
const app = getApp()

// // 录音
// const recorderManager = wx.getRecorderManager();
// const options = {
//     duration: 10000,
//     sampleRate: 16000,
//     numberOfChannels: 1,
//     encodeBitRate: 64000,
//     format: 'pcm',
//     frameSize: 50
// }

// 同声传译
var plugin = requirePlugin("WechatSI")
let manager = plugin.getRecordRecognitionManager()

Page({
  data: {
    currentText: "请长按图片说话！",
  },
  main01() {
    // js的方式进行跳转
    wx.navigateTo({
      url: '../main01/mindex01',
    })
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  // 同声传译开始
  touchStart (){
    console.log("touchStart")
    manager.start({
      lang: 'zh_CN',
      duration:60000
    })
  },

  // 同声传译结束
  touchEnd (){
    console.log("touchEnd")
    manager.stop()
  },

  onLoad: function () {
    // manager.start({
    //   lang: 'zh_CN',
    //   duration:60000
    // })
  },

  onReady: function () {
    let that = this

    manager.onStop = res=>{
      let text = res.result
      console.log(res)
      console.log(res.result.indexOf("老中医"))
      if(text==''){
        console.log('用户没有说话')
      }else{
        that.setData({
          currentText: res.result,
        })
        if(res.result.indexOf("老中医") >= 0) {
          wx.navigateTo({
            url: '../main01/mindex01'
          })
        }
      }
    },

    manager.onRecognize = function(res) {
      console.log(res.result)
      console.log(res.result.indexOf("老中医"))
      if(res.result.indexOf("老中医") >= 0) {
        manager.stop
        wx.navigateTo({
          url: '../main01/mindex01'
        })
      }
    }

    manager.onError = function (res) {
      console.log('manager.onError')
      console.log(res)//报错信息打印
    }
  }

  

 
})
