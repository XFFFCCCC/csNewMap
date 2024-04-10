## 开发部署配置注意事项
### 工程配置
+ IDEA配置run/debug时将working directory选定到web/模块下，否则属性配置文件加载会异常。本项目需要外置配置文件以方便部署

### 远程服务器约定
+ 为避免程序放置个人用户目录，约定最好将程序挪至/data/webapps/目录下
+ 将你的程序目录xxx权限设置为你的用户所有/data/webapps/xxx/
+ /data/webapps/dameon/目录是用于存放守护进程脚本目录，为root权限管理
+ 约定将程序所产生的日志放置在/data/webapps/xxx/logs/目录，这样其他人很快就能定位程序logs

### 部署配置
+ 远程服务器部署的程序目录于/data/webapps/xxx，xxx就是相应的应用，比如IM-server，robot-server,app-server等
+ 使用[Linux daemon tools][2]守护应用进程
  1. 守护进程的目录都在/service 目录下，例如/service/robot-server是聊天机器人服务
  2. /service目录下其实都是[Linux软连接][1]，软链到的目录是/data/webapps/dameon目录下的目录，每个目录下均有个run脚本，这个脚本就是守护进程需要的脚本
  3. 停止守护的服务sudo svc -d /service/xxx，xxx例如可以是robot-server，注意不需要指定目录下的run脚本
  4. 重启守护的服务sudo svc -t /service/xxx
  5. 启动守护服务sudo svc -u /service/xxx
  6. 新增加守护服务，在/service目录下软链到你需要守护的脚本包含run脚本的目录，ln -s 你的执行脚本目录 /service/xxx
  7. 示例robot-server的run脚本
     ```
     #!/bin/bash
     exec setuidgid project /bin/bash /data/webapps/robot-server/config/run.sh
     ```
     **注意**
     - 需要用exec的方式来执行，否则执行的命令同脚本不在一个进程，svc没法真正停止进程
     - setuidgid project，表示使用project用户来执行后面的命令，否则默认会是root用户
     - 后面命令可以是任何你需要启动程序的命令或脚本，例如java -jar xxx.jar或者python xx.py
     


[1]: https://www.jianshu.com/p/29acb9329b24 "Linux链接" 
[2]: https://blog.csdn.net/heyutao007/article/details/79042770 "daemon tools"