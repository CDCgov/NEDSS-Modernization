import { render } from '@testing-library/react';
import { MockedProvider } from '@apollo/client/testing';
import { AnalyticsProvider } from './AnalyticsContext';

describe('AnalyticsWrapper', () => {
    it('renders children with ApolloProvider', async () => {
        const { getByText } = render(
            <MockedProvider>
                <AnalyticsProvider>
                    <div>Hello World</div>
                </AnalyticsProvider>
            </MockedProvider>
        );

        // Expect the "Hello World" text to be present in the rendered output
        expect(getByText('Hello World')).toBeInTheDocument();
    });
});
