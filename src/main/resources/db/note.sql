-- 1. 创建数据库 note（若不存在）
CREATE DATABASE IF NOT EXISTS `note`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

-- 2. 切换到 note 数据库
USE `note`;

--------------------------------------------------------------------------------
-- 3. 创建用户表 t_user
--------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `t_user` (
    `id` BIGINT AUTO_INCREMENT COMMENT '主键，自增',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（加密存储）',
    `nickname` VARCHAR(100) DEFAULT NULL COMMENT '用户昵称，选填',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱，选填',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '电话，选填',
    `role` VARCHAR(50) DEFAULT 'user' COMMENT '角色（user/admin等）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：正常；1：已删除）',
    PRIMARY KEY (`id`),
    KEY `idx_username` (`username`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

--------------------------------------------------------------------------------
-- 4. 创建笔记表 t_note
--------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `t_note` (
    `id` BIGINT AUTO_INCREMENT COMMENT '主键，自增',
    `user_id` BIGINT NOT NULL DEFAULT 0 COMMENT '所属用户ID，如需多用户可外键关联 t_user.id',
    `title` VARCHAR(255) NOT NULL COMMENT '笔记标题',
    `content` TEXT NOT NULL COMMENT '笔记正文',
    `summary` VARCHAR(500) DEFAULT NULL COMMENT '笔记摘要，选填',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：正常；1：已删除）',
    `is_pinned` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否置顶（0：未置顶；1：置顶）',
    `last_view_time` DATETIME DEFAULT NULL COMMENT '最后阅读时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_title` (`title`)
    -- 如需在数据库层强制外键约束，可打开下列注释
    -- ,FOREIGN KEY (`user_id`) REFERENCES `t_user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='笔记表';

--------------------------------------------------------------------------------
-- 5. 创建标签表 t_tag
--------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `t_tag` (
    `id` BIGINT AUTO_INCREMENT COMMENT '主键，自增',
    `user_id` BIGINT NOT NULL DEFAULT 0 COMMENT '所属用户ID，多用户场景可外键关联 t_user.id',
    `name` VARCHAR(100) NOT NULL COMMENT '标签名称',
    `color` VARCHAR(50) DEFAULT NULL COMMENT '标签对应的颜色(如#FF0000)',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '标签描述或其他说明',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：正常；1：已删除）',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_name` (`name`)
    -- 如需外键，打开下列注释
    -- ,FOREIGN KEY (`user_id`) REFERENCES `t_user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

--------------------------------------------------------------------------------
-- 6. 创建笔记-标签关联表 t_note_tag
--------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `t_note_tag` (
    `id` BIGINT AUTO_INCREMENT COMMENT '主键，自增',
    `note_id` BIGINT NOT NULL COMMENT '笔记ID，关联 t_note 表的 id',
    `tag_id` BIGINT NOT NULL COMMENT '标签ID，关联 t_tag 表的 id',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关联创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '关联更新时间',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：正常；1：已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_note_tag` (`note_id`, `tag_id`),
    KEY `idx_note_id` (`note_id`),
    KEY `idx_tag_id` (`tag_id`)
    -- 如需外键，打开下列注释
    -- ,FOREIGN KEY (`note_id`) REFERENCES `t_note`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    -- ,FOREIGN KEY (`tag_id`) REFERENCES `t_tag`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='笔记与标签关系表';

--------------------------------------------------------------------------------
-- 7. 创建相册表 t_album
--------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `t_album` (
    `id` BIGINT AUTO_INCREMENT COMMENT '主键，自增',
    `user_id` BIGINT NOT NULL DEFAULT 0 COMMENT '所属用户ID，如需多用户可外键关联 t_user.id',
    `album_name` VARCHAR(255) NOT NULL COMMENT '相册名称',
    `cover_url` VARCHAR(255) DEFAULT NULL COMMENT '相册封面图片的URL地址',
    `description` TEXT DEFAULT NULL COMMENT '相册描述信息',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：正常；1：已删除）',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
    -- ,FOREIGN KEY (`user_id`) REFERENCES `t_user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='相册表';

--------------------------------------------------------------------------------
-- 8. 创建图片表 t_photo
--------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `t_photo` (
    `id` BIGINT AUTO_INCREMENT COMMENT '主键，自增',
    `album_id` BIGINT NOT NULL COMMENT '关联的相册ID，对应 t_album.id',
    `user_id` BIGINT NOT NULL DEFAULT 0 COMMENT '所属用户ID，多用户场景可外键关联 t_user.id',
    `file_name` VARCHAR(255) NOT NULL COMMENT '图片原始文件名',
    `file_url` VARCHAR(255) NOT NULL COMMENT '图片文件的URL访问地址',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '对图片的描述或备注',
    `file_type` VARCHAR(50) DEFAULT 'image/jpeg' COMMENT '文件类型，如 image/jpeg、image/png 等',
    `file_size` BIGINT DEFAULT 0 COMMENT '文件大小（字节）',
    `sort_order` INT DEFAULT 0 COMMENT '自定义排序序号，数值越大越靠前',
    `access_level` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '访问权限（0：公开；1：私有；2：好友可见 等）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建/上传时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：正常；1：已删除）',
    PRIMARY KEY (`id`),
    KEY `idx_album_id` (`album_id`),
    KEY `idx_user_id` (`user_id`)
    -- ,FOREIGN KEY (`album_id`) REFERENCES `t_album`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    -- ,FOREIGN KEY (`user_id`) REFERENCES `t_user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图片表，用于存储相册中的具体图片信息';

--------------------------------------------------------------------------------
-- 9. 创建AI聊天记录表 t_ai_chat
--------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `t_ai_chat` (
    `id` BIGINT AUTO_INCREMENT COMMENT '主键，自增',
    `session_id` VARCHAR(64) NOT NULL COMMENT '会话ID，用于区分一段连续对话',
    `user_id` BIGINT NOT NULL DEFAULT 0 COMMENT '用户ID，表示消息归属，关联 t_user.id',
    `sender_role` VARCHAR(50) NOT NULL COMMENT '发送方角色：user/system/assistant 等',
    `content` TEXT NOT NULL COMMENT '聊天文本内容',
    `model_name` VARCHAR(100) DEFAULT NULL COMMENT 'AI模型名称,如gpt-3.5-turbo',
    `prompt_tokens` INT DEFAULT 0 COMMENT '提示tokens数量',
    `completion_tokens` INT DEFAULT 0 COMMENT '回复tokens数量',
    `total_tokens` INT DEFAULT 0 COMMENT '总tokens数量',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息生成/发送时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：正常；1：已删除）',
    PRIMARY KEY (`id`),
    KEY `idx_session_id` (`session_id`),
    KEY `idx_user_id` (`user_id`)
    -- ,FOREIGN KEY (`user_id`) REFERENCES `t_user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI聊天记录表';

--------------------------------------------------------------------------------
-- 10. 创建文档表 t_document
--------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `t_document` (
    `id` BIGINT AUTO_INCREMENT COMMENT '主键，自增',
    `user_id` BIGINT NOT NULL DEFAULT 0 COMMENT '文档所属用户ID，关联 t_user.id',
    `doc_name` VARCHAR(255) NOT NULL COMMENT '文档名称',
    `doc_type` VARCHAR(50) DEFAULT 'application/pdf' COMMENT '文档类型(MIME)',
    `doc_url` VARCHAR(255) NOT NULL COMMENT '文档存储URL或路径',
    `doc_size` BIGINT DEFAULT 0 COMMENT '文档大小（字节）',
    `description` TEXT DEFAULT NULL COMMENT '文档简介或备注',
    `access_level` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '访问权限（0公开，1私有，2其他）',
    `version_num` INT NOT NULL DEFAULT 1 COMMENT '文档版本号',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文档创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '文档最后更新时间',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：正常；1：已删除）',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
    -- ,FOREIGN KEY (`user_id`) REFERENCES `t_user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档表，用于存储用户上传或创建的文档信息';

--------------------------------------------------------------------------------
-- 11. 创建文档标签关联表 t_document_tag
--------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `t_document_tag` (
    `id` BIGINT AUTO_INCREMENT COMMENT '主键，自增',
    `document_id` BIGINT NOT NULL COMMENT '关联的文档ID，对应 t_document.id',
    `tag_id` BIGINT NOT NULL COMMENT '关联的标签ID，对应 t_tag.id',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关联创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '关联更新时间',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：正常；1：已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_document_tag` (`document_id`, `tag_id`),
    KEY `idx_document_id` (`document_id`),
    KEY `idx_tag_id` (`tag_id`)
    -- ,FOREIGN KEY (`document_id`) REFERENCES `t_document`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    -- ,FOREIGN KEY (`tag_id`) REFERENCES `t_tag`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档与标签关联表';

--------------------------------------------------------------------------------
-- == 以下为初始测试数据插入示例 ==
--------------------------------------------------------------------------------

-- 1) 插入示例用户
INSERT INTO `t_user` (username, password, nickname, email, phone, role) VALUES
     ('admin', 'admin_pass', '管理员', 'admin@example.com', '13000000000', 'admin'),
     ('test', 'test_pass', '测试用户', 'test@example.com', '13100000000', 'user');

-- 2) 插入示例笔记 (假设 user_id=1 为 admin, user_id=2 为 test)
INSERT INTO `t_note` (user_id, title, content, summary, is_pinned, last_view_time)
VALUES
    (1, '管理笔记标题1', '这里是管理员的笔记内容', '管理员的笔记摘要', 0, '2025-01-01 10:00:00'),
    (2, '测试笔记标题1', '这里是测试用户的笔记内容', '测试笔记摘要', 1, '2025-01-02 09:00:00');

-- 3) 插入示例标签 (两个用户各有一个标签)
INSERT INTO `t_tag` (user_id, name, color, description)
VALUES
    (1, '工作', '#FF0000', '管理员的工作标签'),
    (2, '学习', '#00FF00', '测试用户的学习标签');

-- 4) 为笔记绑定标签 (假设 note_id=1 对应 admin 的笔记， tag_id=1 对应管理员标签)
--    (假设 note_id=2 对应 test 的笔记，  tag_id=2 对应测试用户标签)
INSERT INTO `t_note_tag` (note_id, tag_id)
VALUES
    (1, 1),
    (2, 2);

-- 5) 插入示例相册 (admin的相册 & test的相册)
INSERT INTO `t_album` (user_id, album_name, cover_url, description)
VALUES
    (1, '管理员相册A', 'http://example.com/cover_a.jpg', '存放管理员照片'),
    (2, '测试用户相册B', 'http://example.com/cover_b.jpg', '存放测试用户照片');

-- 6) 插入示例图片 (photo) (与上面相册对应)
INSERT INTO `t_photo` (album_id, user_id, file_name, file_url, description, file_type, file_size, sort_order, access_level)
VALUES
    (1, 1, 'admin_photo.jpg', 'http://example.com/admin_photo.jpg', '管理员的照片', 'image/jpeg', 2048, 1, 0),
    (2, 2, 'test_photo.png', 'http://example.com/test_photo.png', '测试用户的照片', 'image/png', 4096, 2, 1);

-- 7) 插入示例AI聊天记录 (session_id可随意模拟，如 'abc123')
INSERT INTO `t_ai_chat` (session_id, user_id, sender_role, content, model_name)
VALUES
    ('abc123', 1, 'user', '你好，AI！', 'gpt-3.5-turbo'),
    ('abc123', 1, 'assistant', '你好，管理员！很高兴为您服务。', 'gpt-3.5-turbo'),
    ('xyz789', 2, 'user', '帮我写个测试脚本', 'gpt-3.5-turbo'),
    ('xyz789', 2, 'assistant', '好的，这是一个测试脚本示例...', 'gpt-3.5-turbo');

-- 8) 插入示例文档
INSERT INTO `t_document` (user_id, doc_name, doc_type, doc_url, doc_size, description, access_level, version_num)
VALUES
    (1, '项目需求文档', 'application/pdf', 'http://example.com/requirement.pdf', 102400, '需求说明书v1', 0, 1),
    (2, '测试报告', 'application/msword', 'http://example.com/test_report.doc', 204800, '测试报告v2', 1, 2);

-- 9) 插入示例文档-标签关联 (假设文档id=1 -> 管理员文档, tag_id=1 -> 管理员标签)
--                            (假设文档id=2 -> 测试用户文档, tag_id=2 -> 测试用户标签)
INSERT INTO `t_document_tag` (document_id, tag_id)
VALUES
    (1, 1),
    (2, 2);

-- 结束: 所有表和示例数据插入完成