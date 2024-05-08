import { Button } from 'components/button';
import React, { useState, useEffect } from 'react';

interface IdleTimerProps {
    timeout: number; // Timeout in milliseconds
    warningTimeout: number; // Warning Timeout in milliseconds
    onIdle: () => void; // Callback function to execute when idle
}

const IdleTimer: React.FC<IdleTimerProps> = ({ timeout, warningTimeout, onIdle }) => {
    const [idle, setIdle] = useState(false);
    const [showWarningModal, setShowWarningModal] = useState(false);

    useEffect(() => {
        let idleTimer: number;
        let warningTimer: number;

        const resetIdleTimer = () => {
            clearTimeout(idleTimer);
            idleTimer = window.setTimeout(() => {
                setIdle(true);
                removeEventListeners();
                startWarningTimer();
            }, timeout);
        };

        const startWarningTimer = () => {
            setShowWarningModal(true);
            clearTimeout(warningTimer);
            warningTimer = window.setTimeout(() => {
                onIdle();
                setShowWarningModal(false);
            }, warningTimeout);
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
            resetIdleTimer();
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

    return (
        <>
            {showWarningModal && (
                <div className="warning-modal">
                    <p>Warning: Only 2 minutes remaining of idle time!</p>
                    <Button
                        onClick={() => {
                            setShowWarningModal(false);
                            setIdle(false);
                        }}>
                        I'm still here
                    </Button>
                </div>
            )}
        </>
    );
};

export default IdleTimer;
