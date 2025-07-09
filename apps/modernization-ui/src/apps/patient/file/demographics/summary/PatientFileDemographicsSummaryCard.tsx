import { Suspense } from 'react';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { LoadingOverlay } from 'libs/loading';
import { PatientFileDemographicsSummary } from './summary';
import { PatientDemographicsSummaryCard, PatientDemographicsSummaryCardProps } from './PatientDemographicsSummaryCard';

type PatientFileDemographicsSummaryCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileDemographicsSummary>>;
} & Omit<PatientDemographicsSummaryCardProps, 'title'>;

const PatientFileDemographicsSummaryCard = ({ provider, ...remaining }: PatientFileDemographicsSummaryCardProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <PatientDemographicsSummaryCard {...remaining} />
            </LoadingOverlay>
        }>
        <Await resolve={provider.get()}>
            {(summary) => <PatientDemographicsSummaryCard summary={summary} {...remaining} />}
        </Await>
    </Suspense>
);

export { PatientFileDemographicsSummaryCard };
