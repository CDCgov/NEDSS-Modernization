# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Running

1. In the `ui` directory, execute
    ```bash
    npm install
    ```
1. Then execute to start the application. Default url is [http://localhost:3000](http://localhost:3000)
    ```bash
    npm run start
    ```

## Auto Generating Endpoints

The UI code utilizes [gql-typescript-generator](https://github.com/TheBrainFamily/gql-typescript-generator) and [graphql-code-generator](https://github.com/dotansimha/graphql-code-generator) to auto generate GraphQL API requests.

Workflow:

1. Update the API `.graphqls` schema files
1. Run the following command in the `ui` folder while the [API](../api/README.md) is running
    ```
    npm run generate
    ```
1. Use the newly generated methods to make API calls from typescript

## Storybook

Storybook is a component library thats gives inforation on how to use the reusable react components like button,checkbox, table and so on. Run storybook locally to see what components are already available.

```sh
npm run storybook.
```

The no lint option is to ensure there aren't any lint errors as of now. This could be an effort going forward.
