import { render } from '@testing-library/react';
import { AnalyticsProvider } from './AnalyticsContext';

describe('AnalyticsWrapper', () => {
    it('renders children with ApolloProvider', async () => {
        const { getByText } = render(
            <AnalyticsProvider>
                <div>Hello World</div>
            </AnalyticsProvider>
        );

        // Expect the "Hello World" text to be present in the rendered output
        expect(getByText('Hello World')).toBeInTheDocument();
    });
});
