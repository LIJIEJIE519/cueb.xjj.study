# -*- coding: utf-8 -*-
import scrapy
from Music163.items import Music163Item
from scrapy.http import Request
import requests
from lxml import etree
import re


class MusicSpider(scrapy.Spider):
    name = 'music'
    allowed_domains = ['163.com']
    # 开始爬虫地址
    start_urls = ['https://music.163.com/discover/playlist/?limit=35&offset=0']

    def parse(self, response):
        # 创建Item
        item = Music163Item()
        # data = response.body.decode("utf-8")
        # xpath匹配 创建者，简述，图片地址
        createBy = response.xpath('//a[@class="nm nm-icn f-thide s-fc3"]/text()').extract()
        desc = response.xpath('//p[@class="dec"]/a[@class="tit f-thide s-fc0"]/@title').extract()
        imgpath = response.xpath('//ul[@class="m-cvrlst f-cb"]/li/div[@class="u-cover u-cover-1"]/img/@src').extract()
        # 获取另一个页面的url
        href = response.xpath('//ul[@class="m-cvrlst f-cb"]/li//p[@class="dec"]/a/@href').extract()

        item["createBy"] = createBy
        item["desc"] = desc
        item["imgPath"] = imgpath
        # 创建临时列表
        addNumAll = []
        transNumAll = []
        commNumAll = []
        tagNumAll = []
        # 对歌单详细页遍历
        for i in range(len(href)):
            url = 'https://music.163.com' + href[i]
            newres = requests.get(url)
            newres.encoding = 'utf-8'
            # root = etree.HTML(newres.text)
            # addNum = root.xpath('//a[@class="u-btni u-btni-fav "]/i/text()')
            # print(newres.text)

            # 匹配收藏数
            addpat = 'data-count="(.*)"\n.*\nclass="u-btni u-btni-fav "'
            addNum = re.findall(addpat, newres.text, re.S)

            # 匹配转发数
            transpat = 'class="u-btni u-btni-share ".*(\d{1,5})'
            transNum = re.findall(transpat, newres.text)

            # 匹配评论数
            commpat = '<span id="cnt_comment_count">(.*)</span>'
            commNum = re.findall(commpat, newres.text)

            # 匹配标签
            tagpat = '<a class="u-tag" href=".*"><i>(.*)</i></a>'
            tag = re.findall(tagpat, newres.text)
            # 将标签转换为字符串
            tagAll = ''.join(tag)

            addNumAll += addNum
            transNumAll += transNum
            commNumAll += commNum
            # 此处为append()
            tagNumAll.append(tagAll)

        item["addNum"] = addNumAll
        item["transNum"] = transNumAll
        item["commNum"] = commNumAll
        item["tag"] = tagNumAll
        yield item

        for i in range(10, 16):
            url = "https://music.163.com/discover/playlist/?limit=35&offset=" + str(i * 35)
            yield Request(url, self.parse)
