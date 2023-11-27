import { Button, Grid, Icon } from '@trussworks/react-uswds';
import { ReactNode } from 'react';

export type Data = {
    title?: string;
    text?: string | null | undefined;
};

export type Props = {
    title: string;
    data: Data[];
    editing?: boolean;
    onEdit: () => void;
    children: ReactNode;
    readOnly?: boolean;
};

export const EditableCard = ({ title, data, children, editing = false, onEdit, readOnly = false }: Props) => {
    return (
        <div className="common-card">
            <div className="grid-row flex-align-center flex-justify padding-x-2 padding-y-3 border-bottom border-base-lighter">
                <p className="font-sans-lg text-bold margin-0 table-header">{title}</p>
                {!editing && (
                    <Button type="button" className="grid-row" onClick={onEdit} disabled={readOnly}>
                        <Icon.Edit className="margin-right-05" />
                        Edit
                    </Button>
                )}
            </div>
            <div>
                {!editing &&
                    data?.map((item, index) => (
                        <Grid
                            row
                            key={index}
                            className="padding-x-2 padding-y-3 border-bottom border-base-lighter wall-design">
                            <Grid className="text-bold" col={6}>
                                {item.title}
                            </Grid>
                            {item.text && <Grid col={6}>{item.text}</Grid>}
                            {!item.text && (
                                <Grid col={6} className="font-sans-md no-data">
                                    No Data
                                </Grid>
                            )}
                        </Grid>
                    ))}
                {editing && children}
            </div>
        </div>
    );
};
