### trending

* 1.trending用java编写的用于爬每天[github trending](https://github.com/trending)的内容并上传到自己的github上。一年后你会感受到github上contributions密密麻麻的感觉。
* 2.什么是[github trending](https://github.com/trending)?github trending是一个github上被认为每日被star的增长量排行榜。
* 3.具体实现：
   * 1.时间job任务，每天定时忘github trending上抓取一次数据。
   * 2.以yyyy-MM-dd 格式生成 .md文件。
   * 3.推送.md文件到自己github上。
   * 4.Main类上可自定义抓取语言（默认是java,javascript）
* 4.环境：
   * 1.jdk 1.6+
   * 2.maven
   * 3.github账户并构造好ssh key,github推送默认是git push origin master 
   * 4.注意系统编码，linux保持utf-8编码，否则获取数据时无法识别中文，毕竟国人也经常出现在trending上
* 5.运行：linux上启动.sh文件；windows上，使用maven clean package install 后在target目录下输入java -jar trending.jar文件