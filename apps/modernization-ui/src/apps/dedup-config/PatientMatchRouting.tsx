import PatientMatchConfiguration from './PatientMatchConfiguration/PatientMatchConfiguration';
import DedupeContextProvider from './context/DedupeContext';

const routing = [
    {
        path: '/patient-match-config',
        element: (
            <DedupeContextProvider>
                <PatientMatchConfiguration />
            </DedupeContextProvider>
        )
    }
];

export { routing };