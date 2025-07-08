import { LoadingOverlay } from 'libs/loading';
import { Suspense } from 'react';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { RaceDemographic } from 'libs/patient/demographics/race/race';
import { RaceDemographicCard, RaceDemographicCardProps } from 'libs/patient/demographics/race/RaceDemographicCard';

type PatientFileRaceProps = {
    provider: MemoizedSupplier<Promise<Array<RaceDemographic>>>;
} & Omit<RaceDemographicCardProps, 'title'>;

const PatientFileRaceCard = ({ provider, ...remaining }: PatientFileRaceProps) => {
    return (
        <Suspense
            fallback={
                <LoadingOverlay>
                    <RaceDemographicCard data={undefined} title="Race" {...remaining} />
                </LoadingOverlay>
            }>
            <Await resolve={provider.get()}>
                {(resolved) => <RaceDemographicCard data={resolved} title="Race" {...remaining} />}
            </Await>
        </Suspense>
    );
};

export { PatientFileRaceCard };
