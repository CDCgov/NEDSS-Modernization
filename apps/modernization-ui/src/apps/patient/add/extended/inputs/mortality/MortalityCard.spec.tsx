import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import { MortalityCard } from './MortalityCard';

const mockLocationCodedValues = {
    states: {
        all: [{ name: 'StateName', value: '1' }]
    },
    counties: {
        byState: (state: string) => [{ name: 'CountyName', value: '2' }]
    },
    countries: [{ name: 'CountryName', value: '3' }]
};

jest.mock('location/useLocationCodedValues', () => ({
    useLocationCodedValues: () => mockLocationCodedValues
}));

const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <MortalityCard />
        </FormProvider>
    );
};
describe('EthnicityEntryCard', () => {
    it('should display Mortality header on card', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('heading')).toHaveTextContent('Mortality');
    });
});
