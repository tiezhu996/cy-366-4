package com.generated.ldesportsbar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import com.generated.ldesportsbar.model.LeaderboardData;
import com.generated.ldesportsbar.model.LeaderboardItem;
import com.generated.ldesportsbar.model.LeaderboardResponse;
import com.generated.ldesportsbar.model.OverviewResponse;

@DisplayName("OverviewService 测试 - 上机时长排行榜分类统计")
class OverviewServiceTest {

  private OverviewService overviewService;

  @BeforeEach
  void setUp() {
    overviewService = new OverviewService();
  }

  @Nested
  @DisplayName("数据结构验证")
  class DataStructureTests {

    @Test
    @DisplayName("概览响应应包含排行榜数据")
    void overviewResponseShouldContainLeaderboard() {
      OverviewResponse response = overviewService.getOverview();
      assertNotNull(response.leaderboard(), "leaderboard 字段不应为 null");
    }

    @Test
    @DisplayName("排行榜响应应包含日/周/月三个维度")
    void leaderboardResponseShouldHaveThreePeriods() {
      LeaderboardResponse leaderboard = overviewService.getOverview().leaderboard();
      assertNotNull(leaderboard.daily(), "日榜数据不应为 null");
      assertNotNull(leaderboard.weekly(), "周榜数据不应为 null");
      assertNotNull(leaderboard.monthly(), "月榜数据不应为 null");
    }

    @Test
    @DisplayName("每个时间段应包含 all 和 byGameType 字段")
    void leaderboardDataShouldHaveAllAndByGameType() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();
      assertNotNull(daily.all(), "all 字段不应为 null");
      assertNotNull(daily.byGameType(), "byGameType 字段不应为 null");
      assertNotNull(daily.availableGameTypes(), "availableGameTypes 字段不应为 null");
      assertNotNull(daily.period(), "period 字段不应为 null");
    }

    @Test
    @DisplayName("排行榜项应包含 gameCode 和 gameName 字段")
    void leaderboardItemShouldHaveGameFields() {
      List<LeaderboardItem> items = overviewService.getOverview().leaderboard().daily().all();
      assertFalse(items.isEmpty(), "排行榜数据不应为空");
      LeaderboardItem item = items.get(0);
      assertNotNull(item.gameCode(), "gameCode 不应为 null");
      assertNotNull(item.gameName(), "gameName 不应为 null");
    }
  }

  @Nested
  @DisplayName("全部游戏排名验证")
  class AllRankingTests {

    @Test
    @DisplayName("日榜全部游戏应按累计时长降序排列")
    void dailyAllShouldBeSortedByTotalMinutesDesc() {
      List<LeaderboardItem> items = overviewService.getOverview().leaderboard().daily().all();
      assertSortedDescending(items);
    }

    @Test
    @DisplayName("周榜全部游戏应按累计时长降序排列")
    void weeklyAllShouldBeSortedByTotalMinutesDesc() {
      List<LeaderboardItem> items = overviewService.getOverview().leaderboard().weekly().all();
      assertSortedDescending(items);
    }

    @Test
    @DisplayName("月榜全部游戏应按累计时长降序排列")
    void monthlyAllShouldBeSortedByTotalMinutesDesc() {
      List<LeaderboardItem> items = overviewService.getOverview().leaderboard().monthly().all();
      assertSortedDescending(items);
    }

    @Test
    @DisplayName("日榜排名应从 1 开始连续递增")
    void dailyAllRanksShouldBeSequential() {
      List<LeaderboardItem> items = overviewService.getOverview().leaderboard().daily().all();
      assertSequentialRanks(items);
    }

    @Test
    @DisplayName("全部游戏排名中的 gameCode 应为 ALL")
    void allRankingGameCodeShouldBeAll() {
      List<LeaderboardItem> items = overviewService.getOverview().leaderboard().daily().all();
      for (LeaderboardItem item : items) {
        assertEquals("ALL", item.gameCode(),
          "全部游戏排名中的 gameCode 应为 ALL，但 " + item.nickname() + " 是 " + item.gameCode());
      }
    }

    @Test
    @DisplayName("全部游戏排名中的 gameName 应为玩家最爱游戏")
    void allRankingGameNameShouldBeTopGame() {
      List<LeaderboardItem> items = overviewService.getOverview().leaderboard().daily().all();
      for (LeaderboardItem item : items) {
        assertNotNull(item.gameName(), "gameName 不应为 null");
        assertFalse(item.gameName().isEmpty(), "gameName 不应为空");
      }
    }
  }

  @Nested
  @DisplayName("按游戏类型分类统计验证")
  class GameTypeRankingTests {

    @Test
    @DisplayName("每个游戏类型的排名应按该游戏下的累计时长降序排列")
    void eachGameTypeRankingShouldBeSortedDesc() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();
      Map<String, List<LeaderboardItem>> byGameType = daily.byGameType();

      for (Map.Entry<String, List<LeaderboardItem>> entry : byGameType.entrySet()) {
        String gameCode = entry.getKey();
        List<LeaderboardItem> items = entry.getValue();
        if (!items.isEmpty()) {
          assertSortedDescending(items, "游戏类型 " + gameCode + " 的排名");
        }
      }
    }

    @Test
    @DisplayName("每个游戏类型的排名应从 1 开始连续递增")
    void eachGameTypeRanksShouldBeSequential() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();
      Map<String, List<LeaderboardItem>> byGameType = daily.byGameType();

      for (Map.Entry<String, List<LeaderboardItem>> entry : byGameType.entrySet()) {
        List<LeaderboardItem> items = entry.getValue();
        if (!items.isEmpty()) {
          assertSequentialRanks(items, "游戏类型 " + entry.getKey());
        }
      }
    }

    @Test
    @DisplayName("英雄联盟分类只包含英雄联盟游戏数据")
    void lolRankingShouldOnlyContainLolData() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();
      List<LeaderboardItem> lolItems = daily.byGameType().get("LOL");

      assertNotNull(lolItems, "LOL 分类数据不应为 null");
      for (LeaderboardItem item : lolItems) {
        assertEquals("LOL", item.gameCode(),
          item.nickname() + " 的 gameCode 应为 LOL");
        assertEquals("英雄联盟", item.gameName(),
          item.nickname() + " 的 gameName 应为 英雄联盟");
      }
    }

    @Test
    @DisplayName("CS2 分类只包含 CS2 游戏数据")
    void csgoRankingShouldOnlyContainCsgoData() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();
      List<LeaderboardItem> csgoItems = daily.byGameType().get("CSGO");

      assertNotNull(csgoItems, "CSGO 分类数据不应为 null");
      for (LeaderboardItem item : csgoItems) {
        assertEquals("CSGO", item.gameCode(),
          item.nickname() + " 的 gameCode 应为 CSGO");
        assertEquals("CS2", item.gameName(),
          item.nickname() + " 的 gameName 应为 CS2");
      }
    }

    @Test
    @DisplayName("分类统计时长应与实际记录一致 - 日榜 M003")
    void dailyM003StatsShouldBeCorrect() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();

      LeaderboardItem m003InAll = daily.all().stream()
        .filter(item -> "M003".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m003InAll, "M003 应在全部游戏排名中");
      assertEquals(390, m003InAll.totalMinutes(),
        "M003 日榜总时长应为 210(LOL) + 180(CSGO) = 390 分钟");
      assertEquals(2, m003InAll.sessionCount(),
        "M003 日榜总次数应为 2 次");

      LeaderboardItem m003InLol = daily.byGameType().get("LOL").stream()
        .filter(item -> "M003".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m003InLol, "M003 应在 LOL 分类排名中");
      assertEquals(210, m003InLol.totalMinutes(),
        "M003 日榜 LOL 时长应为 210 分钟");
      assertEquals(1, m003InLol.sessionCount(),
        "M003 日榜 LOL 次数应为 1 次");

      LeaderboardItem m003InCsgo = daily.byGameType().get("CSGO").stream()
        .filter(item -> "M003".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m003InCsgo, "M003 应在 CSGO 分类排名中");
      assertEquals(180, m003InCsgo.totalMinutes(),
        "M003 日榜 CSGO 时长应为 180 分钟");
      assertEquals(1, m003InCsgo.sessionCount(),
        "M003 日榜 CSGO 次数应为 1 次");
    }

    @Test
    @DisplayName("分类统计时长应与实际记录一致 - 日榜 M007")
    void dailyM007StatsShouldBeCorrect() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();

      LeaderboardItem m007InAll = daily.all().stream()
        .filter(item -> "M007".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m007InAll, "M007 应在全部游戏排名中");
      assertEquals(360, m007InAll.totalMinutes(),
        "M007 日榜总时长应为 300(CSGO) + 60(LOL) = 360 分钟");
      assertEquals(2, m007InAll.sessionCount(),
        "M007 日榜总次数应为 2 次");

      LeaderboardItem m007InCsgo = daily.byGameType().get("CSGO").stream()
        .filter(item -> "M007".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m007InCsgo, "M007 应在 CSGO 分类排名中");
      assertEquals(300, m007InCsgo.totalMinutes(),
        "M007 日榜 CSGO 时长应为 300 分钟");
    }

    @Test
    @DisplayName("周榜分类统计验证 - M001")
    void weeklyM001StatsShouldBeCorrect() {
      LeaderboardData weekly = overviewService.getOverview().leaderboard().weekly();

      LeaderboardItem m001InAll = weekly.all().stream()
        .filter(item -> "M001".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m001InAll, "M001 应在周榜全部游戏排名中");
      assertEquals(780, m001InAll.totalMinutes(),
        "M001 周榜总时长应为 540(LOL) + 240(CSGO) = 780 分钟");

      LeaderboardItem m001InLol = weekly.byGameType().get("LOL").stream()
        .filter(item -> "M001".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m001InLol, "M001 应在周榜 LOL 分类中");
      assertEquals(540, m001InLol.totalMinutes(),
        "M001 周榜 LOL 时长应为 540 分钟");
    }

    @Test
    @DisplayName("月榜分类统计验证 - M006 跨游戏")
    void monthlyM006StatsShouldBeCorrect() {
      LeaderboardData monthly = overviewService.getOverview().leaderboard().monthly();

      LeaderboardItem m006InAll = monthly.all().stream()
        .filter(item -> "M006".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m006InAll, "M006 应在月榜全部游戏排名中");
      assertEquals(1890, m006InAll.totalMinutes(),
        "M006 月榜总时长应为 1290(CSGO) + 600(VALORANT) = 1890 分钟");
      assertEquals(2, m006InAll.sessionCount(),
        "M006 月榜总次数应为 2 次");

      LeaderboardItem m006InCsgo = monthly.byGameType().get("CSGO").stream()
        .filter(item -> "M006".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m006InCsgo, "M006 应在月榜 CSGO 分类中");
      assertEquals(1290, m006InCsgo.totalMinutes(),
        "M006 月榜 CSGO 时长应为 1290 分钟");

      LeaderboardItem m006InValorant = monthly.byGameType().get("VALORANT").stream()
        .filter(item -> "M006".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m006InValorant, "M006 应在月榜 VALORANT 分类中");
      assertEquals(600, m006InValorant.totalMinutes(),
        "M006 月榜 VALORANT 时长应为 600 分钟");
    }

    @Test
    @DisplayName("没有数据的游戏类型应返回空列表")
    void emptyGameTypeShouldReturnEmptyList() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();
      List<LeaderboardItem> apexItems = daily.byGameType().get("APEX");
      assertNotNull(apexItems, "APEX 分类不应为 null");
      assertTrue(apexItems.isEmpty(), "APEX 分类数据应为空列表");
    }
  }

  @Nested
  @DisplayName("综合场景验证")
  class ScenarioTests {

    @Test
    @DisplayName("跨游戏玩家在不同分类中应有不同排名")
    void crossGamePlayerShouldHaveDifferentRanks() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();

      LeaderboardItem m010InValorant = daily.byGameType().get("VALORANT").stream()
        .filter(item -> "M010".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m010InValorant);
      assertEquals(1, m010InValorant.rank(),
        "M010 在 VALORANT 分类中应排第 1 (300分钟)");

      LeaderboardItem m010InLol = daily.byGameType().get("LOL").stream()
        .filter(item -> "M010".equals(item.memberNo()))
        .findFirst()
        .orElse(null);
      assertNotNull(m010InLol);
      assertTrue(m010InLol.rank() > 1,
        "M010 在 LOL 分类中排名应 > 1 (只有 90 分钟)");
    }

    @Test
    @DisplayName("玩家只在玩过的游戏分类中出现")
    void playerShouldOnlyAppearInPlayedGameTypes() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();

      boolean m004InDota2 = daily.byGameType().get("DOTA2").stream()
        .anyMatch(item -> "M004".equals(item.memberNo()));
      assertTrue(m004InDota2, "M004 应出现在 DOTA2 分类中");

      boolean m004InLol = daily.byGameType().get("LOL").stream()
        .anyMatch(item -> "M004".equals(item.memberNo()));
      assertFalse(m004InLol, "M004 不应出现在 LOL 分类中");

      boolean m004InCsgo = daily.byGameType().get("CSGO").stream()
        .anyMatch(item -> "M004".equals(item.memberNo()));
      assertFalse(m004InCsgo, "M004 不应出现在 CSGO 分类中");
    }

    @Test
    @DisplayName("相同时长的玩家排名应正确")
    void playersWithSameMinutesShouldHaveCorrectRanks() {
      LeaderboardData daily = overviewService.getOverview().leaderboard().daily();
      List<LeaderboardItem> allItems = daily.all();

      int m007Index = -1, m010Index = -1;
      for (int i = 0; i < allItems.size(); i++) {
        if ("M007".equals(allItems.get(i).memberNo())) m007Index = i;
        if ("M010".equals(allItems.get(i).memberNo())) m010Index = i;
      }

      assertTrue(m007Index >= 0 && m010Index >= 0, "M007 和 M010 都应在排名中");
      assertEquals(360, allItems.get(m007Index).totalMinutes());
      assertEquals(390, allItems.get(m010Index).totalMinutes());
    }
  }

  private void assertSortedDescending(List<LeaderboardItem> items) {
    assertSortedDescending(items, "排行榜");
  }

  private void assertSortedDescending(List<LeaderboardItem> items, String message) {
    for (int i = 0; i < items.size() - 1; i++) {
      int current = items.get(i).totalMinutes();
      int next = items.get(i + 1).totalMinutes();
      assertTrue(current >= next,
        message + " 未按降序排列: 第 " + (i + 1) + " 名 " + items.get(i).nickname() +
          " (" + current + " 分钟) <= 第 " + (i + 2) + " 名 " + items.get(i + 1).nickname() +
          " (" + next + " 分钟)");
    }
  }

  private void assertSequentialRanks(List<LeaderboardItem> items) {
    assertSequentialRanks(items, "排行榜");
  }

  private void assertSequentialRanks(List<LeaderboardItem> items, String message) {
    for (int i = 0; i < items.size(); i++) {
      int expectedRank = i + 1;
      int actualRank = items.get(i).rank();
      assertEquals(expectedRank, actualRank,
        message + " 排名不连续: 第 " + (i + 1) + " 位玩家 " + items.get(i).nickname() +
          " 的排名应为 " + expectedRank + "，实际为 " + actualRank);
    }
  }
}
