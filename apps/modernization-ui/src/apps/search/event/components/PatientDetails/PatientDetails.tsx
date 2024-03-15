import { useNavigate } from 'react-router';
import { Grid } from '@trussworks/react-uswds';
import { NoData } from 'components/NoData';

import { calculateAge } from 'utils/util';
import { formattedName } from 'utils';
import { internalizeDate } from 'date';

type Detail = {
    birthTime?: any;
    firstName?: string | null;
    lastName?: string | null;
    currSexCd?: string | null;
    shortId?: number | null;
};

type PatientDetailsProps = {
    patient: Detail | null | undefined;
};

const PatientDetails = ({ patient }: PatientDetailsProps) => {
    const navigate = useNavigate();
    let name = '';
    let birthDate: string | undefined;
    let age: string | undefined;
    let sex: string | undefined;
    if (patient) {
        name =
            !patient.lastName && !patient.firstName
                ? `No Data`
                : formattedName(patient?.lastName ?? '', patient?.firstName ?? '');
        if (patient.birthTime) {
            birthDate = internalizeDate(patient.birthTime);
            age = calculateAge(new Date(patient.birthTime));
        }
        sex = patient.currSexCd === 'M' ? 'Male' : patient.currSexCd === 'F' ? 'Female' : 'Unknown';
    }

    const redirectPatientProfile = () => {
        navigate(`/patient-profile/${patient?.shortId}`);
    };

    return (
        <Grid row gap={3}>
            <Grid col={12} className="margin-bottom-2">
                <p className="margin-0 text-normal text-gray-50 search-result-item-label">LEGAL NAME</p>
                <a
                    onClick={redirectPatientProfile}
                    className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                    style={{ wordBreak: 'break-word', cursor: 'pointer' }}>
                    {name}
                </a>
            </Grid>
            <Grid col={12} className="margin-bottom-2">
                <div className="grid-row flex-align-center">
                    <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                        DATE OF BIRTH
                    </p>
                    <p className="margin-0 font-sans-2xs text-normal">
                        <>
                            {birthDate ? birthDate : <NoData />}
                            <span className="font-sans-2xs"> {age ? `(${age})` : ''}</span>
                        </>
                    </p>
                </div>
                <div className="grid-row flex-align-center">
                    <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">SEX</p>
                    <p className="margin-0 font-sans-2xs text-normal">{sex ? sex : <NoData />}</p>
                </div>
                <div className="grid-row flex-align-center">
                    <p className="margin-0 text-normal search-result-item-label text-gray-50 margin-right-1">
                        PATIENT ID
                    </p>
                    <p className="margin-0 font-sans-2xs text-normal">{patient?.shortId || <NoData />}</p>
                </div>
            </Grid>
        </Grid>
    );
};

export { PatientDetails };
