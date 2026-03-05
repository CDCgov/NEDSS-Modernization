# NBS Modernization Frontend

## Running

1. From the `apps/modernization-ui` directory, execute

    ```bash
    npm install
    ```

2. Then execute to start the application. Default url is [http://localhost:3000](http://localhost:3000)

    ```bash
    npm run start
    ```

## Auto Generating Endpoints

The UI code utilizes [gql-typescript-generator](https://github.com/TheBrainFamily/gql-typescript-generator) and [graphql-code-generator](https://github.com/dotansimha/graphql-code-generator) to auto generate GraphQL API requests.

Workflow:

1. Update the API `.graphqls` schema files
2. Run the following command in the `modernization-ui` folder while the [API](../modernization-api/README.md) is running
    ```
    npm run generate
    ```
3. Use the newly generated methods to make API calls from typescript

## Storybook

[Storybook](https://storybook.js.org/) is a UI framework that provides the ability to construct "stories" that allow us to view, interact, and test reusable React components like button, checkbox, table, as well as richer components like forms and menus. Currently we have implemented stories for all of our Design System components.

Run storybook locally to see what components are already available.

```sh
npm run storybook
```

The no lint option is to ensure there aren't any lint errors as of now. This could be an effort going forward.

## Containerizing

The frontend application can be packaged as a Docker container by executing the following command from the `apps/modernization-ui` directory.

```shell
docker build .
```

## Database Migrations

Liquibase is used in development to manage database migrations. They can be found in `src/main/resources/db`. Only `sqlFile` migrations should be created to ensure it is always an option to manually apply migrations instead of via automatic tooling.

In production, liquibase is not enabled by default. This is controlled by the `liquibase.enabled` property.
