package com.chang.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 每隔6秒打印一下
 */
@Slf4j
@Component
public class SchedulerTask {

    private int count = 0;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //@Scheduled(cron="*/6 * * * * ?")
    @Scheduled(fixedRate = 6000)
    private void process(){
        log.info("定时任务运行次数：{}",count++);
        log.info("现在时间：{}",dateFormat.format(new Date()));
    }
}
