import { createContext, ReactNode, useRef, RefObject, useId } from 'react';

import styles from './classic-modal.module.scss';

const ClassicModalContext = createContext<RefObject<HTMLFormElement> | undefined>(undefined);

type Props = {
    children: ReactNode;
};

const ClassicModalProvider = ({ children }: Props) => {
    const id = useId();
    const form = useRef<HTMLFormElement>(null);

    return (
        <>
            <form id={id} ref={form} aria-hidden className={styles.target} />
            <ClassicModalContext.Provider value={form}>{children}</ClassicModalContext.Provider>
        </>
    );
};

export { ClassicModalProvider, ClassicModalContext };
