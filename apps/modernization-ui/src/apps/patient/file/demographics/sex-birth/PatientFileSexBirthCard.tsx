import { Suspense } from 'react';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { LoadingOverlay } from 'libs/loading';
import { SexBirthDemographicCardProps, SexBirthDemographicCard } from 'libs/patient/demographics/sex-birth';
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
            }>
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
