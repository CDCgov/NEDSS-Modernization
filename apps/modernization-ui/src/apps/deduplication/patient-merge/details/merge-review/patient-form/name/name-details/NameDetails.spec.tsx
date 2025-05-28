import { PatientName } from 'apps/deduplication/api/model/PatientData';
import { NameDetails } from './NameDetails';
import { render, within } from '@testing-library/react';

const defaultName: PatientName = {
    personUid: '1',
    sequence: '1',
    asOf: '2022-06-07T14:24:44.970',
    type: 'Legal',
    prefix: 'Bishop',
    last: 'Smith',
    secondLast: 'Smit',
    first: 'Johnny',
    middle: 'Joe',
    secondMiddle: 'Bob',
    suffix: 'Esquire',
    degree: 'PHD'
};

const Fixture = ({ name = defaultName }: { name?: PatientName }) => {
    return <NameDetails name={name} />;
};

describe('NameDetails', () => {
    it('should render the proper labels', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('As of date')).toBeInTheDocument();
        expect(getByText('Type')).toBeInTheDocument();
        expect(getByText('Prefix')).toBeInTheDocument();
        expect(getByText('Last')).toBeInTheDocument();
        expect(getByText('Second last')).toBeInTheDocument();
        expect(getByText('First')).toBeInTheDocument();
        expect(getByText('Middle')).toBeInTheDocument();
        expect(getByText('Second middle')).toBeInTheDocument();
        expect(getByText('Suffix')).toBeInTheDocument();
        expect(getByText('Degree')).toBeInTheDocument();
    });

    it('should render "---" for missing optional fields', () => {
        const sparseName: PatientName = {
            personUid: '1',
            sequence: '1',
            asOf: '2022-06-07T14:24:44.970',
            type: 'Legal'
        };
        const { getByText } = render(<Fixture name={sparseName} />);

        expect(within(getByText('Prefix').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Last').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Second last').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('First').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Middle').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Second middle').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Suffix').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Degree').parentElement!).getByText('---')).toBeInTheDocument();
    });

    it('should display as of date in proper format', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('06/07/2022')).toBeInTheDocument();
    });
});
