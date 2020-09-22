// pages/main01/mindex01.js
Page({
   data: {
    imageList: [],
    sourceTypeIndex: 2,
    sourceType: ['拍照', '相册', '拍照或相册'],

    sizeTypeIndex: 2,
    sizeType: ['压缩', '原图', '压缩或原图'],

    countIndex: 5,
    count: [1, 2, 3, 4, 5, 6],
    fileName: "",
  },
  chooseImage: function () {
    var _this = this;
    wx.chooseImage({
      count: 6, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // console.log(res.tempFiles[0].path)
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        _this.setData({
          imageList:res.tempFilePaths
        })
        const file = res.tempFiles[0].path
        wx.uploadFile({
          url: 'http://127.0.0.1:9999/uploadImg',
          filePath: res.tempFilePaths[0],
          name: 'file',
          success (res){
            console.log(res)
            _this.data.fileName = res.data
          },
          fail (e) {
            console.log(e)
          }
        })
      }
    })
  },
  bindOne: function() {
    let _this = this;
    console.log(_this.data.fileName)
    wx.request({
      url: 'http://127.0.0.1:9999/ocr',
      data: {
        data: _this.data.fileName
      },
      header: {'content-type':'application/json'},
      method: 'POST',
      dataType: 'json',
      success: (result)=>{
        console.log(result)
      },
      fail: ()=>{
        console.log("fail")
      },
      complete: ()=>{}
    });
  },
  previewImage(e) {
    const current = e.target.dataset.src

    wx.previewImage({
      current,
      urls: this.data.imageList
    })
  },
  /**
   * 1. 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 发送异步请求来初始化页面数据
  },

  /**
   * 3. 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 2. 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 4. 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 5. 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})