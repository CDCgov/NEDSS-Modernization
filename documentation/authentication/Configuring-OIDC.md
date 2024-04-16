# Configuring OpenID Connect

The Modernized NBS services support authentication with OpenID Connect. For local development a pre-configured Keycloak
container has been provided in the `cdc-sandbox` folder. The `development` profile of each service contains the
configuration required to authentication with this container.

To start the development Keycloak container run the following from the `cdc-sandbox` folder.

```shell
docker compose up -d --build keycloak
```

## Configuring Keycloak

1. Log in to the Keycloak instance with a user that can create a realm
2. Create a new realm i.e. `nbs-users`
3. Create a client for an OIDC Client to authenticate users
    - Navigate to `Clients`
    - Click `Create client`
    - Enter the `Client ID` i.e. `nbs-client`
    - Click `Next`
    - Enable `Client authentication`
    - Click `Next`
    - Enter the `Root URL` which is the base URL of the environment Keycloak is being configured for.
    - Add a `Valid redirect URI` of `/login/oauth2/code/nbs-users` to allow an OIDC Client to redirect users to log
      in.
    - Add a `Valid post logout redirect URI` of `/nbs/logged-out` to allow an OIDC Client to redirect users to log
      out.
    - Click `Save`

### Allowing OAuth2 authentication from Postman

Any client can allow authentication from Postman by

- Adding a `Valid redirect URI` of `https://oauth.pstmn.io/v1/callback`.
- Adding a `Web origin` of `https://oauth.pstmn.io`

## Configuring the Services

With a Keycloak realm created the services can now be configured for OIDC authentication. Resource servers will only
need the `nbs.security.oidc.uri` where Clients will also need to be given a Client ID and Client Secret.

The `nbs.security.oidc.uri` property should be set to `https://{keycloak-host}/realms/{realm-name}`
The `nbs.security.oidc.client.id` property will be the ID of the client the service will use.
The `nbs.security.oidc.client.secret` value can be found in the `Credentials` tab of the client within the Keycloak
realm.
