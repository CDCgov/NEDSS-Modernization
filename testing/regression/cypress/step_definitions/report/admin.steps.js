import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

When('I navigate to manage reports', () => {
    cy.visit('/nbs/ListReport.do');
    cy.contains('Report Name').should('be.visible');
});

Then('I should see the report list', () => {
    cy.contains('Report Name').should('be.visible');
});

Then('I should see the {string} configuration page', (type) => {
    cy.contains(`${type} report`).should('be.visible');
});

Then('I should see {int} available filters', (filterCount) => {
    // account for header
    cy.findByRole('group', { name: '3. Available filters' })
        .findAllByRole('row')
        .should('have.length', filterCount + 1);
});

When('I click the filter {int} {string} button', (filterInd, name) => {
    cy.findAllByRole('button', { name }).eq(filterInd).click();
});

When('I add all filters', () => {
    const optionValues = [];
    cy.findByRole('combobox', { name: 'Filter' })
        .findAllByRole('option')
        .each(($option) => {
            // skip placeholder
            if (!$option.val()) return;
            optionValues.push($option.val());
        })
        .then(() => {
            cy.log(`Available filters: ${optionValues.length}`);

            for (const value of optionValues) {
                cy.log(`Selecting filter: ${value}`);
                cy.findByRole('combobox', { name: 'Filter' }).select(value);

                cy.findAllByRole('combobox').each(($item) => {
                    const name = $item.attr('name')
                    if (name === 'selectType') {
                        cy.wrap($item).select('Multi-select filter')
                    } else if (name === 'associatedColumn') {
                        cy.wrap($item).select(1);
                    }
                })

                cy.findByRole('button', { name: 'Add filter' }).click();
            }
        });
});
