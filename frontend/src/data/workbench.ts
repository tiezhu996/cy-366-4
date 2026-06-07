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

const formatDuration = (minutes: number): string => {
  const hours = Math.floor(minutes / 60);
  const mins = minutes % 60;
  return `${hours}小时${mins}分`;
};

interface PlaySession {
  memberNo: string;
  nickname: string;
  avatarUrl: string | null;
  level: number;
  gameCode: string;
  minutes: number;
}

interface MemberGameStat {
  memberNo: string;
  nickname: string;
  avatarUrl: string | null;
  level: number;
  gameCode: string;
  totalMinutes: number;
  sessionCount: number;
}

const gameNameMap: Record<string, string> = {
  LOL: "英雄联盟",
  CSGO: "CS2",
  VALORANT: "无畏契约",
  DOTA2: "DOTA2",
  WZRY: "王者荣耀",
  PUBG: "绝地求生",
  APEX: "Apex英雄",
  OTHER: "其他游戏"
};

function buildAllRanking(sessions: PlaySession[]) {
  const memberStats: Record<string, MemberGameStat> = {};

  for (const session of sessions) {
    const key = session.memberNo;
    if (!memberStats[key]) {
      memberStats[key] = {
        memberNo: session.memberNo,
        nickname: session.nickname,
        avatarUrl: session.avatarUrl,
        level: session.level,
        gameCode: "ALL",
        totalMinutes: 0,
        sessionCount: 0
      };
    }
    memberStats[key].totalMinutes += session.minutes;
    memberStats[key].sessionCount += 1;
  }

  const sortedStats = Object.values(memberStats)
    .sort((a, b) => b.totalMinutes - a.totalMinutes);

  const result = [];
  for (let i = 0; i < sortedStats.length && i < 10; i++) {
    const stat = sortedStats[i];
    const gameMinutes: Record<string, number> = {};
    for (const s of sessions) {
      if (s.memberNo === stat.memberNo) {
        gameMinutes[s.gameCode] = (gameMinutes[s.gameCode] || 0) + s.minutes;
      }
    }
    const topGameCode = Object.entries(gameMinutes)
      .sort((a, b) => b[1] - a[1])[0]?.[0] || "OTHER";
    const topGameName = gameNameMap[topGameCode] || topGameCode;

    result.push({
      rank: i + 1,
      memberNo: stat.memberNo,
      nickname: stat.nickname,
      avatarUrl: stat.avatarUrl,
      level: stat.level,
      totalMinutes: stat.totalMinutes,
      formattedDuration: formatDuration(stat.totalMinutes),
      sessionCount: stat.sessionCount,
      gameCode: "ALL",
      gameName: topGameName
    });
  }
  return result;
}

function buildGameTypeRankings(sessions: PlaySession[]) {
  const gameMemberStats: Record<string, Record<string, MemberGameStat>> = {};

  for (const session of sessions) {
    const gameCode = session.gameCode;
    if (!gameMemberStats[gameCode]) {
      gameMemberStats[gameCode] = {};
    }
    const memberStats = gameMemberStats[gameCode];
    const key = session.memberNo;
    if (!memberStats[key]) {
      memberStats[key] = {
        memberNo: session.memberNo,
        nickname: session.nickname,
        avatarUrl: session.avatarUrl,
        level: session.level,
        gameCode: gameCode,
        totalMinutes: 0,
        sessionCount: 0
      };
    }
    memberStats[key].totalMinutes += session.minutes;
    memberStats[key].sessionCount += 1;
  }

  const result: Record<string, LeaderboardItem[]> = {};
  for (const [gameCode, memberStats] of Object.entries(gameMemberStats)) {
    const gameName = gameNameMap[gameCode] || gameCode;
    const sortedStats = Object.values(memberStats)
      .sort((a, b) => b.totalMinutes - a.totalMinutes);

    const items = sortedStats.map((stat, i) => ({
      rank: i + 1,
      memberNo: stat.memberNo,
      nickname: stat.nickname,
      avatarUrl: stat.avatarUrl,
      level: stat.level,
      totalMinutes: stat.totalMinutes,
      formattedDuration: formatDuration(stat.totalMinutes),
      sessionCount: stat.sessionCount,
      gameCode: gameCode,
      gameName: gameName
    }));
    result[gameCode] = items;
  }

  for (const gt of gameTypes) {
    if (gt.code !== "ALL" && !result[gt.code]) {
      result[gt.code] = [];
    }
  }

  return result;
}

const dailySessions: PlaySession[] = [
  { memberNo: "M001", nickname: "电竞小王子", avatarUrl: null, level: 5, gameCode: "LOL", minutes: 120 },
  { memberNo: "M001", nickname: "电竞小王子", avatarUrl: null, level: 5, gameCode: "CSGO", minutes: 60 },
  { memberNo: "M002", nickname: "暗夜游侠", avatarUrl: null, level: 4, gameCode: "LOL", minutes: 180 },
  { memberNo: "M003", nickname: "游戏达人", avatarUrl: null, level: 6, gameCode: "LOL", minutes: 210 },
  { memberNo: "M003", nickname: "游戏达人", avatarUrl: null, level: 6, gameCode: "CSGO", minutes: 180 },
  { memberNo: "M004", nickname: "孤独的Carry", avatarUrl: null, level: 3, gameCode: "DOTA2", minutes: 90 },
  { memberNo: "M005", nickname: "全图视野", avatarUrl: null, level: 5, gameCode: "LOL", minutes: 60 },
  { memberNo: "M005", nickname: "全图视野", avatarUrl: null, level: 5, gameCode: "PUBG", minutes: 90 },
  { memberNo: "M006", nickname: "残血反杀", avatarUrl: null, level: 4, gameCode: "VALORANT", minutes: 120 },
  { memberNo: "M007", nickname: "一枪爆头", avatarUrl: null, level: 7, gameCode: "CSGO", minutes: 300 },
  { memberNo: "M007", nickname: "一枪爆头", avatarUrl: null, level: 7, gameCode: "LOL", minutes: 60 },
  { memberNo: "M008", nickname: "五杀专业户", avatarUrl: null, level: 5, gameCode: "LOL", minutes: 120 },
  { memberNo: "M008", nickname: "五杀专业户", avatarUrl: null, level: 5, gameCode: "WZRY", minutes: 180 },
  { memberNo: "M009", nickname: "佛系玩家", avatarUrl: null, level: 2, gameCode: "OTHER", minutes: 0 },
  { memberNo: "M010", nickname: "上分机器", avatarUrl: null, level: 6, gameCode: "VALORANT", minutes: 300 },
  { memberNo: "M010", nickname: "上分机器", avatarUrl: null, level: 6, gameCode: "LOL", minutes: 90 }
];

const weeklySessions: PlaySession[] = [
  { memberNo: "M001", nickname: "电竞小王子", avatarUrl: null, level: 5, gameCode: "LOL", minutes: 540 },
  { memberNo: "M001", nickname: "电竞小王子", avatarUrl: null, level: 5, gameCode: "CSGO", minutes: 240 },
  { memberNo: "M002", nickname: "暗夜游侠", avatarUrl: null, level: 4, gameCode: "LOL", minutes: 480 },
  { memberNo: "M003", nickname: "游戏达人", avatarUrl: null, level: 6, gameCode: "LOL", minutes: 630 },
  { memberNo: "M003", nickname: "游戏达人", avatarUrl: null, level: 6, gameCode: "CSGO", minutes: 180 },
  { memberNo: "M004", nickname: "孤独的Carry", avatarUrl: null, level: 3, gameCode: "DOTA2", minutes: 450 },
  { memberNo: "M005", nickname: "全图视野", avatarUrl: null, level: 5, gameCode: "LOL", minutes: 570 },
  { memberNo: "M006", nickname: "残血反杀", avatarUrl: null, level: 4, gameCode: "CSGO", minutes: 390 },
  { memberNo: "M007", nickname: "一枪爆头", avatarUrl: null, level: 7, gameCode: "CSGO", minutes: 840 },
  { memberNo: "M008", nickname: "五杀专业户", avatarUrl: null, level: 5, gameCode: "LOL", minutes: 780 },
  { memberNo: "M009", nickname: "佛系玩家", avatarUrl: null, level: 2, gameCode: "OTHER", minutes: 60 },
  { memberNo: "M010", nickname: "上分机器", avatarUrl: null, level: 6, gameCode: "LOL", minutes: 660 }
];

const monthlySessions: PlaySession[] = [
  { memberNo: "M001", nickname: "电竞小王子", avatarUrl: null, level: 5, gameCode: "LOL", minutes: 2340 },
  { memberNo: "M002", nickname: "暗夜游侠", avatarUrl: null, level: 4, gameCode: "LOL", minutes: 1980 },
  { memberNo: "M003", nickname: "游戏达人", avatarUrl: null, level: 6, gameCode: "LOL", minutes: 2910 },
  { memberNo: "M004", nickname: "孤独的Carry", avatarUrl: null, level: 3, gameCode: "DOTA2", minutes: 1650 },
  { memberNo: "M005", nickname: "全图视野", avatarUrl: null, level: 5, gameCode: "LOL", minutes: 2370 },
  { memberNo: "M006", nickname: "残血反杀", avatarUrl: null, level: 4, gameCode: "CSGO", minutes: 1290 },
  { memberNo: "M006", nickname: "残血反杀", avatarUrl: null, level: 4, gameCode: "VALORANT", minutes: 600 },
  { memberNo: "M007", nickname: "一枪爆头", avatarUrl: null, level: 7, gameCode: "CSGO", minutes: 3240 },
  { memberNo: "M008", nickname: "五杀专业户", avatarUrl: null, level: 5, gameCode: "LOL", minutes: 3180 },
  { memberNo: "M009", nickname: "佛系玩家", avatarUrl: null, level: 2, gameCode: "OTHER", minutes: 360 },
  { memberNo: "M010", nickname: "上分机器", avatarUrl: null, level: 6, gameCode: "LOL", minutes: 2460 }
];

export const localLeaderboard: LeaderboardResponse = {
  daily: {
    period: "daily",
    all: buildAllRanking(dailySessions),
    byGameType: buildGameTypeRankings(dailySessions),
    availableGameTypes: gameTypes,
    updatedAt: Math.floor(Date.now() / 1000)
  },
  weekly: {
    period: "weekly",
    all: buildAllRanking(weeklySessions),
    byGameType: buildGameTypeRankings(weeklySessions),
    availableGameTypes: gameTypes,
    updatedAt: Math.floor(Date.now() / 1000)
  },
  monthly: {
    period: "monthly",
    all: buildAllRanking(monthlySessions),
    byGameType: buildGameTypeRankings(monthlySessions),
    availableGameTypes: gameTypes,
    updatedAt: Math.floor(Date.now() / 1000)
  }
};
