//index.js
//获取应用实例
const app = getApp()

// 录音
const recorderManager = wx.getRecorderManager();
const options = {
    duration: 3000,
    sampleRate: 16000,
    numberOfChannels: 1,
    encodeBitRate: 64000,
    format: 'mp3',
    frameSize: 50
}

Page({
  data: {
    currentText: "我是老中医，很高兴为您服务！",
    timeId: 0,
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
  endTime: function() {
    console.log("停止循环...")
    clearInterval(this.timeId)
  },

  time: function () {
    this.timeId = setInterval(function() {
      recorderManager.start(options)
    }, 4000)
  },
  onLoad: function () {
    recorderManager.onStart(() => {})
    recorderManager.start(options)
    this.time();

    recorderManager.onStop(res => {
      let _that = this;
      wx.uploadFile({
        url: 'http://81.70.101.221:9999/uploadAudio',
        filePath: res.tempFilePath,
        name: 'file',
        success (res){
          console.log(res)
          _that.setData({
            currentText: res.data
          })
          if(res.data == "YES 检测到关键词!") {
            _that.endTime();
            wx.navigateTo({
              url: '../main01/mindex01',
            })
          }
        },
        fail (e) {
          console.log(e)
        }
      })
    })
  },
})
