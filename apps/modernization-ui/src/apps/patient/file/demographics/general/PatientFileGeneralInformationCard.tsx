import { Suspense } from 'react';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { LoadingOverlay } from 'libs/loading';
import {
    GeneralInformationDemographic,
    GeneralInformationDemographicCard,
    GeneralInformationDemographicCardProps
} from 'libs/patient/demographics/general';

type PatientFileGeneralCardProps = {
    provider: MemoizedSupplier<Promise<GeneralInformationDemographic>>;
} & Omit<GeneralInformationDemographicCardProps, 'title'>;

const PatientFileGeneralInformationCard = ({ provider, ...remaining }: PatientFileGeneralCardProps) => {
    return (
        <Suspense
            fallback={
                <LoadingOverlay>
                    <GeneralInformationDemographicCard {...remaining} />
                </LoadingOverlay>
            }>
            <Await resolve={provider.get()}>
                {(demographic) => <GeneralInformationDemographicCard demographic={demographic} {...remaining} />}
            </Await>
        </Suspense>
    );
};

export { PatientFileGeneralInformationCard };
