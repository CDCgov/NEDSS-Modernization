import { useEffect, useState } from 'react';
import { toClockString } from 'utils/util';

type UseCountdownResult = {
    /** The current countdown time, in HH:MM:SS or MM:SS format */
    current: string | undefined;
    start: (millisecs: number) => void;
    clear: () => void;
};

/**
 * A hook to manage a countdown timer. Call start() to start the countdown and clear() to reset it.
 * @return {UseCountdownResult} An object containing functions to interact with the timeout.
 */
const useCountdown = (): UseCountdownResult => {
    const [totalMillisecs, setTotalMillisecs] = useState<number | undefined>();
    const [timerString, setTimerString] = useState<string | undefined>();
    const [warningStartTicks, setWarningStartTicks] = useState<number | undefined>();

    useEffect(() => {
        let interval: number | undefined;
        if (totalMillisecs) {
            interval = window.setInterval(() => {
                setTimerString(toClockString(totalMillisecs - (Date.now() - (warningStartTicks || Date.now()))));
            }, 100);
        }
        return () => window.clearInterval(interval);
    }, [totalMillisecs, warningStartTicks]);

    const start = (millisecs: number) => {
        setTotalMillisecs(millisecs);
        setWarningStartTicks(Date.now());
    };

    const clear = () => {
        setTotalMillisecs(undefined);
        setWarningStartTicks(undefined);
    };

    return { current: timerString, start, clear };
};

export { useCountdown };
