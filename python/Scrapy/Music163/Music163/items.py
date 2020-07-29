# -*- coding: utf-8 -*-
import scrapy


class Music163Item(scrapy.Item):

    createBy = scrapy.Field()       # 创建者
    desc = scrapy.Field()           # 描述
    addNum = scrapy.Field()         # 收藏量
    transNum = scrapy.Field()       # 转发数
    commNum = scrapy.Field()        # 评论数
    tag = scrapy.Field()            # 标签
    imgPath = scrapy.Field()        # 图片路径



