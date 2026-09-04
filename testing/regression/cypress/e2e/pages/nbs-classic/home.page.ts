class ClassicHomePage {
    runELRImporter() {
        cy.exec('npm run ELRImporter');
    }

    navigateToPatientSearchPane() {
        cy.get('#homePageAdvancedSearch').click();
    }

    enterLastName(text: string) {
        cy.get('[id="name.last"]').type(text);
    }

    clickSearchBtnInPatientSearchPane() {
        cy.contains('button', 'Search').eq(0).click();
    }

    enterFirstName(text: string) {
        cy.get('[id="name.last"]').type(text);
    }

    clickAddNewBtnInPatientSearchPane() {
        cy.contains('button', 'Add new').eq(0).click();
    }

    clickAddNewLabReportBtnInPatientSearchPane() {
        cy.contains('button', 'Add new lab report').eq(0).click();
    }

    verifyAddLabReport() {
        cy.contains('Add Lab Report');
    }

    clickDefaultQueue(queueName: string) {
        cy.get('.content ul li').then(($element: any) => {
            const text = $element.text().trim();
            if (!text.includes(`${queueName} (0)`)) {
                cy.get($element).contains(queueName).eq(0).click();
            }
        });
    }

    createTwoPatients() {
        const createPatient = () => {
            cy.get('#homePageAdvancedSearch').click();
            cy.get('[id="name.last"]').type('Simpson');
            cy.get('[id="name.first"]').type('Martin');
            cy.wait(1000);
            cy.contains('button', 'Search').click();
            cy.wait(2000);
            cy.contains('button', 'Add new patient').click();
            cy.wait(2000);
            cy.get('input[id="administrative.asOf"]').eq(0).clear();
            cy.get('input[id="administrative.asOf"]').eq(0).type('03/04/2024');
            cy.contains('button', 'Save').click();
            cy.wait(3000);
            cy.contains('button', 'View patient').click();
            cy.contains('a', 'Home').click();
        };
        createPatient();
        cy.wait(3000);
        createPatient();
    }

    clickMergePatientTab() {
        cy.contains('Merge Patients').eq(0).click();
    }

    clickOnManualSearch() {
        cy.contains('Manual Search').eq(0).click();
    }

    verifyFindPatientPage() {
        cy.contains('Search Criteria').eq(0);
    }

    searchUser() {
        cy.wait(2000);
        cy.get('#DEM102').type('Simpson');
        cy.get('#DEM104').type('Martin');
        cy.get('input[type="button"][value="Submit"]').eq(0).click();
    }

    selectUsersToMerge() {
        cy.get('.selectCheckBoxMerge').click({ multiple: true });
        cy.get('input[type="button"][value="Merge"]').eq(0).click();
        cy.on('window:confirm', () => {
            return true;
        });
    }

    clickSystemIdentifiedTab() {
        cy.contains('System Identified').eq(0).click();
    }

    verifyMergeCandidateListDisplayed() {
        cy.contains('Merge Candidate List').eq(0);
    }

    clickReportsTab() {
        cy.get('a').contains('Reports').click();
    }

    verifyReportsPageDisplayed() {
        cy.contains('Private Reports').eq(0);
    }

    runSASCA01Report() {
        // fill out report form inputs
        cy.enterInput('input[name="TXT_01"]', '1');
        cy.get('input[name="cvg_select_all"]').click();
        // run report
        cy.get('td').contains('Run').eq(0).click();
        cy.get('span').contains('The selected report has been run and is displayed in a new window.');
    }

    verifyDocumentsRequiringSecurityAssignment() {
        cy.get('a').contains('Documents Requiring Security Assignment').eq(0).click();
        cy.get('table#parent th img#queueIcon').eq(3).click();
        cy.get('input#SearchText1').first().type('jaja');
        cy.get('#b1SearchText1').click({ force: true });
    }

    verifyDocumentsRequiringReview() {
        cy.get('a').contains('Documents Requiring Review').eq(0).click();
        // cy.get("a").contains("Lab Report").eq(0).click()
        cy.get('th.sortable').eq(1).find('img#queueIcon').click();
        cy.get('label.selectAll').eq(1).click();
        cy.get('label').contains('Last 14 Days').click();
        cy.get('#b1').click({ force: true });
    }

    verifyOpenInvestigations() {
        cy.get('a').contains('Open Investigations').eq(0).click();
    }

    clickSortTableOption(string: string) {
        cy.get(`button[aria-label="${string}"]`).click();
    }

    verifyTopAfterSortSearch(string: string) {
        cy.get('#patient-search-results tbody tr').eq(0).contains(string);
    }

    verifyNoTopAfterSortSearch(string: string) {
        cy.get('#patient-search-results tbody tr').eq(0).should('not.contain', string);
    }

    searchArray(selector: string, values: string, field = 'value') {
        if (Array.isArray(values)) {
            values.forEach((item) => {
                const value = typeof item === 'object' ? item[field] || item.value : item;
                if (value) {
                    cy.get(selector).contains(value);
                }
            });
        }
    }

    patientVerifySearchTableInfo() {
        const patientData = Cypress.env('patientSearchRowData');
        cy.get('div#patient-search-results').contains(patientData.dob);
        this.searchArray('div#patient-search-results', patientData.names);
        this.searchArray('div#patient-search-results', patientData.ids, 'value');
        this.searchArray('div#patient-search-results', patientData.addresses, 'city');
        this.searchArray('div#patient-search-results', patientData.addresses, 'state');
        this.searchArray('div#patient-search-results', patientData.addresses, 'zipcode');
        this.searchArray('div#patient-search-results', patientData.emails);
        this.searchArray('div#patient-search-results', patientData.phones);
        cy.get('div#patient-search-results a').eq(0).click();
        this.searchArray('p.patient-summary-item-value', patientData.ids, 'value');
        this.searchArray('p.patient-summary-item-value', patientData.addresses, 'city');
        this.searchArray('p.patient-summary-item-value', patientData.addresses, 'state');
        this.searchArray('p.patient-summary-item-value', patientData.addresses, 'zipcode');
        this.searchArray('p.patient-summary-item-value', patientData.phones);
        cy.get('a').contains('Demographics').click();
        cy.contains('a', 'Demographics').should('have.attr', 'aria-current', 'page');
        this.searchArray('main', patientData.emails);
    }

    copySearchRowInfo() {
        cy.wait(1000);
        cy.get('body').then((body) => {
            if (body.find('div#patient-search-results').length > 0) {
                cy.get('div#patient-search-results tbody tr td').then(($tds) => {
                    const tdTexts = $tds.toArray().map((td) => td.innerText.trim());

                    function categorizeAddresses(text: string) {
                        const categorized = [];
                        const lines = text
                            .split(/\n+/)
                            .map((line: string) => line.trim())
                            .filter((line: string) => line); // Remove blank lines
                        let currentType: string | null = null;
                        let currentValue: string[] = [];

                        lines.forEach((line: any) => {
                            if (/^[A-Za-z\s]+$/.test(line) && line.length < 30) {
                                if (currentType && currentValue.length > 0) {
                                    categorized.push(formatAddress(currentType, currentValue));
                                }
                                currentType = line.toLowerCase();
                                currentValue = [];
                            } else {
                                currentValue.push(line);
                            }
                        });

                        if (currentType && currentValue.length > 0) {
                            categorized.push(formatAddress(currentType, currentValue));
                        }

                        return categorized;
                    }

                    function formatAddress(type: string, addressLines: string[]) {
                        if (addressLines.length < 2) {
                            return { type, fullAddress: addressLines.join(', ') };
                        }

                        const cityStateZip: string = addressLines.pop()!;
                        const match = cityStateZip.match(/^(.+),\s([A-Z]{2})\s(\d{5})$/);

                        if (match) {
                            return {
                                type,
                                street: addressLines.join(', '),
                                city: match[1],
                                state: match[2],
                                zipcode: match[3],
                                fullAddress: addressLines.join(', ') + ', ' + cityStateZip,
                            };
                        } else {
                            return { type, fullAddress: addressLines.join(', ') + ', ' + cityStateZip };
                        }
                    }

                    function categorizeEntries(text: string) {
                        const categorized = [];
                        const lines = text
                            .split(/\n+/)
                            .map((line: string) => line.trim())
                            .filter((line: string) => line); // Remove blank lines
                        let currentType: string | null = null;
                        let currentValue: string[] = [];

                        lines.forEach((line: string) => {
                            if (/^[A-Za-z\s]+$/.test(line) && line.length < 30) {
                                if (currentType && currentValue.length > 0) {
                                    categorized.push({ type: currentType, value: currentValue.join(', ') });
                                }
                                currentType = line.toLowerCase();
                                currentValue = [];
                            } else {
                                currentValue.push(line);
                            }
                        });

                        if (currentType && currentValue.length > 0) {
                            categorized.push({ type: currentType, value: currentValue.join(', ') });
                        }

                        return categorized;
                    }

                    const parsedDataTdTexts: any = {
                        patientId: tdTexts[0],
                        names: categorizeEntries(tdTexts[1]),
                        dob: tdTexts[2].split(/\n+/)[0],
                        age: tdTexts[2].split(/\n+/)[1] || null,
                        gender: tdTexts[3],
                        addresses: categorizeAddresses(tdTexts[4]),
                        phones: categorizeEntries(tdTexts[5]),
                        ids: categorizeEntries(tdTexts[6]),
                        emails: tdTexts[7].split(/\n+/).map((email) => ({ type: 'email', value: email })),
                    };

                    cy.log(parsedDataTdTexts);
                    Cypress.env('patientSearchRowData', parsedDataTdTexts);
                    this.patientVerifySearchTableInfo();
                });
            }
        });
    }
}

export default new ClassicHomePage();
