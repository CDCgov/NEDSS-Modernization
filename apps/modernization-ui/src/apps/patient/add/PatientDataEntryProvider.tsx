import { ComponentSizingProvider } from 'design-system/sizing';
import { Outlet } from 'react-router';

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
