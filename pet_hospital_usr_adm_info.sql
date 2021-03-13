
DROP TABLE IF EXISTS `usr_normal`;

/* 用户主信息表，如果是有其他需求，直接把name拆出来，搞一个新表就行，这个表专给登录用 */
CREATE TABLE `usr_normal` (
  `usr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户唯一id',
  `status` varchar(255) DEFAULT NULL COMMENT '0 未激活或已经被禁用；1 可用状态, code就存redis',
  `password` varchar(255) DEFAULT NULL COMMENT '必须有，无论什么登陆方式，md5加盐加密，或者就一直三方登录吧' ,
  `user_mail` varchar(255) DEFAULT NULL, /* 或者不要这个，直接操作三方*/
  `user_name` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL COMMENT '第三方平台用户id',
  `source` varchar(255) DEFAULT NULL COMMENT '来源',
  PRIMARY KEY (`user_id`),
  KEY source_uuid (`source`,`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `adm`;

CREATE TABLE `adm` (
  `adm_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员唯一id' ,
  `password` varchar(255) DEFAULT NULL COMMENT '必须要有，无论什么登陆方式，md5加盐加密' ,
  `adm_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`adm_id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
