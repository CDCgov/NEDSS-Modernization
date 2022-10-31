import BasePage from './BasePage';
enum Selector {
    DELETE_BUTTON = 'input[name=Delete]'
}
export default class ViewLabReport extends BasePage {
    constructor(uid: string) {
        super(`/ViewFile1.do?ContextAction=ObservationLabIDOnSummary&observationUID=${uid}`);
    }

    clickDelete(): void {
        this.clickFirst(Selector.DELETE_BUTTON);
    }
}
