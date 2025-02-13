import React, { useState, useEffect, useCallback, useRef } from 'react';
import debounce from 'lodash.debounce';
import { Confirmation } from 'design-system/modal';
import { useTimeout } from './useTimeout';
import { useCountdown } from './useCountdown';

interface IdleTimerProps {
    /** Timeout in milliseconds: amount of time before modal shows */
    timeout: number;
    /** Warning Timeout in milliseconds: amount of time modal shows before onIdle event is fired */
    warningTimeout: number;
    /** Callback function to execute when idle */
    onIdle: () => void;
}

const IdleTimer: React.FC<IdleTimerProps> = ({ timeout, warningTimeout, onIdle }) => {
    const [idle, setIdle] = useState(false);
    const idleTimer = useTimeout();
    const warningTimer = useTimeout();
    const countdown = useCountdown();

    // this starts the warning timer and shows the warning modal
    const startWarningTimer = useCallback(() => {
        setIdle(true);
        warningTimer.start(
            () => {
                onIdle();
            },
            warningTimeout,
            true
        );
        countdown.start(warningTimeout);
    }, [onIdle, warningTimeout]);

    // this resets the idle timer, when there is mouse activity or the warning modal is dismissed
    const resetIdleTimer = useCallback(() => {
        warningTimer.clear();
        setIdle(false);
        idleTimer.start(
            () => {
                startWarningTimer();
            },
            timeout,
            true
        );
        countdown.clear();
    }, [timeout, startWarningTimer]);
    const debouncedResetIdleTimer = useCallback(debounce(resetIdleTimer, 100), [resetIdleTimer]);

    const handleActivity = useCallback(() => {
        if (!idle) {
            debouncedResetIdleTimer();
        }
    }, [idle, debouncedResetIdleTimer]);
    const handleActivityRef = useRef(handleActivity);

    useEffect(() => {
        // set up ref to the handleActivity function
        // we do this so we can use the same function in the event listeners
        handleActivityRef.current = handleActivity;
    }, [handleActivity]);

    useEffect(() => {
        // first call to start the idle timer
        debouncedResetIdleTimer();
        return () => {
            idleTimer.clear();
            warningTimer.clear();
            debouncedResetIdleTimer.cancel();
        };
    }, []);

    useEffect(() => {
        const handleEvent = () => {
            handleActivityRef.current();
        };

        const addEventListeners = () => {
            const events = ['mousemove', 'keydown', 'mousedown', 'touchstart'];
            events.forEach((event) => {
                document.addEventListener(event, handleEvent);
            });
        };

        const removeEventListeners = () => {
            const events = ['mousemove', 'keydown', 'mousedown', 'touchstart'];
            events.forEach((event) => {
                document.removeEventListener(event, handleEvent);
            });
        };

        addEventListeners();

        return () => {
            removeEventListeners();
        };
    }, [timeout, onIdle]);

    const handleContinue = () => {
        setIdle(false);
        debouncedResetIdleTimer();
    };

    const handleLogout = () => {
        onIdle();
    };

    return (
        idle && (
            <Confirmation
                title="Timeout"
                confirmText="Continue"
                cancelText="Logout"
                forceAction={true}
                onConfirm={handleContinue}
                onCancel={handleLogout}>
                Your session will timeout in <time>{countdown.current}</time> minutes due to inactivity. Would you like
                to continue your session in NBS?
            </Confirmation>
        )
    );
};

export default IdleTimer;
