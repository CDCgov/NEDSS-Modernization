import { Button, Grid, Icon } from '@trussworks/react-uswds';
import { ReactNode, useState } from 'react';
import './style.scss';
import { EthnicityForm } from '../DemographicsForm/Ethnicity';
import { MortalityForm } from '../DemographicsForm/Mortality';
import { GeneralPatientInformation } from '../DemographicsForm/GenearalPatientData';
import { SexBirthForm } from '../DemographicsForm/SexBirth';
import { PatientBirth, PatientEthnicity, PatientGeneral, PatientMortality } from 'generated/graphql/schema';

export type TableProps = {
    tableHeader?: string;
    buttons?: ReactNode | ReactNode[];
    tableData?: any[];
    type?: 'race' | 'ethnicity' | 'general' | 'mortality' | 'sex' | undefined;
    data?: PatientGeneral | PatientMortality | PatientEthnicity | PatientBirth;
};

export const HorizontalTable = ({ tableHeader, buttons, tableData, type, data }: TableProps) => {
    const [raceForm, setRaceForm] = useState<TableProps['type']>(undefined);
    buttons = (
        <Button type="button" className="grid-row" onClick={() => setRaceForm(type)}>
            <Icon.Edit className="margin-right-05" />
            Edit
        </Button>
    );

    return (
        <div className="common-card">
            <div className="grid-row flex-align-center flex-justify padding-x-2 padding-y-3 border-bottom border-base-lighter">
                <p className="font-sans-lg text-bold margin-0 table-header">{tableHeader}</p>
                {raceForm !== type && buttons}
            </div>

            <div className="padding-2">
                {raceForm !== type &&
                    tableData?.map((item, index) => (
                        <Grid row key={index} className="padding-x-2 padding-y-3 border-bottom wall-design">
                            <Grid col={6}>{item.title}</Grid>
                            {item.text && <Grid col={6}>{item.text}</Grid>}
                            {!item.text && (
                                <Grid col={6} className="font-sans-md no-data">
                                    No data
                                </Grid>
                            )}
                        </Grid>
                    ))}
            </div>
            {raceForm === 'ethnicity' && (
                <EthnicityForm data={data as PatientEthnicity} setEthnicityForm={() => setRaceForm(undefined)} />
            )}
            {raceForm === 'mortality' && (
                <MortalityForm data={data as PatientMortality} setMortalityForm={() => setRaceForm(undefined)} />
            )}
            {raceForm === 'general' && (
                <GeneralPatientInformation
                    data={data as PatientGeneral}
                    setGeneralForm={() => setRaceForm(undefined)}
                />
            )}
            {raceForm === 'sex' && (
                <SexBirthForm data={data as PatientBirth} setSexBirthForm={() => setRaceForm(undefined)} />
            )}
        </div>
    );
};
