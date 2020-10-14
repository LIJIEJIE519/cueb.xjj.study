
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
    currentText: "result!",
    timeId: 0
  },
  endTime: function() {
    console.log("停止循环...")
    clearInterval(this.timeId)
  },

  // 点击录音
  beginRecoder: function() {
    recorderManager.start(options)
  },
  endRecoder: function() {
    recorderManager.onStop(res => {
      wx.uploadFile({
        url: 'http://127.0.0.1:9999/uploadAudio',
        filePath: res.tempFilePath,
        name: 'file',
        success (res){
          console.log(res)
          this.currentText = res.data
        },
        fail (e) {
          console.log(e)
        }
      })
    })
    recorderManager.stop()
  },

  // 长按录音
  touchStart: function() {
    recorderManager.start(options)
  },
  touchEnd: function() {
    recorderManager.stop()
  },

  time: function () {
    this.timeId = setInterval(function() {
      recorderManager.start(options)
    }, 4000)
  },
  onLoad: function () {
    recorderManager.onStart(() => {
      // console.log('recorder start')
    })
    recorderManager.start(options)
    this.time();

    recorderManager.onStop(res => {
      let _that = this
      wx.uploadFile({
        url: 'http://81.70.101.221:9999/uploadAudio',
        filePath: res.tempFilePath,
        name: 'file',
        success (res){
          console.log(res)
          if(res.data == "YES 检测到关键词!") {
            _that.currentText = res.data;
            _that.endTime()
          }
        },
        fail (e) {
          console.log(e)
        }
      })
    })
    
  
  },
})
