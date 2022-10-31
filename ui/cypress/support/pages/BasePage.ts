/// <reference types="cypress" />

export default abstract class BasePage {
    relativeUrl: string;
    defaultOptions = { log: Cypress.env('detailedLogs') };

    constructor(relativeUrl: string) {
        this.relativeUrl = relativeUrl;
    }

    protected getElement(selector: string): Cypress.Chainable<JQuery<HTMLElement>> {
        return cy.get(selector, this.defaultOptions);
    }

    protected setText(selector: string, value: string): void {
        if (value.trim().length === 0) {
            this.getElement(selector).clear(this.defaultOptions);
        } else {
            this.getElement(selector).clear(this.defaultOptions).type(value, this.defaultOptions);
        }
    }

    protected setInnerHtml(selector: string, content: string): void {
        this.getElement(selector).then((element) => {
            element[0].innerHTML = content;
        });
    }

    protected setValue(selector: string, value: string): void {
        this.getElement(selector).then((element) => {
            (element[0] as HTMLInputElement).value = value;
        });
    }

    protected setChecked(selector: string, checked: boolean): void {
        this.getElement(selector).then((results) => {
            const inputElement = results[0] as HTMLInputElement;
            if (inputElement.checked !== checked) {
                inputElement.click();
            }
        });
    }

    protected select(selector: string, selection: string[], options?: Partial<Cypress.SelectOptions>): void {
        this.getElement(selector).select(selection, { ...this.defaultOptions, ...options });
    }

    protected click(selector: string): void {
        this.getElement(selector).click(this.defaultOptions);
    }

    protected clickFirst(selector: string): Cypress.Chainable {
        return this.getElement(selector).first(this.defaultOptions).click(this.defaultOptions);
    }

    protected submit(selector: string): void {
        this.getElement(selector).submit(this.defaultOptions);
    }

    protected clear(selector: string): void {
        this.getElement(selector).clear(this.defaultOptions);
    }

    protected blur(selector: string): void {
        this.getElement(selector).blur(this.defaultOptions);
    }

    public navigateTo(): void {
        cy.visit(this.relativeUrl, this.defaultOptions);
    }
}
