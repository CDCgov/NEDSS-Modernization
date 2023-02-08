import { Button, Grid, Icon } from '@trussworks/react-uswds';
import { ReactNode, useState } from 'react';
import './style.scss';
import { RaceForm } from '../DemographicsForm/Race';
import { EthnicityForm } from '../DemographicsForm/Ethnicity';
import { MortalityForm } from '../DemographicsForm/Mortality';
import { GeneralPatientInformation } from '../DemographicsForm/GenearalPatientData';

export type TableProps = {
    tableHeader?: string;
    buttons?: ReactNode | ReactNode[];
    tableData?: any[];
    type?: 'race' | 'ethnicity' | 'general' | 'mortality' | 'sex' | undefined;
};

export const HorizontalTable = ({ tableHeader, buttons, tableData, type }: TableProps) => {
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
                <p className="font-sans-lg text-bold margin-0">{tableHeader}</p>
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
            {raceForm === 'race' && <RaceForm setRaceForm={() => setRaceForm(undefined)} />}
            {raceForm === 'ethnicity' && <EthnicityForm setEthnicityForm={() => setRaceForm(undefined)} />}
            {raceForm === 'mortality' && <MortalityForm setMortalityForm={() => setRaceForm(undefined)} />}
            {raceForm === 'general' && <GeneralPatientInformation setGeneralForm={() => setRaceForm(undefined)} />}
        </div>
    );
};
