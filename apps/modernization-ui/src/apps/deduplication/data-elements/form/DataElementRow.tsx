import React from 'react';
import NumericInput from './NumericInput';
import Checkbox from '../form/Checkbox';
import { DataElements, DataElement } from '../types/DataElement';

interface DataElementRowProps {
    field: keyof DataElements;
    data: DataElement;
    onChange: (field: keyof DataElements, key: string, value: boolean | number | undefined) => void;
}

const DataElementRow: React.FC<DataElementRowProps> = ({ field, data, onChange }) => {
    return (
        <tr>
            <td>
                <Checkbox
                    checked={data.active || false}
                    onChange={(checked: boolean) => onChange(field, 'active', checked)}
                />
                {field}
            </td>
            <td>
                <NumericInput value={data.logOdds} onChange={(value) => onChange(field, 'logOdds', value)} />
            </td>
            <td>
                <NumericInput value={data.threshold} onChange={(value) => onChange(field, 'threshold', value)} />
            </td>
        </tr>
    );
};

export default DataElementRow;
