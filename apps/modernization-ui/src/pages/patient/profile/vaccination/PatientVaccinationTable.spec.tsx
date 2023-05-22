import { render } from '@testing-library/react';
import { PageProvider } from 'page';
import { PatientVaccinationTable } from './PatientVaccinationTable';
import { ClassicModalProvider } from 'classic/ClassicModalContext';

describe('when rendered', () => {
    it('should display sentence cased contact headers', async () => {
        const { container } = render(
            <ClassicModalProvider>
                <PageProvider>
                    <PatientVaccinationTable patient={'patient'} vaccinations={[]}></PatientVaccinationTable>
                </PageProvider>
            </ClassicModalProvider>
        );

        const tableHeader = container.getElementsByClassName('table-header');
        expect(tableHeader[0].innerHTML).toBe('Vaccinations');

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Date created');
        expect(tableHeads[1].innerHTML).toBe('Provider');
        expect(tableHeads[2].innerHTML).toBe('Date administered');
        expect(tableHeads[3].innerHTML).toBe('Vaccine administered');
        expect(tableHeads[4].innerHTML).toBe('Associated with');
        expect(tableHeads[5].innerHTML).toBe('Event #');
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
        const { container, findByText } = render(
            <ClassicModalProvider>
                <PageProvider>
                    <PatientVaccinationTable patient={'patient'} vaccinations={vaccinations}></PatientVaccinationTable>
                </PageProvider>
            </ClassicModalProvider>
        );

        const tableData = container.getElementsByClassName('table-data');

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
