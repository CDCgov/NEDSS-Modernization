import { Alert as USWDSAlert, Button, Icon } from '@trussworks/react-uswds';
import { createContext, useState, useContext, ReactNode, useEffect } from 'react';
import './alert.scss';

type AlertType = 'success' | 'warning' | 'error' | 'info';

type Message = { header?: string; message?: string | ReactNode };

type Alert = { type: AlertType } & Message;

type AlertInteraction = {
    clear: () => void;
    showAlert: (alert: Alert) => void;
    showSuccess: (message: Message) => void;
    showError: (message: Message) => void;
    showInfo: (message: Message) => void;
    showWarning: (message: Message) => void;
};

const AlertContext = createContext<AlertInteraction | undefined>(undefined);

type AlertProviderProps = {
    duration?: number;
    children: ReactNode;
};

function AlertProvider({ duration = 5000, children }: AlertProviderProps) {
    const [alert, setAlert] = useState<Alert | null>(null);
    const clear = () => setAlert(null);

    useEffect(() => {
        if (alert) {
            const timeout = setTimeout(() => {
                clear();
            }, duration);

            return () => clearTimeout(timeout);
        }
    }, [alert]);

    const value: AlertInteraction = {
        clear,
        showAlert: (alert: Alert) => setAlert(alert),
        showSuccess: (message: Message) => setAlert({ type: 'success', header: 'Success', ...message }),
        showError: (message: Message) => setAlert({ type: 'error', header: 'Error', ...message }),
        showInfo: (message: Message) => setAlert({ type: 'info', header: 'Info', ...message }),
        showWarning: (message: Message) => setAlert({ type: 'warning', header: 'Warning', ...message })
    };

    return (
        <AlertContext.Provider value={value}>
            {alert && (
                <USWDSAlert
                    type={alert.type}
                    heading={alert?.header || alert.type}
                    headingLevel="h4"
                    cta={
                        <Button type="button" unstyled onClick={clear}>
                            <Icon.Close />
                        </Button>
                    }>
                    {alert.message ?? ''}
                </USWDSAlert>
            )}
            {children}
        </AlertContext.Provider>
    );
}

function useAlert() {
    const context = useContext(AlertContext);
    if (!context) {
        throw new Error('useAlert must be used within an AlertProvider');
    }
    return context;
}

export { AlertProvider, useAlert };
