class HomePage {
    chartDropdown = 'input[name="charts_textbox"]';
    visualizationTitle = 'h2';
  
    selectChart(chartName) {
      cy.get(this.chartDropdown).type(chartName + '{enter}');
    }
  
    verifyVisualizationIsDisplayed(chartTitle) {
      cy.get(this.visualizationTitle).contains(chartTitle).should('be.visible');
    }
  }
  
  export const homePage = new HomePage();
  