import { PagesContext } from 'apps/page-builder/context/PagesContext';
import { useContext, useEffect, useState } from 'react';
import { SelectInput } from 'components/FormInputs/SelectInput';
import './RangeToggle.scss';

export const RangeToggle = () => {
    const { pageSize, setPageSize } = useContext(PagesContext);
    const [range, setRange] = useState(10);
    const options = [
        { name: '10', value: '10' },
        { name: '25', value: '25' },
        { name: '50', value: '50' }
    ];

    useEffect(() => {
        setPageSize(range);
    }, [range]);

    return (
        <div className="range-toggle">
            <SelectInput defaultValue={pageSize} options={options} onChange={(e) => setRange(Number(e.target.value))} />
        </div>
    );
};
