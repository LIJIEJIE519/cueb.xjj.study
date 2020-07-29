# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html
import pymysql
from pandas import DataFrame
import pandas as pd


class Music163Pipeline(object):

    def __init__(self):
        self.musicInfoAll = DataFrame()
        self.conn = pymysql.connect(
            host="127.0.0.1",
            user='root',
            passwd='123456',
            db='python',
            charset='utf8'
        )

    def process_item(self, item, spider):

        musicInfo = DataFrame([item["createBy"], item["desc"], item["addNum"], item["transNum"], item["commNum"],
                               item["tag"], item["imgPath"]]).T
        musicInfo.columns = ["创建者", "简述", "收藏数", "转发数", "评论数", "标签", "图片地址"]
        self.musicInfoAll = pd.concat([self.musicInfoAll, musicInfo])
        self.musicInfoAll.to_csv("MusicInfo.csv", encoding="gb18030")

        for i in range(len(item["createBy"])):
            createBy = item["createBy"][i]
            descs = item["desc"][i]
            addNum = item["addNum"][i]
            transNum = item["transNum"][i]
            commNum = item["commNum"][i]
            tag = item["tag"][i]
            imgPath = item["imgPath"][i]

            cursor = self.conn.cursor()
            sql = "insert into music163(createBy,descs,addNum,transNum,commNum,tag,imgPath) " \
                  "values ('"+createBy+"','"+descs+"','"+addNum+"','"+transNum+"','"+commNum+"','"+tag+"','"+imgPath+"') "
            cursor.execute(sql)
            self.conn.commit()
        return item

    def close_spider(self, spider):
        self.conn.close()



