package com.psiphiglobal.hyperledger_client.api;

import com.psiphiglobal.hyperledger_client.ChaincodeExecutionException;

public interface ChaincodeExecutor
{
    String invoke(String functionName, String... args) throws ChaincodeExecutionException;

    String query(String functionName, String... args) throws ChaincodeExecutionException;
}
