import { Outlet } from 'react-router';
import { ComponentSizingProvider } from 'design-system/sizing';
import { SearchFromAddPatientProvider } from './useSearchFromAddPatient';
import { PatientDataEntryMethodProvider } from './usePatientDataEntryMethod';

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
