package com.generated.ldesportsbar.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.generated.ldesportsbar.model.FeatureItem;
import com.generated.ldesportsbar.model.GameType;
import com.generated.ldesportsbar.model.KpiItem;
import com.generated.ldesportsbar.model.LeaderboardData;
import com.generated.ldesportsbar.model.LeaderboardItem;
import com.generated.ldesportsbar.model.LeaderboardResponse;
import com.generated.ldesportsbar.model.OperationRecord;
import com.generated.ldesportsbar.model.OverviewResponse;

@Service
public class OverviewService {

  private final List<GameType> gameTypes = List.of(
    new GameType("ALL", "全部游戏", null),
    new GameType("LOL", "英雄联盟", null),
    new GameType("CSGO", "CS2", null),
    new GameType("VALORANT", "无畏契约", null),
    new GameType("DOTA2", "DOTA2", null),
    new GameType("WZRY", "王者荣耀", null),
    new GameType("PUBG", "绝地求生", null),
    new GameType("APEX", "Apex英雄", null),
    new GameType("OTHER", "其他游戏", null)
  );

  private final Map<String, String> gameNameMap = Map.of(
    "LOL", "英雄联盟",
    "CSGO", "CS2",
    "VALORANT", "无畏契约",
    "DOTA2", "DOTA2",
    "WZRY", "王者荣耀",
    "PUBG", "绝地求生",
    "APEX", "Apex英雄",
    "OTHER", "其他游戏"
  );

  static class MemberGameStat {
    String memberNo;
    String nickname;
    String avatarUrl;
    Integer level;
    String gameCode;
    Integer totalMinutes;
    Integer sessionCount;

    MemberGameStat(String memberNo, String nickname, String avatarUrl, Integer level, String gameCode) {
      this.memberNo = memberNo;
      this.nickname = nickname;
      this.avatarUrl = avatarUrl;
      this.level = level;
      this.gameCode = gameCode;
      this.totalMinutes = 0;
      this.sessionCount = 0;
    }

    void addSession(int minutes) {
      this.totalMinutes += minutes;
      this.sessionCount += 1;
    }
  }

  static class PlaySession {
    String memberNo;
    String nickname;
    String avatarUrl;
    Integer level;
    String gameCode;
    int minutes;

    PlaySession(String memberNo, String nickname, String avatarUrl, Integer level, String gameCode, int minutes) {
      this.memberNo = memberNo;
      this.nickname = nickname;
      this.avatarUrl = avatarUrl;
      this.level = level;
      this.gameCode = gameCode;
      this.minutes = minutes;
    }
  }

  public OverviewResponse getOverview() {
    return new OverviewResponse(
      "电竞馆上机管理系统",
      "ldesportsbar",
      "面向电竞馆/网咖门店，提供机位状态监控、会员充值消费和赛事管理的运营工具。",
      List.of(new FeatureItem(1, "机位/包厢实时状态看板", "以网格或列表形式展示所有机位/包厢的实时状态（空闲/使用中/故障/预约），支持按区域筛选，点击可查看详情。", "已上线", "88%"),
        new FeatureItem(2, "会员充值与时长包", "会员账户支持充值余额，购买时长包（如10小时/30小时/月卡），消费时优先扣除时长包余额，不足时扣余额。", "排期中", "31 单"),
        new FeatureItem(3, "机位预约与续费", "会员可提前预约指定机位和时段，到店扫码开机，使用过程中可续费延长上机时间，快到期前提醒续费。", "巡检中", "10 项"),
        new FeatureItem(4, "上机时长排行榜", "记录会员累计上机时长，生成日/周/月排行榜，激励高频玩家，支持按游戏类型分类统计。", "优化中", "4 级"),
        new FeatureItem(5, "赛事报名与战队管理", "门店发布电竞赛事（如LOL/CSGO/王者荣耀），玩家以个人或战队形式报名，系统自动抽签分组，记录比赛结果和战绩。", "可导出", "28 条")),
      List.of(new KpiItem("今日处理", "106", "+12%", "primary"),
        new KpiItem("预约/订单", "43", "+8%", "warm"),
        new KpiItem("履约率", "91%", "+3%", "cool"),
        new KpiItem("待处理", "10", "需跟进", "neutral")),
      List.of(new OperationRecord("ldesportsbar-1", "机位/包厢实时状态看板", "运营组", "已上线", "88%", "高"),
        new OperationRecord("ldesportsbar-2", "会员充值与时长包", "管理员", "排期中", "31 单", "中"),
        new OperationRecord("ldesportsbar-3", "机位预约与续费", "服务台", "巡检中", "10 项", "低"),
        new OperationRecord("ldesportsbar-4", "上机时长排行榜", "财务组", "优化中", "4 级", "高"),
        new OperationRecord("ldesportsbar-5", "赛事报名与战队管理", "审核组", "可导出", "28 条", "中")),
      buildLeaderboardResponse()
    );
  }

  private LeaderboardResponse buildLeaderboardResponse() {
    return new LeaderboardResponse(
      buildDailyLeaderboard(),
      buildWeeklyLeaderboard(),
      buildMonthlyLeaderboard()
    );
  }

  private String formatDuration(int minutes) {
    int hours = minutes / 60;
    int mins = minutes % 60;
    return hours + "小时" + mins + "分";
  }

  private List<LeaderboardItem> buildAllRanking(List<PlaySession> sessions) {
    Map<String, MemberGameStat> memberStats = new HashMap<>();

    for (PlaySession session : sessions) {
      String key = session.memberNo;
      MemberGameStat stat = memberStats.computeIfAbsent(key,
        k -> new MemberGameStat(session.memberNo, session.nickname, session.avatarUrl, session.level, "ALL"));
      stat.addSession(session.minutes);
    }

    List<MemberGameStat> sortedStats = memberStats.values().stream()
      .sorted(Comparator.comparingInt((MemberGameStat s) -> s.totalMinutes).reversed())
      .collect(Collectors.toList());

    List<LeaderboardItem> result = new ArrayList<>();
    for (int i = 0; i < sortedStats.size() && i < 10; i++) {
      MemberGameStat stat = sortedStats.get(i);
      String topGame = findTopGame(sessions, stat.memberNo);
      result.add(new LeaderboardItem(
        i + 1,
        stat.memberNo,
        stat.nickname,
        stat.avatarUrl,
        stat.level,
        stat.totalMinutes,
        formatDuration(stat.totalMinutes),
        stat.sessionCount,
        "ALL",
        topGame
      ));
    }
    return result;
  }

  private String findTopGame(List<PlaySession> sessions, String memberNo) {
    Map<String, Integer> gameMinutes = new HashMap<>();
    for (PlaySession session : sessions) {
      if (session.memberNo.equals(memberNo)) {
        gameMinutes.merge(session.gameCode, session.minutes, Integer::sum);
      }
    }
    return gameMinutes.entrySet().stream()
      .max(Map.Entry.comparingByValue())
      .map(e -> gameNameMap.getOrDefault(e.getKey(), e.getKey()))
      .orElse("未知");
  }

  private Map<String, List<LeaderboardItem>> buildGameTypeRankings(List<PlaySession> sessions) {
    Map<String, Map<String, MemberGameStat>> gameMemberStats = new HashMap<>();

    for (PlaySession session : sessions) {
      String gameCode = session.gameCode;
      gameMemberStats.computeIfAbsent(gameCode, k -> new HashMap<>());

      Map<String, MemberGameStat> memberStats = gameMemberStats.get(gameCode);
      String key = session.memberNo;
      MemberGameStat stat = memberStats.computeIfAbsent(key,
        k -> new MemberGameStat(session.memberNo, session.nickname, session.avatarUrl, session.level, gameCode));
      stat.addSession(session.minutes);
    }

    Map<String, List<LeaderboardItem>> result = new HashMap<>();
    for (Map.Entry<String, Map<String, MemberGameStat>> gameEntry : gameMemberStats.entrySet()) {
      String gameCode = gameEntry.getKey();
      String gameName = gameNameMap.getOrDefault(gameCode, gameCode);

      List<MemberGameStat> sortedStats = gameEntry.getValue().values().stream()
        .sorted(Comparator.comparingInt((MemberGameStat s) -> s.totalMinutes).reversed())
        .collect(Collectors.toList());

      List<LeaderboardItem> items = new ArrayList<>();
      for (int i = 0; i < sortedStats.size(); i++) {
        MemberGameStat stat = sortedStats.get(i);
        items.add(new LeaderboardItem(
          i + 1,
          stat.memberNo,
          stat.nickname,
          stat.avatarUrl,
          stat.level,
          stat.totalMinutes,
          formatDuration(stat.totalMinutes),
          stat.sessionCount,
          gameCode,
          gameName
        ));
      }
      result.put(gameCode, items);
    }

    for (GameType gt : gameTypes) {
      if (!"ALL".equals(gt.code()) && !result.containsKey(gt.code())) {
        result.put(gt.code(), List.of());
      }
    }

    return result;
  }

  private LeaderboardData buildDailyLeaderboard() {
    List<PlaySession> sessions = List.of(
      new PlaySession("M001", "电竞小王子", null, 5, "LOL", 120),
      new PlaySession("M001", "电竞小王子", null, 5, "CSGO", 60),
      new PlaySession("M002", "暗夜游侠", null, 4, "LOL", 180),
      new PlaySession("M003", "游戏达人", null, 6, "LOL", 210),
      new PlaySession("M003", "游戏达人", null, 6, "CSGO", 180),
      new PlaySession("M004", "孤独的Carry", null, 3, "DOTA2", 90),
      new PlaySession("M005", "全图视野", null, 5, "LOL", 60),
      new PlaySession("M005", "全图视野", null, 5, "PUBG", 90),
      new PlaySession("M006", "残血反杀", null, 4, "VALORANT", 120),
      new PlaySession("M007", "一枪爆头", null, 7, "CSGO", 300),
      new PlaySession("M007", "一枪爆头", null, 7, "LOL", 60),
      new PlaySession("M008", "五杀专业户", null, 5, "LOL", 120),
      new PlaySession("M008", "五杀专业户", null, 5, "WZRY", 180),
      new PlaySession("M009", "佛系玩家", null, 2, "OTHER", 0),
      new PlaySession("M010", "上分机器", null, 6, "VALORANT", 300),
      new PlaySession("M010", "上分机器", null, 6, "LOL", 90)
    );

    return new LeaderboardData(
      "daily",
      buildAllRanking(sessions),
      buildGameTypeRankings(sessions),
      gameTypes,
      (int) (System.currentTimeMillis() / 1000)
    );
  }

  private LeaderboardData buildWeeklyLeaderboard() {
    List<PlaySession> sessions = List.of(
      new PlaySession("M001", "电竞小王子", null, 5, "LOL", 540),
      new PlaySession("M001", "电竞小王子", null, 5, "CSGO", 240),
      new PlaySession("M002", "暗夜游侠", null, 4, "LOL", 480),
      new PlaySession("M003", "游戏达人", null, 6, "LOL", 630),
      new PlaySession("M003", "游戏达人", null, 6, "CSGO", 180),
      new PlaySession("M004", "孤独的Carry", null, 3, "DOTA2", 450),
      new PlaySession("M005", "全图视野", null, 5, "LOL", 570),
      new PlaySession("M006", "残血反杀", null, 4, "CSGO", 390),
      new PlaySession("M007", "一枪爆头", null, 7, "CSGO", 840),
      new PlaySession("M008", "五杀专业户", null, 5, "LOL", 780),
      new PlaySession("M009", "佛系玩家", null, 2, "OTHER", 60),
      new PlaySession("M010", "上分机器", null, 6, "LOL", 660)
    );

    return new LeaderboardData(
      "weekly",
      buildAllRanking(sessions),
      buildGameTypeRankings(sessions),
      gameTypes,
      (int) (System.currentTimeMillis() / 1000)
    );
  }

  private LeaderboardData buildMonthlyLeaderboard() {
    List<PlaySession> sessions = List.of(
      new PlaySession("M001", "电竞小王子", null, 5, "LOL", 2340),
      new PlaySession("M002", "暗夜游侠", null, 4, "LOL", 1980),
      new PlaySession("M003", "游戏达人", null, 6, "LOL", 2910),
      new PlaySession("M004", "孤独的Carry", null, 3, "DOTA2", 1650),
      new PlaySession("M005", "全图视野", null, 5, "LOL", 2370),
      new PlaySession("M006", "残血反杀", null, 4, "CSGO", 1290),
      new PlaySession("M006", "残血反杀", null, 4, "VALORANT", 600),
      new PlaySession("M007", "一枪爆头", null, 7, "CSGO", 3240),
      new PlaySession("M008", "五杀专业户", null, 5, "LOL", 3180),
      new PlaySession("M009", "佛系玩家", null, 2, "OTHER", 360),
      new PlaySession("M010", "上分机器", null, 6, "LOL", 2460)
    );

    return new LeaderboardData(
      "monthly",
      buildAllRanking(sessions),
      buildGameTypeRankings(sessions),
      gameTypes,
      (int) (System.currentTimeMillis() / 1000)
    );
  }
}
