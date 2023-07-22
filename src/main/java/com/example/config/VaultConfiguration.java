package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.support.VaultToken;

@Configuration
class VaultConfiguration extends AbstractVaultConfiguration {

    @Value("${spring.cloud.vault.app-role.role-id")
    String roleId;
    @Value("${spring.cloud.vault.app-role.secret-id")
    String secretId;
    @Override
    public VaultEndpoint vaultEndpoint() {
        return new VaultEndpoint();
    }

    @Override
    public ClientAuthentication clientAuthentication() {

        AppRoleAuthenticationOptions options = AppRoleAuthenticationOptions.builder()
                .roleId(AppRoleAuthenticationOptions.RoleId.provided(roleId))
                //.secretId(AppRoleAuthenticationOptions.SecretId.wrapped(VaultToken.of("…")))
                .secretId(AppRoleAuthenticationOptions.SecretId.provided(secretId))
                .build();

        return new AppRoleAuthentication(options, restOperations());
    }
}