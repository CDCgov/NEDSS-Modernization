import { Control, FieldValues } from 'react-hook-form';
import { EventType, PregnancyStatus } from '../../generated/graphql/schema';
import { SelectControl } from '../FormInputs/SelectControl';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    searchType?: string;
};

export const GeneralSearch = ({ control, searchType = '' }: GeneralSearchProps) => {
    return (
        <>
            {searchType === EventType.Investigation && (
                <SelectControl
                    control={control}
                    name="condition"
                    label="Condition:"
                    onChangeMethod={(e) => console.log(e)}
                    options={[
                        {
                            name: 'ALL',
                            value: 'all'
                        }
                    ]}
                />
            )}
            <SelectControl
                control={control}
                name="programArea"
                label="Program Area:"
                onChangeMethod={(e) => console.log(e)}
                options={[
                    {
                        name: 'ALL',
                        value: 'all'
                    }
                ]}
            />
            <SelectControl
                control={control}
                name="jurisdiction"
                label="Jurisdiction:"
                onChangeMethod={(e) => console.log(e)}
                options={[
                    {
                        name: 'ALL',
                        value: 'all'
                    }
                ]}
            />
            <SelectControl
                control={control}
                name="pregnancyTest"
                label="Pregnancy Test:"
                onChangeMethod={(e) => console.log(e)}
                options={[
                    { name: PregnancyStatus.Yes, value: PregnancyStatus.Yes },
                    { name: PregnancyStatus.No, value: PregnancyStatus.No },
                    { name: PregnancyStatus.Unknown, value: PregnancyStatus.Unknown }
                ]}
            />
        </>
    );
};
