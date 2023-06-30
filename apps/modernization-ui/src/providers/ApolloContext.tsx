import { ApolloClient, ApolloProvider, createHttpLink, from, InMemoryCache } from '@apollo/client';
import { setContext } from '@apollo/client/link/context';
import { useContext } from 'react';
import { Config } from '../config';
import { UserContext } from './UserContext';

export default function ApolloWrapper(props: any) {
    const { state } = useContext(UserContext);

    const httpLink = createHttpLink({
        uri: `${Config.modernizationUrl}/modernization-api/graphql`
    });
    const authMiddleware = setContext((_, { headers }) => {
        let header = {};
        header = {
            ...headers,
            authorization: `Bearer ${state.getToken()}`
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
