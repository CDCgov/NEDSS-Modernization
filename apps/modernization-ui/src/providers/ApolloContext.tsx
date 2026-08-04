import { ReactNode } from 'react';

import { ApolloClient, ApolloProvider, createHttpLink, InMemoryCache } from '@apollo/client';

type ApolloWrapperProps = {
    children: ReactNode;
};

const ApolloWrapper = ({ children }: ApolloWrapperProps) => {
    const link = createHttpLink();
    const cache = new InMemoryCache();
    const client = new ApolloClient({
        link,
        cache,
    });
    return <ApolloProvider client={client}>{children}</ApolloProvider>;
};

export { ApolloWrapper };
