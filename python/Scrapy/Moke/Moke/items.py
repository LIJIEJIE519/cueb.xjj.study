# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class MokeItem(scrapy.Item):
    name = scrapy.Field()
    rank = scrapy.Field()
    users = scrapy.Field()
    desc = scrapy.Field()
    imgpath = scrapy.Field()


