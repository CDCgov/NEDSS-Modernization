import React from 'react';
import { Button } from 'components/button';
import { useAddPatientFromSearch } from './add/useAddPatientFromSearch';
import { permissions, Permitted } from 'libs/permission';
import { Icon } from 'design-system/icon';

type Props = {
    disabled: boolean;
    buttonRef?: React.RefObject<HTMLButtonElement>;
};

const PatientSearchActions = ({ disabled, buttonRef }: Props) => {
    const { add } = useAddPatientFromSearch();

    return (
        <Permitted permission={permissions.patient.add}>
            <Button
                buttonRef={buttonRef}
                type="button"
                onClick={add}
                disabled={disabled}
                icon={<Icon name="add_circle" />}>
                Add new patient
            </Button>
        </Permitted>
    );
};

export { PatientSearchActions };
