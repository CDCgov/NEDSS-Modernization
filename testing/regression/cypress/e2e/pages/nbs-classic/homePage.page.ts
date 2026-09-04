class HomePage {
    chartDropdown = 'input[name="charts_textbox"]';
    visualizationTitle = 'h2';

    selectChart(chartName: string) {
        cy.get(this.chartDropdown).type(chartName + '{enter}');
    }

    verifyVisualizationIsDisplayed(chartTitle: string) {
        cy.get(this.visualizationTitle).contains(chartTitle).should('be.visible');
    }
}

export const homePage = new HomePage();
