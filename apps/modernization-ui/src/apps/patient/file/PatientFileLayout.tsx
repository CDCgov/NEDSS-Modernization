import { CSSProperties, ReactNode, useCallback, useState } from 'react';
import { Patient } from './patient';
import { PatientFileHeader } from './PatientFileHeader';

import styles from './patient-file-layout.module.scss';

type PatientFileLayoutProps = {
    patient: Patient;
    actions?: (patient: Patient) => ReactNode;
    navigation: (patient: Patient) => ReactNode;
    children?: ReactNode | ReactNode[];
};

const PatientFileLayout = ({ patient, actions, navigation, children }: PatientFileLayoutProps) => {
    const [inline, setInline] = useState<CSSProperties | undefined>();

    const targeted = useCallback((element: HTMLElement | null) => {
        if (element) {
            setInline({ ['--patient-file-header-height']: `${element.clientHeight}px` } as CSSProperties);
        }
    }, []);

    return (
        <div className={styles.file}>
            <header ref={targeted}>
                <PatientFileHeader patient={patient} actions={actions?.(patient)} />
                <nav>{navigation(patient)}</nav>
            </header>
            <main style={inline}>{children}</main>
        </div>
    );
};

export { PatientFileLayout };
