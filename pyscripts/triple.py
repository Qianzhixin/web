import os
from shutil import copy
import json
import traceback
import datetime
import pymysql
import xlrd

conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='123456',
                       db='internet_facing_plus', charset='utf8')


def excel(path, sheet, removeFirst=True):
    wb = xlrd.open_workbook(path)  # 打开Excel文件
    sheet = wb.sheet_by_name(sheet)  # 通过excel表格名称(rank)获取工作表
    dat = []  # 创建空list
    for a in range(sheet.nrows):  # 循环读取表格内容（每次读取一行数据）
        cells = sheet.row_values(a)  # 每行数据赋值给cells
        if not removeFirst:
            dat.append(cells)
        else:
            removeFirst = False
    return dat


def getRegulationByDocId(docId):
    cursor = conn.cursor()

    sql = "select * from external_regulation where text_path = '" + str(docId) + ".docx'"

    res = None

    try:
        cursor.execute(sql)
        res = cursor.fetchone()
    except Exception:
        traceback.print_exc()
    finally:
        cursor.close()

    return res


def getRegulationByTitleAndNumber(title, number):
    cursor = conn.cursor()

    sql = "select * from external_regulation where title = '" + title + "' and number = '" + number + "'"

    res = None

    try:
        cursor.execute(sql)
        res = cursor.fetchone()
    except Exception:
        traceback.print_exc()
    finally:
        cursor.close()

    return res


def insertTriple(s_id, s_title, t_id, t_title, predicate):
    cursor = conn.cursor()

    sql = "INSERT INTO triple (`s_id`, `s_title`, `t_id`, `t_title`, `predicate`) " \
          "VALUES (%s, %s, %s, %s, %s)"

    try:
        cursor.execute(sql, [s_id, s_title, t_id, t_title, predicate])
        conn.commit()
    except Exception:
        traceback.print_exc()
        conn.rollback()
    finally:
        cursor.close()


def initTriples(dat, predicate):
    for cells in dat:
        docId = int(cells[0])
        rs = cells[1].split('/')

        if not (len(rs) == 1 and rs[0] == ''):

            source = getRegulationByDocId(docId)
            if source is None:
                continue

            for s in rs:
                tn = separateTitleNumber(s)
                if tn is not None:
                    target = getRegulationByTitleAndNumber(tn[0], tn[1])
                    if target is None:
                        temp = ''
                        if tn[1] != '':
                            temp = "（" + tn[1] + "）"
                        name = tn[0] + temp
                        insertTriple(source[0], source[1], None, name, predicate)
                    else:
                        insertTriple(source[0], source[1], target[0], target[1], predicate)


# 分离标题和文号
def separateTitleNumber(title_number):
    if len(title_number) == 0:
        return ['', '']

    if title_number[0] != '《':
        return None

    i = 1
    while i < len(title_number) and title_number[i] != '》':
        i += 1
    title = title_number[1:i]

    number = ''
    j = i + 1
    if j < len(title_number):
        while j < len(title_number) and title_number[j] != '）':
            j += 1
        number = title_number[i + 2:j]

    return [title, number]


def main():
    global conn

    path = 'C:\\Users\\Gorgiya\\Desktop\\data\\5635-192704.xls'
    sheet = 'Sheet1'

    dat = excel(path, sheet)

    initTriples(dat, 1)

    conn.close()


if __name__ == '__main__':
    main()
