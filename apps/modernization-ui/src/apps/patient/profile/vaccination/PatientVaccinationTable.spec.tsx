import { render } from '@testing-library/react';
import { PatientVaccinationTable } from './PatientVaccinationTable';
import { ClassicModalProvider } from 'classic/ClassicModalContext';
import { WithinTableProvider } from 'components/Table/testing';

describe('when rendered', () => {
    it('should display sentence cased contact headers', async () => {
        const { getAllByRole, getByRole } = render(
            <ClassicModalProvider>
                <WithinTableProvider>
                    <PatientVaccinationTable patient={'patient'} vaccinations={[]}></PatientVaccinationTable>
                </WithinTableProvider>
            </ClassicModalProvider>
        );

        const tableHeader = getByRole('heading');
        expect(tableHeader).toHaveTextContent('Vaccinations');

        const tableHeads = getAllByRole('columnheader');

        expect(tableHeads[0]).toHaveTextContent('Date created');
        expect(tableHeads[1]).toHaveTextContent('Provider');
        expect(tableHeads[2]).toHaveTextContent('Date administered');
        expect(tableHeads[3]).toHaveTextContent('Vaccine administered');
        expect(tableHeads[4]).toHaveTextContent('Associated with');
        expect(tableHeads[5]).toHaveTextContent('Event #');
    });
});

describe('when at least one contact is available for a patient', () => {
    const vaccinations = [
        {
            vaccination: '10055380',
            createdOn: new Date('2023-03-17T20:08:45.213Z'),
            provider: 'provider-value',
            administeredOn: new Date('2023-01-17T05:00:00Z'),
            administered: 'administered-value',
            event: 'event-value',
            associatedWith: {
                id: 'association-id',
                local: 'association-local',
                condition: 'associated-condition'
            }
        }
    ];

    it('should display the contact', async () => {
        const { findAllByRole, findByText } = render(
            <ClassicModalProvider>
                <WithinTableProvider>
                    <PatientVaccinationTable patient={'patient'} vaccinations={vaccinations}></PatientVaccinationTable>
                </WithinTableProvider>
            </ClassicModalProvider>
        );

        const tableData = await findAllByRole('cell');

        const dateCreated = await findByText(/03\/17\/2023/);

        expect(dateCreated).toHaveTextContent('03:08 PM');

        expect(tableData[0]).toContainElement(dateCreated);
        expect(tableData[1]).toHaveTextContent('provider-value');
        expect(tableData[2]).toHaveTextContent('01/17/2023');
        expect(tableData[3]).toHaveTextContent('administered-value');

        //  Associated With
        const association = await findByText('association-local');

        expect(tableData[4]).toContainElement(association);
        const associationCondition = await findByText('associated-condition');

        expect(tableData[4]).toContainElement(associationCondition);

        expect(tableData[5]).toHaveTextContent('event-value');
    });
});
