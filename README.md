# Demo
管理系统

## 准备工作
### 1. 安装jdk（1.8+）
本项目使用的java开发的，安装过程不在此展开，安装后配置环境变量。

### 2. 安装maven
本项目使用的maven进行管理的，安装过程不在此展开，安装后配置环境变量。

### 3. 安装MySQL
安装过程不在此展开，安装成功后，将用户名、密码和数据库名配置到`application-dev.yml`中，prod环境类似。

### 4. 安装Redis(哨兵)
安装过程不在此展开，安装成功后，将IP、端口、密码以及哨兵节点配置到`application-dev.yml`中，prod环境类似。

## 快速开始
### 1. 初始化数据库
```
source schema.sql
```

### 2. 编译
```
mvn clean install
```

### 3. 运行
```
java -jar demo-web/target/demo-web-*.jar
```

## 技术栈
- Spring
- Spring Boot
- Spring Session
- Mybatis
- PageHelper
- Mybatis Generator
- Redis(Sentinel)
- RabbitMQ
- Log4j2(yml)
- Swagger2

## 内置接口
- 登录
- 注销
