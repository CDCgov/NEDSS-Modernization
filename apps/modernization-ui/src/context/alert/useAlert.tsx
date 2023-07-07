import { Alert, Button, Icon } from '@trussworks/react-uswds';
import { createContext, useState, useContext, ReactNode, useEffect } from 'react';
import './alert.scss';

type AlertType = 'success' | 'warning' | 'error' | 'info';

export type AlertProp = { type: AlertType; header?: string; message?: string };

type AlertContextType = {
    showAlert: ({ type, header, message }: AlertProp) => void;
};

const AlertContext = createContext<AlertContextType | undefined>(undefined);

type AlertProviderProps = {
    children: ReactNode;
};

export function AlertProvider({ children }: AlertProviderProps) {
    const [alert, setAlert] = useState<AlertProp | null>(null);
    const showAlert = ({ type, header, message }: AlertProp) => {
        setAlert({ type, header, message });
    };

    useEffect(() => {
        if (alert) {
            const timeout = setTimeout(() => {
                setAlert(null);
            }, 5000);

            return () => clearTimeout(timeout);
        }
    }, [alert]);

    const contextValue: AlertContextType = {
        showAlert
    };

    return (
        <AlertContext.Provider value={contextValue}>
            {alert && (
                <Alert
                    type={alert.type}
                    heading={alert?.header || alert.type}
                    headingLevel="h4"
                    cta={
                        <Button type="button" unstyled onClick={() => setAlert(null)}>
                            <Icon.Close />
                        </Button>
                    }>
                    {alert.message || ''}
                </Alert>
            )}
            {children}
        </AlertContext.Provider>
    );
}

export function useAlert() {
    const context = useContext(AlertContext);
    if (!context) {
        throw new Error('useAlert must be used within an AlertProvider');
    }
    return context;
}
