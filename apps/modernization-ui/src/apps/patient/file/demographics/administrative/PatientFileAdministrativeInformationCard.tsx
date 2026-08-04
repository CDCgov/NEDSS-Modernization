import { Suspense } from 'react';

import { LoadingOverlay } from 'libs/loading';
import {
    AdministrativeInformation,
    AdministrativeInformationCard,
    AdministrativeInformationCardProps,
} from 'libs/patient/demographics/administrative';
import { MemoizedSupplier } from 'libs/supplying';
import { Await } from 'react-router';

type PatientFileAdministrativeInformationCardProps = {
    provider: MemoizedSupplier<Promise<AdministrativeInformation>>;
} & Omit<AdministrativeInformationCardProps, 'title'>;

const PatientFileAdministrativeInformationCard = ({
    provider,
    ...remaining
}: PatientFileAdministrativeInformationCardProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <AdministrativeInformationCard {...remaining} />
            </LoadingOverlay>
        }
    >
        <Await resolve={provider.get()}>
            {(resolved) => <AdministrativeInformationCard data={resolved} {...remaining} />}
        </Await>
    </Suspense>
);

export { PatientFileAdministrativeInformationCard };
