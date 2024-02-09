import './RequiredFieldMarker.scss';

interface Props {
    functionName: string;
    fieldName: string;
}

type RequiredFields = {
    [key: string]: string[];
};

const RequiredFieldMarker = ({ functionName, fieldName }: Props) => {
    console.log('functionName', functionName);
    const requiredFields: RequiredFields = {
        default: ['ruleFunction'],
        Enable: ['ruleFunction', 'sourceText', 'sourceValues', 'comparitor', 'targetType', 'targetQuestions'],
        Disable: ['ruleFunction', 'sourceText', 'sourceValues', 'comparitor', 'targetType', 'targetQuestions'],
        'Data validation': ['ruleFunction', 'sourceText', 'comparitor', 'targetQuestions'],
        Hide: ['ruleFunction', 'sourceText', 'sourceValues', 'comparitor', 'targetType', 'targetQuestions'],
        Unhide: ['ruleFunction', 'sourceText', 'sourceValues', 'comparitor', 'targetType', 'targetQuestions'],
        'Require If': ['ruleFunction', 'sourceText', 'sourceValues', 'comparitor', 'targetQuestions']
    };

    if (requiredFields[functionName].includes(fieldName)) {
        return <span className="required"></span>;
    } else {
        return null;
    }
};

export default RequiredFieldMarker;
