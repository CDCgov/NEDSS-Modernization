import { Investigations } from './investigations/Investigations';
import LabReports from './labReports/LabReports';

export const PatientFileEvents = () => {
    return (
        <div>
            <Investigations />
            <br />
            <LabReports />
        </div>
    );
};
