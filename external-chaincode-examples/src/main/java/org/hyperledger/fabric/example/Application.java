/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package org.hyperledger.fabric.example;

import org.hyperledger.fabric.shim.ChaincodeServer;
import org.hyperledger.fabric.shim.ChaincodeServerImpl;
import org.hyperledger.fabric.shim.TlsConfig;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        ChaincodeServer chaincodeServer = new ChaincodeServerImpl(
            new SimpleAsset(),
            "mycc:e484f74af068d10be10c4760f1c92bb324f7a304bb3e91217b13576de5b0a081",
            "127.0.0.1:9999",
            new TlsConfig() {
                @Override
                public void setDisabled(boolean disabled) {
                    super.setDisabled(true);
                }
            });

        chaincodeServer.start();
        chaincodeServer.blockUntilShutdown();
    }

}
