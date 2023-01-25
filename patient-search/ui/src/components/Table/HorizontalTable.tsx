import { Button, Grid, Icon } from '@trussworks/react-uswds';
import { ReactNode } from 'react';
import './style.scss';

type TableProps = {
    tableHeader?: string;
    buttons?: ReactNode | ReactNode[];
    tableData?: any[];
};

export const HorizontalTable = ({ tableHeader, buttons, tableData }: TableProps) => {
    buttons = (
        <Button type="button" className="grid-row">
            <Icon.Edit className="margin-right-05" />
            Edit
        </Button>
    );

    return (
        <div className="common-card">
            <div className="grid-row flex-align-center flex-justify padding-x-2 padding-y-3 border-bottom border-base-lighter">
                <p className="font-sans-lg text-bold margin-0">{tableHeader}</p>
                {buttons}
            </div>

            <div className="padding-2">
                {tableData?.map((item, index) => (
                    <Grid row key={index} className="padding-2 border-bottom wall-design">
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
        </div>
    );
};
