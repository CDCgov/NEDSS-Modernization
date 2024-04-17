import { ApolloClient, ApolloProvider, createHttpLink, InMemoryCache } from '@apollo/client';
import { setContext } from '@apollo/client/link/context';
import { authorization } from 'authorization';

import { ReactNode } from 'react';

const httpLink = createHttpLink();

const authenticated = setContext((_, { headers }) => {
    const header = {
        ...headers,
        authorization: authorization()
    };
    return {
        headers: header
    };
});

const cache = new InMemoryCache();

const client = new ApolloClient({
    link: httpLink.concat(authenticated),
    cache
});

type ApolloWrapperProps = {
    children: ReactNode;
};

export default function ApolloWrapper({ children }: ApolloWrapperProps) {
    return <ApolloProvider client={client}>{children}</ApolloProvider>;
}
