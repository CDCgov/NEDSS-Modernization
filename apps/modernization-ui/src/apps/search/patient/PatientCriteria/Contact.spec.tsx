import { render } from '@testing-library/react';
import { Contact } from './Contact';
import { PatientCriteriaEntry } from '../criteria';
import { useForm } from 'react-hook-form';
import { ApolloClient, ApolloProvider, createHttpLink, InMemoryCache } from '@apollo/client';


const link = createHttpLink();

const cache = new InMemoryCache();

const client = new ApolloClient({
    link,
    cache
})
const Component = () => {
    const { control } = useForm<PatientCriteriaEntry>({
        defaultValues: {
            status: [{ name: 'Active', label: 'Active', value: 'ACTIVE' }]
        },
        mode: 'onBlur'
    });
    return <ApolloProvider client={client}><Contact control={control} /></ApolloProvider>;
}

describe('when Address renders', () => {
    it('should render 3 input fields', () => {
        const { container } = render(<Component />);
        const inputs = container.getElementsByTagName('input');
        expect(inputs.length).toBe(2);
    });
});
