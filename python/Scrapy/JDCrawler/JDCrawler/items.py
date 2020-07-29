# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy
class JdcrawlerItem(scrapy.Item):
    name = scrapy.Field()
    shop = scrapy.Field()
    allPrice = scrapy.Field()
    allComment = scrapy.Field()