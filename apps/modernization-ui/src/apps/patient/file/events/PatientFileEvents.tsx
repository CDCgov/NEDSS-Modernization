import { permissions, Permitted } from 'libs/permission';
import { Investigations } from './investigations/Investigations';

export const PatientFileEvents = () => {
    return (
        <Permitted permission={permissions.investigation.view}>
            <Investigations />
        </Permitted>
    );
};
