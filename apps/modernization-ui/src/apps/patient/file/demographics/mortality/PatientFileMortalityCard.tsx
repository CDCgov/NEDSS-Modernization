import { Suspense } from 'react';

import { Await } from 'react-router';

import { LoadingOverlay } from 'libs/loading';
import {
    MortalityDemographic,
    MortalityDemographicCard,
    MortalityDemographicCardProps,
} from 'libs/patient/demographics/mortality';
import { MemoizedSupplier } from 'libs/supplying';

type PatientFileMortalityCardProps = {
    provider: MemoizedSupplier<Promise<MortalityDemographic>>;
} & Omit<MortalityDemographicCardProps, 'title'>;

const PatientFileMortalityCard = ({ provider, ...remaining }: PatientFileMortalityCardProps) => {
    return (
        <Suspense
            fallback={
                <LoadingOverlay>
                    <MortalityDemographicCard {...remaining} />
                </LoadingOverlay>
            }
        >
            <Await resolve={provider.get()}>
                {(demographic) => <MortalityDemographicCard demographic={demographic} {...remaining} />}
            </Await>
        </Suspense>
    );
};

export { PatientFileMortalityCard };
export type { PatientFileMortalityCardProps };
