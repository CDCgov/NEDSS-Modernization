import { createContext, ReactNode, useContext, useState } from 'react';

const ActiveIdContext = createContext<{ activeId: string | null; setActiveId: (val: string | null) => void }>({
    activeId: null,
    setActiveId: () => {},
});

type Props = {
    children: ReactNode;
};
const ActiveIdProvider = ({ children }: Props) => {
    const [activeId, setActiveId] = useState<string | null>(null);

    return <ActiveIdContext.Provider value={{ activeId, setActiveId }}>{children}</ActiveIdContext.Provider>;
};

const useActiveId = () => {
    return useContext(ActiveIdContext);
};

export { ActiveIdProvider, useActiveId };
