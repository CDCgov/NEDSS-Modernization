import { render } from '@testing-library/react';
import { Address } from './Address';
import { PatientCriteriaEntry } from '../criteria';
import { FormProvider, useForm } from 'react-hook-form';
import { renderHook } from '@testing-library/react-hooks';
import { ApolloClient, ApolloProvider, createHttpLink, InMemoryCache } from '@apollo/client';


const link = createHttpLink();

const cache = new InMemoryCache();

const client = new ApolloClient({
    link,
    cache
})

const { result } = renderHook(() =>
    useForm<PatientCriteriaEntry>({
        mode: 'onChange',
        defaultValues: { status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }] }
    })
);

const setup = () => {
    return render (
        <ApolloProvider client={client}>
            <FormProvider {...result.current}>
                <Address />
            </FormProvider>
        </ApolloProvider>
    );
};

describe('when Address renders', () => {
    it('should render 3 input fields', () => {
        const { container } = setup();
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toEqual(3);
    });
});
