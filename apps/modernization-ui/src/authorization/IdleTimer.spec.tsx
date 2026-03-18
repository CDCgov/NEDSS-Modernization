import { Mock } from 'vitest';
import { act, fireEvent, render } from '@testing-library/react';
import IdleTimer, { IdleTimerProps } from './IdleTimer';

const default_timeout = 5000;

const Fixture = ({ onIdle = () => {}, timeout = default_timeout, warningTimeout = 2000 }: Partial<IdleTimerProps>) => {
    return <IdleTimer timeout={timeout} keepAlivePath="/foo" warningTimeout={warningTimeout} onIdle={onIdle} />;
};

describe('IdleTimer Component', () => {
    let onIdle: Mock;

    beforeEach(() => {
        onIdle = vi.fn();

        vi.useFakeTimers();
        vi.clearAllTimers();
        vi.clearAllMocks();
        vi.resetAllMocks();
    });

    it('should render without crashing', () => {
        render(<Fixture onIdle={onIdle} />);
    });

    it('should start idle timer on mount and display warning modal', () => {
        const { queryByRole } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            vi.advanceTimersByTime(default_timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
    });

    it('should display warning modal with countdown', () => {
        const { queryByRole, queryByText } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            vi.advanceTimersByTime(default_timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
        expect(queryByText('Your session will timeout', { exact: false })).toBeInTheDocument();
        // advance timer enough for first countdown interval
        act(() => {
            vi.advanceTimersByTime(100);
        });
        expect(queryByText('0:01')).toBeInTheDocument();
    });

    it('should reset idle timer on activity', () => {
        const { queryByRole } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            vi.advanceTimersByTime(default_timeout - 1000);
        });
        fireEvent.mouseMove(document.body);
        act(() => {
            vi.advanceTimersByTime(1100);
        });
        expect(queryByRole('dialog')).not.toBeInTheDocument();
    });

    it('should call onIdle after warning timeout', async () => {
        render(<Fixture onIdle={onIdle} timeout={17} warningTimeout={29} />);

        // advance time by the timeout plus warning, then a little extra to ensure the task
        // completes.  Time advancement should be wrapped in an act due to state changes that
        // occur within the component after the timeout and warningTimeout durations. The
        // warning timer's callback is async, so we need to use the async act + advancer
        await act(async () => await vi.advanceTimersByTimeAsync(default_timeout + 29 + 100));

        expect(global.fetch).toHaveBeenCalledWith('/nbs/logout', { credentials: 'include' });

        expect(onIdle).toHaveBeenCalled();
    });

    it('should reset timers on continue', () => {
        const { queryByRole, getByText } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            vi.advanceTimersByTime(default_timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
        fireEvent.click(getByText('Continue'));
        expect(queryByRole('dialog')).not.toBeInTheDocument();
        act(() => {
            vi.advanceTimersByTime(default_timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
    });

    it('should call onIdle on logout', () => {
        const { queryByRole, getByText } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            vi.advanceTimersByTime(default_timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
        fireEvent.click(getByText('Logout'));
        expect(onIdle).toHaveBeenCalledTimes(1);
    });
});
