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

// ENV CORE_CHAINCODE_ID_NAME=externalcc:06d1d324e858751d6eb4211885e9fd9ff74b62cb4ffda2242277fac95d467033
    public static void main(String[] args) throws IOException, InterruptedException {
        ChaincodeServer chaincodeServer = new ChaincodeServerImpl(
            new SimpleAsset(),
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
