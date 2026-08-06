import { Suspense } from 'react';

import { Await } from 'react-router';

import { LoadingOverlay } from 'libs/loading';
import {
    EthnicityDemographic,
    EthnicityDemographicCard,
    EthnicityDemographicCardProps,
} from 'libs/patient/demographics/ethnicity';
import { MemoizedSupplier } from 'libs/supplying';

type PatientFileEthnicityCardType = {
    provider: MemoizedSupplier<Promise<EthnicityDemographic>>;
} & Omit<EthnicityDemographicCardProps, 'title'>;

const PatientFileEthnicityCard = ({ provider, ...remaining }: PatientFileEthnicityCardType) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <EthnicityDemographicCard {...remaining} />
            </LoadingOverlay>
        }
    >
        <Await resolve={provider.get()}>
            {(demographic) => <EthnicityDemographicCard demographic={demographic} {...remaining} />}
        </Await>
    </Suspense>
);

export { PatientFileEthnicityCard };
