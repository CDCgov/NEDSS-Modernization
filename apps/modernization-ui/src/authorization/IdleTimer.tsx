import React, { useState, useEffect, useCallback, useRef } from 'react';
import debounce from 'lodash.debounce';
import { Confirmation } from 'design-system/modal';
import { toClockString } from 'utils/util';

interface IdleTimerProps {
    timeout: number; // Timeout in milliseconds
    warningTimeout: number; // Warning Timeout in milliseconds
    onIdle: () => void; // Callback function to execute when idle
}

const IdleTimer: React.FC<IdleTimerProps> = ({ timeout, warningTimeout, onIdle }) => {
    const [idle, setIdle] = useState(false);
    // const [idleTimer, setIdleTimer] = useState<number | undefined>();
    // const [warningTimer, setWarningTimer] = useState<number | undefined>();
    const idleTimerRef = useRef<number | undefined>();
    const warningTimerRef = useRef<number | undefined>();
    const [warningStartTicks, setWarningStartTicks] = useState<number | undefined>();
    const [timerString, setTimerString] = useState<string | undefined>();
    // const idleTimer = idleTimerRef.current;
    // const warningTimer = warningTimerRef.current;
    console.log('IdleTimer', idleTimerRef.current, warningTimerRef.current);

    // this starts the warning timer and shows the warning modal
    const startWarningTimer = useCallback(() => {
        console.log('IdleTimer warning', warningTimerRef.current, warningTimerRef.current);
        setIdle(true);
        clearTimeout(warningTimerRef.current);
        warningTimerRef.current = window.setTimeout(() => {
            onIdle();
        }, warningTimeout);
        setWarningStartTicks(Date.now());
    }, [onIdle, warningTimeout]);

    // this resets the idle timer, when there is mouse activity or the warning modal is dismissed
    const resetIdleTimer = useCallback(() => {
        console.log('IdleTimer reset', warningTimerRef.current, warningTimerRef.current);
        clearTimeout(warningTimerRef.current);
        clearTimeout(warningTimerRef.current);
        setIdle(false);
        idleTimerRef.current = window.setTimeout(() => {
            startWarningTimer();
        }, timeout);
        warningTimerRef.current = undefined;
        setWarningStartTicks(undefined);
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
        console.log('IdleTimer first call');
        debouncedResetIdleTimer();
        return () => {
            clearTimeout(idleTimerRef.current);
            clearTimeout(warningTimerRef.current);
            debouncedResetIdleTimer.cancel();
            console.log('IdleTimer cleanup', idleTimerRef.current, warningTimerRef.current);
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

    useEffect(() => {
        const interval = setInterval(() => {
            setTimerString(toClockString(warningTimeout - (Date.now() - (warningStartTicks || Date.now()))));
        }, 100);

        return () => clearInterval(interval);
    }, [warningTimeout, warningStartTicks]);

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
                Your session will timeout in {timerString} minutes due to inactivity. Would you like to continue your
                session in NBS?
            </Confirmation>
        )
    );
};

export default IdleTimer;
