import { Suspense } from 'react';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { LoadingOverlay } from 'libs/loading';
import {
    AddressDemographic,
    AddressDemographicCard,
    AddressDemographicCardProps
} from 'libs/patient/demographics/address';

type PatientFileAddressProps = {
    provider: MemoizedSupplier<Promise<AddressDemographic[]>>;
} & Omit<AddressDemographicCardProps, 'title'>;

const PatientFileAddressCard = ({ provider, ...remaining }: PatientFileAddressProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <AddressDemographicCard {...remaining} />
            </LoadingOverlay>
        }>
        <Await resolve={provider.get()}>
            {(resolved) => <AddressDemographicCard data={resolved} {...remaining} />}
        </Await>
    </Suspense>
);

export { PatientFileAddressCard };
