# -*- coding: utf-8 -*-

import pymysql
class KeqqcrawlerPipeline(object):
    # 构造函数  --定义mysql连接
    def __init__(self):
        self.conn = pymysql.connect(
            host = "127.0.0.1",
            user = 'root',
            passwd = '123456',
            db = 'python',
            charset = 'utf8'
        )

    # 保存到mysql
    def process_item(self, item, spider):
        for i in range(len(item["name"])):
            name = item["name"][i]
            users = item["users"][i]
            price = item["price"][i]
            agency = item["agency"][i]

            cursor = self.conn.cursor()
            sql = "insert into keqq(name,users,price,agency) values ('" + name + "','" + users + "','" + price + "','" + agency + "')"
            cursor.execute(sql)
            self.conn.commit()
        return item

    # 关闭mysql连接
    def close_spider(self,spider):
        self.conn.close()