# -*- coding: utf-8 -*-
import scrapy
import requests
from qqMusic.items import QqmusicItem


class QqmusicSpider(scrapy.Spider):
    name = 'qqmusic'
    allowed_domains = ['qq.com']
    start_urls = ['https://www.baidu.com/']

    def parse(self, response):
        item = QqmusicItem()
        data = response.body.decode("utf-8")

        url = 'https://www.baidu.com/'
        res = requests.get(url)
        print("res.text   :")
        print(res.text)

