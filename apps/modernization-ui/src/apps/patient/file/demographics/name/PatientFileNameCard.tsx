import { Suspense } from 'react';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { LoadingOverlay } from 'libs/loading';
import { NameDemographic, NameDemographicCard, NameDemographicCardProps } from 'libs/patient/demographics/name';

type PatientFileNameCardProps = {
    provider: MemoizedSupplier<Promise<NameDemographic[]>>;
} & Omit<NameDemographicCardProps, 'title'>;

const PatientFileNameCard = ({ provider, ...remaining }: PatientFileNameCardProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <NameDemographicCard {...remaining} />
            </LoadingOverlay>
        }>
        <Await resolve={provider.get()}>{(resolved) => <NameDemographicCard data={resolved} {...remaining} />}</Await>
    </Suspense>
);

export { PatientFileNameCard };
export type { PatientFileNameCardProps };
