/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package org.hyperledger.fabric.shim;

import org.hyperledger.fabric.metrics.Metrics;
import org.hyperledger.fabric.metrics.MetricsProvider;
import org.hyperledger.fabric.shim.chaincode.EmptyChaincode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ChaincodeServerTest {
    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String ccid = "mycc:fcbf8724572d42e859a7dd9a7cd8e2efb84058292017df6e3d89178b64e6c831";
    private String addressChaincodeServer = "127.0.0.1:9999";
    private Chaincode chaincode = new EmptyChaincode();
    private TlsConfig tlsConfig = new TlsConfig();

    @Test
    public void testChaincodeServer_EmptyChaincode_new() {
        ChaincodeBase chaincodeBase = new EmptyChaincode();
//        ChaincodeServer chaincodeServer = new ChaincodeServerImpl(chaincodeBase, ccid, addressChaincodeServer, chaincode, tlsConfig);
    }

    @Test
    public void testChaincodeServer_EmptyChaincode_connect() {
        ChaincodeBase chaincodeBase = new EmptyChaincode();
//        ChaincodeServer chaincodeServer = new ChaincodeServerImpl(chaincodeBase, ccid, addressChaincodeServer, chaincode, tlsConfig);
//        try {
//            chaincodeServer.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testChaincodeServer_EmptyChaincode_start() {
        ChaincodeBase chaincodeBase = new EmptyChaincode();
//        ChaincodeServer chaincodeServer = new ChaincodeServerImpl(chaincodeBase, ccid, addressChaincodeServer, chaincode, tlsConfig);
//        try {
//            chaincodeServer.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            chaincodeServer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testChaincodeServer_ChaincodeBase_full_start() {
        ChaincodeBase chaincodeBase = new EmptyChaincode();

        environmentVariables.set("CORE_CHAINCODE_ID_NAME", "mycc");
        environmentVariables.set("CORE_PEER_ADDRESS", "127.0.0.1:7050");
        environmentVariables.set("CORE_PEER_TLS_ENABLED", "false");
        environmentVariables.set("CORE_PEER_TLS_ROOTCERT_FILE", "src/test/resources/ca.crt");
        environmentVariables.set("CORE_TLS_CLIENT_KEY_PATH", "src/test/resources/client.key.enc");
        environmentVariables.set("CORE_TLS_CLIENT_CERT_PATH", "src/test/resources/client.crt.enc");

        chaincodeBase.processEnvironmentOptions();
        chaincodeBase.validateOptions();

//        ChaincodeServer chaincodeServer = new ChaincodeServerImpl(chaincodeBase, ccid, addressChaincodeServer, chaincode, tlsConfig);
//        try {
//            chaincodeServer.connect();
//            chaincodeServer.start();
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
