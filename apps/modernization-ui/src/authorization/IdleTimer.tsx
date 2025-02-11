import React, { useState, useEffect, useCallback, useRef } from 'react';
import debounce from 'lodash.debounce';
import { Confirmation } from 'design-system/modal';

interface IdleTimerProps {
    timeout: number; // Timeout in milliseconds
    warningTimeout: number; // Warning Timeout in milliseconds
    onIdle: () => void; // Callback function to execute when idle
    onContinue?: () => void; // Callback function to execute when user clicks continue
}

const IdleTimer: React.FC<IdleTimerProps> = ({ timeout, warningTimeout, onIdle, onContinue }) => {
    const [idle, setIdle] = useState(false);
    const [idleTimer, setIdleTimer] = useState<number | undefined>();
    const [warningTimer, setWarningTimer] = useState<number | undefined>();
    const warningMins = Math.ceil(warningTimeout / 60000);

    // this starts the warning timer and shows the warning modal
    const startWarningTimer = useCallback(() => {
        setIdle(true);
        clearTimeout(warningTimer);
        setWarningTimer(
            window.setTimeout(() => {
                onIdle();
            }, warningTimeout)
        );
    }, [onIdle, warningTimeout, warningTimer]);

    // this resets the idle timer, when there is mouse activity or the warning modal is dismissed
    const resetIdleTimer = useCallback(() => {
        clearTimeout(idleTimer);
        clearTimeout(warningTimer);
        setIdle(false);
        setIdleTimer(
            window.setTimeout(() => {
                startWarningTimer();
            }, timeout)
        );
        setWarningTimer(undefined);
    }, [idleTimer, warningTimer, startWarningTimer]);
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
            clearTimeout(idleTimer);
            clearTimeout(warningTimer);
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
        onContinue?.();
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
                Your session will timeout in {warningMins} minutes. Would you like to continue your session in NBS?
            </Confirmation>
        )
    );
};

export default IdleTimer;
