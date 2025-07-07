import {
    PhoneEmailDemographicCard,
    PhoneEmailDemographicCardProps
} from 'libs/patient/demographics/phoneEmail/PhoneEmailDemographicCard';
import { LoadingOverlay } from 'libs/loading';
import { Suspense } from 'react';
import { Await } from 'react-router';
import { PhoneEmailDemographic } from 'libs/patient/demographics/phoneEmail/phoneEmails';
import { MemoizedSupplier } from 'libs/supplying';

type PatientFilePhoneEmailProps = {
    provider: MemoizedSupplier<Promise<Array<PhoneEmailDemographic>>>;
} & Omit<PhoneEmailDemographicCardProps, 'title'>;

const PatientFilePhoneEmailCard = ({ provider, ...remaining }: PatientFilePhoneEmailProps) => {
    return (
        <Suspense
            fallback={
                <LoadingOverlay>
                    <PhoneEmailDemographicCard data={undefined} title="Phone & email" {...remaining} />
                </LoadingOverlay>
            }>
            <Await resolve={provider.get()}>
                {(resolved) => <PhoneEmailDemographicCard data={resolved} title="Phone & email" {...remaining} />}
            </Await>
        </Suspense>
    );
};

export { PatientFilePhoneEmailCard };
