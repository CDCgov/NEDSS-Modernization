import { Outlet, useParams } from 'react-router';
import { PatientFileHeader } from './PatientFileHeader';
import { Button } from 'components/button';
import { Icon } from 'design-system/icon';

import styles from './patient-file.module.scss';
import { PatientFileProvider } from './PatientFileContext';
import { DeleteAction } from './delete';

const ViewActions = () => {
    return (
        <>
            <DeleteAction buttonClassName={styles['usa-button']} />
            <Button
                aria-label="Print"
                data-tooltip-position="top"
                data-tooltip-offset="center"
                icon={<Icon name="print" />}
                sizing={'medium'}
                secondary
            />
            <Button aria-label="Edit" icon={<Icon name="edit" />} secondary labelPosition="right" sizing={'medium'}>
                Edit
            </Button>
        </>
    );
};

export const PatientFile = () => {
    const { id } = useParams();

    return (
        <PatientFileProvider id={id}>
            <div className={styles.file}>
                <PatientFileHeader id={id ?? ''} headerActions={ViewActions} />
                <Outlet />
            </div>
        </PatientFileProvider>
    );
};
