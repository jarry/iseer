/*************************************

 前言：
    Iseer is a similar images retrieval system(CBIR:http://en.wikipedia.org/wiki/CBIR)
    Iseer是一个基于图像内容的检索系统，通过分析图像颜色直方图、纹理特征、颗粒质感以及空间场景等信息计算图像的相似度，从而检索出形态比较近似的图像来。

    因为这是一个实验的性的项目，并没有做什么精确的相似度调整。
    本项目给予Lire与lucene，做了一些参数的调整与尝试，然后搭建一套Java Web系统。

    文档并没有整理全，有些代码也没有放上来，仅能用来作为学习交流使用。
   
    李春平 2013/1/18

******************************************/

 iseer
=======================================
 Description: 本文件是项目开发环境的说明文件和规范文档，使用前一定要熟悉
              目录调整时及时修改本文件
 Author: lichunping
 Mail: jarryli@gmail.com
 Date: 2011-8-10
-------------------------
 List:
 1. 目录说明
 2. 安装说明(见INSTALL.txt)
-------------------------

*  目录说明： 
=======================================
ROOT
 + backup          # 放置备份文件，主要是代码以及配置文件等 
 + build           # 放置Java开发相关的执行脚本，主要指ant 
 + conf            # 放置配置文件，用作说明和每次修改时备份
 + css-src         # 放置CSS源文件，开发过程中CSS 通过 @import方式引入进来，最后合并压缩
   - build         # CSS自动构建脚本目录 
 + tmp             # 开发时用到一些临时目录，比如试用图片，tmp下的图片一般上上传到SVN，仅在开发机使用

 + doc             # 放置文档
   - tech-doc      # 开发文档资料
   - project-doc   # 项目文档资料

 + js-src          # 放置JavaScript源文件，最后合并压缩
   - build         # JavaScript自动构建脚本目录

 - output          # 放置输出文件，如war包

 + shell           # 放置供系统执行的脚本等
 + sql             # 放置原始SQL以及每次修改后的SQL
 + src             # Java源文件目录
   + modulename    # 模块名称一
     + src         # Java源码
       + main      
         + java    # Java源码路径，其下的目录对应WEB-INF/classes目录
         + resources  # 资源文件，包括配置等，指向WEB-INF/classes目录
       + test
         + java    # Java测试源码路径，其下的目录对应WEB-INF/classes目录
         + resources  # 测试资源文件，包括配置等，指向WEB-INF/classes目录 

   + module2       # 模块名称二，与一目录结构一致
 
 + WEB-INF
   - classes      # 对应Tomcat WEB-INF/classes，主要是一些配置文件

 + test           # 测试主文件夹。包含Java与JS测试文件。
   + java         # Java部分主要测试web server、数据库、web service、API等基础功能
     + src        # 业务功能的Unit Test放在各业务模块的test目录下
        + main    # 但也可以建立一些与业务功能相关的基本功能或跨模块关联功能的测试
          + java  # class文件统一输出在com.jiae.project.test包下
             + com
               + jiae
                 + project
                    + test
                       + service
                           - impl
                       + dao
                          - impl
                       + basic

    + js          # js 测试用例，目录结构与源码模块目录对应
                  # 开发环境配置static路径，指向项目根目录，通过static路径访问test目录
       + qunit    # 测试工具

 + tools          # 一些单独的工具程序

 + webapp         # 项目运行目录，该目录下文件上传到服务器发布
   + css          
   + img           
   + js
   + swf
   - error        # 服务运行出错页面
   + WEB-INF
     + classes    # 放置配置文件和java编译后的class文件
     - lib        # 项目需要的编译依赖包
     + web        # 页面，可放置供struts映射的文件

 .classpath       # eclipse编译环境$CLASSPATH设置
 .project         # 项目结构说明文件
 .README.txt      # 帮助说明文档


* 命名规则
=======================================
- 文件夹  首字母小写与驼峰式命名: subModule

- 文件    
    - java 首字母大写且与class名称相同 HelloWorld.class
    - js   首字母大写且与主类名称相同  HelloWorld.Action.js
    - css  首字母小写与驼峰式命名      helloWorld.css

- 方法与变量
   - java  驼峰式命名                  helloWorld
   - js    驼峰式命名                  helloWorld
   - css   小写、以中杠连接            hello-world

- 其他
    各开发目录的一级目录均有README.txt说明文件，使用前需产看
    同时严格遵照百度Java编码规范、百度JavaScript编码规范、百度Web开发安全规范等


* 其他说明文件
=======================================
- ROOT/src/README       # 后端模块说明
- ROOT/build/README     # 后端编译说明
- ROOT/js-src/README    # JavaScript开发配置说明
- ROOT/css-src/README   # CSS开发配置说明
