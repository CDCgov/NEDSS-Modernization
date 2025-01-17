import React, { useState, useEffect, useCallback, useRef } from 'react';
import debounce from 'lodash.debounce';
import { Confirmation } from 'design-system/modal';

interface IdleTimerProps {
    timeout: number; // Timeout in milliseconds
    warningTimeout: number; // Warning Timeout in milliseconds
    onIdle: () => void; // Callback function to execute when idle
}

const IdleTimer: React.FC<IdleTimerProps> = ({ timeout, warningTimeout, onIdle }) => {
    const [idle, setIdle] = useState(false);
    // const [showWarningModal, setShowWarningModal] = useState(false);
    const [idleTimer, setIdleTimer] = useState<number | undefined>();
    const [warningTimer, setWarningTimer] = useState<number | undefined>();
    const warningMins = Math.ceil(warningTimeout / 60000);

    // this starts the warning timer and shows the warning modal
    const startWarningTimer = useCallback(() => {
        console.log('idle', 'start warning');
        setIdle(true);
        clearTimeout(warningTimer);
        setWarningTimer(
            window.setTimeout(() => {
                console.log('idle', 'warning timeout');
                // setShowWarningModal(false);
                // setIdle(false);
                onIdle();
            }, warningTimeout)
        );
    }, [onIdle, warningTimeout, warningTimer]);

    // this resets the idle timer, when there is mouse activity or the warning modal is dismissed
    const resetIdleTimer = useCallback(() => {
        console.log('idle', 'reset idle', idleTimer, warningTimer);
        clearTimeout(idleTimer);
        clearTimeout(warningTimer);
        setIdle(false);
        setIdleTimer(
            window.setTimeout(() => {
                // setIdle(true);
                // removeEventListeners();
                startWarningTimer();
            }, timeout)
        );
        setWarningTimer(undefined);
    }, [idleTimer, warningTimer, startWarningTimer]);

    const debouncedResetIdleTimer = useCallback(debounce(resetIdleTimer, 100), [resetIdleTimer]);

    const handleActivity = useCallback(() => {
        console.log('idle', 'handleActivity');
        if (!idle) {
            debouncedResetIdleTimer();
        }
    }, [idle, debouncedResetIdleTimer]);
    const handleActivityRef = useRef(handleActivity);

    useEffect(() => {
        handleActivityRef.current = handleActivity;
    }, [handleActivity]);

    useEffect(() => {
        console.log('idle', 'start timer');
        debouncedResetIdleTimer();
        return () => {
            clearTimeout(idleTimer);
            clearTimeout(warningTimer);
            debouncedResetIdleTimer.cancel();
        };
    }, []);
    // const clearIdleTimer = () => {
    //     clearTimeout(idleTimer);
    //     setIdleTimer(undefined);
    // };

    // const clearWarningTimer = () => {
    //     clearTimeout(warningTimer);
    //     setWarningTimer(undefined);
    // };

    // const [, handleActivity] = useReducer((state) => {
    //     console.log('idle', 'handleActivity');
    //     if (!idle) {
    //         debouncedResetIdleTimer();
    //     }
    //     return state;
    // }, null);

    useEffect(() => {
        console.log('idle', 'init events');

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
            // clearTimeout(idleTimer);
            // clearTimeout(warningTimer);
            // debouncedResetIdleTimer.cancel();
            removeEventListeners();
            // debouncedResetIdleTimer.cancel();
        };
    }, [timeout, onIdle]);

    // useEffect(() => {
    //     console.log('idle', 'idle changed', idle);

    //     if (!idle && !showWarningModal) {
    //         debouncedResetIdleTimer();
    //     }
    // }, [idle, showWarningModal, debouncedResetIdleTimer]);

    // const handleActivity = useCallback(() => {
    //     console.log('idle', 'handleActivity');
    //     if (!showWarningModal) {
    //         debouncedResetIdleTimer();
    //     }
    // }, [showWarningModal, debouncedResetIdleTimer]);

    const handleContinue = () => {
        console.log('idle', 'continue');
        // setShowWarningModal(false);
        setIdle(false);
        debouncedResetIdleTimer();
    };

    const handleLogout = () => {
        console.log('idle', 'logout');
        // setIdle(false);
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
