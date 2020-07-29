# -*- coding: utf-8 -*-
import scrapy
from keqqCrawler.items import KeqqcrawlerItem
from scrapy.http import Request
class KespiderSpider(scrapy.Spider):
    # 运行mysql爬虫
    name = 'kespider'
    allowed_domains = ['ke.qq.com']
    start_urls = ['https://ke.qq.com/course/list?mt=1001&st=2004&task_filter=0000000&&page=1']

    def parse(self, response):
        item = KeqqcrawlerItem()

        # 获取课程名称
        name = response.xpath('//div[@class="market-bd market-bd-6 course-list course-card-list-multi-wrap"]//h4[@class="item-tt"]/a/@title').extract()
        # 获取用户人数
        users = response.xpath('//span[@class="line-cell item-user"]/text()').extract()
        # 去除空格
        for i in range(len(users)):
            users[i] = users[i].strip()
        # 获取价格--限制span标签
        price = response.xpath('//div[@class="market-bd market-bd-6 course-list course-card-list-multi-wrap"]//div[@class="item-line item-line--bottom"]/span[1]/text()').extract()
        # 获取课程认证
        agency = response.xpath('//a[@class="item-source-link"]/@title').extract()
        # 添加item
        item["name"] = name
        item["users"] = users
        item["price"] = price
        item["agency"] = agency
        print(agency)
        yield item

        # 爬取多页
        for i in range(2,4):
            url = "https://ke.qq.com/course/list?mt=1001&st=2004&task_filter=0000000&&page=" + str(i)
            yield Request(url, callback=self.parse)