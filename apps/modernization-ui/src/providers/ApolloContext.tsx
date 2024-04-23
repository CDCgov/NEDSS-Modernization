import { ApolloClient, ApolloProvider, createHttpLink, InMemoryCache } from '@apollo/client';

import { ReactNode } from 'react';

const link = createHttpLink();

const cache = new InMemoryCache();

const client = new ApolloClient({
    link,
    cache
});

type ApolloWrapperProps = {
    children: ReactNode;
};

const ApolloWrapper = ({ children }: ApolloWrapperProps) => {
    return <ApolloProvider client={client}>{children}</ApolloProvider>;
};

export { ApolloWrapper };
