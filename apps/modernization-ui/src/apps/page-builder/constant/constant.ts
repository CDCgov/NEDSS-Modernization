const fieldType = [
    { label: 'Radio buttons (single select)', value: 'radio' },
    { label: 'Checkboxes (multi select)', value: 'check' },
    { label: 'Dropdown', value: 'dropdown' },
    { label: 'Single textbox', value: 'TEXT' },
    { label: 'Text area', value: 'area' },
    { label: 'Multi-select dropdown', value: 'multi-select' },
    { label: 'Date / time', value: 'date-time' }
];

const coded = [
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
        label: 'Single-select save (readonly)'
    },
    {
        value: 1025,
        label: 'Multi-select save (readonly)'
    },
    {
        value: 1027,
        label: 'Single-select no save (readonly)'
    },
    {
        value: 1028,
        label: 'Multi-select no save (readonly)'
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
        label: 'Readonly User text, number, or date no save'
    }
];

const statusOptions = [
    { label: 'Published', value: 'published' },
    {
        label: 'Draft',
        value: 'Draft'
    },
    {
        label: 'Published with draft',
        value: 'PublishedWithDraft'
    }
];

const eventYpeOption = [
    { label: 'Investigation', value: 'Investigation' },
    {
        label: 'Contact record',
        value: 'Contact record'
    },
    {
        label: 'Interview',
        value: 'Interview'
    },
    {
        label: 'Lab isolate tracking',
        value: 'Lab isolate tracking'
    },
    {
        label: 'Lab report',
        value: 'Lab report'
    },
    {
        label: 'Lab susceptibility',
        value: 'Lab susceptibility'
    },
    {
        label: 'Lab susceptibility',
        value: 'Vaccination'
    }
];

const pageFieldList = [
    {
        name: 'Page Name',
        value: 'PageName'
    },
    {
        name: 'Event Type',
        value: 'eventType'
    },
    {
        name: 'Related Condition(s)',
        value: 'relatedCondition'
    },
    {
        name: 'Status',
        value: 'status'
    },
    {
        name: 'Last updated',
        value: 'lastUpdated'
    },
    {
        name: 'Last updated by',
        value: 'lastUpdatedBy'
    }
];

const businessRuleFieldList = [
    {
        name: 'Source Fields',
        value: 'sourceFields'
    },
    {
        name: 'Logic',
        value: 'logic'
    },
    {
        name: 'Values',
        value: 'values'
    },
    {
        name: 'Function',
        value: 'function'
    },
    {
        name: 'Target Fields',
        value: 'targetFields'
    },
    {
        name: 'Id',
        value: 'id'
    }
];

const questionFieldList = [
    {
        name: 'Unique Name',
        value: 'UniqueName'
    },
    {
        name: 'Unique ID',
        value: 'UniqueId'
    },
    {
        name: 'Subgroup',
        value: 'Subgroup'
    },
    {
        name: 'Type',
        value: 'Type'
    },
    {
        name: 'Label',
        value: 'Label'
    },
    {
        name: 'Status',
        value: 'Status'
    }
];

const valueSetFieldList = [
    {
        name: 'Value set name',
        value: 'ValueSetName'
    },
    {
        name: 'Value set description',
        value: 'ValueSetDescription'
    },
    {
        name: 'Type',
        value: 'Type'
    },
    {
        name: 'Value set code',
        value: 'ValueSetCode'
    },
    {
        name: 'Status',
        value: 'Status'
    }
];

const conditionFieldList = [
    {
        name: 'Condition',
        value: 'Condition'
    },
    {
        name: 'Code',
        value: 'Code'
    },
    {
        name: 'Program area',
        value: 'ProgramArea'
    },
    {
        name: 'Condition Family',
        value: 'ConditionFamily'
    },
    {
        name: 'Coinfection group',
        value: 'CoinfectionGroup'
    },
    {
        name: 'NND',
        value: 'NND'
    },
    {
        name: 'Status',
        value: 'Status'
    }
];

const initOperator = [
    {
        name: 'Starts with',
        value: 'startsWith'
    },
    {
        name: 'Contains',
        value: 'contains'
    },
    {
        name: 'Equals',
        value: 'Equals'
    },
    {
        name: 'Not equal to',
        value: 'net'
    }
];

const arithOperator = [
    {
        name: 'Equals',
        value: 'Equals'
    },
    {
        name: 'Not equal to',
        value: 'net'
    }
];

const nonDateCompare = [
    {
        name: 'Equal to',
        value: '='
    },
    {
        name: 'No equal to',
        value: '!='
    }
];

const dateCompare = [
    {
        name: 'Less than',
        value: '<'
    },
    {
        name: 'Less or equal to',
        value: '<='
    },
    {
        name: 'Greater or equal to',
        value: '>='
    },
    {
        name: 'Greater than',
        value: '>'
    }
];

const dateOperator = [
    {
        name: 'Between',
        value: 'btw'
    },
    {
        name: 'Today',
        value: 'today'
    },
    {
        name: 'Yesterday',
        value: 'yesterday'
    },
    {
        name: 'Past 7 days ',
        value: 'past 7 days '
    },
    {
        name: 'Past 30 days ',
        value: 'past 30 days '
    }
];

export {
    coded,
    text,
    dateOrNumeric,
    fieldType,
    statusOptions,
    eventYpeOption,
    pageFieldList,
    arithOperator,
    dateOperator,
    initOperator,
    businessRuleFieldList,
    questionFieldList,
    valueSetFieldList,
    conditionFieldList,
    nonDateCompare,
    dateCompare
};
