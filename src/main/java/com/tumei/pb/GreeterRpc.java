package com.tumei.pb;

import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by caihan on 16/12/10.
 */
public class GreeterRpc extends GreeterGrpc.GreeterImplBase {
    private Log log = LogFactory.getLog(GreeterRpc.class);

    @Override
    public void sayHello(Auth.HelloRequest request, StreamObserver<Auth.HelloReply> responseObserver) {
        log.info("hello....");
    }
}
