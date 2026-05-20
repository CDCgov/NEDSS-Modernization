import { CheckboxGroup } from 'design-system/checkbox';
import { ReportColumn } from 'generated';
import { Selectable } from 'options';

const ColumnSelector = ({ columns }: { columns: ReportColumn[] }) => {
    const options: Selectable[] = columns
        .filter(({ isDisplayable }) => isDisplayable)
        .map((c) => ({ value: c.id.toString(), name: c.title }));
        
        console.log({options, columns})
    return (
        <div>
            <CheckboxGroup options={options} name="Columns" label="Columns"></CheckboxGroup>
        </div>
    );
};

export { ColumnSelector };
