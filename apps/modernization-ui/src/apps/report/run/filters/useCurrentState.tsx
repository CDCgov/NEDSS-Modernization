import { createContext, ReactNode, useContext } from 'react';
import { useWatch } from 'react-hook-form';
import { ReportExecuteForm } from '../ReportRunPage';

const CurrentStateContext = createContext<string | undefined>(undefined);

type Props = {
    stateFilterId?: number;
    children: ReactNode;
};
const CurrentStateProvider = ({ stateFilterId, children }: Props) => {
    const stateVal = useWatch<ReportExecuteForm>({ name: `basicFilter.${stateFilterId}` });

    // get first state in case it is used in multi-select
    // The ways this is used downstream very much assume only one state (to match NBS 6 logic)
    // in the future, may want to consider how to support more states being selected
    const state = typeof stateVal == 'string' ? stateVal : stateVal?.length ? stateVal[0] : stateVal;

    return <CurrentStateContext.Provider value={state}>{children}</CurrentStateContext.Provider>;
};

const useCurrentState = () => {
    return useContext(CurrentStateContext);
};

export { CurrentStateProvider, useCurrentState };
