<script setup lang="ts">
import { onMounted, ref } from "vue";
import { fetchOverview } from "./api/client";
import { APP_CODE, APP_NAME } from "./constants/app";
import { REQUEST_MESSAGES } from "./constants/messages";
import { createFallbackOverview } from "./state/dashboard";
import type { OverviewResponse } from "./types";
import FeatureStrip from "./components/FeatureStrip.vue";
import MetricGrid from "./components/MetricGrid.vue";
import OperationsTable from "./components/OperationsTable.vue";
import LeaderboardView from "./components/LeaderboardView.vue";

const overview = ref<OverviewResponse>(createFallbackOverview());
const notice = ref(REQUEST_MESSAGES.overviewFallback);
const currentView = ref<"overview" | "leaderboard">("overview");

const viewTabs = [
  { value: "overview", label: "运营总览", icon: "📊" },
  { value: "leaderboard", label: "时长排行榜", icon: "🏆" }
] as const;

function goHealth() {
  window.location.href = REQUEST_MESSAGES.healthPath;
}

function switchView(view: "overview" | "leaderboard") {
  currentView.value = view;
}

onMounted(async () => {
  try {
    overview.value = await fetchOverview();
    notice.value = "后端服务已联通，当前展示实时接口数据。";
  } catch {
    notice.value = REQUEST_MESSAGES.overviewFallback;
  }
});
</script>

<template>
  <main class="app-shell">
    <header class="topbar">
      <div>
        <span class="brand-code">{{ APP_CODE }}</span>
        <h1 class="brand-title">{{ APP_NAME }}</h1>
      </div>
      <van-button type="primary" @click="goHealth">API Health</van-button>
    </header>

    <section class="view-switcher">
      <button
        v-for="tab in viewTabs"
        :key="tab.value"
        class="view-tab"
        :class="{ active: currentView === tab.value }"
        @click="switchView(tab.value)"
      >
        <span class="tab-icon">{{ tab.icon }}</span>
        <span class="tab-label">{{ tab.label }}</span>
      </button>
    </section>

    <section class="workspace">
      <template v-if="currentView === 'overview'">
        <div class="lead-grid">
          <article class="hero-panel">
            <span class="pill">{{ notice }}</span>
            <h2>{{ overview.appName }}</h2>
            <p>{{ overview.description }}</p>
          </article>
          <MetricGrid :items="overview.kpis" />
        </div>
        <FeatureStrip :items="overview.features" />
        <section class="work-panel">
          <h2>运营任务流</h2>
          <OperationsTable :records="overview.records" />
        </section>
      </template>

      <template v-if="currentView === 'leaderboard'">
        <div class="lead-grid">
          <article class="hero-panel">
            <span class="pill">{{ notice }}</span>
            <h2>上机时长排行榜</h2>
            <p>查看会员累计上机时长排名，按日/周/月统计，支持按游戏类型筛选。</p>
          </article>
          <MetricGrid :items="overview.kpis" />
        </div>
        <LeaderboardView :leaderboard="overview.leaderboard" />
      </template>
    </section>
  </main>
</template>

<style scoped>
.view-switcher {
  display: flex;
  gap: 8px;
  padding: 0 clamp(20px, 4vw, 56px);
  background: color-mix(in srgb, #f1f8f6 86%, white 14%);
  border-bottom: 1px solid color-mix(in srgb, #162423 12%, transparent);
  overflow-x: auto;
}

.view-tab {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 14px 24px;
  border: none;
  background: transparent;
  border-radius: 8px 8px 0 0;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  color: color-mix(in srgb, #162423 65%, transparent);
  white-space: nowrap;
  position: relative;
  margin-bottom: -1px;
}

.view-tab:hover {
  background: color-mix(in srgb, #1b7f82 8%, transparent);
  color: #162423;
}

.view-tab.active {
  background: color-mix(in srgb, #1b7f82 12%, transparent);
  color: #1b7f82;
}

.view-tab.active::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 16px;
  right: 16px;
  height: 3px;
  background: #1b7f82;
  border-radius: 3px 3px 0 0;
}

.tab-icon {
  font-size: 18px;
}

.tab-label {
  font-weight: 700;
}

@media (max-width: 860px) {
  .view-switcher {
    padding: 0 12px;
  }

  .view-tab {
    padding: 12px 16px;
    font-size: 14px;
  }

  .tab-icon {
    font-size: 16px;
  }
}
</style>
