import PatientMatchConfiguration from './PatientMatchConfiguration';
<<<<<<< HEAD
import PatientMatchContextProvider from './context/PatientMatchContext';
=======
import DedupeContextProvider from './context/DedupeContext';
>>>>>>> case-dedup-ui

const routing = [
    {
        path: '/patient-match-config',
        element: (
<<<<<<< HEAD
            <PatientMatchContextProvider>
                <PatientMatchConfiguration />
            </PatientMatchContextProvider>
=======
            <DedupeContextProvider>
                <PatientMatchConfiguration />
            </DedupeContextProvider>
>>>>>>> case-dedup-ui
        )
    }
];

export { routing };
