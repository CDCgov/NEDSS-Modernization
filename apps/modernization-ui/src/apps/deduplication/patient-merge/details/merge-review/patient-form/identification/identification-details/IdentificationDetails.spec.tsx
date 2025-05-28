import { render, within } from '@testing-library/react';
import { PatientIdentification } from 'apps/deduplication/api/model/PatientData';
import { IdentificationDetails } from './IdentificationDetails';

const defaultIdentification: PatientIdentification = {
    personUid: '1',
    sequence: '1',
    asOf: '2014-03-11T00:00:00.000',
    type: "Driver's license",
    assigningAuthority: 'TX',
    value: '10001'
};

const Fixture = ({ identification = defaultIdentification }: { identification?: PatientIdentification }) => {
    return <IdentificationDetails identification={identification} />;
};

describe('IdentificationDetails', () => {
    it('should render the proper labels', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('As of date')).toBeInTheDocument();
        expect(getByText('Type')).toBeInTheDocument();
        expect(getByText('Assigning authority')).toBeInTheDocument();
        expect(getByText('ID value')).toBeInTheDocument();
    });

    it('should render "---" for missing optional fields', () => {
        const sparseIdentification: PatientIdentification = {
            personUid: '1',
            sequence: '1',
            asOf: '2014-03-11T00:00:00.000',
            type: "Driver's license",
            value: '10001'
        };
        const { getByText } = render(<Fixture identification={sparseIdentification} />);
        expect(within(getByText('Assigning authority').parentElement!).getByText('---')).toBeInTheDocument();
    });

    it('should display as of date in proper format', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('03/11/2014')).toBeInTheDocument();
    });
});
