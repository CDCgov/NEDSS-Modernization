import { useParams } from 'react-router';
import { Heading } from 'components/heading';

import styles from './patient-file.module.scss';

export const PatientFile = () => {
    const { id } = useParams();

    return (
        <div className={styles.file}>
            <header>
                <Heading level={1}>Patient ID: {id}</Heading>
            </header>
            <main className="main-body"></main>
        </div>
    );
};
