package com.cx.monitor.endpoint;

import com.cx.common.annotation.CommonEndPoint;
import com.cx.common.annotation.CommonEndPoint;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.List;

/**
 * @author Administrator·
 */
@CommonEndPoint
public class CommonHttpTraceEndpoint {

    private final HttpTraceRepository repository;

    public CommonHttpTraceEndpoint(HttpTraceRepository repository) {
        this.repository = repository;
    }

    public apsHttpTraceDescriptor traces() {
        return new apsHttpTraceDescriptor(this.repository.findAll());
    }

    public static final class apsHttpTraceDescriptor {

        private final List<HttpTrace> traces;

        private apsHttpTraceDescriptor(List<HttpTrace> traces) {
            this.traces = traces;
        }

        public List<HttpTrace> getTraces() {
            return this.traces;
        }
    }
}
