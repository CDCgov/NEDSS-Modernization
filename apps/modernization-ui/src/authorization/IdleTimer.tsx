import React, { useState, useEffect } from 'react';
import debounce from 'lodash.debounce';
import { Confirmation } from 'design-system/modal';

interface IdleTimerProps {
    timeout: number; // Timeout in milliseconds
    warningTimeout: number; // Warning Timeout in milliseconds
    onIdle: () => void; // Callback function to execute when idle
}

const IdleTimer: React.FC<IdleTimerProps> = ({ timeout, warningTimeout, onIdle }) => {
    const [idle, setIdle] = useState(false);
    const [showWarningModal, setShowWarningModal] = useState(false);
    const [idleTimer, setIdleTimer] = useState<number | undefined>();
    const [warningTimer, setWarningTimer] = useState<number | undefined>();
    const warningMins = Math.ceil(warningTimeout / 60000);

    useEffect(() => {
        //let idleTimer: number | undefined = undefined;
        //let warningTimer: number | undefined = undefined;
        console.log('idle', 'init', timeout, warningTimeout, idleTimer, warningTimer, idle);

        const resetIdleTimer = () => {
            console.log('idle', 'reset idle', idleTimer, warningTimer);
            clearTimeout(idleTimer);
            clearTimeout(warningTimer);
            setIdleTimer(
                window.setTimeout(() => {
                    setIdle(true);
                    removeEventListeners();
                    startWarningTimer();
                }, timeout)
            );
            setWarningTimer(undefined);
        };
        const debouncedResetIdleTimer = debounce(resetIdleTimer, 100);

        const startWarningTimer = () => {
            console.log('idle', 'start warning');
            setShowWarningModal(true);
            clearTimeout(warningTimer);
            setWarningTimer(
                window.setTimeout(() => {
                    console.log('idle', 'warning timeout');
                    onIdle();
                    setShowWarningModal(false);
                }, warningTimeout)
            );
        };

        const removeEventListeners = () => {
            const events = ['mousemove', 'keydown', 'mousedown', 'touchstart'];
            events.forEach((event) => {
                document.removeEventListener(event, handleActivity);
            });
        };

        const addEventListeners = () => {
            const events = ['mousemove', 'keydown', 'mousedown', 'touchstart'];
            events.forEach((event) => {
                document.addEventListener(event, handleActivity);
            });
        };

        const handleActivity = () => {
            // if (!showWarningModal) {
            //     // once warning modal is displayed, user must interact with continue or logout buttons
            //     resetIdleTimer();
            // }
            // resetIdleTimer();
            debouncedResetIdleTimer();
        };

        if (!idle) {
            addEventListeners();
            resetIdleTimer();
        }

        return () => {
            clearTimeout(idleTimer);
            clearTimeout(warningTimer);
            removeEventListeners();
        };
    }, [timeout, onIdle, idle]);

    const handleContinue = () => {
        console.log('idle', 'continue');
        setShowWarningModal(false);
        setIdle(false);
    };

    const handleLogout = () => {
        console.log('idle', 'logout');
        setShowWarningModal(false);
        onIdle();
    };

    return (
        <>
            {showWarningModal && (
                <Confirmation
                    title="Timeout"
                    confirmText="Continue"
                    cancelText="Logout"
                    forceAction={true}
                    onConfirm={handleContinue}
                    onCancel={handleLogout}>
                    Your session will timeout in {warningMins} minutes. Would you like to continue your session in NBS?
                </Confirmation>
            )}
        </>
    );
};

export default IdleTimer;
