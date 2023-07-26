package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.annotation.VaultPropertySource;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.support.VaultToken;

import java.net.URI;

//@Configuration
@VaultPropertySource(
        value = "${datasource.vault-creds-path}",
        propertyNamePrefix = "datasource.",
        renewal = VaultPropertySource.Renewal.ROTATE)
@VaultPropertySource(
        value = "${app.vault-kv-secret-path}",
        propertyNamePrefix = "secret.movie-service.")
public class VaultConfiguration extends AbstractVaultConfiguration {

    @Override
    public VaultEndpoint vaultEndpoint() {
        String uri = getEnvironment().getProperty("vault.uri");
        if (uri == null) {
            throw new IllegalStateException();
        }
        return VaultEndpoint.from(URI.create(uri));
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        String roleId = getEnvironment().getProperty("vault.app-role.role-id");
        if (roleId == null) {
            throw new IllegalStateException();
        }
        AppRoleAuthenticationOptions options = AppRoleAuthenticationOptions.builder()
                .roleId(AppRoleAuthenticationOptions.RoleId.provided(roleId))
                .build();

        return new AppRoleAuthentication(options, restOperations());
    }
}

/*
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
                //.secretId(AppRoleAuthenticationOptions.SecretId.wrapped(VaultToken.of()))
                .secretId(AppRoleAuthenticationOptions.SecretId.provided(secretId))
                .build();

        return new AppRoleAuthentication(options, restOperations());
    }
}
*/
