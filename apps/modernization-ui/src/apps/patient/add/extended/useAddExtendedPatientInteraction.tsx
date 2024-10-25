import { createContext, ReactNode, useContext } from 'react';
import { CreatedPatient } from 'apps/patient/add/api';
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
    validationErrors: ValidationErrors;
};
type SubFormDirtyState = { address: boolean; phone: boolean; identification: boolean; name: boolean; race: boolean };

type ValidationErrors = {
    dirtySections: SubFormDirtyState;
};

type AddExtendedPatientState = Working | Created | Invalid;

type AddExtendedPatientInteraction = AddExtendedPatientState & {
    create: (entry: ExtendedNewPatientEntry) => void;
    setSubFormState: (subFormState: Partial<SubFormDirtyState>) => void;
};

export type { AddExtendedPatientInteraction, AddExtendedPatientState };

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
export type { SubFormDirtyState, ValidationErrors };
