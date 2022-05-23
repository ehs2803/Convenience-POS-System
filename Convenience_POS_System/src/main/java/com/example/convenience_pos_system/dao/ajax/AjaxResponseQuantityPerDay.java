package com.example.convenience_pos_system.dao.ajax;

import java.util.List;

public class AjaxResponseQuantityPerDay {
    private String state;
    private List<AjaxProductQuantityPerDay> ajaxProductQuantityPerDayList;

    public AjaxResponseQuantityPerDay() {
    }

    public AjaxResponseQuantityPerDay(String state, List<AjaxProductQuantityPerDay> ajaxProductQuantityPerDayList) {
        this.state = state;
        this.ajaxProductQuantityPerDayList = ajaxProductQuantityPerDayList;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAjaxProductQuantityPerDayList(List<AjaxProductQuantityPerDay> ajaxProductQuantityPerDayList) {
        this.ajaxProductQuantityPerDayList = ajaxProductQuantityPerDayList;
    }
}
