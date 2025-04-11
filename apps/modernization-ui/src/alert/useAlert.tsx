import { AlertMessage } from 'design-system/message';
import { createContext, ReactNode, useContext, useEffect, useMemo, useState } from 'react';
import styles from './alert.module.scss';

type Alert = {
    type: 'information' | 'success' | 'warning' | 'error';
    title?: string;
    message: ReactNode | ReactNode[];
};
type AlertInteraction = {
    clear: () => void;
    showAlert: (alert: Alert) => void;
    showSuccess: (message: ReactNode | ReactNode[]) => void;
    showError: (message: ReactNode | ReactNode[]) => void;
    showInfo: (message: ReactNode | ReactNode[]) => void;
    showWarning: (message: ReactNode | ReactNode[]) => void;
};

const AlertContext = createContext<AlertInteraction | undefined>(undefined);

type Props = {
    duration?: number;
    children: ReactNode | ReactNode[];
};
const AlertProvider = ({ duration = 5000, children }: Props) => {
    const [alert, setAlert] = useState<Alert | null>(null);

    useEffect(() => {
        if (alert) {
            const timeout = setTimeout(() => {
                setAlert(null);
            }, duration);

            return () => clearTimeout(timeout);
        }
    }, [alert]);

    const value: AlertInteraction = useMemo(() => {
        return {
            clear: () => setAlert(null),
            showAlert: (alert: Alert) => setAlert(alert),
            showSuccess: (message: ReactNode | ReactNode[]) => setAlert({ type: 'success', title: 'Success', message }),
            showError: (message: ReactNode | ReactNode[]) => setAlert({ type: 'error', title: 'Error', message }),
            showInfo: (message: ReactNode | ReactNode[]) => setAlert({ type: 'information', title: 'Info', message }),
            showWarning: (message: ReactNode | ReactNode[]) => setAlert({ type: 'warning', title: 'Warning', message })
        };
    }, []);

    return (
        <AlertContext.Provider value={value}>
            {alert && (
                <AlertMessage type={alert.type} title={alert.title} className={styles.alertToast}>
                    {alert.message}
                </AlertMessage>
            )}
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
