import { createContext } from 'react';
import { UseFormReturn } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';

export const InvestigationFormContext = createContext<
    UseFormReturn<InvestigationFilterEntry, Partial<InvestigationFilterEntry>, undefined>
>({} as UseFormReturn<InvestigationFilterEntry, Partial<InvestigationFilterEntry>, undefined>);
