
 Jiae前端环境说明
=======================================
 Description: 本文件是前端开发环境的说明文件 //TODO: 待更新
 Author: jarryli
 Mail: jarryli@gmail.com
 Date: 2012-5-5
-------------------------
 List:
 1. 目录说明
 2. 文件命名与结构说明
-------------------------

*  目录说明： 
=======================================
css-src
                 ## 辅助工具
 + build           # 放置css合并压缩的执行脚本，主要指ant
   -tmp            # 压缩时生成的临时目录

                 ## 核心与通用
 + core            # 最基本的样式与全局配置，重定义了html tag样式
  // - reset.css     # 重置默认样式重置，根据情况是否引入
 + com             # 项目通用的css，比如header,footer,其他公共部分
 + ui              # ui部分，如浮动层，button等

 + ....          ## 业务模块部分
                   根据自己业务模块划分的css文件夹
                   尽量把相同的放在一块，很独立的模块或功能才建立单独的文件夹
                   如: pin, board, user，其他都可以合并到com中



 *  文件命名与结构说明： 
=======================================

* css文件按照模块名起名，全部为小写，文件用中杠连接;
   示例：
    css-src[folder]
        + module1Name[folder]          # 所有css全部放在模块根目录下即可
            - module1.css
            - module1-function.css
        - module1.css

        + module2Name[folder]
            - module2.css
        - module1.css                  # css-src根目录下各模块css文件是import各模块css的文件
        - module2.css                  # 上线时通过合并压缩工具合并到运行主目录的css目录下(ROOT/webapp/css/)
                                       # 为了减少css请求数，根据需要再把同类的css引入合并到一起


* 书写样式内部要根据自己的业务进行css命名前缀(css命名空间);
   如:
       .pin
       {
       
       }
       
      .pin .pin-img
       {
       
       }
   
* 不允许设置顶级样式, 全局样式可以放入com/com.css中，com下所有css修改时修改人需要说明理由
   如:
       body{
       
       }
       html{
       
       }
       table{
       
       }
     
* 所有class都使用classname，而不采用id。没有#Header样式，只有.header 或 .Header

* 全局性的唯一的大的布局块采用首字母大写，
  如:.Header .PinWrapper, 一个页面最多三个首字母大写的Class用来布局(也可以没有首字母大写的class，写成.pin-wrapper或.pin-container), 其他classname均为小写，以下划线连接，如.pin
  所有class均通过顶级class往下声明，除了单独的通用模块或UI组件，其他一律不得直接声明class

  如：
  非通用，写在各业务模块
  .Wrapper {}
  .Wrapper .board-container
  .Wrapper .board-contianer ul li {}

  通用,一律写在com里
  .header
  .pin
  .button
  .upload-contianer
  .user-contianer


* 通过全局性唯一的块样式 + 标签来定义多级样式，减少子节点的classname
    如：
        .pin dl.repin dt
        {
        }

        .Header a.logo em
        {
        }

        .red-button:active span
        {
        }

* 基础字体字号由core全局单独指定，一个样式尽量控制在4个样式名称内


* 语义化具体命名、格式书写规范项等详细内容参见《CSS编码规范》
