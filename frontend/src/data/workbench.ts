import type { FeatureItem, KpiItem, OperationRecord, LeaderboardResponse, GameType } from "../types";

export const localFeatures: FeatureItem[] = [
  {
    "id": 1,
    "title": "机位/包厢实时状态看板",
    "description": "以网格或列表形式展示所有机位/包厢的实时状态（空闲/使用中/故障/预约），支持按区域筛选，点击可查看详情。",
    "status": "已上线",
    "metric": "88%"
  },
  {
    "id": 2,
    "title": "会员充值与时长包",
    "description": "会员账户支持充值余额，购买时长包（如10小时/30小时/月卡），消费时优先扣除时长包余额，不足时扣余额。",
    "status": "排期中",
    "metric": "31 单"
  },
  {
    "id": 3,
    "title": "机位预约与续费",
    "description": "会员可提前预约指定机位和时段，到店扫码开机，使用过程中可续费延长上机时间，快到期前提醒续费。",
    "status": "巡检中",
    "metric": "10 项"
  },
  {
    "id": 4,
    "title": "上机时长排行榜",
    "description": "记录会员累计上机时长，生成日/周/月排行榜，激励高频玩家，支持按游戏类型分类统计。",
    "status": "优化中",
    "metric": "4 级"
  },
  {
    "id": 5,
    "title": "赛事报名与战队管理",
    "description": "门店发布电竞赛事（如LOL/CSGO/王者荣耀），玩家以个人或战队形式报名，系统自动抽签分组，记录比赛结果和战绩。",
    "status": "可导出",
    "metric": "28 条"
  }
];

export const localKpis: KpiItem[] = [
  {
    "label": "今日处理",
    "value": "106",
    "trend": "+12%",
    "tone": "primary"
  },
  {
    "label": "预约/订单",
    "value": "43",
    "trend": "+8%",
    "tone": "warm"
  },
  {
    "label": "履约率",
    "value": "91%",
    "trend": "+3%",
    "tone": "cool"
  },
  {
    "label": "待处理",
    "value": "10",
    "trend": "需跟进",
    "tone": "neutral"
  }
];

export const operationRecords: OperationRecord[] = [
  {
    "key": "ldesportsbar-1",
    "name": "机位/包厢实时状态看板",
    "owner": "运营组",
    "status": "已上线",
    "metric": "88%",
    "priority": "高"
  },
  {
    "key": "ldesportsbar-2",
    "name": "会员充值与时长包",
    "owner": "管理员",
    "status": "排期中",
    "metric": "31 单",
    "priority": "中"
  },
  {
    "key": "ldesportsbar-3",
    "name": "机位预约与续费",
    "owner": "服务台",
    "status": "巡检中",
    "metric": "10 项",
    "priority": "低"
  },
  {
    "key": "ldesportsbar-4",
    "name": "上机时长排行榜",
    "owner": "财务组",
    "status": "优化中",
    "metric": "4 级",
    "priority": "高"
  },
  {
    "key": "ldesportsbar-5",
    "name": "赛事报名与战队管理",
    "owner": "审核组",
    "status": "可导出",
    "metric": "28 条",
    "priority": "中"
  }
];

export const gameTypes: GameType[] = [
  { code: "ALL", name: "全部游戏", iconUrl: null },
  { code: "LOL", name: "英雄联盟", iconUrl: null },
  { code: "CSGO", name: "CS2", iconUrl: null },
  { code: "VALORANT", name: "无畏契约", iconUrl: null },
  { code: "DOTA2", name: "DOTA2", iconUrl: null },
  { code: "WZRY", name: "王者荣耀", iconUrl: null },
  { code: "PUBG", name: "绝地求生", iconUrl: null },
  { code: "APEX", name: "Apex英雄", iconUrl: null },
  { code: "OTHER", name: "其他游戏", iconUrl: null }
];

export const localLeaderboard: LeaderboardResponse = {
  daily: {
    period: "daily",
    gameTypeCode: "ALL",
    items: [
      { rank: 1, memberNo: "M007", nickname: "一枪爆头", avatarUrl: null, level: 7, totalMinutes: 300, formattedDuration: "5小时0分", sessionCount: 1, favoriteGame: "CS2" },
      { rank: 2, memberNo: "M001", nickname: "电竞小王子", avatarUrl: null, level: 5, totalMinutes: 120, formattedDuration: "2小时0分", sessionCount: 1, favoriteGame: "英雄联盟" },
      { rank: 3, memberNo: "M010", nickname: "上分机器", avatarUrl: null, level: 6, totalMinutes: 300, formattedDuration: "5小时0分", sessionCount: 1, favoriteGame: "无畏契约" },
      { rank: 4, memberNo: "M003", nickname: "游戏达人", avatarUrl: null, level: 6, totalMinutes: 390, formattedDuration: "6小时30分", sessionCount: 2, favoriteGame: "英雄联盟" },
      { rank: 5, memberNo: "M002", nickname: "暗夜游侠", avatarUrl: null, level: 4, totalMinutes: 180, formattedDuration: "3小时0分", sessionCount: 1, favoriteGame: "英雄联盟" },
      { rank: 6, memberNo: "M005", nickname: "全图视野", avatarUrl: null, level: 5, totalMinutes: 90, formattedDuration: "1小时30分", sessionCount: 1, favoriteGame: "绝地求生" },
      { rank: 7, memberNo: "M008", nickname: "五杀专业户", avatarUrl: null, level: 5, totalMinutes: 180, formattedDuration: "3小时0分", sessionCount: 1, favoriteGame: "王者荣耀" },
      { rank: 8, memberNo: "M006", nickname: "残血反杀", avatarUrl: null, level: 4, totalMinutes: 120, formattedDuration: "2小时0分", sessionCount: 1, favoriteGame: "无畏契约" },
      { rank: 9, memberNo: "M004", nickname: "孤独的Carry", avatarUrl: null, level: 3, totalMinutes: 90, formattedDuration: "1小时30分", sessionCount: 1, favoriteGame: "DOTA2" },
      { rank: 10, memberNo: "M009", nickname: "佛系玩家", avatarUrl: null, level: 2, totalMinutes: 0, formattedDuration: "0小时0分", sessionCount: 0, favoriteGame: "其他游戏" }
    ],
    availableGameTypes: gameTypes,
    updatedAt: Math.floor(Date.now() / 1000)
  },
  weekly: {
    period: "weekly",
    gameTypeCode: "ALL",
    items: [
      { rank: 1, memberNo: "M008", nickname: "五杀专业户", avatarUrl: null, level: 5, totalMinutes: 780, formattedDuration: "13小时0分", sessionCount: 2, favoriteGame: "英雄联盟" },
      { rank: 2, memberNo: "M007", nickname: "一枪爆头", avatarUrl: null, level: 7, totalMinutes: 840, formattedDuration: "14小时0分", sessionCount: 2, favoriteGame: "CS2" },
      { rank: 3, memberNo: "M005", nickname: "全图视野", avatarUrl: null, level: 5, totalMinutes: 570, formattedDuration: "9小时30分", sessionCount: 2, favoriteGame: "英雄联盟" },
      { rank: 4, memberNo: "M003", nickname: "游戏达人", avatarUrl: null, level: 6, totalMinutes: 810, formattedDuration: "13小时30分", sessionCount: 3, favoriteGame: "英雄联盟" },
      { rank: 5, memberNo: "M010", nickname: "上分机器", avatarUrl: null, level: 6, totalMinutes: 660, formattedDuration: "11小时0分", sessionCount: 2, favoriteGame: "英雄联盟" },
      { rank: 6, memberNo: "M001", nickname: "电竞小王子", avatarUrl: null, level: 5, totalMinutes: 540, formattedDuration: "9小时0分", sessionCount: 3, favoriteGame: "英雄联盟" },
      { rank: 7, memberNo: "M002", nickname: "暗夜游侠", avatarUrl: null, level: 4, totalMinutes: 480, formattedDuration: "8小时0分", sessionCount: 2, favoriteGame: "英雄联盟" },
      { rank: 8, memberNo: "M004", nickname: "孤独的Carry", avatarUrl: null, level: 3, totalMinutes: 450, formattedDuration: "7小时30分", sessionCount: 2, favoriteGame: "DOTA2" },
      { rank: 9, memberNo: "M006", nickname: "残血反杀", avatarUrl: null, level: 4, totalMinutes: 390, formattedDuration: "6小时30分", sessionCount: 2, favoriteGame: "CS2" },
      { rank: 10, memberNo: "M009", nickname: "佛系玩家", avatarUrl: null, level: 2, totalMinutes: 60, formattedDuration: "1小时0分", sessionCount: 1, favoriteGame: "其他游戏" }
    ],
    availableGameTypes: gameTypes,
    updatedAt: Math.floor(Date.now() / 1000)
  },
  monthly: {
    period: "monthly",
    gameTypeCode: "ALL",
    items: [
      { rank: 1, memberNo: "M008", nickname: "五杀专业户", avatarUrl: null, level: 5, totalMinutes: 3180, formattedDuration: "53小时0分", sessionCount: 8, favoriteGame: "英雄联盟" },
      { rank: 2, memberNo: "M007", nickname: "一枪爆头", avatarUrl: null, level: 7, totalMinutes: 3240, formattedDuration: "54小时0分", sessionCount: 7, favoriteGame: "CS2" },
      { rank: 3, memberNo: "M003", nickname: "游戏达人", avatarUrl: null, level: 6, totalMinutes: 2910, formattedDuration: "48小时30分", sessionCount: 9, favoriteGame: "英雄联盟" },
      { rank: 4, memberNo: "M010", nickname: "上分机器", avatarUrl: null, level: 6, totalMinutes: 2460, formattedDuration: "41小时0分", sessionCount: 6, favoriteGame: "英雄联盟" },
      { rank: 5, memberNo: "M005", nickname: "全图视野", avatarUrl: null, level: 5, totalMinutes: 2370, formattedDuration: "39小时30分", sessionCount: 7, favoriteGame: "英雄联盟" },
      { rank: 6, memberNo: "M001", nickname: "电竞小王子", avatarUrl: null, level: 5, totalMinutes: 2340, formattedDuration: "39小时0分", sessionCount: 8, favoriteGame: "英雄联盟" },
      { rank: 7, memberNo: "M002", nickname: "暗夜游侠", avatarUrl: null, level: 4, totalMinutes: 1980, formattedDuration: "33小时0分", sessionCount: 6, favoriteGame: "英雄联盟" },
      { rank: 8, memberNo: "M004", nickname: "孤独的Carry", avatarUrl: null, level: 3, totalMinutes: 1650, formattedDuration: "27小时30分", sessionCount: 5, favoriteGame: "DOTA2" },
      { rank: 9, memberNo: "M006", nickname: "残血反杀", avatarUrl: null, level: 4, totalMinutes: 1290, formattedDuration: "21小时30分", sessionCount: 5, favoriteGame: "CS2" },
      { rank: 10, memberNo: "M009", nickname: "佛系玩家", avatarUrl: null, level: 2, totalMinutes: 360, formattedDuration: "6小时0分", sessionCount: 3, favoriteGame: "其他游戏" }
    ],
    availableGameTypes: gameTypes,
    updatedAt: Math.floor(Date.now() / 1000)
  }
};
