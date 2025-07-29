import { createContext, ReactNode, useRef, RefObject, useId } from 'react';

const ClassicModalContext = createContext<RefObject<HTMLFormElement> | undefined>(undefined);

type Props = {
    children: ReactNode;
};

const ClassicModalProvider = ({ children }: Props) => {
    const id = useId();
    const form = useRef<HTMLFormElement>(null);

    return (
        <form id={id} ref={form}>
            <ClassicModalContext.Provider value={form}>{children}</ClassicModalContext.Provider>
        </form>
    );
};

export { ClassicModalProvider, ClassicModalContext };
