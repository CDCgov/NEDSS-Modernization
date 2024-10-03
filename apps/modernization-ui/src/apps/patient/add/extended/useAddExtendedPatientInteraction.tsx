import { createContext, ReactNode, useContext } from 'react';
import { CreatedPatient } from './api';
import { ExtendedNewPatientEntry } from './entry';

type Working = {
    status: 'waiting' | 'working';
};

type Created = {
    status: 'created';
    created: CreatedPatient;
};

type Invalid = {
    status: 'invalid';
    validationErrors: SubFormDirtyState;
};
type SubFormDirtyState = { address: boolean; phone: boolean; identification: boolean; name: boolean; race: boolean };

type AddExtendedPatientInteraction = (Working | Created | Invalid) & {
    create: (entry: ExtendedNewPatientEntry) => void;
    setSubFormState: (subFormState: Partial<SubFormDirtyState>) => void;
};

export type { AddExtendedPatientInteraction, Working, Created, Invalid };

const AddExtendedPatientInteractionContext = createContext<AddExtendedPatientInteraction | undefined>(undefined);

type Props = {
    interaction: AddExtendedPatientInteraction;
    children: ReactNode;
};

const AddExtendedPatientInteractionProvider = ({ interaction, children }: Props) => {
    return (
        <AddExtendedPatientInteractionContext.Provider value={interaction}>
            {children}
        </AddExtendedPatientInteractionContext.Provider>
    );
};

const useAddExtendedPatientInteraction = (): AddExtendedPatientInteraction => {
    const context = useContext(AddExtendedPatientInteractionContext);

    if (context === undefined) {
        throw new Error('useAddExtendedPatientInteraction must be used within a AddExtendedPatientInteractionProvider');
    }

    return context;
};

export { AddExtendedPatientInteractionProvider, useAddExtendedPatientInteraction };
export type { SubFormDirtyState };
