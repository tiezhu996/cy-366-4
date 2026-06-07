CREATE TABLE IF NOT EXISTS operation_records (
  id INT AUTO_INCREMENT PRIMARY KEY,
  module_name VARCHAR(120) NOT NULL,
  owner_name VARCHAR(80) NOT NULL,
  status VARCHAR(40) NOT NULL,
  metric VARCHAR(40) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS members (
  id INT AUTO_INCREMENT PRIMARY KEY,
  member_no VARCHAR(32) UNIQUE NOT NULL,
  nickname VARCHAR(64) NOT NULL,
  avatar_url VARCHAR(255),
  level INT DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS game_types (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(32) UNIQUE NOT NULL,
  name VARCHAR(64) NOT NULL,
  icon_url VARCHAR(255),
  sort_order INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS play_sessions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  member_id INT NOT NULL,
  game_type_id INT NOT NULL,
  seat_no VARCHAR(32) NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME,
  duration_minutes INT DEFAULT 0,
  status VARCHAR(20) DEFAULT 'active',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_member_time (member_id, start_time),
  INDEX idx_game_time (game_type_id, start_time),
  FOREIGN KEY (member_id) REFERENCES members(id),
  FOREIGN KEY (game_type_id) REFERENCES game_types(id)
);

INSERT INTO operation_records (module_name, owner_name, status, metric)
VALUES ('机位/包厢实时状态看板', '运营组', 'ready', '100%');

INSERT INTO members (member_no, nickname, avatar_url, level) VALUES
('M001', '电竞小王子', NULL, 5),
('M002', '暗夜游侠', NULL, 4),
('M003', '游戏达人', NULL, 6),
('M004', '孤独的Carry', NULL, 3),
('M005', '全图视野', NULL, 5),
('M006', '残血反杀', NULL, 4),
('M007', '一枪爆头', NULL, 7),
('M008', '五杀专业户', NULL, 5),
('M009', '佛系玩家', NULL, 2),
('M010', '上分机器', NULL, 6);

INSERT INTO game_types (code, name, sort_order) VALUES
('LOL', '英雄联盟', 1),
('CSGO', 'CS2', 2),
('VALORANT', '无畏契约', 3),
('DOTA2', 'DOTA2', 4),
('WZRY', '王者荣耀', 5),
('PUBG', '绝地求生', 6),
('APEX', 'Apex英雄', 7),
('OTHER', '其他游戏', 99);

INSERT INTO play_sessions (member_id, game_type_id, seat_no, start_time, end_time, duration_minutes, status) VALUES
(1, 1, 'A01', DATE_SUB(NOW(), INTERVAL 2 HOUR), NOW(), 120, 'completed'),
(1, 1, 'A01', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 22 HOUR), 180, 'completed'),
(1, 2, 'B03', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 240, 'completed'),
(2, 1, 'A02', DATE_SUB(NOW(), INTERVAL 4 HOUR), DATE_SUB(NOW(), INTERVAL 1 HOUR), 180, 'completed'),
(2, 3, 'C01', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 300, 'completed'),
(3, 5, 'D02', DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 30 MINUTE), 150, 'completed'),
(3, 1, 'A05', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), 420, 'completed'),
(3, 2, 'B01', DATE_SUB(NOW(), INTERVAL 6 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR), 240, 'completed'),
(4, 4, 'E01', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 20 HOUR), 90, 'completed'),
(4, 1, 'A03', DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), 360, 'completed'),
(5, 1, 'A01', DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), 480, 'completed'),
(5, 6, 'F02', DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 30 MINUTE), 90, 'completed'),
(6, 2, 'B02', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 270, 'completed'),
(6, 3, 'C02', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 18 HOUR), 120, 'completed'),
(7, 2, 'B05', DATE_SUB(NOW(), INTERVAL 5 HOUR), NOW(), 300, 'active'),
(7, 7, 'G01', DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 540, 'completed'),
(8, 1, 'A06', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), 600, 'completed'),
(8, 5, 'D01', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 12 HOUR), 180, 'completed'),
(9, 8, 'H01', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 60, 'completed'),
(10, 1, 'A08', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 360, 'completed'),
(10, 3, 'C03', DATE_SUB(NOW(), INTERVAL 6 HOUR), DATE_SUB(NOW(), INTERVAL 1 HOUR), 300, 'completed');
