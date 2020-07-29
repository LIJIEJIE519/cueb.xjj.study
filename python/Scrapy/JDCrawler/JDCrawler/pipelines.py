# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html
import pymysql

class JdcrawlerPipeline(object):

    def __init__(self):
        self.conn = pymysql.connect(
            host='127.0.0.1',
            user='root',
            passwd='123456',
            db='python',
            charset='utf8'
        )
    def process_item(self, item, spider):
        for i in range(len(item["name"])):
            name = item["name"][i]
            shop = item["shop"][i]
            price = item["allPrice"][i]
            comment = item["allComment"][i]
            cursor = self.conn.cursor()
            # jd_info()里一定要加上字段
            mysql = "insert into jd_info(name,shop,price,comment) values('" + name + "','" + shop + "','" + price + "','" + comment + "')"
            cursor.execute(mysql)
            self.conn.commit()
        return item

    def close_spider(self,spider):
        self.conn.close()