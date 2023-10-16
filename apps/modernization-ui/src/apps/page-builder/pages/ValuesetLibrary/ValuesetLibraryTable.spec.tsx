import { findByTestId, fireEvent, render, screen, waitFor } from '@testing-library/react';
import { ValueSet } from 'apps/page-builder/generated';
import { BrowserRouter } from 'react-router-dom';
import { AlertProvider } from '../../../../alert';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const pageSummary: ValueSet = {};
        const summaries = [pageSummary];
        const { container } = render(
            <BrowserRouter>
                <AlertProvider>
                    <ValuesetLibraryTable summaries={summaries} />
                </AlertProvider>
            </BrowserRouter>
        );

        const tableHeader = container.getElementsByClassName('table-head');
        expect(tableHeader[1].textContent).toBe('Type');
        expect(tableHeader[2].textContent).toBe('Value set name');
        expect(tableHeader[3].textContent).toBe('Value set description');
        expect(tableHeader[4].textContent).toBe('');
    });
});

describe('when at least one summary is available', () => {
    const pageSummary: ValueSet = {
        classCd: 'code_value_general',
        name: 'ACT_ENCOUNTER_CODE',
        assigningAuthorityCd: '2.16.840.1.113833',
        assigningAuthorityDescTxt: 'Health Level Seven',
        codeSetDescTxt: 'Act Encounter Code that is used to define patient encounter.',
        effectiveFromTime: '2017-10-03T15:46:19.160Z',
        effectiveToTime: '',
        isModifiableInd: 'N',
        nbsUid: 1312,
        sourceVersionTxt: '3',
        sourceDomainNm: 'VADS',
        statusCd: 'A',
        statusToTime: '',
        codeSetGroupId: 6010,
        adminComments:
            'Created to support Patient Encounter Type for eICR in NBS 5.2. The valueset is a subset of the larger ActClass valueset.',
        valueSetNm: 'Act Encounter Code',
        ldfPicklistIndCd: 'Y',
        valueSetCode: 'ACT_ENCOUNTER_CODE',
        valueSetTypeCd: 'PHIN',
        valueSetOid: '2.16.840.1.113883.5.4',
        valueSetStatusCd: 'Published',
        valueSetStatusTime: '2009-07-02T00:00:00Z',
        parentIsCd: 0,
        addTime: '2017-10-03T15:46:19.160Z',
        addUserId: 10000000
    };
    const summaries = [pageSummary];

    it('should display the page summaries', async () => {
        const { container } = render(
            <BrowserRouter>
                <AlertProvider>
                    <ValuesetLibraryTable summaries={summaries} />
                </AlertProvider>
            </BrowserRouter>
        );

        const tableData = container.getElementsByClassName('table-data');
        expect(tableData[1]).toHaveTextContent('PHIN');
        expect(tableData[2]).toHaveTextContent('Act Encounter Code');
        expect(tableData[3]).toHaveTextContent('Act Encounter Code that is used to define patient encounter');
    });

    it('has a button to expand the row', async () => {
        const container = render(
            <BrowserRouter>
                <AlertProvider>
                    <ValuesetLibraryTable summaries={summaries}></ValuesetLibraryTable>
                </AlertProvider>
            </BrowserRouter>
        );

        const button = container.getByRole('button', {
            name: /expand-more/i
        });
        expect(button).toBeInTheDocument();
    });

    describe('when the expand button is clicked', () => {
        // need to figure out why "click" scenario not working.
        it('displays the expand less button', async () => {
            const { getByTestId } = render(
                <BrowserRouter>
                    <AlertProvider>
                        <ValuesetLibraryTable summaries={summaries}></ValuesetLibraryTable>
                    </AlertProvider>
                </BrowserRouter>
            );

            const expandButton = await screen.findByTestId('expand-more');
            fireEvent.click(expandButton);

            await waitFor(() => {
                expect(screen.findByTestId('expand-less')).toBeTruthy();
            });
        });
    });
});
