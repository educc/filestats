package com.edu.filestats.statistics;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatisticStrategyFactory {

    private List<StatisticStrategy> strategies;

    public StatisticStrategyFactory(List<StatisticStrategy> strategies) {
        this.strategies = strategies;
    }

    public List<StatisticStrategy> getAll() {
        return strategies;
    }

    public void setStrategies(List<StatisticStrategy> strategies) {
        this.strategies = strategies;
    }
}
