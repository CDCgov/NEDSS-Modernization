import React from 'react';
import { act, fireEvent, render } from '@testing-library/react';
import IdleTimer from './IdleTimer';

interface FixtureProps {
    onIdle: () => void;
}

const timeout = 5000;
const warningTimeout = 2000;

const Fixture: React.FC<FixtureProps> = ({ onIdle }) => {
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

    it('should call onIdle after warning timeout', () => {
        const { queryByRole } = render(<Fixture onIdle={onIdle} />);
        act(() => {
            jest.advanceTimersByTime(timeout + 100);
        });
        expect(queryByRole('dialog')).toBeInTheDocument();
        jest.advanceTimersByTime(2000);
        expect(onIdle).toHaveBeenCalledTimes(0);
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
        expect(onIdle).toHaveBeenCalledTimes(0);
    });
});
