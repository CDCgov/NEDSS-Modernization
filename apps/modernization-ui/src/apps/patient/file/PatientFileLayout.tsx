import { CSSProperties, ReactNode, useCallback, useId, useState } from 'react';
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
    const headerId = useId();

    const targeted = useCallback((element: HTMLElement | null) => {
        if (element) {
            setInline({ ['--patient-file-header-height']: `${element.clientHeight}px` } as CSSProperties);
        }
    }, []);

    return (
        <>
            <SkipLink id={headerId} />
            <div className={styles.file}>
                <header id={headerId} ref={targeted}>
                    <PatientFileHeader patient={patient} actions={actions?.(patient)} />
                    <nav>{navigation(patient)}</nav>
                </header>
                <main style={inline}>{children}</main>
            </div>
        </>
    );
};

export { PatientFileLayout };
