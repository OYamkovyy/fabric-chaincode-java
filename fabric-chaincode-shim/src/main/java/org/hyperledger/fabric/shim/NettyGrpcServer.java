/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package org.hyperledger.fabric.shim;


import io.grpc.*;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import io.grpc.stub.StreamObservers;
import org.hyperledger.fabric.protos.peer.Chaincode;
import org.hyperledger.fabric.protos.peer.ChaincodeGrpc;
import org.hyperledger.fabric.protos.peer.ChaincodeShim;
import org.hyperledger.fabric.shim.impl.ChaincodeMessageFactory;
import org.hyperledger.fabric.shim.impl.ChaincodeSupportClient;

import javax.net.ssl.SSLException;

import static org.hyperledger.fabric.shim.ChaincodeBase.CORE_CHAINCODE_ID_NAME;

public class NettyGrpcServer implements GrpcServer {

    private final Server server;

    static class ConnectChaincodeToPeer extends ChaincodeGrpc.ChaincodeImplBase {

        @Override
        public StreamObserver<ChaincodeShim.ChaincodeMessage> connect(StreamObserver<ChaincodeShim.ChaincodeMessage> responseObserver) {
            String chaincodeId = System.getenv(CORE_CHAINCODE_ID_NAME);
            final ChaincodeShim.ChaincodeMessage reply = ChaincodeMessageFactory.newRegisterChaincodeMessage(Chaincode.ChaincodeID.newBuilder().setName(chaincodeId).build());
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            return responseObserver;
        }
    }

    public NettyGrpcServer(int port, TlsConfig tlsConfig, String peerHost, int peerPort, ChaincodeBase chaincodeBase, ChaincodeServer chaincodeServer) throws SSLException {
        final ServerBuilder serverBuilder = NettyServerBuilder.forPort(port)
                .addService(new ConnectChaincodeToPeer())
                .keepAliveTime(1, TimeUnit.MINUTES)
                .keepAliveTimeout(20, TimeUnit.SECONDS)
                .permitKeepAliveTime(1, TimeUnit.MINUTES)
                .permitKeepAliveWithoutCalls(true)
                .maxConnectionAge(5, TimeUnit.SECONDS)
                .maxInboundMetadataSize(100 * 1024 * 1024)
                .maxInboundMessageSize(100 * 1024 * 1024);
//
//                .build()
//                .start();

//        final SslContext sslContext;
//        if (tlsConfig != null && !tlsConfig.isDisabled()) {
//            final File certificatePemFile = Paths.get(tlsConfig.getCert()).toFile();
//            final File privateKeyPemFile = Paths.get(tlsConfig.getKey()).toFile();

//            sslContext = SslContextBuilder.forServer(certificatePemFile, privateKeyPemFile).build();
//            sslContext = GrpcSslContexts.configure(SslContextBuilder.forServer(certificatePemFile, privateKeyPemFile)).build();
//            serverBuilder.usesslContext(sslContext);
//        }

//        final ServerServiceDefinition serverServiceDefinition = ServerServiceDefinition.builder(ServiceDescriptor.newBuilder("").build()).build();
//        nettyChannelBuilder.addService(serverServiceDefinition);

        server = serverBuilder.build();
    }

    public void start() throws IOException {
        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(() -> {
                            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                            System.err.println("*** shutting down gRPC server since JVM is shutting down");
                            try {
                                NettyGrpcServer.this.stop();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.err.println("*** server shut down");
                        }));
        try {
            server.start().awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public void stop() throws InterruptedException {
        if (server != null) {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }
}
