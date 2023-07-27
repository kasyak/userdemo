package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.VaultResponseSupport;

public class VaultUsage {
    @Autowired
    private VaultOperations operations;

    public VaultResponseSupport<String> readPassword(String userRole) {
        VaultResponseSupport<String> response = operations.read(userRole, String.class);
        return response;
    }
}
