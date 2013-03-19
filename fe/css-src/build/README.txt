
Jiae项目build文件说明
=======================================
 Description: 本文件是项目前端css压缩与合并的脚本文件
 Author: lichunping
 Mail: jarryli@gmail.com
 Date: 2012-4-14
-------------------------

* 文件说明： 
=======================================
# compress-css.xml
    压缩css，接针对webapp下的上线css文件进行压缩，在压缩前需要执行合并脚本。
    压缩时需要用到临时目录(tmp)

# copy-include.xml
    复制include文件，在开发过程中css按模块划分若干为文件，通过@import进行开发测试。
    当执行合并压缩后，执行该脚本可以恢复@import引入文件

# merge-css.xml
    合并css到指定的一个或几个文件，本文件需要根据@import变化更新维护