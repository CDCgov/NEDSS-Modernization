import { act, fireEvent, render, waitFor } from '@testing-library/react';
import IdleTimer, { IdleTimerProps } from './IdleTimer';

global.fetch = jest.fn();

const timeout = 5000;

const Fixture = ({ onIdle = () => {}, timeout = 5000, warningTimeout = 2000 }: Partial<IdleTimerProps>) => {
    return <IdleTimer timeout={timeout} keepAlivePath="/foo" warningTimeout={warningTimeout} onIdle={onIdle} />;
};

describe('IdleTimer Component', () => {
    let onIdle: jest.Mock;

    beforeEach(() => {
        onIdle = jest.fn();

        jest.useFakeTimers();
        jest.clearAllTimers();
    });

    it('should render without crashing', () => {
        render(<Fixture onIdle={onIdle} />);
    });

    it('should start idle timer on mount and display warning modal', () => {
        const { queryByRole } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            jest.advanceTimersByTime(timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
    });

    it('should display warning modal with countdown', () => {
        const { queryByRole, queryByText } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            jest.advanceTimersByTime(timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
        expect(queryByText('Your session will timeout', { exact: false })).toBeInTheDocument();
        // advance timer enough for first countdown interval
        act(() => {
            jest.advanceTimersByTime(100);
        });
        expect(queryByText('0:01')).toBeInTheDocument();
    });

    it('should reset idle timer on activity', () => {
        const { queryByRole } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            jest.advanceTimersByTime(timeout - 1000);
        });
        fireEvent.mouseMove(document.body);
        act(() => {
            jest.advanceTimersByTime(1100);
        });
        expect(queryByRole('dialog')).not.toBeInTheDocument();
    });

    it('should call onIdle after warning timeout', async () => {
        (global.fetch as jest.Mock).mockResolvedValue({});

        render(<Fixture onIdle={onIdle} timeout={17} warningTimeout={29} />);

        // advance time by the timeout plus warning, then a little extra to ensure the task
        // completes.  Time advancement should be wrapped in an act due to state changes that
        // occur within the component after the timeout and warningTimeout durations
        act(() => jest.advanceTimersByTime(17 + 29 + 100));

        expect(global.fetch).toHaveBeenCalledWith('/nbs/logout', { credentials: 'include' });

        // onIdle isn't called right away, wait for it to be called
        await waitFor(() => expect(onIdle).toHaveBeenCalled());
    });

    it('should reset timers on continue', () => {
        const { queryByRole, getByText } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            jest.advanceTimersByTime(timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
        fireEvent.click(getByText('Continue'));
        expect(queryByRole('dialog')).not.toBeInTheDocument();
        act(() => {
            jest.advanceTimersByTime(timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
    });

    it('should call onIdle on logout', () => {
        const { queryByRole, getByText } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            jest.advanceTimersByTime(timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
        fireEvent.click(getByText('Logout'));
        expect(onIdle).toHaveBeenCalledTimes(1);
    });
});
