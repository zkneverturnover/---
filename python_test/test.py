#coding=utf-8
import pymssql as p
# from tkinter import *
# from functools import partial #使得可以向command中传递参数
import easygui as g

# def p(a) :
#     print (a)
#     conn = pymssql.connect('localhost', user='sa', password='123456', database='student')
#     cursor = conn.cursor()
#     cursor.execute('select * from S')
#     for row in cursor :
#         print( row)

def logon() :
    login = g.multpasswordbox(msg='请输入您的用户名和密码：\n',fields=['用户名','密码'],values=['sa','123456'])

    try :
        p.connect('localhost',user=login[0],password=login[1],database='student')

        return True
    except p.OperationalError as e :
        g.msgbox(msg='error',title='用户名或密码错误',ok_button='返回')
        return False

def select() :
    sel_list = g.multchoicebox(msg='选择',
                title='请选择您要查询的内容',
                choices=['sno','sname','sdept','ssex','sbirth'])
    conn = p.connect('localhost', user='sa', password='123456', database='student')
    cursor = conn.cursor()
    sel_code = 'select '+ ','.join(sel_list) + ' from S '   #注意加逗号，最后不用逗号

    cursor.execute(sel_code)
    a = str()
    for row in cursor :
        a = a + str(row).replace('\'',' ') + '\n'
    g.textbox(msg='查询成功',title='查询结果',text=a)
    cursor.close()
    conn.close()

def insert() :
    conn = p.connect('localhost', user='sa', password='123456', database='student',autocommit=True)     #autocommit=ture才能更改数据库

    info_list = g.multenterbox(msg='增加信息',
                title='请输入您要增加的记录',
                fields=['sno','sname','ssex','sbirth','sdept'],
                values=[2016302346,'无产者','男','1949-10-01','CS'])

    print(info_list)
    code = 'insert into S values (%s,%s,%s,%s,%s)'   #注意加逗号，最后不用逗号

    print(code%tuple(info_list))
    cursor = conn.cursor()
    cursor.execute(code,tuple(info_list))

    cursor.close()
    conn.close()

    g.msgbox(msg='信息添加成功',title='增加信息')

def update() :
    conn = p.connect('localhost', user='sa', password='123456', database='student',autocommit=True)  # autocommit=ture才能更改数据库

    info_list = g.multenterbox(msg='删除信息',
                               title='请输入您要删除的记录',
                               fields=['sno', 'sname', 'ssex', 'sbirth', 'sdept'],
                               values=[2016302346, '无产者', '男', '1949-10-01', 'CS'])

    print(info_list)
    code = 'insert into S values (%s,%s,%s,%s,%s)'  # 注意加逗号，最后不用逗号

    print(code % tuple(info_list))
    cursor = conn.cursor()
    cursor.execute(code, tuple(info_list))

    cursor.close()
    conn.close()

    g.msgbox(msg='信息添加成功', title='增加信息')




def delete() :
    pass
def choices() :
    function = g.choicebox(msg='您要干什么',
                title='请选择您需要的服务',
                choices=['查询信息','增加信息','删除信息','修改信息'])
    if function == '查询信息' :
        select()
    elif function == '增加信息' :
        insert()
    elif function == '删除信息' :
        delete()
    else :
        update()







t = logon()
while not t :
    t = logon()
choices()