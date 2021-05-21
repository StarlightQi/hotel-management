/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.5.40 : Database - hotel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hotel` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `hotel`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `power` int(11) NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`,`name`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`name`,`password`,`remarks`,`power`) values (1,'root','123123','超级管理员',1),(2,'小明','123123','普通管理员',0),(3,'大名','098623','普通管理员',0);

/*Table structure for table `chargeback` */

DROP TABLE IF EXISTS `chargeback`;

CREATE TABLE `chargeback` (
  `id` int(11) NOT NULL,
  `hid` int(11) DEFAULT NULL,
  `uid` varchar(30) DEFAULT NULL,
  `dtime` datetime NOT NULL COMMENT '下订单时间',
  `ttime` date DEFAULT NULL COMMENT '订单原本退订时间',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '这次的操作时间',
  `result` tinyint(1) DEFAULT '1' COMMENT '操作结果true正常，false为未及时处理',
  `reason` varchar(200) DEFAULT NULL COMMENT '产生这个结果的原因',
  `deduct` int(11) DEFAULT '0' COMMENT '操作为过时就扣除金额',
  PRIMARY KEY (`id`),
  KEY `hs` (`hid`),
  KEY `us` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `chargeback` */

insert  into `chargeback`(`id`,`hid`,`uid`,`dtime`,`ttime`,`ctime`,`result`,`reason`,`deduct`) values (1,2,'ludashen','2019-12-09 19:12:40','2019-12-09','2019-12-16 19:15:52',1,NULL,0),(2,3,'luasdas','2019-12-09 19:14:18','2019-12-09','2019-12-15 19:15:57',1,NULL,0),(3,7,'别再捏我脸了拉','2019-12-09 19:14:47','2019-12-27','2019-12-15 19:16:00',1,NULL,0),(4,4,'别再捏我脸了拉','2019-12-09 19:15:06','2019-12-13','2019-12-14 19:16:04',1,NULL,0),(5,8,'爱吃没谁了','2019-12-09 19:15:18','2019-12-21','2019-12-14 19:16:07',1,NULL,0),(6,2,'靖安司打杂','2019-12-09 19:15:29','2019-12-13','2019-12-16 19:15:52',1,NULL,0),(7,4,'wudidewo','2019-12-09 19:16:22','2019-12-21','2019-12-13 19:17:11',1,NULL,0),(8,7,'爱吃没谁了','2019-12-09 19:16:32','2019-12-14','2019-12-13 19:17:14',1,NULL,0),(9,11,'爱吃没谁了','2019-12-09 19:16:43','2019-12-27','2019-12-16 19:17:18',1,NULL,0),(10,11,'快乐的圈外人 ','2019-12-09 19:16:53','2019-12-22','2019-12-13 19:17:18',1,NULL,0),(11,10,'爱吃没谁了','2019-12-09 19:17:04','2019-12-19','2019-12-12 19:17:21',1,NULL,0),(12,6,'快乐的圈外人 ','2019-12-11 22:56:25','2019-12-08','2019-12-14 23:00:09',1,NULL,0),(13,9,'别再捏我脸了拉','2019-12-11 22:59:58','2019-12-07','2019-12-13 23:00:09',1,NULL,0),(16,2,'ludashen','2019-12-09 22:29:40','2019-12-09','2019-12-14 22:32:16',0,'没钱了',0),(17,15,'ludashen','2019-12-09 22:34:49','2019-12-09','2019-12-15 22:35:24',0,'没钱了',0);

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(30) NOT NULL,
  `hid` int(30) NOT NULL,
  `comment` varchar(100) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `hid` (`hid`),
  CONSTRAINT `hid` FOREIGN KEY (`hid`) REFERENCES `house` (`hid`),
  CONSTRAINT `uid` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

insert  into `comment`(`id`,`uid`,`hid`,`comment`,`time`) values (1,'别再捏我脸了拉',2,'非常棒','2019-12-19 19:22:24'),(2,'luasdas',2,'位置优越交通便利，3条地铁站出门几分钟就走到了。酒店设施齐全，房间干净整洁，住的很舒服。前台人员服务都很好，很主动，热情。早餐不错，品种多，个人觉得性价比蛮高。','2019-12-09 19:24:08'),(3,'luasdas',2,'价格在市区中不算贵，住宿的环境、卫生等比同价的快捷酒店好多了，服务态度规范，值得推荐，虽然装修时间比较长，但是无异味，这点缺好是优点。前台还有是党员，没想到。','2019-12-09 19:24:17'),(4,'luasdas',2,'早餐很丰盛，前台小姐姐非常好，看我们第一次来，给我们升级房型，住得太舒服了，下次还过来','2019-12-09 19:24:25'),(5,'luasdas',7,'地理位置好，交通方便，房间舒适卫生，服务人员很热情，乐于提供各种帮助，刘兰小姑娘非常热情，值得奖励','2019-12-09 19:24:44'),(6,'luasdas',7,'房间设施齐全，服务人员素质高，前台小姐姐还是位党员*^O^*正式挂牌的三星酒店，跟一些快捷商务酒店很不一样。早餐品种丰富，房间设施新','2019-12-09 19:24:55'),(7,'luasdas',8,'房间采光不错，卫生也搞的挺好的，房间旁边是个学校，足够安静，对于我这个对睡眠质量要求比较高的人来说，感觉可以了','2019-12-09 19:25:02'),(8,'luasdas',8,'来酒店 给麻烦升级 表示感谢 酒店保持着老牌三星的水准 特别是任何的服务员都彬彬有礼 特别客气 这个必须要赞 还有早餐也免费多给了一份 虽然早餐的品种不多 但是西点和中餐都一应','2019-12-09 19:25:10'),(9,'ludashen',2,'又小又破的酒店，纯粹是为了考试方便才订的。连停车都无法解决，来旅游的不建议你们住。服务也没什么好的，早餐也非常简单。','2019-12-09 19:26:26'),(10,'别再捏我脸了拉',7,'看离虹桥机场很近，就在携程上选择了这家酒店，入住体验还是不错的，总台服务员热情，客房好像是新装修的，虽然小但是真的很干净整洁。很好的体验！','2019-12-09 19:27:10'),(11,'别再捏我脸了拉',8,'酒店的前台人员服务挺好，很主动，早餐不错，品种多，房间不错，洗手间很干净，交通方便，这个地段，价格算是便宜的了，性价比高。','2019-12-09 19:27:30'),(12,'ludashen',2,'陆大哥好帅啊','2019-12-09 22:29:21'),(13,'ludashen',10,'陆大哥好帅啊','2019-12-09 22:34:30');

/*Table structure for table `ding` */

DROP TABLE IF EXISTS `ding`;

CREATE TABLE `ding` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hid` int(11) NOT NULL,
  `uid` varchar(30) NOT NULL,
  `sday` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `dday` date NOT NULL,
  PRIMARY KEY (`id`,`hid`),
  KEY `house` (`hid`),
  KEY `user` (`uid`),
  CONSTRAINT `house` FOREIGN KEY (`hid`) REFERENCES `house` (`hid`),
  CONSTRAINT `user` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `ding` */

insert  into `ding`(`id`,`hid`,`uid`,`sday`,`dday`) values (14,3,'舒淇qw','2019-12-09 19:17:54','2019-12-27'),(15,4,'wudidewo','2019-12-09 19:18:04','2019-12-13');

/*Table structure for table `house` */

DROP TABLE IF EXISTS `house`;

CREATE TABLE `house` (
  `hid` int(11) NOT NULL AUTO_INCREMENT,
  `hname` varchar(100) NOT NULL,
  `hdetails` varchar(200) DEFAULT NULL,
  `himg` varchar(100) DEFAULT NULL,
  `hprice` int(11) NOT NULL,
  PRIMARY KEY (`hid`,`hname`),
  KEY `hid` (`hid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `house` */

insert  into `house`(`hid`,`hname`,`hdetails`,`himg`,`hprice`) values (2,'豪华大房间','雨伞、房内保险箱、空调、衣柜/衣橱、暖气、针线包、220V电压插座、遮光窗帘、手动窗帘、床具:鸭绒被、床具:毯子或被子、沙发、开夜床、房间内高速上网、客房WIFI','1575870572187.png',900),(3,'华景观双床房','从美通社获知，凯悦酒店集团近日宣布，京都柏悦酒店正式落成开业。酒店地处这座历史名城的中心地带，以缤纷多姿、高雅奢华、珍贵罕有的体验引领宾客展开一场感味京都千载悠情的精彩旅程。','1575870703306.png',1000),(4,'典雅大床房 ','雨伞、房内保险箱、空调、衣柜/衣橱、暖气、针线包、220V电压插座、遮光窗帘、手动窗帘、床具:鸭绒被、床具:毯子或被子、沙发、开夜床、房间内高速上网、客房WIFI','1575871012503.png',5000),(6,'古朴典雅到包间','雨伞、房内保险箱、空调、衣柜/衣橱、暖气、针线包、220V电压插座、遮光窗帘、手动窗帘、床具:鸭绒被、床具:毯子或被子、沙发、开夜床、房间内高速上网、客房WIFI','1575871173312.png',5000),(7,'商务大床房','多种规格电源插座、110V电压插座、房内保险箱、空调、闹钟、针线包、220V电压插座、遮光窗帘、手动窗帘、备用床具、床具:鸭绒被、床具:毯子或被子、沙发、房间内高速上网、客房WIFi','1575871266403.png',300),(8,'特惠大床房(无窗)','让舒适与轻奢相拥；酒店圣罗拉餐厅环境舒适安逸，主打西餐、辅以粤菜、川菜、沪菜，在此享受由港粤名厨精心烹饪的佳肴，舌尖不由自主的跳起了芭蕾。早餐的菜品琳琅满目可达40种，为保证口味，餐品均选优质食材当天加工','1575871412336.png',2000),(9,'电竞客房','多种规格电源插座、110V电压插座、房内保险箱、空调、闹钟、针线包、220V电压插座、遮光窗帘、手动窗帘、备用床具、床具:鸭绒被、床具:毯子或被子、沙发、房间内高速','1575871481240.png',9000),(10,'亲子套房','多种规格电源插座、110V电压插座、房内保险箱、空调、闹钟、针线包、220V电压插座、遮光窗帘、手动窗帘、备用床具、床具:鸭绒被、床具:毯子或被子、沙发、房间内高速上网','1575871568039.png',2000),(11,'高级大床房','高级大床房','1575871633065.png',9000),(12,'气派大房','幅员辽阔，书桌、多种规格电源插座、110V电压插座、空调免费、衣柜/衣橱、针线包、220V电压插座、遮光窗帘、手动窗帘、床具:毯子或被子、开夜床、电子秤、房间内高速上网、客房WIFI','1575871741064.png',10),(13,'水下客房','水游城 holiday 酒店,舒适客房,儿童食宿全包,商务休闲相融合.会员更享低至85折房价,线上预订,价格更优,即刻预订,尽享舒适!','1575885006126.png',3000),(14,'豪华特大床房','房内保险箱，    空调，    针线包，    220V电压插座，    手动窗帘，    床具:毯子或被子，    房间内高速上网，    客房WIFI免费 媒体科技(3)有线频道，    电视机，    电话','1575885090084.png',9000),(15,'奇异大房','既省心又省钱,吃·住·行·游, 注意事项,让旅行不被套路,三亚水下酒店 亲身体会,经验分享,景点大全','1575885182477.png',200),(16,'商务客房','商务酒店的未来之路还很漫长，比如产业结构的非均衡发展、民族品牌的成长缓慢、持续竞争力弱化等一系列问题都需要在发展中加以解决。','1575885282386.png',3000),(17,'精选高尔夫露台房','(暖冬预付折上折)（仅适用内宾）','1575885357949.png',3000),(18,'亲子主题房','书桌、多种规格电源插座、110V电压插座、空调、衣柜/衣橱、针线包、220V电压插座、遮光窗帘、自动窗帘、坐卧两用长沙发、特长睡床(超过两米)、备用床具、床具:鸭绒被、床具:毯子或被子、沙发、开夜床、房间内高速上网、客房WIF','1575885487719.png',2222),(20,'情侣豪华房','本房源一楼为1.5米沙发床可以展开。楼上为一张大床房。同时能满足2-4人入住。','1575885609886.png',4544),(21,'市中心淮海中路新天地北','本房源位于南昌路上一楼老洋房LOFT，是上海经典老弄堂之一。毗邻淮海中路 新天地 K11 IAPM。位置特别好，出入交通都特别方便。步行13号线淮海中路地铁站仅2分钟，距离1/2/10/12号线仅5-8分钟。门口有许多网红店，也是下午茶的好去处。','1575886001681.png',453),(22,'最美旧租界','房间虽小，但是五脏俱全，有空调，除湿机，投影仪，冰箱，24小时热水，无线网，衣架，一客一换的布草，浴巾，牙刷牙膏，洗发水沐浴露，洗手液采光和隔音不是特别好，但是性价比绝佳。','1575886309080.png',600),(23,'睡眠研究所','厨房使用费：如需使用厨房，收取使用及清洁费50元。 如果当前房源已被预定,可点击头像LOGO页面最下方查看其他16个房源。房子为130平米的三室两厅，房主夫妇住在其中一间次卧室，您预订的这间为主卧朝南带独立卫生间，距离地铁站300米。','1575886373932.png',5000);

/*Table structure for table `roominfo` */

DROP TABLE IF EXISTS `roominfo`;

CREATE TABLE `roominfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `funtion` varchar(200) NOT NULL,
  `hnames` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hn` (`hnames`),
  CONSTRAINT `hn` FOREIGN KEY (`hnames`) REFERENCES `house` (`hid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `roominfo` */

insert  into `roominfo`(`id`,`name`,`funtion`,`hnames`) values (1,'建筑面积','17平方米',9),(2,'楼层','5-9,11-12层',9),(3,'窗户','有窗',9),(4,'媒体科技','国内长途电话、国际长途电话、有线频道、卫星频道、液晶电视机',9),(5,'建筑面积','17平方米',2),(6,'楼层','5-9,11-12层',2),(7,'建筑面积','18-22平方米',3),(8,'楼层','4-7层',3),(9,'浴室','拖鞋、浴室化妆放大镜、24小时热水、浴衣、独立淋浴间、吹风机、淋浴、洗浴用品',3),(10,'image','1575889650427.png',3),(11,'image','1575889669584.png',2),(12,'image','1575889688475.png',2),(13,'image','1575889738181.png',2),(14,'image','1575889744903.png',2),(15,'image','1575889754688.png',3),(16,'image','1575889794093.png',4),(17,'image','1575889800183.png',4),(18,'image','1575889809083.png',4),(19,'食品饮品','电热水壶、咖啡壶/茶壶、瓶装水',4);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` varchar(30) NOT NULL,
  `uName` varchar(100) DEFAULT NULL,
  `uPassword` varchar(100) NOT NULL,
  `uPhone` varchar(30) NOT NULL,
  `uBirthday` date NOT NULL,
  `uSex` tinyint(1) NOT NULL DEFAULT '1',
  `head` varchar(100) DEFAULT 'head.png',
  `money` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`),
  KEY `uSerid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`uid`,`uName`,`uPassword`,`uPhone`,`uBirthday`,`uSex`,`head`,`money`) values ('luasdas','luasdas','123123','1783453453','2000-01-01',0,'1575888594715.png',100),('ludashen','ludashen','123123','123123','2000-01-21',0,'1575886817930.png',201),('wudidewo','wudidewo','123123','1823423423','2000-01-30',0,'1575889026730.png',100),('别再捏我脸了拉','别再捏我脸了拉','123123','1434534512','2000-01-26',0,'1575889131457.png',-97),('快乐的圈外人 ','快乐的圈外人 ','123123','1387234324','1996-06-19',0,'1575888898966.png',3),('爱吃没谁了','爱吃没谁了','123123','1768734532','2000-01-20',1,'1575888794385.png',0),('舒淇qw','舒淇qw','123123','1843543543','2000-01-20',1,'1575888702083.png',100),('靖安司打杂','靖安司打杂','123123','1854654645','2000-01-22',0,'1575888961913.png',0);

/*!50106 set global event_scheduler = 1*/;

/* Event structure for event `ding_enent` */

/*!50106 DROP EVENT IF EXISTS `ding_enent`*/;

DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`localhost` EVENT `ding_enent` ON SCHEDULE EVERY 1 DAY STARTS '2019-12-10 02:00:00' ON COMPLETION NOT PRESERVE ENABLE DO INSERT INTO chargeback(id,hid,uid,dtime,ttime)SELECT id,hid,uid,sday,dday FROM ding WHERE CURDATE()>dday */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
