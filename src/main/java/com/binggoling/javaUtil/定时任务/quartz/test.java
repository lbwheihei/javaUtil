package com.binggoling.javaUtil.定时任务.quartz;

/** 
 * @Description: 测试类 
 * 
 * @ClassName: QuartzTest 
 * @Copyright: Copyright (c) 2014 
 * 
 * @author Comsys-LZP 
 * @date 2014-6-26 下午03:35:05 
 * @version V2.0 
 */  
public class test {  
    public static void main(String[] args) {  
        try {  
            String job_name = "动态任务调度";  
            System.out.println("【系统启动】开始(每1秒输出一次)...");    
            QuartzManager.addJob(job_name, QuartzJob.class, "0/1 * * * * ?");    
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  