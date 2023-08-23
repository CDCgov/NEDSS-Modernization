const fieldType = [
    { label: 'Radio buttons (single select)', value: 'radio' },
    { label: 'Checkboxes (multi select)', value: 'check' },
    { label: 'Dropdown', value: 'dropdown' },
    { label: 'Single textbox', value: 'text' },
    { label: 'Text area', value: 'area' },
    { label: 'Multi-select dropdown', value: 'multi-select' },
    { label: 'Date / time', value: 'date-time' }
];
const coded: any = [
    {
        value: 1007,
        label: 'Single-Select (Drop down)'
    },
    {
        value: 1013,
        label: 'Multi-Select (List Box)'
    },
    {
        value: 1024,
        label: 'Readonly single-select save'
    },
    {
        value: 1025,
        label: 'Readonly multi-select save'
    },
    {
        value: 1027,
        label: 'Readonly single-select no save'
    },
    {
        value: 1028,
        label: 'Readonly multi-select no save'
    },
    {
        value: 1031,
        label: 'Code Lookup'
    }
];

const dateOrNumeric = [
    {
        value: 1008,
        label: 'User entered text, number or date'
    },
    {
        value: 1026,
        label: 'Readonly User entered text, number, or date'
    },
    {
        value: 1029,
        label: 'Readonly User  text, number, or date no save'
    }
];

const text = [
    {
        value: 1008,
        label: 'User entered text, number or date'
    },
    {
        value: 1009,
        label: 'Multi-line user-entered text'
    },
    {
        value: 1019,
        label: 'Multi-line Notes with User/Date Stamp'
    },
    {
        value: 1026,
        label: 'Readonly User entered text, number, or date'
    },
    {
        value: 1029,
        label: 'Readonly User  text, number, or date no save'
    }
];

export { coded, text, dateOrNumeric, fieldType };
