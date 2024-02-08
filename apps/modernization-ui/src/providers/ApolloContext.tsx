import { ApolloClient, ApolloProvider, createHttpLink, from, InMemoryCache } from '@apollo/client';
import { setContext } from '@apollo/client/link/context';
import { authorization } from 'authorization';
import { Config } from '../config';

export default function ApolloWrapper(props: any) {
    const httpLink = createHttpLink({
        uri: `${Config.modernizationUrl}/graphql`
    });
    const authMiddleware = setContext((_, { headers }) => {
        let header = {};
        header = {
            ...headers,
            authorization: authorization()
        };
        return {
            headers: header
        };
    });
    const client = new ApolloClient({
        link: from([authMiddleware, httpLink]),
        cache: new InMemoryCache()
    });
    return (
        <div>
            <ApolloProvider client={client}>{props.children}</ApolloProvider>
        </div>
    );
}
