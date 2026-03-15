package com.umevent.management.shared.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final Logger digestLog = LoggerFactory.getLogger("DIGEST");
    private static final String TRACE_ID = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String traceId = request.getHeader("X-Correlation-Id");
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
        }

        long start = System.currentTimeMillis();
        MDC.put(TRACE_ID, traceId);
        response.setHeader("X-Correlation-Id", traceId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("request method={} uri={} status={} durationMs={} traceId={}",
                request.getMethod(), request.getRequestURI(), response.getStatus(), duration, traceId);

            String digestCode = response.getStatus() >= 400 ? "ERROR" : "SUCCESS";
            digestLog.info("[status,{}],[code,{}],[method,{}],[uri,{}],[durationMs,{}]",
                response.getStatus(), digestCode, request.getMethod(), request.getRequestURI(), duration);

            MDC.remove(TRACE_ID);
        }
    }
}
