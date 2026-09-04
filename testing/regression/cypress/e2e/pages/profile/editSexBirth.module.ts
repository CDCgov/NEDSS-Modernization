class EditSexBirthModule {
    get section() {
        return 'div[data-testid=grid] .common-card';
    }

    date() {
        const currentDate = new Date();
        const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
        const day = currentDate.getDate().toString().padStart(2, '0');
        const year = currentDate.getFullYear();
        const formattedDate = `${month}/${day}/${year}`;
        cy.get(this.section).eq(3).find('.usa-input.usa-date-picker__external-input').eq(0).type(formattedDate);
        return this;
    }

    currentSex(sex: any) {
        cy.get(this.section).eq(3).findByLabelText('Current sex').select(sex);
        return this;
    }

    save() {
        cy.get(this.section).eq(3).contains('button', 'Save');
        cy.get(this.section).click();
    }

    isCurrentSexAdded(currentSex: string) {
        cy.get(this.section).eq(3).contains(currentSex).should('be.visible');
    }
}
export default new EditSexBirthModule();
