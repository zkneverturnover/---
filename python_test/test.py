# -*- coding: UTF-8 -*-
import pymssql as p
from pymssql import *
import easygui as g

S_column = ['sno','sname','sdept','sbirth','ssex']
S_column_ch = ['学号','姓名','性别','生日','专业']
C_column = []

def logon() :
    login = g.multpasswordbox(msg='请输入您的用户名和密码：\n',fields=['用户名','密码'],values=['sa','123456'])

    try :
        p.connect('localhost',user=login[0],password=login[1],database='student')

        return True
    except p.OperationalError  :

        g.msgbox(msg='error',title='用户名或密码错误',ok_button='返回')
        return False

def select() :
    sel_list = g.multchoicebox(msg='选择',
                title='请选择您要查询的内容',
                choices=S_column)
    conn = p.connect('localhost', user='sa', password='123456', database='student')
    cursor = conn.cursor()
    sel_code = 'select '+ ','.join(sel_list) + ' from S '   #注意加逗号，最后不用逗号

    cursor.execute(sel_code)
    cursor1=cursor.fetchall()
    a = ''
    for row in cursor1 :
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

    code = 'insert into S values (%s,%s,%s,%s,%s)'   #注意加逗号，最后不用逗号

    # print(code%tuple(info_list))
    cursor = conn.cursor()
    cursor.execute(code,tuple(info_list))

    cursor.close()
    conn.close()

    g.msgbox(msg='信息添加成功',title='增加信息')

def delete() :
    conn = p.connect('localhost', user='sa', password='123456', database='student',autocommit=True)  # autocommit=ture才能更改数据库
    del_thing =g.buttonbox(msg='你要删除什么？',title='删除',choices=['删除学生信息','删除选课信息','删除课程信息'])

    if del_thing == '删除学生信息' :
        del_list = g.multenterbox(msg='输入要删除的信息',title='删除',fields=['学号','学生姓名'],values=['2016302346','无产者'])
        del_list.insert(0,'S')
        del_code = 'sno = \'%s\' and sname = \'%s \' '
    elif del_thing == '删除选课信息' :
        del_list = g.multenterbox(msg='输入要删除的信息', title='删除', fields=['课程号', '学号'],values=['1','95001'])
        del_list.insert(0, 'SC')
        del_code = 'cno = %s  and sno = \'%s\' '
    else :
        del_list = g.multenterbox(msg='输入要删除的信息', title='删除', fields=['课程号', '课程名'],values=['1','数据库'])
        del_list.insert(0, 'C')
        del_code = 'cno = \'%s\' and cname = \'%s\' '
        print(del_list)

    code = 'delete from %s where ' + del_code  # 注意加逗号，最后不用逗号

    # print(code % tuple(del_list))
    cursor = conn.cursor()
    cursor.execute(code % tuple(del_list))

    cursor.close()
    conn.close()

    g.msgbox(msg='信息已经删除', title='删除')

def update() :
    conn = p.connect('localhost', user='sa', password='123456', database='student',
                     autocommit=True)  # autocommit=ture才能更改数据库
    cursor = conn.cursor()

    update_thing = g.buttonbox(msg='你要修改什么？', title='修改', choices=['修改学生信息', '修改选课信息', '修改课程信息'])
    update_list = []
    if update_thing == '修改学生信息':
        log_in = g.multenterbox(msg='学生信息验证', title='修改', fields=['学号', '学生姓名'], values=['2016302346', '无产者'])
        cursor.execute('select * from S where sno = %s and sname = %s',tuple(log_in))

        old_info = cursor.fetchone()
        if old_info :
            new_info = g.multenterbox(msg='请输入新的属性值',title='修改',fields=S_column_ch,values=old_info)
            code = ' update S set %s = \'%s\' where sno = %s '
            for i in range(len(S_column)) :
                if i == 0 :
                    continue
                a = tuple([S_column[i],new_info[i],str(old_info[0])])
                update_list.append(a)
                # update_list.append(S_column[i])
                # update_list.append(new_info[i])
                # update_list.append(old_info[0])

        else:
            g.msgbox(msg='error')
            pass
    else:
        pass

    for i in update_list :
        print(i)
        cursor.execute(code%i)

    # print(update_list)
    # cursor.executemany(code,update_list)






    cursor.close()
    conn.close()

    g.msgbox(msg='修改成功', title='修改')




def choices() :
    function = g.buttonbox(msg='您要干什么',
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


