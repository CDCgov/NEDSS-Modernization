import { Button, Grid, Icon } from '@trussworks/react-uswds';
import { ReactNode, useState } from 'react';
import './style.scss';
import { PatientBirth, PatientGeneral, PatientMortality } from 'generated/graphql/schema';
import { NoData } from 'components/NoData';

export type TableProps = {
    tableHeader?: string;
    buttons?: ReactNode | ReactNode[];
    tableData?: any[];
    type?: 'general' | 'mortality' | 'sex' | undefined;
    data?: PatientGeneral | PatientMortality | PatientBirth;
};

export const HorizontalTable = ({ tableHeader, buttons, tableData, type }: TableProps) => {
    const [entry, setEntry] = useState<TableProps['type']>(undefined);
    buttons = (
        <Button type="button" className="grid-row" onClick={() => setEntry(type)}>
            <Icon.Edit className="margin-right-05" />
            Edit
        </Button>
    );

    return (
        <div className="common-card">
            <div className="grid-row flex-align-center flex-justify padding-x-2 padding-y-3 border-bottom border-base-lighter">
                <p className="font-sans-lg text-bold margin-0 table-header">{tableHeader}</p>
                {entry !== type && buttons}
            </div>

            <div className="padding-2">
                {entry !== type &&
                    tableData?.map((item, index) => (
                        <Grid row key={index} className="padding-x-2 padding-y-3 border-bottom wall-design">
                            <Grid col={6}>{item.title}</Grid>
                            {item.text && <Grid col={6}>{item.text}</Grid>}
                            {!item.text && (
                                <Grid col={6} className="font-sans-md no-data">
                                    <NoData />
                                </Grid>
                            )}
                        </Grid>
                    ))}
            </div>
        </div>
    );
};
