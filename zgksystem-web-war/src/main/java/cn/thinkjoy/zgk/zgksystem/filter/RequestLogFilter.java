package cn.thinkjoy.zgk.zgksystem.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.RequestContextFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yangguorong on 16/4/25.
 */
public class RequestLogFilter extends RequestContextFilter{

    private static final Logger LOGGER = LoggerFactory.getLogger("SPECLOGGER");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        StringBuffer logMessage = new StringBuffer();
        boolean hasError = false;
        long startTime = System.currentTimeMillis();
        try {
            logMessage.append("(");
            logMessage.append(request.getRequestURI());
            logMessage.append(",");
            logMessage.append(request.getParameter("token"));
            logMessage.append(",");
            logMessage.append(request.getMethod());
            logMessage.append(",");

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            hasError = true;
        } finally {
            if (hasError) {
                logMessage.append("N");
            } else {
                logMessage.append("Y");
            }
            logMessage.append(",");
            logMessage.append(System.currentTimeMillis() - startTime);
            logMessage.append("ms)");

            LOGGER.info(logMessage.toString());
        }
    }
}
