# -*- coding: utf-8 -*-
import scrapy
from Moke.items import MokeItem
from scrapy.http import Request
import re
class MokeSpider(scrapy.Spider):
    name = 'moke'
    allowed_domains = ['imooc.com']
    start_urls = ['https://www.imooc.com/course/list?c=be&page=1']

    def parse(self, response):
        item = MokeItem()
        data = response.body.decode("utf-8")
        name = response.xpath('//div[@class="course-card-content"]/h3/text()').extract()
        rank = response.xpath('//div[@class="course-card-info"]/span[1]/text()').extract()
        users = response.xpath('//div[@class="course-card-info"]/span[2]/text()').extract()
        desc = response.xpath('//p[@class="course-card-desc"]/text()').extract()
        imgpat = '<img class="course-banner lazy" data-original=".*" src="(.*)" '
        imgpath = re.findall(imgpat, data)

        item["name"] = name
        item["rank"] = rank
        item["users"] = users
        item["desc"] = desc
        item["imgpath"] = imgpath
        yield item

        for i in range(2,5):
            url = 'https://www.imooc.com/course/list?c=be&page=' + str(i)
            yield Request(url, callback=self.parse)