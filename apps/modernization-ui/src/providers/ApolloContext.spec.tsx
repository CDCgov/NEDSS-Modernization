import React from 'react';
import { render } from '@testing-library/react';
import { MockedProvider } from "@apollo/client/testing/react";

import { ApolloWrapper } from './ApolloContext';

describe('ApolloWrapper', () => {
    it('renders children with ApolloProvider', async () => {
        const { getByText } = render(
            <MockedProvider>
                <ApolloWrapper>
                    <div>Hello World</div>
                </ApolloWrapper>
            </MockedProvider>
        );

        // Expect the "Hello World" text to be present in the rendered output
        expect(getByText('Hello World')).toBeInTheDocument();
    });
});
