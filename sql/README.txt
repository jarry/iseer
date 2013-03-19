
 Iseer项目sql目录说明
=======================================
 Description: 
   本文件是项目使用到的所有sql文件
   从数据库创建到更新，每次修改都记录下来
   保留原始结构以及基本数据

 Author: lichunping
 Mail: jarryli@gmail.com
 Date: 2012-9-20
-------------------------

* 基本信息： 
=======================================
- 软件版本
    mysql-5.0.67 Ver 14.12 Distrib 5.0.67

- 字符
    采用 UTF-8

- 引擎
    ENGINE=InnoDB


* SQL规范
=======================================
- 库名
    与项目同，小写
    如: iseer

- 表名
    实体表：全小写，名词，采用复数形式，与hibernate实体对应，一般来讲多一个S或ES
    如: users、apps
    关联表：全小写，名词，中间下划线连接
    如: user_system

- 字段名
    全大写，名词或动词，中间以下划线连接
    如: USER_ID、APP_ID
    每个表都设一个自增量ID

- ER图
    每设计玩一张表时，最好同时输出一张ER图。 
    数据库先期在设计工具设计，如在powerdesigner设计，然后再导入客户端工具里面执行
    分别生成两个结构文件

- 外键
   关系表与附属表都通过外键与实体表链接，通过数据库机制来保证数据结构完整与安全

- 事务
   通过spring+hibernate进行事务管理，保证业务数据的完整性与正确性


* 结构关系图
=======================================
- 具体数据库设计文件

* 表划分
=======================================
- 具体参见structure*.sql文件

* 授权
=======================================
# 用root登录

$ mysql5/bin>mysql -h localhost -u root

建立root用户与密码:
# grant all on *.* to 'root'@'localhost' identified by '123456';
更改用户密码
# UPDATE `user` SET `password`=PASSWORD('123456') WHERE `user`='root'; 
# FLUSH PRIVILEGES;

# 授权iseer用户以localhost访问数据库
mysql> GRANT ALL PRIVILEGES ON iseer.* TO 'iseer'@'localhost' IDENTIFIED BY 'iseer798' WITH GRANT OPTION;

# 授权iseer用户以任何主机ip访问数据库
mysql> GRANT ALL PRIVILEGES ON iseer.* TO 'iseer'@'%' IDENTIFIED BY 'iseer798';

# 如果开启了iptables，通过 # service iptables status检查
# 修改iptables，允许其他主机访问tcp与udp的3306端口
-A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT
-A INPUT -m state --state NEW -m tcp -p udp --dport 3306 -j ACCEPT

