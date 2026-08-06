import { Suspense } from 'react';

import { Await } from 'react-router';

import { LoadingOverlay } from 'libs/loading';
import {
    AddressDemographic,
    AddressDemographicCard,
    AddressDemographicCardProps,
} from 'libs/patient/demographics/address';
import { MemoizedSupplier } from 'libs/supplying';

type PatientFileAddressProps = {
    provider: MemoizedSupplier<Promise<AddressDemographic[]>>;
} & Omit<AddressDemographicCardProps, 'title'>;

const PatientFileAddressCard = ({ provider, ...remaining }: PatientFileAddressProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <AddressDemographicCard {...remaining} />
            </LoadingOverlay>
        }
    >
        <Await resolve={provider.get()}>
            {(resolved) => <AddressDemographicCard data={resolved} {...remaining} />}
        </Await>
    </Suspense>
);

export { PatientFileAddressCard };
