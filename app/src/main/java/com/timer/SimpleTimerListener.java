package com.timer;

public interface SimpleTimerListener {
    public void before(long delay, long period);
    public void after(long delay, long period);
}
