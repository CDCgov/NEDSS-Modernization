import { Suspense } from 'react';

import { LoadingOverlay } from 'libs/loading';
import { MemoizedSupplier } from 'libs/supplying';
import { Await } from 'react-router';

import { PatientDemographicsSummaryCard, PatientDemographicsSummaryCardProps } from './PatientDemographicsSummaryCard';
import { PatientFileDemographicsSummary } from './summary';

type PatientFileDemographicsSummaryCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileDemographicsSummary>>;
} & Omit<PatientDemographicsSummaryCardProps, 'title'>;

const PatientFileDemographicsSummaryCard = ({ provider, ...remaining }: PatientFileDemographicsSummaryCardProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <PatientDemographicsSummaryCard {...remaining} />
            </LoadingOverlay>
        }
    >
        <Await resolve={provider.get()}>
            {(summary) => <PatientDemographicsSummaryCard summary={summary} {...remaining} />}
        </Await>
    </Suspense>
);

export { PatientFileDemographicsSummaryCard };
