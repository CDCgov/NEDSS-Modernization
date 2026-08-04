import { Suspense } from 'react';

import { LoadingOverlay } from 'libs/loading';
import { SexBirthDemographicCard, SexBirthDemographicCardProps } from 'libs/patient/demographics/sex-birth';
import { MemoizedSupplier } from 'libs/supplying';
import { Await } from 'react-router';

import { PatientFileSexBirthDemographic } from './PatientFileSexBirthDemographic';

type PatientFileSexBirthCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileSexBirthDemographic>>;
} & Omit<SexBirthDemographicCardProps, 'title' | 'ageResolver'>;

const PatientFileSexBirthCard = ({ provider, ...remaining }: PatientFileSexBirthCardProps) => {
    return (
        <Suspense
            fallback={
                <LoadingOverlay>
                    <SexBirthDemographicCard ageResolver={() => undefined} {...remaining} />
                </LoadingOverlay>
            }
        >
            <Await resolve={provider.get()}>
                {(resolved) => (
                    <SexBirthDemographicCard
                        demographic={resolved.demographic}
                        ageResolver={resolved.ageResolver}
                        {...remaining}
                    />
                )}
            </Await>
        </Suspense>
    );
};

export { PatientFileSexBirthCard };
