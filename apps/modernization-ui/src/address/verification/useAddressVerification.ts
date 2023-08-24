import { useConfiguration } from 'configuration';
import { VerifiableAdddress } from './VerifiableAddress';
import { StateCodedValues } from 'location';
import { useAddressVerificationAPI } from './useAddressVerificationAPI';
import { useState } from 'react';

type AddressVerificationState =
    | { status: 'idle'; input: null }
    | {
          status: 'verifying' | 'unverified' | 'verified' | 'not-verifiable';
          input: VerifiableAdddress;
      }
    | { status: 'verified-found'; input: VerifiableAdddress; alternative: VerifiableAdddress };

type Props = {
    states: StateCodedValues;
};

const noop = () => {
    const [state, setState] = useState<AddressVerificationState>({ status: 'idle', input: null });

    const verify = (input: VerifiableAdddress) => setState({ status: 'not-verifiable', input });
    const reset = () => setState({ status: 'idle', input: null });

    return { state, verify, reset };
};

const useAddressVerification = ({ states }: Props) => {
    const configuration = useConfiguration();

    const enabled = configuration.features.address.verification;

    const key = configuration.settings.smarty?.key;

    return enabled && key ? useAddressVerificationAPI({ key, states }) : noop();
};

export { useAddressVerification };

export type { AddressVerificationState };
