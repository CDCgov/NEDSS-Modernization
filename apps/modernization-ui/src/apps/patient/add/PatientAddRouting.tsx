import { FeatureGuard } from 'feature';
import { AddPatient } from './AddPatient';
import { AddPatientExtended } from './extended/AddPatientExtended';
import { Outlet } from 'react-router';
import { BasicExtendedTransitionProvider } from 'apps/patient/add/useBasicExtendedTransition';
import { AddPatientBasic } from './basic/AddPatientBasic';
import { SearchFromAddPatientProvider } from 'apps/search/patient/add/useSearchFromAddPatient';
import { ComponentSizingProvider } from 'design-system/sizing';

const PatientDataProviderWrapper = () => (
    <ComponentSizingProvider>
        <SearchFromAddPatientProvider>
            <BasicExtendedTransitionProvider>
                <Outlet />
            </BasicExtendedTransitionProvider>
        </SearchFromAddPatientProvider>
    </ComponentSizingProvider>
);

const routing = [
    {
        element: <PatientDataProviderWrapper />,
        children: [
            {
                path: '/add-patient',
                element: <AddPatient />
            },
            {
                path: '/patient/add',
                element: (
                    <FeatureGuard guard={(features) => features?.patient?.add?.enabled}>
                        <AddPatientBasic />
                    </FeatureGuard>
                )
            },
            {
                path: '/patient/add/extended',
                element: (
                    <FeatureGuard guard={(features) => features?.patient?.add?.extended?.enabled}>
                        <AddPatientExtended />
                    </FeatureGuard>
                )
            }
        ]
    }
];

export { routing };
