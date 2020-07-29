import scrapy
from JDCrawler.items import JdcrawlerItem
import re
import requests
from scrapy.http import Request

class FirstSpider(scrapy.Spider):
    name = 'jdInfo'
    allowed_domains = ['jd.com']
    # 爬虫网址
    start_urls = ['https://list.jd.com/list.html?cat=9987,653,655']

    def parse(self, response):
        item = JdcrawlerItem()
        allPrice = []
        allComment = []
        # 商品名称 # em下的文字直接用text()
        name = response.xpath('//li[@class="gl-item"]//div[@class="p-name"]/a/em/text()').extract()
        for i in range(len(name)):
            name[i] = name[i].strip()
        #print(name)
        shop = response.xpath('//li[@class="gl-item"]//div[@class="p-shop"]/@data-shop_name').extract()
        sku = response.xpath('//li[@class="gl-item"]/div/@data-sku').extract()
        for i in range(len(sku)):
            priceUrl = 'https://p.3.cn/prices/mgets?callback=jQuery7561688&skuIds=J_' + sku[i]
            comUrl = 'https://club.jd.com/comment/productCommentSummaries.action?my=pinglun&referenceIds=' + sku[i]
            priceres = requests.get(priceUrl)
            comRes = requests.get(comUrl)
            pricePat = '"p":"(.*)"'
            comPat = '"CommentCount":(.*),"AverageScore"'

            price = re.compile(pricePat).findall(priceres.text)
            coment = re.compile(comPat).findall(comRes.text)
            allPrice = allPrice + price
            allComment += coment

        item["name"] = name
        item["shop"] = shop
        item["allPrice"] = allPrice
        item["allComment"] = allComment
        yield item

        for i in range(2,3):
            url = 'https://list.jd.com/list.html?cat=9987,653,655&page=' + str(i)
            yield Request(url, callback=self.parse)