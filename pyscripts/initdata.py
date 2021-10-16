import os
from shutil import copy
import json
import traceback
import datetime
import pymysql


def findAllFilesWithSpecifiedSuffix(target_dir, target_suffix="json"):
    find_res = []
    target_suffix_dot = "." + target_suffix
    walk_generator = os.walk(target_dir)
    for root_path, dirs, files in walk_generator:
        if len(files) < 1:
            continue
        for file in files:
            file_name, suffix_name = os.path.splitext(file)
            if suffix_name == target_suffix_dot:
                find_res.append(os.path.join(root_path, file))
    return find_res


data_path = "D:\\result"  # 爬取数据的路径
data_root_path = "D:\\warehouse\\SePractice\\testdir"  # 正文文件存储的根路径
json_paths = findAllFilesWithSpecifiedSuffix(data_path)

conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password='123456',
                       db='internet_facing_plus', charset='utf8')
cursor = conn.cursor()

for json_path in json_paths:
    dic = {}
    with open(json_path, 'r', encoding="utf-8") as f:
        dic = json.load(f)

    for k in dic.keys():
        if dic[k] is None:
            dic[k] = ''

    doc_name = json_path.split('\\')[-1].replace('json', 'doc')
    doc_source_path = data_path + '\\' + doc_name
    doc_tatget_path = data_root_path + '\\' + doc_name
    # 复制正文doc到data_root_path
    copy(doc_source_path, doc_tatget_path)

    sql = "INSERT INTO external_regulation (`title`, `number`, `type`, `publishing_department`, `effectiveness_level`, " \
          "`release_date`, `implementation_date`, `interpretation_department`, `input_person_id`, `input_date`, " \
          "`text_path`, `state`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')" % \
          (dic['title'], dic['number'], dic['type'], dic['publishing_department'], dic['effectiveness_level'],
           dic['release_date'], dic['implementation_date'], dic['interpretation_department'], 1,
           datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
           doc_tatget_path.replace("\\", "\\\\"),  # 解决入库丢失反斜杠的问题
           dic['state'])
    try:
        cursor.execute(sql)
        conn.commit()
    except Exception:
        traceback.print_exc()
        conn.rollback()

conn.close()
