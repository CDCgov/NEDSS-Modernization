import { render } from '@testing-library/react';
import { DataElementRow } from './DataElementRow';
import { FormProvider, useForm } from 'react-hook-form';
import { DataElements } from '../DataElement';

const configuration: DataElements = {
    lastName: {
        active: true,
        m: 0.8,
        u: 0.003,
        threshold: 0.7
    }
};

jest.mock('apps/deduplication/api/useDataElements', () => ({
    useDataElements: () => {
        return { configuration };
    }
}));
const Wrapper = () => {
    const form = useForm<DataElements>({ defaultValues: configuration });
    return (
        <FormProvider {...form}>
            <table>
                <tbody>
                    <DataElementRow field="lastName" fieldName="Last Name" />
                </tbody>
            </table>
        </FormProvider>
    );
};

describe('DataElementRow', () => {
    it('should render the row', () => {
        const { getByText, getByDisplayValue } = render(<Wrapper />);

        // Field
        expect(getByText('Last Name')).toBeInTheDocument();
        // M
        expect(getByDisplayValue('0.8')).toBeInTheDocument();
        // U
        expect(getByDisplayValue('0.003')).toBeInTheDocument();
        // Threshold
        expect(getByDisplayValue('0.7')).toBeInTheDocument();
        // Odds ratio
        expect(getByText(0.8 / 0.003)).toBeInTheDocument();
        // Log odds
        expect(getByText(Math.log(0.8) - Math.log(0.003))).toBeInTheDocument();
    });
});
