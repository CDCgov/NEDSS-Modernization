import { useRef, useEffect } from 'react';

type UseTimeoutResult = {
    start: (callback: () => void, delay: number | null, clearIfExisting?: boolean) => void;
    clear: () => void;
    timeoutID: () => number | undefined;
};

/**
 * A hook to manage setting and clearing timeout in a clean and reusable way.
 * @return {UseTimeoutResult} An object containing functions to interact with the timeout.
 */
const useTimeout = (): UseTimeoutResult => {
    const timerRef = useRef<number | undefined>();

    useEffect(() => {
        // this is a last resort cleanup, the consumer should manually call clear() before unmounting
        return () => {
            clear();
        };
    }, []);

    const start = (callback: () => void, delay: number | null, clearIfExisting?: boolean) => {
        if (clearIfExisting && timerRef.current) {
            window.clearTimeout(timerRef.current);
        }
        timerRef.current = window.setTimeout(callback, delay || 0);
    };

    const clear = () => {
        window.clearTimeout(timerRef.current);
    };

    const timeoutID = () => timerRef.current;

    return { start, clear, timeoutID };
};

export default useTimeout;
