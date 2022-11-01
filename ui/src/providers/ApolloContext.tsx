import { ApolloClient, ApolloProvider, createHttpLink, InMemoryCache } from '@apollo/client';
import { setContext } from '@apollo/client/link/context';
import { useContext } from 'react';
import { Config } from '../config';
import { UserContext } from './UserContext';

export default function ApolloContext(props: any) {
    const { state } = useContext(UserContext);

    const httpLink = createHttpLink({
        uri: `http://localhost:${Config.port}/graphql`
    });
    const authMiddleware = setContext((_, { headers }) => {
        let header = {};
        header = {
            ...headers,
            authorization: `Bearer ${state.token}`
        };
        return {
            headers: header
        };
    });
    const client = new ApolloClient({
        link: authMiddleware.concat(httpLink),
        cache: new InMemoryCache()
    });
    return (
        <div>
            <ApolloProvider client={client}>{props.children}</ApolloProvider>
        </div>
    );
}
