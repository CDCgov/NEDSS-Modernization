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

Storybook is a component library that gives information on how to use the reusable react components like button,checkbox, table and so on. Run storybook locally to see what components are already available.

```sh
npm run storybook.
```

The no lint option is to ensure there aren't any lint errors as of now. This could be an effort going forward.

## Containerizing

The frontend application can be packaged as a Docker container by executing the following command from the `apps/moderniztion-ui` directory

```shell
docker build .
```
