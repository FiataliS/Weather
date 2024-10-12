package org.mifi;

import org.mifi.entity.Fact;
import org.mifi.entity.Forecasts;

import java.util.List;

public class Response {
    private Fact fact;
    private List<Forecasts> forecasts;

    public Fact getFact() {
        return fact;
    }

    public List<Forecasts> getForecasts() {
        return forecasts;
    }
}
