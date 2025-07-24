import { createContext, ReactNode, useContext, useEffect, useMemo, useState } from 'react';
import { Alert, AlertOptions } from './alert';
import { AlertToast, AlertToastStatus } from './AlertToast';

type Message = Alert['message'];

type AlertInteraction = {
    clear: () => void;
    showAlert: (alert: Alert) => void;
    showSuccess: (message: Message, options?: AlertOptions) => void;
    showError: (message: Message, options?: AlertOptions) => void;
    showInfo: (message: Message, options?: AlertOptions) => void;
    showWarning: (message: Message, options?: AlertOptions) => void;
};

const AlertContext = createContext<AlertInteraction | undefined>(undefined);

type ActiveAlert = {
    status: AlertToastStatus;
    alert: Alert;
};

type Props = {
    duration?: number;
    children: ReactNode | ReactNode[];
};
const AlertProvider = ({ duration = 5000, children }: Props) => {
    const [active, setActive] = useState<ActiveAlert>();

    useEffect(() => {
        if (active?.status === 'showing') {
            const timeout = setTimeout(() => {
                setActive((current) => current && { ...current, status: 'leaving' });
            }, active.alert.options?.duration ?? duration);

            return () => clearTimeout(timeout);
        } else if (active?.status === 'leaving') {
            const removal = setTimeout(() => {
                setActive(undefined);
            }, 1000);

            return () => clearTimeout(removal);
        }
    }, [active]);

    const value: AlertInteraction = useMemo(() => {
        const showAlert = (alert: Alert) => setActive({ status: 'showing', alert });

        return {
            clear: () => setActive(undefined),
            showAlert,
            showSuccess: (message: Message, options?: AlertOptions) =>
                showAlert({ type: 'success', title: 'Success', message, options }),
            showError: (message: Message, options?: AlertOptions) =>
                showAlert({ type: 'error', title: 'Error', message, options }),
            showInfo: (message: Message, options?: AlertOptions) =>
                showAlert({ type: 'information', title: 'Info', message, options }),
            showWarning: (message: Message, options?: AlertOptions) =>
                showAlert({ type: 'warning', title: 'Warning', message, options })
        };
    }, [setActive]);

    return (
        <AlertContext.Provider value={value}>
            {active && <AlertToast status={active.status}>{active.alert}</AlertToast>}
            {children}
        </AlertContext.Provider>
    );
};

const useAlert = () => {
    const context = useContext(AlertContext);
    if (!context) {
        throw new Error('useAlert must be used within an AlertProvider');
    }
    return context;
};

export { AlertProvider, useAlert };
