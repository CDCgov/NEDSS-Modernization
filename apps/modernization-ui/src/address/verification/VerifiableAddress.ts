import { StateCodedValue } from 'location';

type VerifiableAdddress = {
    address1: string | null;
    city: string | null;
    state: StateCodedValue | null;
    zip: string | null;
};

type VerifiableAddressConsumer = (address: VerifiableAdddress) => void;

export type { VerifiableAdddress, VerifiableAddressConsumer };
