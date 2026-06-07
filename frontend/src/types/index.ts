export interface FeatureItem {
  id: number;
  title: string;
  description: string;
  status: string;
  metric: string;
}

export interface KpiItem {
  label: string;
  value: string;
  trend: string;
  tone: string;
}

export interface OperationRecord {
  key: string;
  name: string;
  owner: string;
  status: string;
  metric: string;
  priority: string;
}

export interface GameType {
  code: string;
  name: string;
  iconUrl: string | null;
}

export interface LeaderboardItem {
  rank: number;
  memberNo: string;
  nickname: string;
  avatarUrl: string | null;
  level: number;
  totalMinutes: number;
  formattedDuration: string;
  sessionCount: number;
  gameCode: string;
  gameName: string;
}

export interface LeaderboardData {
  period: string;
  all: LeaderboardItem[];
  byGameType: Record<string, LeaderboardItem[]>;
  availableGameTypes: GameType[];
  updatedAt: number;
}

export interface LeaderboardResponse {
  daily: LeaderboardData;
  weekly: LeaderboardData;
  monthly: LeaderboardData;
}

export interface OverviewResponse {
  appName: string;
  appCode: string;
  description: string;
  features: FeatureItem[];
  kpis: KpiItem[];
  records: OperationRecord[];
  leaderboard: LeaderboardResponse;
}
