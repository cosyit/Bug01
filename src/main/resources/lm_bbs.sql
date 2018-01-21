use lmbbs;
  CREATE TABLE `lm_user` (
  `id` varchar(45) NOT NULL,     
  `loginName` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `nickName` varchar(128) DEFAULT NULL,
  `gender` varchar(16) DEFAULT NULL,
  `avatar` mediumblob,  
  `signature` varchar(255) DEFAULT NULL,
  `registrationTime` datetime DEFAULT NULL,
  `lastVisitTime` datetime DEFAULT NULL,
  `lastVisitIpAddr` varchar(50) DEFAULT NULL,
  `topicCount` int(11) DEFAULT NULL,                     
  `articleCount` int(11) DEFAULT NULL,                  
  `locked` bit(1) DEFAULT NULL,                           
  `autoLoginAuthKey` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)) ;
  
   	CREATE TABLE `lm_role` (
  `id` varchar(255) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `defaultForNewUser` bit(1) DEFAULT NULL,  
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `lm_user_role` (
  `userId` varchar(45) NOT NULL,
  `roleId` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `FK9C95CD553B7C6692` (`roleId`),
  KEY `FK9C95CD55E0A72A72` (`userId`),
  CONSTRAINT `FK9C95CD55E0A72A72` FOREIGN KEY (`userId`) REFERENCES `lm_user` (`id`),
  CONSTRAINT `FK9C95CD553B7C6692` FOREIGN KEY (`roleId`) REFERENCES `lm_role` (`id`)
);

CREATE TABLE `lm_systemprivilege` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `action` varchar(64) DEFAULT NULL,
  `resource` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `lm_role_systemprivileges` (
  `roleId` varchar(255) NOT NULL,
  `systemPrivilegeId` varchar(255) NOT NULL,
  PRIMARY KEY (`roleId`,`systemPrivilegeId`),
  KEY `FK70476C853B7C6692` (`roleId`),
  KEY `FK70476C8558D7FED6` (`systemPrivilegeId`),
  CONSTRAINT `FK70476C8558D7FED6` FOREIGN KEY (`systemPrivilegeId`) REFERENCES 
`lm_systemprivilege` (`id`),
  CONSTRAINT `FK70476C853B7C6692` FOREIGN KEY (`roleId`) REFERENCES `lm_role` (`id`)
) ;

INSERT INTO `lm_systemprivilege` VALUES 
('297e0592448b4e7201448b4ee117001e','发表主题','Create','Topic'),
('297e0592448b4e7201448b4ee117001f','查看主题','Retrieval','Topic'),
('297e0592448b4e7201448b4ee1170020','修改主题','Update','Topic'),
('297e0592448b4e7201448b4ee1170021','删除主题','Delete','Topic'),
('297e0592448b4e7201448b4ee1170022','移动主题','Move','Topic'),
('297e0592448b4e7201448b4ee1170023','发表回复','Create','Reply'),
('297e0592448b4e7201448b4ee1170024','查看回复','Retrieval','Reply'),
('297e0592448b4e7201448b4ee1170025','修改回复','Update','Reply'),
('297e0592448b4e7201448b4ee1170026','删除回复','Delete','Reply'),
('297e0592448b4e7201448b4ee1170027','发表附件','Create','Attachment'),
('297e0592448b4e7201448b4ee1170028','查看附件','Retrieval','Attachment'),
('297e0592448b4e7201448b4ee1170029','更新附件','Update','Attachment'),
('297e0592448b4e7201448b4ee117002a','删除附件','Delete','Attachment'),
('297e0592448b4e7201448b4ee117002b','下载附件','Download','Attachment'),
('297e0592448b4e7201448b4ee117002c','发表投票','Create','Poll'),
('297e0592448b4e7201448b4ee117002d','查看投票','Retrieval','Poll'),
('297e0592448b4e7201448b4ee117002e','修改投票','Update','Poll'),
('297e0592448b4e7201448b4ee117002f','删除投票','Delete','Poll'),
('297e0592448b4e7201448b4ee1170030','参与投票','Vote','Poll'),
('297e0592448b4e7201448b4ee1170031','管理系统','Manage','System');

-- 第二大块数据层

CREATE TABLE `lm_category` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `lm_forum` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `myorder` int(11) DEFAULT NULL,
  `categoryId` varchar(255) DEFAULT NULL,
  `topicCount` int(11) DEFAULT NULL,
  `articleCount` int(11) DEFAULT NULL,
  `lastTopicId` varchar(255) DEFAULT NULL,
  `lastArticlePostTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK903A938CCE73292` (`categoryId`),
  CONSTRAINT `FK903A938CCE73292` FOREIGN KEY (`categoryId`) REFERENCES `lm_category` 
(`id`)
) ;

CREATE TABLE `lm_topic` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `authorId` varchar(45) DEFAULT NULL,
  `postTime` datetime DEFAULT NULL,
  `ipAddr` varchar(16) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `forumId` varchar(255) DEFAULT NULL,
  `summary` text,
  `viewCount` int(11) DEFAULT NULL,
  `lastReplyId` varchar(255) DEFAULT NULL,
  `lastArticlePostTime` datetime DEFAULT NULL,
  `nextFloor` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK90FFD39A5FF9C116` (`forumId`),
  KEY `FK6899E4A141601CB290ffd39a` (`authorId`),
  CONSTRAINT `FK6899E4A141601CB290ffd39a` FOREIGN KEY (`authorId`) REFERENCES 
`lm_user` (`id`),
  CONSTRAINT `FK90FFD39A5FF9C116` FOREIGN KEY (`forumId`) REFERENCES `lm_forum` 
(`id`)
);

alter table lm_forum 
add foreign key (lastTopicId) REFERENCES lm_topic(id);

CREATE TABLE `lm_reply` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `authorId` varchar(45) DEFAULT NULL,
  `postTime` datetime DEFAULT NULL,
  `ipAddr` varchar(50) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `floor` int(11) DEFAULT NULL,
  `topicId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK90DF1955AF81E56` (`topicId`),
  KEY `FK6899E4A141601CB290df1955` (`authorId`),
  CONSTRAINT `FK6899E4A141601CB290df1955` FOREIGN KEY (`authorId`) REFERENCES 
`lm_user` (`id`),
  CONSTRAINT `FK90DF1955AF81E56` FOREIGN KEY (`topicId`) REFERENCES `lm_topic` (`id`)
);

alter table lm_topic 
add foreign key (lastReplyId) REFERENCES lm_reply(id);


