import { CodegenConfig } from '@graphql-codegen/cli';

const config: CodegenConfig = {
    schema: './src/generated/schema.graphqls',
    documents: './src/generated/**/*.{gql,graphql,graphqls}',
    generates: {
        './src/generated/graphql/schema.ts': {
            plugins: ['typescript', 'typescript-operations', 'typescript-react-apollo']
        }
    }
};
export default config;
