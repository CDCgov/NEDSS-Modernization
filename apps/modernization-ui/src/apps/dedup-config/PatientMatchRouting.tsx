import PatientMatchConfiguration from './PatientMatchConfiguration/PatientMatchConfiguration';
import DedupeContextProvider from './context/DedupeContext';
import { AlertProvider } from 'alert/useAlert';

const routing = [
    {
        path: '/patient-match-config',
        element: (
            <AlertProvider>
                <DedupeContextProvider>
                    <PatientMatchConfiguration />
                </DedupeContextProvider>
            </AlertProvider>
        )
    }
];

export { routing };
