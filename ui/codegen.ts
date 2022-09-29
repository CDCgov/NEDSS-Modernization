import { CodegenConfig } from '@graphql-codegen/cli';

const config: CodegenConfig = {
    schema: 'http://localhost:8080/graphql',
    documents: './src/generated/**/*.{gql,graphql,graphqls}',
    generates: {
        './src/generated/graphql/schema.ts': {
            plugins: ['typescript', 'typescript-operations', 'typescript-react-apollo']
        }
    }
};
export default config;
