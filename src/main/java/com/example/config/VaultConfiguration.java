package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.vault.annotation.VaultPropertySource;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;

import java.net.URI;

@Configuration
@VaultPropertySource(value = "${spring.vault.path}")
public class VaultConfiguration extends AbstractVaultConfiguration {

    @Override
    public VaultEndpoint vaultEndpoint() {
        String uri = getEnvironment().getProperty("spring.vault.uri");
        if (uri == null) {
            throw new IllegalStateException();
        }
        return VaultEndpoint.from(URI.create(uri));
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        String roleId = getEnvironment().getProperty("spring.vault.app-role.role-id");
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
