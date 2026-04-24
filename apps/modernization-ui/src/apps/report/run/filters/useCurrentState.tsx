import { createContext, ReactNode, useContext } from 'react';
import { useWatch } from 'react-hook-form';
import { ReportExecuteForm } from '../ReportRunPage';
import { BasicFilterConfiguration } from 'generated';

const CurrentStateContext = createContext<string | undefined>(undefined);

type Props = {
    stateFilter?: BasicFilterConfiguration;
    children: ReactNode;
};
const CurrentStateProvider = ({ stateFilter, children }: Props) => {
    const stateFilterId = stateFilter?.reportFilterUid;
    const stateVal = useWatch<ReportExecuteForm>({
        name: `basicFilter.${stateFilterId}`,
        defaultValue: stateFilter?.defaultValue,
    });

    // get first state in case it is used in multi-select
    // The ways this is used downstream very much assume only one state (to match NBS 6 logic)
    // in the future, may want to consider how to support more states being selected
    const state = stateVal?.[0];

    return <CurrentStateContext.Provider value={state}>{children}</CurrentStateContext.Provider>;
};

const useCurrentState = () => {
    return useContext(CurrentStateContext);
};

export { CurrentStateProvider, useCurrentState };
