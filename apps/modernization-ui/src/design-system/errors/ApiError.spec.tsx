import { render } from '@testing-library/react';
import { ApiError } from 'generated';
import { ApiErrorBanner } from './ApiError';

describe('ApiError', () => {
    const consoleError = console.error; // save original console.error function
    beforeEach(() => {
        console.error = vi.fn().mockImplementation(() => {}); // check it's called, but don't log
    });
    afterAll(() => {
        console.error = consoleError; // restore original console.error after all tests
    });

    it('handles ApiError with string body', () => {
        const error = new ApiError(
            { method: 'GET', url: '/test' },
            { url: '/test', status: 500, statusText: 'SERVER ERROR', ok: false, body: '' },
            'Uh oh'
        );

        const { getByText, getByRole } = render(<ApiErrorBanner action="doing" item="thing" error={error} />);

        expect(console.error).toHaveBeenCalledTimes(1);
        expect(
            getByRole('heading', {
                name: 'There was an error doing this thing. If this error persists, contact your system administrator.',
            })
        );
        expect(getByText(/ApiError Details:/)).toBeVisible();
        expect(getByText(/500 SERVER ERROR/)).toBeVisible();
    });

    it('handles ApiError with json body with message', () => {
        const error = new ApiError(
            { method: 'GET', url: '/test' },
            { url: '/test', status: 500, statusText: 'SERVER ERROR', ok: false, body: { message: 'I am details' } },
            'Uh oh'
        );

        const { getByText } = render(<ApiErrorBanner action="doing" item="thing" error={error} />);

        expect(console.error).toHaveBeenCalledTimes(1);
        expect(getByText(/500 SERVER ERROR/)).toBeVisible();
        expect(getByText('I am details')).toBeVisible();
    });

    it('handles ApiError with json body with no message', () => {
        const error = new ApiError(
            { method: 'GET', url: '/test' },
            { url: '/test', status: 500, statusText: 'SERVER ERROR', ok: false, body: { notAMessage: 'I am details' } },
            'Uh oh'
        );

        const { getByText } = render(<ApiErrorBanner action="doing" item="thing" error={error} />);

        expect(console.error).toHaveBeenCalledTimes(1);
        expect(getByText(/500 SERVER ERROR/)).toBeVisible();
    });

    it('handles other error', () => {
        const error = new RangeError('Something broke');

        const { getByText } = render(<ApiErrorBanner action="doing" item="thing" error={error} />);

        expect(console.error).toHaveBeenCalledTimes(1);
        expect(getByText(/RangeError Details:/)).toBeVisible();
        expect(getByText('Something broke')).toBeVisible();
    });

    it('handles string', () => {
        const error = 'Something broke';

        const { getByText } = render(<ApiErrorBanner action="doing" item="thing" error={error} />);

        expect(console.error).toHaveBeenCalledTimes(1);
        expect(getByText(/Details:/)).toBeVisible();
        expect(getByText('Something broke')).toBeVisible();
    });

    it('handles arbitrary object', () => {
        const error = { key: 'uh oh' };

        const { getByText } = render(<ApiErrorBanner action="doing" item="thing" error={error} />);

        expect(console.error).toHaveBeenCalledTimes(1);
        expect(getByText(/Details:/)).toBeVisible();
        expect(getByText(/"key": "uh oh"/)).toBeVisible();
    });
});
