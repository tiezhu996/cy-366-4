<script setup lang="ts">
import { ref, computed } from "vue";
import type { LeaderboardResponse, LeaderboardItem, GameType } from "../types";

const props = defineProps<{
  leaderboard: LeaderboardResponse;
}>();

type Period = "daily" | "weekly" | "monthly";

const currentPeriod = ref<Period>("daily");
const selectedGameType = ref("ALL");

const periodOptions = [
  { value: "daily", label: "日榜" },
  { value: "weekly", label: "周榜" },
  { value: "monthly", label: "月榜" }
] as const;

const gameTypes = computed<GameType[]>(() => {
  return props.leaderboard[currentPeriod.value]?.availableGameTypes || [];
});

const gameTypeMap = computed<Record<string, string>>(() => {
  const map: Record<string, string> = {};
  gameTypes.value.forEach((gt) => {
    map[gt.code] = gt.name;
  });
  return map;
});

const currentData = computed(() => {
  const data = props.leaderboard[currentPeriod.value];
  if (selectedGameType.value === "ALL") {
    return data;
  }

  const targetGameName = gameTypeMap.value[selectedGameType.value];
  const filteredItems = data.items.filter((item) => item.favoriteGame === targetGameName);

  const sortedItems = [...filteredItems].sort((a, b) => b.totalMinutes - a.totalMinutes);

  const rankedItems = sortedItems.map((item, index) => ({
    ...item,
    rank: index + 1
  }));

  return {
    ...data,
    gameTypeCode: selectedGameType.value,
    items: rankedItems
  };
});

function getRankBadge(rank: number) {
  if (rank === 1) return { class: "rank-gold", icon: "🥇" };
  if (rank === 2) return { class: "rank-silver", icon: "🥈" };
  if (rank === 3) return { class: "rank-bronze", icon: "🥉" };
  return { class: "rank-normal", icon: `#${rank}` };
}

function selectPeriod(period: Period) {
  currentPeriod.value = period;
}

function selectGameType(code: string) {
  selectedGameType.value = code;
}

function formatUpdateTime(timestamp: number) {
  const date = new Date(timestamp * 1000);
  return `${date.getHours().toString().padStart(2, "0")}:${date.getMinutes().toString().padStart(2, "0")}`;
}
</script>

<template>
  <section class="leaderboard-view" aria-label="上机时长排行榜">
    <header class="leaderboard-header">
      <div class="leaderboard-title-row">
        <h2 class="leaderboard-title">🏆 上机时长排行榜</h2>
        <span class="update-time">更新于 {{ formatUpdateTime(currentData.updatedAt) }}</span>
      </div>

      <div class="leaderboard-controls">
        <div class="period-tabs">
          <button
            v-for="opt in periodOptions"
            :key="opt.value"
            class="period-tab"
            :class="{ active: currentPeriod === opt.value }"
            @click="selectPeriod(opt.value)"
          >
            {{ opt.label }}
          </button>
        </div>

        <div class="game-type-filters">
          <button
            v-for="gt in gameTypes"
            :key="gt.code"
            class="game-type-tag"
            :class="{ active: selectedGameType === gt.code }"
            @click="selectGameType(gt.code)"
          >
            {{ gt.name }}
          </button>
        </div>
      </div>
    </header>

    <div class="leaderboard-content">
      <div class="leaderboard-table">
        <div class="leaderboard-row head">
          <span class="col-rank">排名</span>
          <span class="col-member">会员</span>
          <span class="col-level">等级</span>
          <span class="col-duration">时长</span>
          <span class="col-sessions">次数</span>
          <span class="col-favorite">最爱游戏</span>
        </div>

        <div
          v-for="item in currentData.items"
          :key="item.memberNo"
          class="leaderboard-row"
          :class="{ 'top-three': item.rank <= 3 }"
        >
          <span class="col-rank">
            <span class="rank-badge" :class="getRankBadge(item.rank).class">
              {{ getRankBadge(item.rank).icon }}
            </span>
          </span>
          <span class="col-member">
            <span class="member-avatar">{{ item.nickname.charAt(0) }}</span>
            <span class="member-info">
              <strong class="member-name">{{ item.nickname }}</strong>
              <small class="member-no">{{ item.memberNo }}</small>
            </span>
          </span>
          <span class="col-level">
            <span class="level-badge">Lv.{{ item.level }}</span>
          </span>
          <span class="col-duration">
            <strong class="duration-value">{{ item.formattedDuration }}</strong>
          </span>
          <span class="col-sessions">{{ item.sessionCount }} 次</span>
          <span class="col-favorite">
            <span class="game-tag">{{ item.favoriteGame }}</span>
          </span>
        </div>
      </div>

      <div v-if="currentData.items.length === 0" class="empty-state">
        <p>暂无排行数据</p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.leaderboard-view {
  margin-top: 26px;
  border: 1px solid color-mix(in srgb, #162423 13%, transparent);
  background: color-mix(in srgb, #f1f8f6 86%, white 14%);
  box-shadow: 0 18px 50px color-mix(in srgb, #162423 10%, transparent);
  border-radius: 8px;
  padding: clamp(22px, 4vw, 42px);
}

.leaderboard-header {
  margin-bottom: 24px;
}

.leaderboard-title-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.leaderboard-title {
  margin: 0;
  font-size: clamp(20px, 2.5vw, 28px);
  font-weight: 800;
}

.update-time {
  font-size: 13px;
  color: color-mix(in srgb, #162423 60%, transparent);
}

.leaderboard-controls {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.period-tabs {
  display: flex;
  gap: 8px;
  padding: 4px;
  background: color-mix(in srgb, #162423 6%, transparent);
  border-radius: 8px;
  width: fit-content;
}

.period-tab {
  padding: 10px 24px;
  border: none;
  background: transparent;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  color: color-mix(in srgb, #162423 70%, transparent);
}

.period-tab:hover {
  background: color-mix(in srgb, #162423 8%, transparent);
}

.period-tab.active {
  background: #1b7f82;
  color: white;
  box-shadow: 0 4px 12px color-mix(in srgb, #1b7f82 40%, transparent);
}

.game-type-filters {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.game-type-tag {
  padding: 6px 16px;
  border: 1px solid color-mix(in srgb, #162423 15%, transparent);
  background: transparent;
  border-radius: 999px;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  color: color-mix(in srgb, #162423 75%, transparent);
}

.game-type-tag:hover {
  background: color-mix(in srgb, #1b7f82 10%, transparent);
  border-color: color-mix(in srgb, #1b7f82 30%, transparent);
}

.game-type-tag.active {
  background: color-mix(in srgb, #1b7f82 18%, transparent);
  border-color: #1b7f82;
  color: #1b7f82;
  font-weight: 700;
}

.leaderboard-table {
  overflow: hidden;
  border: 1px solid color-mix(in srgb, #162423 12%, transparent);
  border-radius: 8px;
}

.leaderboard-row {
  display: grid;
  grid-template-columns: 80px 1.5fr 100px 1.2fr 100px 1fr;
  gap: 12px;
  padding: 16px 18px;
  border-top: 1px solid color-mix(in srgb, #162423 10%, transparent);
  align-items: center;
  transition: background 0.2s ease;
}

.leaderboard-row:hover {
  background: color-mix(in srgb, #1b7f82 6%, transparent);
}

.leaderboard-row.head {
  background: color-mix(in srgb, #1b7f82 12%, transparent);
  font-weight: 800;
  border-top: 0;
  font-size: 13px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  color: color-mix(in srgb, #162423 75%, transparent);
}

.leaderboard-row.top-three {
  background: color-mix(in srgb, #c65f44 5%, transparent);
}

.leaderboard-row.top-three:hover {
  background: color-mix(in srgb, #c65f44 10%, transparent);
}

.col-rank {
  display: flex;
  justify-content: center;
}

.rank-badge {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  font-weight: 800;
  font-size: 14px;
}

.rank-gold {
  background: linear-gradient(135deg, #ffd700, #ffb300);
  box-shadow: 0 4px 12px color-mix(in srgb, #ffd700 50%, transparent);
}

.rank-silver {
  background: linear-gradient(135deg, #c0c0c0, #a0a0a0);
  box-shadow: 0 4px 12px color-mix(in srgb, #c0c0c0 50%, transparent);
}

.rank-bronze {
  background: linear-gradient(135deg, #cd7f32, #b87333);
  box-shadow: 0 4px 12px color-mix(in srgb, #cd7f32 50%, transparent);
}

.rank-normal {
  background: color-mix(in srgb, #162423 10%, transparent);
  font-size: 13px;
}

.col-member {
  display: flex;
  align-items: center;
  gap: 12px;
  min-width: 0;
}

.member-avatar {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  background: linear-gradient(135deg, #1b7f82, #155a5c);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 800;
  font-size: 18px;
  flex-shrink: 0;
}

.member-info {
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.member-name {
  font-size: 15px;
  font-weight: 700;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.member-no {
  font-size: 12px;
  color: color-mix(in srgb, #162423 55%, transparent);
}

.col-level {
  display: flex;
  justify-content: center;
}

.level-badge {
  padding: 4px 12px;
  border-radius: 999px;
  background: linear-gradient(135deg, #c65f44, #a04d36);
  color: white;
  font-size: 12px;
  font-weight: 700;
}

.col-duration {
  text-align: center;
}

.duration-value {
  font-size: 18px;
  font-weight: 800;
  color: #1b7f82;
}

.col-sessions {
  text-align: center;
  font-size: 14px;
  color: color-mix(in srgb, #162423 75%, transparent);
}

.col-favorite {
  display: flex;
  justify-content: center;
}

.game-tag {
  padding: 4px 12px;
  border-radius: 6px;
  background: color-mix(in srgb, #1b7f82 14%, transparent);
  font-size: 12px;
  font-weight: 600;
  color: #1b7f82;
}

.empty-state {
  text-align: center;
  padding: 60px 20px;
  color: color-mix(in srgb, #162423 50%, transparent);
}

@media (max-width: 860px) {
  .leaderboard-row {
    grid-template-columns: 60px 1fr 80px;
    grid-template-areas:
      "rank member member"
      "rank level duration"
      "rank sessions favorite";
    padding: 14px 12px;
    gap: 8px;
  }

  .leaderboard-row.head {
    display: none;
  }

  .col-rank { grid-area: rank; }
  .col-member { grid-area: member; }
  .col-level { grid-area: level; }
  .col-duration { grid-area: duration; }
  .col-sessions { grid-area: sessions; }
  .col-favorite { grid-area: favorite; }

  .rank-badge {
    width: 36px;
    height: 36px;
    font-size: 12px;
  }

  .member-avatar {
    width: 38px;
    height: 38px;
    font-size: 16px;
  }

  .duration-value {
    font-size: 15px;
  }

  .leaderboard-controls {
    flex-direction: column;
  }

  .period-tabs {
    width: 100%;
  }

  .period-tab {
    flex: 1;
    padding: 10px 12px;
    font-size: 13px;
  }

  .game-type-filters {
    overflow-x: auto;
    flex-wrap: nowrap;
    padding-bottom: 4px;
  }

  .game-type-tag {
    flex-shrink: 0;
  }
}
</style>
