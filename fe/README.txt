
 Iseer前端环境说明
=======================================
 Description: 本文件是前端开发环境的说明文件 //TODO:   待更新
 Author: jarryli
 Mail: jarryli@gmail.com
 Date: 2012-5-9
-------------------------
 List:
 1. 目录说明
 2. 文件命名与结构说明
 3. 使用说明
-------------------------

*  目录与根目录文件说明： 
=======================================

ROOT(fe)
                 
                ## 前端源码目录，在开发时需要引入源码进行调试，要开通一个HttpServer指向该目录，比如 /jiae指向该目录。开发引用时候通过document.write('/jiae/js-src/*.js')来引入js文件。

  + css-src        # css 源码文件夹
  + js-src         # js  源码文件夹
  - shell          # 自动构建脚本的文件夹
  + test           # 单元测试文件夹
  - tools          # 一些前端需要使用的工具，比如压缩器等

               ## webapp是前端开发运行目录(Apache或Nginx指向该目录，
                  如果开发运行目录指向/iseer/static/的来运行的话，需要将webapp下的内容复制到/iseer/static/以便进行测试) 
                  同时webapp也是临时的合并压缩的目录，执行脚本合并压缩代码
                  并把webapp目录下的全部文件复制到/iseer/static/运行目录下
                  static为静态运行目录，结构与webapp一致。
                  本地开发时webapp可能会有多余的测试文件，故将/iseer/fe/webapp在版本管理中忽略

  + webapp         # 
      - js         # js运行文件
      - css        # css运行文件
      - img        # 项目用到的icon文件，也可单独放置在其他图片服务器
      - swf        # flash文件
      - tpl        # 前端模板文件，根据需要是否建立



 *  文件命名与结构说明： 
=======================================
 
 js与css源码常见源码文件下的README文件
 webapp是开发运行目录，建立以上规定的目录即可
 与/iseer/static/目录相同


 *  文件命名与结构说明： 
=======================================

 * 通过版本管理工具把项目拉到本地，其中fe目录是前端的开发目录，fe/webapp是前端开发时候的运行目录。开发完成后，合并压缩后复制内容到/iseer/static前端web运行目录。
 * 先通过shell 下的相关脚本创建运行目录下的文件夹
 * 在开发完成测试时，执行shell调用ant 把相关的js、css合并到运行目录。具体常见shell以及源码下的build目录

