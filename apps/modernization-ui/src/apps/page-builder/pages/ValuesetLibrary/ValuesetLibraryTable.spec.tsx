import { PageProvider } from 'page';
import { ValuesetLibraryTable } from './ValuesetLibraryTable';
import { screen, render, fireEvent } from '@testing-library/react';
import { ValueSet } from 'apps/page-builder/generated';
import { BrowserRouter } from 'react-router-dom';
import { AlertProvider } from '../../../../alert';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const { container } = render(
            <BrowserRouter>
                <PageProvider>
                    <AlertProvider>
                        <ValuesetLibraryTable summaries={[]} />
                    </AlertProvider>
                </PageProvider>
            </BrowserRouter>
        );

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Type');
        expect(tableHeads[1].innerHTML).toBe('Value set name');
        expect(tableHeads[2].innerHTML).toBe('Value set description');
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
                <PageProvider>
                    <AlertProvider>
                        <ValuesetLibraryTable summaries={summaries} />
                    </AlertProvider>
                </PageProvider>
            </BrowserRouter>
        );

        const tableData = container.getElementsByClassName('table-data');
        expect(tableData[0]).toHaveTextContent('PHIN');
        expect(tableData[1]).toHaveTextContent('Act Encounter Code');
        expect(tableData[2]).toHaveTextContent('Act Encounter Code that is used to define patient encounter');
    });

    it('has a button to expand the row', async () => {
        const container = render(
            <BrowserRouter>
                <PageProvider>
                    <AlertProvider>
                        <ValuesetLibraryTable summaries={summaries}></ValuesetLibraryTable>
                    </AlertProvider>
                </PageProvider>
            </BrowserRouter>
        );

        const button = container.getByRole('button', {
            name: /expand-more/i
        });
        expect(button).toBeInTheDocument();
    });

    describe('when the expand button is clicked', () => {
        // need to figure out why "click" scenario not working.
        it.skip('displays the expand less button', async () => {
            render(
                <BrowserRouter>
                    <PageProvider>
                        <AlertProvider>
                            <ValuesetLibraryTable summaries={summaries}></ValuesetLibraryTable>
                        </AlertProvider>
                    </PageProvider>
                </BrowserRouter>
            );

            const expandButton = screen.getByRole('button', {
                name: /expand-more/i
            });

            fireEvent.click(expandButton);

            // const expandLess = screen.getByRole('button', {
            //     name: /expand-less/i
            // });

            // const details = screen.getByText('Value set code');
            const less = screen.getByRole('button', {
                name: /expand-less/i
            });

            expect(less).toBeInTheDocument();
        });
    });
});
