/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package org.hyperledger.fabric.shim;

public class TlsConfig {
    //Disabled forces default to be TLS enabled
    private boolean disabled = true;
    private String key;
    private String cert;
    // ClientCACerts set if connecting peer should be verified
    private byte[] clientCACerts;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public byte[] getClientCACerts() {
        return clientCACerts;
    }

    public void setClientCACerts(byte[] clientCACerts) {
        this.clientCACerts = clientCACerts;
    }
}
