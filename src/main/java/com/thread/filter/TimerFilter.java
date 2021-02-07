package com.thread.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: Doug Li
 * @Date 2021/1/31
 * @Describe: 统计请求耗时
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class TimerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TimerFilter init ");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        try{
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            log.info("请求处理完成:URL:[{}],耗时:[{}]",((HttpServletRequest) servletRequest).getRequestURI(),System.currentTimeMillis()-startTime);
        }
    }

    @Override
    public void destroy() {
        log.info("TimerFilter destroy");
    }
}
