import { createContext, ReactNode, useContext, useEffect } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { ReportExecuteForm } from '../../ReportRunPage';
import { BasicFilterConfiguration } from 'generated';
import { useConfiguration } from 'configuration';

const CurrentStateContext = createContext<string | undefined>(undefined);

type Props = {
    stateFilter?: BasicFilterConfiguration;
    children: ReactNode;
};
const CurrentStateProvider = ({ stateFilter, children }: Props) => {
    const { setValue } = useFormContext<ReportExecuteForm>();
    const { ready, properties } = useConfiguration();
    const stateFilterId = stateFilter?.reportFilterUid;
    const formName: `basicFilter.${string}` = `basicFilter.id_${stateFilterId}`
    const stateVal = useWatch<ReportExecuteForm>({
        name: formName,
        defaultValue: stateFilter?.defaultValue,
    });

    // get first state in case it is used in multi-select
    // The ways this is used downstream very much assume only one state (to match NBS 6 logic)
    // in the future, may want to consider how to support more states being selected
    const state = (stateVal as undefined | string[])?.[0];

    // If there isn't a state set when the config first loads, set it to the default state
    useEffect(() => {
        if (ready && stateFilterId && !state && properties.entries.NBS_STATE_CODE) {
            setValue(formName, [properties.entries.NBS_STATE_CODE])
        }
    }, [ready])

    return <CurrentStateContext.Provider value={state}>{children}</CurrentStateContext.Provider>;
};

const useCurrentState = () => {
    return useContext(CurrentStateContext);
};

export { CurrentStateProvider, useCurrentState };
