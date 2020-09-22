
// 录音
// const recorderManager = wx.getRecorderManager();
// const options = {
//     duration: 10000,
//     sampleRate: 16000,
//     numberOfChannels: 1,
//     encodeBitRate: 64000,
//     format: 'mp3',
//     frameSize: 50
// }

// 同声传译
var plugin = requirePlugin("WechatSI")
let manager = plugin.getRecordRecognitionManager()

Page({
  data: {
    currentText: "",
  },

  beginRecoder: function() {
    console.log("开始录音1")
    recorderManager.start(options)
  },
  endRecoder: function() {
    console.log("endRecoder")
    recorderManager.onStop((res) => {
      console.log('recorder stop', res)
    })
    recorderManager.stop()
  },
  touchStart (){
    console.log("touchStart")
    manager.start({
      lang: 'zh_CN',
      duration:60000
    })
  },
  touchEnd (){
    console.log("touchEnd")
    manager.stop()
  },

  onLoad: function () {
    // console.log("开始录音监听")
    // recorderManager.onStart(() => {
    //   console.log('recorder start')
    // })
  
  },

  onReady: function () {
    console.log("onReady")

    manager.onStop = res=>{
      let text = res.result
      console.log(res)
      if(text==''){
        console.log('用户没有说话')
      }else{
        console.log(text)
      }
    },

    manager.onRecognize = function(res) {
      console.log("current result", res.result)
      // this.setData({
      //   currentText: res.result,
      // })
    }

    manager.onError = function (res) {
      console.log('manager.onError')
      console.log(res)//报错信息打印
    }
  }

  

 
})
