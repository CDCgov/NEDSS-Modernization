import { Suspense } from 'react';

import { LoadingOverlay } from 'libs/loading';
import {
    IdentificationDemographic,
    IdentificationDemographicCard,
    IdentificationDemographicCardProps,
} from 'libs/patient/demographics/identification';
import { MemoizedSupplier } from 'libs/supplying';
import { Await } from 'react-router';

type PatientFileIdentificationProps = {
    provider: MemoizedSupplier<Promise<IdentificationDemographic[]>>;
} & Omit<IdentificationDemographicCardProps, 'title'>;

const PatientFileIdentificationCard = ({ provider, ...remaining }: PatientFileIdentificationProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <IdentificationDemographicCard {...remaining} />
            </LoadingOverlay>
        }
    >
        <Await resolve={provider.get()}>
            {(resolved) => <IdentificationDemographicCard data={resolved} {...remaining} />}
        </Await>
    </Suspense>
);

export { PatientFileIdentificationCard };
