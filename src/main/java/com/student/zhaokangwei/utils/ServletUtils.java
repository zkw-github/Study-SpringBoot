package com.student.zhaokangwei.utils;

import javax.servlet.ServletContext;

public class ServletUtils {

    /**
     * 记录访问量
     * @param context
     * @return 返回访问量
     */
    public static int recordVisits(ServletContext context) {
//        Object visitsObj = context.getAttribute("visits");    //从上下文属性中获取visits属性值
//        if (visitsObj == null) {
//            //获取上下文初始化参数，然后赋值给visits对象
//            visits = Integer.parseInt(context.getInitParameter("visits"));
//        } else {
//            visits = Integer.parseInt(visitsObj.toString());
//        }


        int visits = Integer.parseInt(context.getAttribute("visits").toString());//从上下文属性中获取visits属性值
        //获取完之后，再将visits存入上下文属性中
        context.setAttribute("visits", ++visits);
        return visits;
    }

}
