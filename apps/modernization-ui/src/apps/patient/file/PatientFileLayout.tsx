import { CSSProperties, ReactNode, useCallback, useState } from 'react';
import { Patient } from './patient';
import { PatientFileHeader } from './PatientFileHeader';
import { SkipLink } from 'SkipLink';

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
        <>
            <SkipLink id="patient-name-header" />
            <div className={styles.file}>
                <header id="patient-name-header" ref={targeted} tabIndex={-1}>
                    <PatientFileHeader patient={patient} actions={actions?.(patient)} />
                    <nav>{navigation(patient)}</nav>
                </header>
                <main style={inline}>{children}</main>
            </div>
        </>
    );
};

export { PatientFileLayout };
