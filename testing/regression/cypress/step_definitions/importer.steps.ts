import { When } from '@badeball/cypress-cucumber-preprocessor';
import { getInsertELRQuery } from '@utils/queries';
import dbConfig from '@/db.config';

/**
 * Generates and imports a unique ELR
 * and assigns a program area to remove from DRSA queue
 * @param {string} programArea - Program Area to set the ELR
 */
When('I add and import an ELR for the {string} program area', (programArea: string) => {
    const ELR_QUERY = getInsertELRQuery();
    cy.task('sqlServer', { connectConfig: dbConfig, sqlQuery: ELR_QUERY }).then(() => {
        cy.exec('npm run ELRImporter').then(() => {
            cy.visit('/nbs/HomePage.do?method=loadHomePage');
            // visit DRSA queue for the newly imported ELR
            cy.get('a').contains('Documents Requiring Security Assignment').eq(0).click();
            cy.contains('a', 'Date Received').click(); // sort the documents received by latest
            cy.get('input[name="selectCheckBox"]').eq(0).check();

            cy.contains('a', 'Transfer Ownership').click();
            cy.enterInput('input[title="Program Area"]:not([disabled])', programArea); // assign program area

            cy.findAllByRole('button', { name: 'Submit' }).first().click();
        });
    });
});
