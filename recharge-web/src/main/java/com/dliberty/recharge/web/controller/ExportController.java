package com.dliberty.recharge.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.dliberty.recharge.api.service.ITbRechargeCardService;
import com.dliberty.recharge.common.utils.BeanUtil;
import com.dliberty.recharge.dto.ExportRechargeCardDto;
import com.dliberty.recharge.vo.conditions.RechargeCardQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author changzzc
 */
@Controller
@RequestMapping("/export")
@Api(value = "充值卡导出接口", tags = "ExportController")
public class ExportController {

    private static final Integer default_size = 100000;

    private static Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    private ITbRechargeCardService tbRechargeCardService;

    @ApiOperation("充值卡导出")
    @GetMapping("/rechargeCard")
    public void exportRechargeCard(RechargeCardQueryVo vo , HttpServletResponse response) throws Exception{

        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        ThreadLocal<Map<String , Integer>> mainThreadLocal = new ThreadLocal<>();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        OutputStream out = null;
        ExcelWriter writer = null;

        try{
            out = response.getOutputStream();
            WriteWorkbook writeWorkbook = new WriteWorkbook();
            writeWorkbook.setOutputStream(out);
            writeWorkbook.setExcelType(ExcelTypeEnum.XLSX);
            writer = new ExcelWriter(writeWorkbook);
            String fileName = URLEncoder.encode("充值卡导出数据" + DateUtils.format(new Date() , DateUtils.DATE_FORMAT_14), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            int count = tbRechargeCardService.exportCount(vo);
            long pages = count / default_size;
            if (count % default_size != 0L) {
                ++pages;
            }

            for (int index = 1; index <= pages; index++ ){
                fixedThreadPool.execute(new QueryRunnable(writer , index , default_size, BeanUtil.toBean(vo , RechargeCardQueryVo.class), singleThreadPool , mainThreadLocal));
            }
            fixedThreadPool.shutdown();
            fixedThreadPool.awaitTermination(1 , TimeUnit.HOURS);
            singleThreadPool.shutdown();
            singleThreadPool.awaitTermination(1 , TimeUnit.HOURS);
            logger.info("线程池执行结束了");

        }catch (Exception e){
            e.printStackTrace();
            logger.info("导出失败");
        }finally {
            out.flush();
            writer.finish();
            out.close();
        }
    }

    class ExportRunnable implements Runnable{

        private ExcelWriter writer;
        private List<ExportRechargeCardDto> list;
        private ThreadLocal<Map<String , Integer>> mainThreadLocal;


        public ExportRunnable(ExcelWriter writer, List<ExportRechargeCardDto> list, ThreadLocal<Map<String , Integer>> mainThreadLocal) {
            this.writer = writer;
            this.list = list;
            this.mainThreadLocal = mainThreadLocal;
        }

        @Override
        public void run() {
            Map<String, List<ExportRechargeCardDto>> detailsMap = list.stream() .collect(Collectors.groupingBy(ExportRechargeCardDto::getMoney));
            for(String key : detailsMap.keySet()){
                if(mainThreadLocal.get() == null){
                    Map<String , Integer> map = new HashMap<>();
                    mainThreadLocal.set(map);
                }
                if(!mainThreadLocal.get().containsKey(key)){
                    mainThreadLocal.get().put(key , mainThreadLocal.get().size());
                }
                writer.write(detailsMap.get(key) , EasyExcel.writerSheet(mainThreadLocal.get().get(key) , key).build());
            }
        }
    }

    class QueryRunnable implements Runnable{
        private ExcelWriter writer;
        private Integer pageNo;
        private Integer pageSize;
        private RechargeCardQueryVo rechargeCardQueryVo;
        private ExecutorService singleThreadPool;
        private ThreadLocal<Map<String , Integer>> mainThreadLocal;

        public QueryRunnable(ExcelWriter writer, Integer pageNo, Integer pageSize , RechargeCardQueryVo rechargeCardQueryVo , ExecutorService singleThreadPool, ThreadLocal<Map<String , Integer>> mainThreadLocal) {
            this.writer = writer;
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.rechargeCardQueryVo = rechargeCardQueryVo;
            this.singleThreadPool = singleThreadPool;
            this.mainThreadLocal = mainThreadLocal;
        }

        @Override
        public void run() {
            rechargeCardQueryVo.setPageNo(pageNo);
            rechargeCardQueryVo.setPageSize(pageSize);
            Long start = System.currentTimeMillis();
            List<ExportRechargeCardDto> exportList = tbRechargeCardService.export(rechargeCardQueryVo);
            Long end = System.currentTimeMillis();
            logger.info("{}消耗：{}" ,pageNo, end - start);
            singleThreadPool.execute(new ExportRunnable(writer , exportList , mainThreadLocal));
        }
    }
}
