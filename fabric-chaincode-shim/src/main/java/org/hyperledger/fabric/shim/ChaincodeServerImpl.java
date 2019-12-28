/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package org.hyperledger.fabric.shim;

import java.io.IOException;

public class ChaincodeServerImpl implements ChaincodeServer {

    // Addesss is the listen address of the chaincode server
    private final String addressChaincodeServer;
    // TlsConfig is the TLS properties passed to chaincode server
    private final TlsConfig tlsConfig;
    Server server;

    // KaOpts keepalive options, sensible defaults provided if nil
    //KaOpts *keepalive.ServerParameters

    private final ChaincodeBase chaincodeBase;

    public ChaincodeServerImpl(ChaincodeBase chaincodeBase, String addressChaincodeServer, TlsConfig tlsConfig) {
        this.chaincodeBase = chaincodeBase;
        this.chaincodeBase.processEnvironmentOptions();
        this.chaincodeBase.validateOptions();

        this.addressChaincodeServer = addressChaincodeServer;
        this.tlsConfig = tlsConfig;
    }

    // Start the server
    public void start() throws IOException {
        if (this.addressChaincodeServer == null || this.addressChaincodeServer.isEmpty()) {
            throw new IOException("address must be specified");
        }

        if (this.chaincodeBase == null) {
            throw new IOException("chaincode must be specified");
        }

        if (!this.tlsConfig.isDisabled()) {
            // validate
        }

        // create listener and grpc server
        server = new ServerImpl(this.addressChaincodeServer, tlsConfig, chaincodeBase);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void blockUntilShutdown() throws InterruptedException {
        server.blockUntilShutdown();
    }
}
