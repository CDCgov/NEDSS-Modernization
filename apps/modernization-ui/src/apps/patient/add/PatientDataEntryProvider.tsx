import { Outlet } from 'react-router';

import { ComponentSizingProvider } from 'design-system/sizing';

import { PatientDataEntryMethodProvider } from './usePatientDataEntryMethod';
import { SearchFromAddPatientProvider } from './useSearchFromAddPatient';

const PatientDataEntryProvider = () => (
    <ComponentSizingProvider>
        <SearchFromAddPatientProvider>
            <PatientDataEntryMethodProvider>
                <Outlet />
            </PatientDataEntryMethodProvider>
        </SearchFromAddPatientProvider>
    </ComponentSizingProvider>
);

export { PatientDataEntryProvider };
