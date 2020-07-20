package com.cloudbest.search.component;

import com.cloudbest.search.service.EsItemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 每天导入商品数据到es的定时器
 * @author : hdq
 * @date 2020/7/14 10:50
 */
@Slf4j
@Component
public class ImportItemsTask {

    @Autowired
    private EsItemsService esItemsService;

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每1分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @Scheduled(cron = "0 0 0 * * ?")
    private void importAll(){
        log.debug("-------定时任务 商品导入es--------");
        int count = esItemsService.importAll();
        log.info("商品导入数据条数：{}",count);
    }
}
