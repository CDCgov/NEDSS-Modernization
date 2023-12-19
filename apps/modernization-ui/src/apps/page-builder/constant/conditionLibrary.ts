export enum Column {
    Condition = 'Condition',
    Code = 'Code',
    ProgramArea = 'Program area',
    ConditionFamily = 'Condition family',
    CoinfectionGroup = 'Coinfection group',
    NND = 'NND',
    InvestigationPage = 'Investigation page',
    Status = 'Status'
}

export const conditionTableColumns = [
    { name: Column.Condition, sortable: true },
    { name: Column.Code, sortable: true },
    { name: Column.ProgramArea, sortable: true },
    { name: Column.ConditionFamily, sortable: true },
    { name: Column.CoinfectionGroup, sortable: false },
    { name: Column.NND, sortable: true },
    { name: Column.InvestigationPage, sortable: true },
    { name: Column.Status, sortable: true }
];
