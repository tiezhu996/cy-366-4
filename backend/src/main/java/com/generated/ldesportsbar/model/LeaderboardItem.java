package com.generated.ldesportsbar.model;

public record LeaderboardItem(
  Integer rank,
  String memberNo,
  String nickname,
  String avatarUrl,
  Integer level,
  Integer totalMinutes,
  String formattedDuration,
  Integer sessionCount,
  String favoriteGame
) {
}
