package com.generated.ldesportsbar.service;

import java.util.List;
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

  private LeaderboardData buildDailyLeaderboard() {
    return new LeaderboardData(
      "daily",
      "ALL",
      List.of(
        new LeaderboardItem(1, "M003", "游戏达人", null, 6, 390, "6小时30分", 2, "英雄联盟"),
        new LeaderboardItem(2, "M007", "一枪爆头", null, 7, 300, "5小时0分", 1, "CS2"),
        new LeaderboardItem(3, "M010", "上分机器", null, 6, 300, "5小时0分", 1, "无畏契约"),
        new LeaderboardItem(4, "M002", "暗夜游侠", null, 4, 180, "3小时0分", 1, "英雄联盟"),
        new LeaderboardItem(5, "M008", "五杀专业户", null, 5, 180, "3小时0分", 1, "王者荣耀"),
        new LeaderboardItem(6, "M001", "电竞小王子", null, 5, 120, "2小时0分", 1, "英雄联盟"),
        new LeaderboardItem(7, "M006", "残血反杀", null, 4, 120, "2小时0分", 1, "无畏契约"),
        new LeaderboardItem(8, "M005", "全图视野", null, 5, 90, "1小时30分", 1, "绝地求生"),
        new LeaderboardItem(9, "M004", "孤独的Carry", null, 3, 90, "1小时30分", 1, "DOTA2"),
        new LeaderboardItem(10, "M009", "佛系玩家", null, 2, 0, "0小时0分", 0, "其他游戏")
      ),
      gameTypes,
      (int) (System.currentTimeMillis() / 1000)
    );
  }

  private LeaderboardData buildWeeklyLeaderboard() {
    return new LeaderboardData(
      "weekly",
      "ALL",
      List.of(
        new LeaderboardItem(1, "M007", "一枪爆头", null, 7, 840, "14小时0分", 2, "CS2"),
        new LeaderboardItem(2, "M003", "游戏达人", null, 6, 810, "13小时30分", 3, "英雄联盟"),
        new LeaderboardItem(3, "M008", "五杀专业户", null, 5, 780, "13小时0分", 2, "英雄联盟"),
        new LeaderboardItem(4, "M010", "上分机器", null, 6, 660, "11小时0分", 2, "英雄联盟"),
        new LeaderboardItem(5, "M005", "全图视野", null, 5, 570, "9小时30分", 2, "英雄联盟"),
        new LeaderboardItem(6, "M001", "电竞小王子", null, 5, 540, "9小时0分", 3, "英雄联盟"),
        new LeaderboardItem(7, "M002", "暗夜游侠", null, 4, 480, "8小时0分", 2, "英雄联盟"),
        new LeaderboardItem(8, "M004", "孤独的Carry", null, 3, 450, "7小时30分", 2, "DOTA2"),
        new LeaderboardItem(9, "M006", "残血反杀", null, 4, 390, "6小时30分", 2, "CS2"),
        new LeaderboardItem(10, "M009", "佛系玩家", null, 2, 60, "1小时0分", 1, "其他游戏")
      ),
      gameTypes,
      (int) (System.currentTimeMillis() / 1000)
    );
  }

  private LeaderboardData buildMonthlyLeaderboard() {
    return new LeaderboardData(
      "monthly",
      "ALL",
      List.of(
        new LeaderboardItem(1, "M007", "一枪爆头", null, 7, 3240, "54小时0分", 7, "CS2"),
        new LeaderboardItem(2, "M008", "五杀专业户", null, 5, 3180, "53小时0分", 8, "英雄联盟"),
        new LeaderboardItem(3, "M003", "游戏达人", null, 6, 2910, "48小时30分", 9, "英雄联盟"),
        new LeaderboardItem(4, "M010", "上分机器", null, 6, 2460, "41小时0分", 6, "英雄联盟"),
        new LeaderboardItem(5, "M005", "全图视野", null, 5, 2370, "39小时30分", 7, "英雄联盟"),
        new LeaderboardItem(6, "M001", "电竞小王子", null, 5, 2340, "39小时0分", 8, "英雄联盟"),
        new LeaderboardItem(7, "M002", "暗夜游侠", null, 4, 1980, "33小时0分", 6, "英雄联盟"),
        new LeaderboardItem(8, "M004", "孤独的Carry", null, 3, 1650, "27小时30分", 5, "DOTA2"),
        new LeaderboardItem(9, "M006", "残血反杀", null, 4, 1290, "21小时30分", 5, "CS2"),
        new LeaderboardItem(10, "M009", "佛系玩家", null, 2, 360, "6小时0分", 3, "其他游戏")
      ),
      gameTypes,
      (int) (System.currentTimeMillis() / 1000)
    );
  }
}
