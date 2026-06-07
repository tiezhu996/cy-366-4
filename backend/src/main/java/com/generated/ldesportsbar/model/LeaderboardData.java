package com.generated.ldesportsbar.model;

import java.util.List;

public record LeaderboardData(
  String period,
  String gameTypeCode,
  List<LeaderboardItem> items,
  List<GameType> availableGameTypes,
  Integer updatedAt
) {
}
