/*
Copyright IBM Corp. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
*/

package org.hyperledger.fabric.shim;

public interface Server {

    void start() throws Exception;

    void stop() throws Exception;

    void blockUntilShutdown() throws InterruptedException;
}
