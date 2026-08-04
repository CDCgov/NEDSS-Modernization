import { Suspense } from 'react';

import { LoadingOverlay } from 'libs/loading';
import {
    PhoneEmailDemographic,
    PhoneEmailDemographicCard,
    PhoneEmailDemographicCardProps,
} from 'libs/patient/demographics/phoneEmail';
import { MemoizedSupplier } from 'libs/supplying';
import { Await } from 'react-router';

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
            }
        >
            <Await resolve={provider.get()}>
                {(resolved) => <PhoneEmailDemographicCard data={resolved} {...remaining} />}
            </Await>
        </Suspense>
    );
};

export { PatientFilePhoneEmailCard };
