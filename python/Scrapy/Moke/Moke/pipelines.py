# -*- coding: utf-8 -*-

import pymysql
from pandas import DataFrame
import pandas as pd


class MokePipeline(object):

    # 构造函数--定义数据库及MySQL
    def __init__(self):
        self.keInfoAll = DataFrame()
        self.conn = pymysql.connect(
            host="127.0.0.1",
            user='root',
            passwd='123456',
            db='python',
            charset='utf8'
        )

    def process_item(self, item, spider):

        # c导出到.csv
        keInfo = DataFrame([item["name"],item["rank"],item["users"],item["desc"],item["imgpath"]]).T
        # 设置列名
        keInfo.columns = ["课程名","等级","访问量","课程描述","图片地址"]
        self.keInfoAll = pd.concat([self.keInfoAll, keInfo])
        self.keInfoAll.to_csv("keInfo.csv", encoding="gbk")

        # 存储数据到mysql
        for i in range(len(item["name"])):
            name = item["name"][i]
            rank = item["rank"][i]
            users = item["users"][i]
            descs = item["desc"][i]
            imgpath = item["imgpath"][i]
            # 定义游标
            cursor = self.conn.cursor()
            sql = "insert into moke(name,rank,users,descs,imgpath) values ('"+name+"','"+rank+"','"+users+"','"+descs+\
                  "','"+imgpath+"') "
            # 执行mysql命令
            cursor.execute(sql)
            self.conn.commit()
        return item

    # 关闭mysql连接
    def close_spider(self,spider):
        self.conn.close()




