/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package org.hyperledger.fabric.shim;

import java.io.IOException;
import java.time.Duration;

public class ServerImpl implements Server {

    private GrpcServer grpcServer;

    public ServerImpl(String address, TlsConfig tlsConf, ChaincodeBase chaincodeBase) throws IOException {
        if (address == null || address.isEmpty()) {
            throw new IOException("server listen address not provided");
        }

        final String host = address.substring(0, address.indexOf(":"));
        final int port = Integer.parseInt(address.substring(address.indexOf(":") + 1));

        grpcServer = new NettyGrpcServer(port, tlsConf, chaincodeBase);
    }

    public void start() throws IOException {
        if (grpcServer == null) {
            throw new IOException("null server");
        }

        grpcServer.start();
    }

    public void stop() throws InterruptedException {
        if (grpcServer != null) {
            grpcServer.stop();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (grpcServer != null) {
            grpcServer.blockUntilShutdown();
        }
    }
}
