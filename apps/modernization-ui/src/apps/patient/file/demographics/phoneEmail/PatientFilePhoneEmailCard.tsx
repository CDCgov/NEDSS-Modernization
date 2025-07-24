import { Suspense } from 'react';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { LoadingOverlay } from 'libs/loading';
import {
    PhoneEmailDemographic,
    PhoneEmailDemographicCard,
    PhoneEmailDemographicCardProps
} from 'libs/patient/demographics/phoneEmail';

type PatientFilePhoneEmailProps = {
    provider: MemoizedSupplier<Promise<PhoneEmailDemographic[]>>;
} & Omit<PhoneEmailDemographicCardProps, 'title'>;

const PatientFilePhoneEmailCard = ({ provider, ...remaining }: PatientFilePhoneEmailProps) => {
    return (
        <Suspense
            fallback={
                <LoadingOverlay>
                    <PhoneEmailDemographicCard {...remaining} />
                </LoadingOverlay>
            }>
            <Await resolve={provider.get()}>
                {(resolved) => <PhoneEmailDemographicCard data={resolved} {...remaining} />}
            </Await>
        </Suspense>
    );
};

export { PatientFilePhoneEmailCard };
