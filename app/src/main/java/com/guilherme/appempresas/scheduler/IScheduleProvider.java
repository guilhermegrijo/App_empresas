package com.guilherme.appempresas.scheduler;

import io.reactivex.rxjava3.core.Scheduler;

public interface IScheduleProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
