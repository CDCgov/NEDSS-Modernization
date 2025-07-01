import { AddressDemographicCard } from 'libs/patient/demographics/address';
import { Sizing } from 'design-system/field';
import { usePatientFileAddress } from './usePatientFileAddres';
import { LoadingCard } from 'libs/loading';
import { Shown } from 'conditional-render';

type PatientFileAddressProps = {
    patient: number;
    sizing: Sizing;
};

const PatientFileAddressCard = ({ patient, sizing }: PatientFileAddressProps) => {
    const data = usePatientFileAddress(patient);

    return (
        <Shown when={!data.isLoading} fallback={<LoadingCard id="loading-addresses" title="Address" sizing={sizing} />}>
            <AddressDemographicCard id={'patient-file-address-demographic'} data={data.data} sizing={sizing} />
        </Shown>
    );
};

export { PatientFileAddressCard };
