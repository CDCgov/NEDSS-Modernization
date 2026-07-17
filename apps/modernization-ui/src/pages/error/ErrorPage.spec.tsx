import { render } from '@testing-library/react';
import { ApiError } from 'generated';
import { useRouteError } from 'react-router';
import { ErrorPage } from './ErrorPage';
import { NotFoundError } from './NotFoundError';

vi.mock('react-router');

describe('ErrorPage', () => {
    it('handles ApiError with string body', () => {
        const error = new ApiError(
            { method: 'GET', url: '/test' },
            { url: '/test', status: 500, statusText: 'SERVER ERROR', ok: false, body: '' },
            'Uh oh'
        );
        vi.fn(useRouteError).mockReturnValue(error);

        const { getByText, getByRole } = render(<ErrorPage />);

        expect(getByText('500')).toBeVisible();
        expect(getByText('SERVER ERROR')).toBeVisible();
        expect(getByRole('link', { name: 'Return to home' })).toBeVisible();
    });

    it('handles ApiError with message body', () => {
        const error = new ApiError(
            { method: 'GET', url: '/test' },
            { url: '/test', status: 500, statusText: 'SERVER ERROR', ok: false, body: { message: 'Uh oh' } },
            'Uh oh'
        );
        vi.fn(useRouteError).mockReturnValue(error);

        const { getByText, getByRole } = render(<ErrorPage />);

        expect(getByText('500')).toBeVisible();
        expect(getByText('SERVER ERROR')).toBeVisible();
        expect(getByText('Uh oh')).toBeVisible();
        expect(getByRole('link', { name: 'Return to home' })).toBeVisible();
    });

    it('handles 404 ApiError', () => {
        const error = new ApiError(
            { method: 'GET', url: '/test' },
            { url: '/test', status: 404, statusText: 'Not found', ok: false, body: { message: 'Uh oh' } },
            'Uh oh'
        );
        vi.fn(useRouteError).mockReturnValue(error);

        const { getByText, getByRole } = render(<ErrorPage />);

        expect(getByText('404')).toBeVisible();
        expect(getByText('Not found')).toBeVisible();
        expect(getByRole('link', { name: 'Return to home' })).toBeVisible();
    });

    it('handles NotFoundError', () => {
        const error = new NotFoundError();
        vi.fn(useRouteError).mockReturnValue(error);

        const { getByText, getByRole } = render(<ErrorPage />);

        expect(getByText('404')).toBeVisible();
        expect(getByText('Not found')).toBeVisible();
        expect(getByRole('link', { name: 'Return to home' })).toBeVisible();
    });

    it('handles arbitrary error', () => {
        const error = new RangeError('Too big!');
        vi.fn(useRouteError).mockReturnValue(error);

        const { getByText, getByRole } = render(<ErrorPage />);

        expect(getByText('Error')).toBeVisible();
        expect(getByText('Too big!')).toBeVisible();
        expect(getByText('The stack trace is:')).toBeVisible();
        expect(getByRole('link', { name: 'Return to home' })).toBeVisible();
    });

    it('handles arbitrary string data', () => {
        const error = 'what have we here';
        vi.fn(useRouteError).mockReturnValue(error);

        const { getByText, getByRole } = render(<ErrorPage />);

        expect(getByText('Unknown error')).toBeVisible();
        expect(getByText(/what have we here/)).toBeVisible();
        expect(getByRole('link', { name: 'Return to home' })).toBeVisible();
    });

    it('handles arbitrary object data', () => {
        const error = { data: 'what have we here' };
        vi.fn(useRouteError).mockReturnValue(error);

        const { getByText, getByRole } = render(<ErrorPage />);

        expect(getByText('Unknown error')).toBeVisible();
        expect(getByText(/what have we here/)).toBeVisible();
        expect(getByRole('link', { name: 'Return to home' })).toBeVisible();
    });
});
