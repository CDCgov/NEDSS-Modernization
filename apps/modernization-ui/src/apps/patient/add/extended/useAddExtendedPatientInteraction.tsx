import { createContext, ReactNode, useContext } from 'react';
import { AddPatientInteraction } from 'apps/patient/add';
import { Created, Working } from '../useAddPatient';
import { PatientDemographicsEntry } from 'libs/patient/demographics';

type Invalid = {
    status: 'invalid';
    validationErrors: ValidationErrors;
};

type AddExtendedPatientState = Working | Created | Invalid;

type SubFormDirtyState = { address: boolean; phone: boolean; identification: boolean; name: boolean; race: boolean };

type ValidationErrors = {
    dirtySections: SubFormDirtyState;
};

type AddExtendedPatientInteraction = AddExtendedPatientState &
    Pick<AddPatientInteraction<PatientDemographicsEntry>, 'create'> & {
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
