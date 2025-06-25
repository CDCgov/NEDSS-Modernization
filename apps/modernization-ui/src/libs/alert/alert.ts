import { ReactNode } from 'react';

type AlertOptions = {
    duration?: number;
};

type Alert = {
    type: 'information' | 'success' | 'warning' | 'error';
    title?: string;
    message: ReactNode | ReactNode[];
    options?: AlertOptions;
};

export type { Alert, AlertOptions };
