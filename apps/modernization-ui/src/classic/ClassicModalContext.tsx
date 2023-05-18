import { createContext, ReactNode, useRef, RefObject } from 'react';

const ClassicModalContext = createContext<RefObject<HTMLFormElement> | undefined>(undefined);

type Props = {
    children: ReactNode;
};

const ClassicModalProvider = ({ children }: Props) => {
    const form = useRef<HTMLFormElement>(null);

    return (
        <form id="classic" ref={form}>
            <ClassicModalContext.Provider value={form}>{children}</ClassicModalContext.Provider>
        </form>
    );
};

export { ClassicModalProvider, ClassicModalContext };
