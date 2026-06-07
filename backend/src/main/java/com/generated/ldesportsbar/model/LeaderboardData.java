package com.generated.ldesportsbar.model;

import java.util.List;
import java.util.Map;

public record LeaderboardData(
  String period,
  List<LeaderboardItem> all,
  Map<String, List<LeaderboardItem>> byGameType,
  List<GameType> availableGameTypes,
  Integer updatedAt
) {
}
