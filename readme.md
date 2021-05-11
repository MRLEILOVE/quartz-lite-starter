
<p align="center">
<a href='https://gitee.com/leiguoqing/quartz-lite-starter'>
    <img alt="Fork me on Gitee" src="https://gitee.com/oschina/gitee-opensource-guide/widgets/widget_6.svg?color=d9d177">
</a>
</p>
<p align="center">
<a href='https://quartz-lite.cn.utools.club/quartz-lite/login.html'>
    <img alt="Fork me on Gitee" src="https://leigq-blog.oss-cn-shenzhen.aliyuncs.com/gitee/logo.png">
</a>
</p>


<p align="center">
<a href='https://gitee.com/leiguoqing/springboot-quartz-lite'>springboot-quartz-lite</a> 的 starter 版本，开箱即用。
</p>

<p align="center">
    <a href="https://github.com/xuxueli/xxl-job/actions">
        <img src="https://github.com/xuxueli/xxl-job/workflows/Java%20CI/badge.svg" >
    </a>
    <a href="https://gitee.com/leiguoqing/quartz-lite-starter/releases/1.1.3">
        <img src="https://img.shields.io/badge/release-v1.1.3-green">
    </a>
    <a href='https://spring.io/projects/spring-boot'>
        <img alt="springboot-version" src="https://img.shields.io/badge/SpringBoot-2.2.9.RELEASE-orange">
    </a>
    <a href='http://www.quartz-scheduler.org/'>
        <img alt="Quartz-version" src="https://img.shields.io/badge/Quartz-2.3.2-blue">
    </a>
    <a href='https://mp.baomidou.com/'>
        <img alt="mybatis-plus-version" src="https://img.shields.io/badge/MybatisPlus-3.2.0-lightgrey?link=http://left&link=http://right">
    </a>
    <a href='https://www.apache.org/licenses/LICENSE-2.0'>
        <img alt="apache" src="https://img.shields.io/badge/license-Apache2-red?link=http://left&link=http://right">
    </a>
</p>


## 功能展示

### 登录

用户需要正常登录后，方可进入系统，若不登陆直接访问列表或日志页面，则会自动跳转至登录页面。每次登录有效时长为30分钟，过期后任何操作，也会跳转至登录页面。

帐号、密码、验证码均使用 RSA 加密，帐号、密码、RSA公钥、私钥可在application.yml中进行配置。

默认帐号、密码均为：admin。

登录接口添加 `参数签名+时间戳` 机制，时间戳用于防止 DDOS 攻击，参数签名防止被抓包恶意修改参数。

![](https://images.gitee.com/uploads/images/2020/0813/173640_adef7344_1425122.png)

### 任务列表

任务列表显示每个任务的基本信息，可对任务进行`立即执行`、`暂停`、`恢复`、`删除`、`修改`、`日志查询`操作。

对于不会写 cron 表达式的同学，可以点击左上角的 `在线生成Cron` 按钮进行生成。

右上角可以设置页面的自动刷新频率，默认一秒钟刷新一次。

右上角可以退出登录。

![](https://images.gitee.com/uploads/images/2020/0813/173640_cecbc007_1425122.png)

![](https://images.gitee.com/uploads/images/2020/0813/173640_0fe7e663_1425122.png)

### 任务日志

任务日志页面，可以看到对应任务的`执行时间`、`执行结果`、`执行成功或异常信息`。

任务的日志，按执行时间倒序排序。

任务执行失败时，可以配置是否需要发送邮件到指定邮箱，后面会讲如何配置。

![](https://images.gitee.com/uploads/images/2020/0813/173640_fb2754a5_1425122.png)

![](https://images.gitee.com/uploads/images/2020/0813/173640_e050c54a_1425122.png)

![](https://images.gitee.com/uploads/images/2020/0813/173640_1280671e_1425122.png)


## 使用说明

### 1、创建框架所需表

新建一个数据库或在已存在的数据库中，执行以下SQL脚本，即可创建框架所需表。

```sql
-- IN YOUR QUARTZ PROPERTIES FILE, YOU'LL NEED TO SET
-- ORG.QUARTZ.JOBSTORE.DRIVERDELEGATECLASS = ORG.QUARTZ.IMPL.JDBCJOBSTORE.STDJDBCDELEGATE
-- BY: RON CORDELL - RONCORDELL
--  I DIDN'T SEE THIS ANYWHERE, SO I THOUGHT I'D POST IT HERE.
--  THIS IS THE SCRIPT FROM QUARTZ TO CREATE THE TABLES IN A MYSQL DATABASE, MODIFIED TO USE INNODB INSTEAD OF MYISAM.

-- 该SQL适用于2.X版本
-- 1.X版本：HTTPS://BLOG.CSDN.NET/ZHU19774279/ARTICLE/DETAILS/44946645
-- QUARTZ表结构说明:HTTPS://WWW.CNBLOGS.COM/MEET/P/QUARTZ-BIAO-JIE-GOU-SHUO-MING.HTML

DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

CREATE TABLE QRTZ_JOB_DETAILS(
  SCHED_NAME VARCHAR(120) NOT NULL,
  JOB_NAME VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  JOB_CLASS_NAME VARCHAR(250) NOT NULL,
  IS_DURABLE VARCHAR(1) NOT NULL,
  IS_NONCONCURRENT VARCHAR(1) NOT NULL,
  IS_UPDATE_DATA VARCHAR(1) NOT NULL,
  REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
  JOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP))
  ENGINE=INNODB;

CREATE TABLE QRTZ_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  JOB_NAME VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  NEXT_FIRE_TIME BIGINT(13) NULL,
  PREV_FIRE_TIME BIGINT(13) NULL,
  PRIORITY INTEGER NULL,
  TRIGGER_STATE VARCHAR(16) NOT NULL,
  TRIGGER_TYPE VARCHAR(8) NOT NULL,
  START_TIME BIGINT(13) NOT NULL,
  END_TIME BIGINT(13) NULL,
  CALENDAR_NAME VARCHAR(200) NULL,
  MISFIRE_INSTR SMALLINT(2) NULL,
  JOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP))
  ENGINE=INNODB;

CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  REPEAT_COUNT BIGINT(7) NOT NULL,
  REPEAT_INTERVAL BIGINT(12) NOT NULL,
  TIMES_TRIGGERED BIGINT(10) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=INNODB;

CREATE TABLE QRTZ_CRON_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  CRON_EXPRESSION VARCHAR(120) NOT NULL,
  TIME_ZONE_ID VARCHAR(80),
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=INNODB;

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  STR_PROP_1 VARCHAR(512) NULL,
  STR_PROP_2 VARCHAR(512) NULL,
  STR_PROP_3 VARCHAR(512) NULL,
  INT_PROP_1 INT NULL,
  INT_PROP_2 INT NULL,
  LONG_PROP_1 BIGINT NULL,
  LONG_PROP_2 BIGINT NULL,
  DEC_PROP_1 NUMERIC(13,4) NULL,
  DEC_PROP_2 NUMERIC(13,4) NULL,
  BOOL_PROP_1 VARCHAR(1) NULL,
  BOOL_PROP_2 VARCHAR(1) NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=INNODB;

CREATE TABLE QRTZ_BLOB_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  BLOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=INNODB;

CREATE TABLE QRTZ_CALENDARS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  CALENDAR_NAME VARCHAR(200) NOT NULL,
  CALENDAR BLOB NOT NULL,
  PRIMARY KEY (SCHED_NAME,CALENDAR_NAME))
  ENGINE=INNODB;

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP))
  ENGINE=INNODB;

CREATE TABLE QRTZ_FIRED_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  ENTRY_ID VARCHAR(95) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  FIRED_TIME BIGINT(13) NOT NULL,
  SCHED_TIME BIGINT(13) NOT NULL,
  PRIORITY INTEGER NOT NULL,
  STATE VARCHAR(16) NOT NULL,
  JOB_NAME VARCHAR(200) NULL,
  JOB_GROUP VARCHAR(200) NULL,
  IS_NONCONCURRENT VARCHAR(1) NULL,
  REQUESTS_RECOVERY VARCHAR(1) NULL,
  PRIMARY KEY (SCHED_NAME,ENTRY_ID))
  ENGINE=INNODB;

CREATE TABLE QRTZ_SCHEDULER_STATE (
  SCHED_NAME VARCHAR(120) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
  CHECKIN_INTERVAL BIGINT(13) NOT NULL,
  PRIMARY KEY (SCHED_NAME,INSTANCE_NAME))
  ENGINE=INNODB;

CREATE TABLE QRTZ_LOCKS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME VARCHAR(40) NOT NULL,
  PRIMARY KEY (SCHED_NAME,LOCK_NAME))
  ENGINE=INNODB;

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);


/*Table structure for table `sys_task` */

DROP TABLE IF EXISTS `sys_task`;

CREATE TABLE `sys_task`
(
    `id`          bigint(64) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `task_name`   varchar(100) DEFAULT NULL COMMENT '任务名',
    `task_group`  varchar(50) DEFAULT NULL COMMENT '任务组',
    `task_class`  varchar(100) DEFAULT NULL COMMENT '执行类',
    `note`        varchar(50) DEFAULT NULL COMMENT '任务说明',
    `cron`        varchar(50) DEFAULT NULL COMMENT '定时规则',
    `exec_params` text COMMENT '执行参数',
    `exec_date`   datetime    DEFAULT NULL COMMENT '执行时间',
    `exec_result` tinyint(1)  DEFAULT NULL COMMENT '执行结果（成功:1、失败:0、正在执行：-1)',
    `concurrent`  tinyint(1)  DEFAULT '0' COMMENT '是否允许并发，0(false)：不允许 1（true）：允许',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator`     bigint(20)  DEFAULT NULL COMMENT '创建人',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modifier`    bigint(20)  DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_task_class` (`task_class`),
    UNIQUE KEY `uk_task_name_task_group` (`task_name`, `task_group`) COMMENT '任务名任务组复合唯一索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;



/*Data for the table `sys_task` */

/*Table structure for table `sys_task_log` */

DROP TABLE IF EXISTS `sys_task_log`;

CREATE TABLE `sys_task_log`
(
    `id`               bigint(64) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `task_name`        varchar(100) DEFAULT NULL COMMENT '任务名',
    `exec_date`        datetime    DEFAULT NULL COMMENT '执行时间',
    `exec_result`      tinyint(1)  DEFAULT NULL COMMENT '执行结果（成功:1、失败:0、正在执行：-1)',
    `exec_result_text` text COMMENT '成功信息或抛出的异常信息',
    `task_id`          bigint(20)  DEFAULT NULL COMMENT '任务id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


/*Data for the table `sys_task_log` */

COMMIT;
```

### 2、项目集成

1、引入 Maven 依赖

在 pom.xml 中添加依赖：

```xml
<dependencies>
    <!-- mysql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- 引入 Quartz-Lite 的 Starter -->
    <dependency>
        <groupId>com.gitee.leiguoqing.quartz-lite-starter</groupId>
        <artifactId>quartz-lite-spring-boot-starter</artifactId>
        <version>1.1.3</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

当然，如果你不想直接在项目的 pom.xml 文件里面加 `<repositories>`，也可以在 Maven setting.xml 中加，就像下面这样：

```xml
<profiles>
    <profile>
          <id>myProfile</id>
          <!-- 激活这个配置 -->
          <activation>
              <activeByDefault>true</activeByDefault>
              <jdk>1.8</jdk>
          </activation>

          <!-- 全局属性配置，让所有 maven 项目新建时默认都是 java8、UTF-8 -->
          <properties>
              <jdk.version>1.8</jdk.version>
              <maven.compiler.source>1.8</maven.compiler.source>
              <maven.compiler.target>1.8</maven.compiler.target>
              <encoding>UTF-8</encoding>
          </properties>
          
          <!--依赖仓库的一些配置，其主要作用是用来覆写 nexus 仓库的一些策略-->
          <repositories>

              <repository>
                  <id>aliyunRepositories</id>
                  <name>aliyunRepositories</name>
                  <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
                  <!-- 允许下载 releases 类型依赖 -->
                  <releases><enabled>true</enabled></releases>
                  <!-- 允许下载 snapshots 类型依赖 -->
                  <snapshots><enabled>true</enabled></snapshots>
              </repository>
      
              <repository>
                  <id>jitpack.io</id>
                  <name>jitpackRepositories</name>
                  <url>https://jitpack.io</url>
                  <!-- 允许下载 releases 类型依赖 -->
                  <releases><enabled>true</enabled></releases>
                  <!-- 允许下载 snapshots 类型依赖 -->
                  <snapshots><enabled>true</enabled></snapshots>
              </repository>
      
          </repositories>
          
          <!--插件仓库配置-->
          <pluginRepositories>
    
            <pluginRepository>
                <id>aliyunPluginRepositories</id>
                <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
                <!-- 允许下载 releases 类型依赖 -->
                <releases><enabled>true</enabled></releases>
                <!-- 允许下载 snapshots 类型依赖 -->
                <snapshots><enabled>true</enabled></snapshots>
            </pluginRepository>
      
            <pluginRepository>
                <id>jitpack.io</id>
                <url>https://jitpack.io</url>
                <!-- 允许下载 releases 类型依赖 -->
                <releases><enabled>true</enabled></releases>
                <!-- 允许下载 snapshots 类型依赖 -->
                <snapshots><enabled>true</enabled></snapshots>
            </pluginRepository>

          </pluginRepositories>
    </profile>
</profiles>
```

Maven settings.xml 中的 `<repositories>` 标签 和 pom.xml 中不一样，不能直接放在外面，需要放在 `<profiles>` 标签里面。

__重点来了__：如果你发现你无法获取到 Jar 包，很有可能是你的 Maven setting.xml 配置有问题。

你的 setting.xml 中是否有这样的配置？

```xml
<mirror>
    <id>nexus-aliyun</id>
    <mirrorOf>*</mirrorOf>
    <name>aliyun</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
</mirror>
```

如果是，那就是上面的 `<mirrorOf>` 配置出了问题，需要改成：

```xml
<mirror>
    <id>nexus-aliyun</id>
    <mirrorOf>*,!jitpack</mirrorOf>
    <name>aliyun</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
</mirror>
``` 

原因：如果把 `mirrorOf` 配置成 * ，那么所有的远程仓库都会阿里云上面找，但是 Quartz-Lite 的 Jar 是通过 jitpack 管理的，阿里云上面没有，所有要将 jitpack 排除掉，这样就能正常下载到 Jar 包了。

2、配置数据库

在 `application.yml` 中添加以下配置：

```yml
spring:
  # ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 数据源 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ #
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: {username}
    password: {password}
    url: jdbc:mysql://localhost:3306/{database}?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&autoReconnect=true&serverTimezone=UTC&rewriteBatchedStatements=true
```

3、配置 Quartz-Lite

Quartz-Lite 的配置是在 Quartz 框架的配置之上做增强，原本 Quartz 框架的配置该怎么配还是怎么配，下面给出一个参考配置：

```yml
spring:
  # ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ quartz配置 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ #
  quartz:
    ## 任务执行异常发送邮件配置
    mail:
      ## 是否启用
      enable: true
      ## 发送邮件的邮箱
      send-email-form: 'xxxxxxxx@qq.com'
      ## 接收邮件的邮箱，可配至多个
      send-email-to:
        - 'xxxxxxxx@qq.com'
    ## 任务页面配置
    task-view:
      # 帐号
      login-username: admin
      # 密码
      login-password: '123456'
    security:
      # 公钥、私钥配置
      auth:
        ## RSA 公钥
        pubKey: 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCV+cJIYadGdhQc84MlxsCOTZuyaeKAwImBkY23j4PdVaXh/8bRaz/KXI6V1ArgO1Q2vrDc177xfXVNgQZQz2SIApdJtXZzn/shZ73kT5xXqsUxE4L0bg9gF5IJ259ggQzG8S+OmzfJB4SUOrXvwIe8vJJXsHdxD136A0RGezC8QwIDAQAB'
        ## RSA 私钥
        priKey: 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJX5wkhhp0Z2FBzzgyXGwI5Nm7Jp4oDAiYGRjbePg91VpeH/xtFrP8pcjpXUCuA7VDa+sNzXvvF9dU2BBlDPZIgCl0m1dnOf+yFnveRPnFeqxTETgvRuD2AXkgnbn2CBDMbxL46bN8kHhJQ6te/Ah7y8klewd3EPXfoDREZ7MLxDAgMBAAECgYAFHoJYMCUijZNALbuzRWZ7NQD0hRK7LFdFOe+pbVel7W99GFrz+QIzaRdg290HLF9Cgx3MW/zjh1HCtH2/smSPBmiTizV6I4lD+WwWgSOQHrKTSSqYfnWUStu/8z5Gucp7vlExT5KULEoMLWCXNOlI+G8+5N4XLrFwunpUT26CAQJBAOOqdCLZuVl/UK0vZfwmJMkgDCQqcgiTanor4w8oQpfQn8FsNwRB8Y9n2V1fVmAkvrKhm1Ig0Q8Tj3vYWEF+NQECQQCopA55NFHKf/29LnzP9q8h4VdRUa6QRqjGtOhQ2GS9uWcQ/H1hpPZiABEaXF5iDAhBiHG9yEHXtUD/O4wQZ91DAkEAq2SVje8PRLs+R0MZqhwlMWz49vklZCNm05bal3ydtaEPxBPtzzy92FI8J7kwU60WC3Dyd3/RI2J8cKXMu3GCAQJAEfK785xk5BdxKvRKpluL0iBIicgWuxY6GkPgwdH2Dtcvp/gnZAAJlO6K43JXPToomsjpyhgJIesRitiMlKZpPwJBAKnvS/4R6c8VaYK+Vfhq7LLSZrGdmQTpd/cO794OkZgFeo34qoE3gHEx+ulflStkdhGms6oobkCd3W72E1vwzo4='
    #相关属性配置
    properties:
      org:
        quartz:
          # 线程调度器
          scheduler:
            # 线程调度器实例名
            instanceName: myQuartzScheduler
            instanceId: AUTO
          # 如何存储任务和触发器等信息
          jobStore:
            # 默认存储在内存中 org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore
            # 持久化
            class: org.quartz.impl.jdbcjobstore.JobStoreTX
            # 驱动代理
            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
            # 数据库中表的前缀
            tablePrefix: QRTZ_
            # 是否集群
            isClustered: true
            clusterCheckinInterval: 10000
            useProperties: false
            # 数据源命名
            dataSource: quartzDataSource
            misfireThreshold: 5000
          # 线程池
          threadPool:
            # 实例化ThreadPool时，使用的线程类为SimpleThreadPool
            class: org.quartz.simpl.SimpleThreadPool
            # 并发个数, 线程池的线程数，即最多3个任务同时跑
            threadCount: 3
            # 优先级
            threadPriority: 5
            threadsInheritContextClassLoaderOfInitializingThread: true
    #数据库方式
    job-store-type: jdbc
      #初始化表结构
    #jdbc:
    #initialize-schema: never
```

上面配置中的 `quartz.mail` 、 `quartz.task-view` 、 `quartz.security` 为 Quartz-Lite 框架的增强配置，下面来具体说下这几个配置是干啥用的。

1、 `quartz.mail` 主要是任务执行异常发送邮件的配置，如果不配置则不会启用该功能，此功能依赖于 spring-boot-starter-mail，所以要想使用此功能还得先配置 spring-boot-starter-mail，如下：

```yml
spring:
# ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 邮件 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ #
  mail:
    # 账户
    username: *********@qq.com
    # 密码[授权码]
    password: vmz*********bgee
    # host
    host: smtp.qq.com
    # 协议
    protocol: smtp
    # 端口
    port: 587
    # 编码
    default-encoding: UTF-8
    properties:
      mail:
        smtp:
          # 开启认证
          auth: true
          # 启用TLS校验,,某些邮箱[例如QQ的企业邮箱]需要TLS安全校验,同理有SSL校验
          startssl:
            enable: true
            required: true
          starttls:
            enable: true
            required: true
```

配置好 spring-boot-starter-mail 之后，还有确保 `quartz.mail.enable` 配置为 true，否则邮件功能还是不会启用的。


2、`quartz.task-view` 主要是配置任务可视化页面的登录账号、密码，如果不配置默认账号、密码均为 admin。


3、`quartz.security.auth` 主要是配置 RSA 加密所用到的公钥、私钥，不配置的话也会使用默认的，但是这里还是建议大家重新配置一下，可以自己写个 main 方法，然后调用框架中的 `com.leigq.quartzlite.autoconfigure.util.RsaCoder#generateKeyPair()` 方法生成新的公钥、私钥。

至此，Quartz-Lite 就配置好了，现在可以运行项目了，项目启动后，在浏览器输入：<http://localhost:8080/quartz-lite/login.html> 即可进入登录页面。

此时系统中是一个任务都没有的，下面给出一个任务的示例代码：

```java
package com.example.quartzlite.starter.test.task;

import com.leigq.quartzlite.starter.bean.job.BaseTaskExecute;
import com.leigq.quartzlite.starter.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Map;

/**
 * 简单的任务示例
 *
 * @author leigq <br>
 * @date ：2019-03-05 16:28 <br>
 */
@Slf4j
@Component
public class HelloQuartz extends BaseTaskExecute implements Serializable {

	/**
	 * 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
	 */
	private static final long serialVersionUID = 8969855105016200770L;

	@Resource
	private QuartzJobService quartzJobService;

	/**
	 * 每隔 2 秒钟执行一次
	 *
	 * @param dataMap the data map
	 */
	@Override
	public void execute(Map<String, Object> dataMap) throws InterruptedException {

		// */2 * * * * ?
		log.warn(">>>>>>>>>Hello Quartz1 start!");
		// 测试依赖注入
		log.warn("quartzJobService = {}", quartzJobService);
		// 测试获取任务参数
		log.warn("参数aaa的值 = [{}]", dataMap.get("aaa") + "");

		// 模拟耗时
		Thread.sleep(5 * 1000);
	}
}
```

新建任务说明：

- 继承 com.leigq.quartzlite.starter.bean.job.BaseTaskExecute 类，重写 execute 方法

- 在任务类上面加上 @Component 注解，使其受 Spring 托管

- dataMap 为执行任务的参数，在新增页面上可以看到此参数的配置方法。


## 需要注意的点

1、拦截器

如果你的项目里面有用到拦截器，请排除 Quartz-Lite 相关请求（Quartz-Lite 内部有自己的拦截器来处理登录功能），就像下面这样：

```java
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/* 添加testInterceptor拦截器 */
		TestInterceptor testInterceptor = new TestInterceptor();
		registry.addInterceptor(testInterceptor)
				// 添加拦截规则，先把所有路径都加入拦截，再一个个排除
				.addPathPatterns("/**")
				// 排除拦截，表示该路径不用拦截 TODO 排除 Quartz-Lite 的相关拦截
				.excludePathPatterns("/quartz-lite/**", "/quartz-lite/*.html", "/static/quartzlite/**");
	}
}
```

- __/quartz-lite/**__ : 所有Controller 请求，Quartz-Lite 的所有 Controller 请求都是以 `quartz-lite` 开头
- __/quartz-lite/*.html__ ：HTML 页面地址
- __/static/quartzlite/**__ ：静态资源


2、MyBatis、MyBatisPlus配置

如果你的项目是使用的 MyBatis 或 MyBatisPlus 框架，在配置 `mapper-locations` 属性时需要注意，像下面这样：

MyBatisPlus:

```yml
# ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ MyBatis Plus 配置 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ #
mybatis-plus:
  # mybatis mapper文件的位置
  mapper-locations:
    # 自己项目的 Mapper.xml 文件位置，MP 默认为 classpath*:/mapper/**/*.xml
    - classpath*:/mapper/**/*.xml
    # TODO Quartz-Lite 的 Mapper.xml 文件位置这里要配置，不然 Quartz-Lite的 Mapper.xml 文件无法解析
    - classpath*:/mapper/lite/*.xml
  configuration:
    # 使用mybatis插入自增主键ID的数据后返回自增的ID，配合keyProperty="id"使用
    use-generated-keys: true
    # 设置自动驼峰命名转换
    map-underscore-to-camel-case: true
```

仔细看上面的 `mapper-locations` 配置，如果你不是使用 MP 的默认配置的话，就需要把 `classpath*:/mapper/lite/*.xml` 加进去了。

MyBatis 的配置几乎一样，这里就不过多赘述了。


## 演示地址

[https://quartz-lite.cn.utools.club](https://quartz-lite.cn.utools.club/quartz-lite/login.html)

- 账号：admin
- 密码：123456
