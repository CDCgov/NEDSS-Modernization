import { Button, Icon, Tag } from '@trussworks/react-uswds';
import React, { useContext, useEffect, useState } from 'react';
import { SelectControl } from 'components/FormInputs/SelectControl';
import { useForm } from 'react-hook-form';
import { MultiSelectInput } from 'components/selection/multi';
import { fetchConditions } from '../../../services/conditionAPI';
import { Condition } from '../../../generated';
import { UserContext } from '../../../../../providers/UserContext';
import { FilterPanel } from '../../../components/FilterModal/FilterPanel';
import { FilterWrapper } from '../../../components/FilterModal/FilterWrapper';

import {
    statusOptions,
    eventYpeOption,
    valueSetFieldList,
    arithOperator,
    dateOperator,
    initOperator
} from '../../../constant/constant';
import { ValueSetsContext } from '../../../context/ValueSetContext';

export const QuestionLibraryFilterModal = () => {
    const methods = useForm();
    const { control } = methods;
    const initial = { operator: '', selectedField: '', fieldName: '' };
    const { state } = useContext(UserContext);
    const [isDelete, setIsDelete] = useState(false);
    const [queryList, setQueryList] = useState<any>([]);
    const [isModalHidden, setIsModalHidden] = useState<any>(true);
    const [filterData, setFilterData] = useState<any>(initial);
    const [showFilter, setShowFilter] = useState<any>(false);
    const [saveFilter, setSaveFilter] = useState<any>(false);
    const [conditions, setConditions] = useState<any>([{}]);
    const [selectedConditions, setSelectedConditions] = useState<any>(['condi1']);
    const [operatorOptions, setOperatorOptions] = useState<any>(initOperator);
    const authorization = `Bearer ${state.getToken()}`;
    const { setFilter } = useContext(ValueSetsContext);

    const toggleModal = () => {
        setIsModalHidden(!isModalHidden);
        setSaveFilter(false);
        setIsDelete(false);
    };
    const applyFilter = () => {
        setFilter(queryList.join(','));
        setIsModalHidden(true);
    };

    const handleOnChange = ({ target }: any) => {
        setFilterData({ ...filterData, [target.name]: target?.type === 'checkbox' ? target?.checked : target.value });
    };
    const handleSelect = (data: any) => {
        setSelectedConditions(data);
    };

    const handleSubmit = () => {
        setQueryList([...queryList, `${selectedField} ${operator} ${selectedConditions.join(' ')}`]);
        setShowFilter(!showFilter);
    };
    useEffect(() => {
        fetchConditions(authorization).then((data: Condition[]) => {
            const list = data.map((option) => {
                return {
                    name: option.conditionShortNm!,
                    value: option.investigationFormCd!
                };
            });
            setConditions(list);
        });
    }, []);
    const handleRemove = (removeTag: string) => {
        setQueryList((preTag: string[]) => preTag.filter((tag) => tag !== removeTag));
        setFilter(queryList.join(','));
    };
    const renderAction = (
        <>
            {!isDelete && queryList.length > 0 ? (
                <Button
                    type="submit"
                    className="margin-right-2 line-btn delete-ln-btn"
                    unstyled
                    onClick={() => setIsDelete(true)}>
                    <Icon.Delete className="margin-right-2px" />
                    <span> Delete </span>
                </Button>
            ) : (
                <span />
            )}
            <div className="display-flex">
                <Button
                    type="submit"
                    className="filter-btn hide-save-btn"
                    onClick={() => setSaveFilter(!saveFilter)}
                    outline>
                    Save Filter As
                </Button>
                <Button type="submit" className="filter-btn" onClick={applyFilter}>
                    Apply
                </Button>
            </div>
        </>
    );

    const { selectedField, operator } = filterData;

    useEffect(() => {
        if (selectedField === 'lastUpdated') {
            setOperatorOptions(dateOperator);
        } else if (selectedField === 'eventType' || selectedField === 'status') {
            setOperatorOptions(arithOperator);
        } else {
            setOperatorOptions(initOperator);
        }
    }, [selectedField]);

    const getConditionOption = (field: string) => {
        switch (field) {
            case 'eventType':
                return eventYpeOption;
            case 'status':
                return statusOptions;
            default:
                return conditions;
        }
    };
    const renderPanel = (
        <FilterPanel footerAction={renderAction} header="Filter">
            <label className="sub-title">Show the Following</label>
            <div className="tag-base">
                <label>All Value Set</label>
            </div>
            {queryList.length > 0 && (
                <div>
                    <label className="filter-match-header">Matching all of these filters</label>
                    <div className="search-tag">
                        {queryList.map((query: string, index: number) => (
                            <div className="tag-cover" key={index}>
                                <Tag background="#005EA2">{query}</Tag>
                                <Icon.Close onClick={() => handleRemove(query)} />
                            </div>
                        ))}
                    </div>
                </div>
            )}
            {showFilter && (
                <div className="filter-input">
                    <SelectControl
                        control={control}
                        name="selectedField"
                        label="Selected Field"
                        onChangeMethod={handleOnChange}
                        options={valueSetFieldList}
                    />
                    <SelectControl
                        control={control}
                        name="operator"
                        label="Operator"
                        onChangeMethod={handleOnChange}
                        options={operatorOptions}
                    />
                    <MultiSelectInput
                        onChange={handleSelect}
                        name="condition"
                        placeholder=""
                        label="Type a Value (multiple allowed)"
                        options={getConditionOption(selectedField)}
                    />
                    <div className="ds-u-justify-content--end display-flex margin-top-1em">
                        <Button type="submit" className="filter-btn" onClick={() => setShowFilter(!showFilter)} outline>
                            Cancel
                        </Button>
                        <Button type="submit" className="filter-btn" onClick={handleSubmit}>
                            Done
                        </Button>
                    </div>
                </div>
            )}
            <Button type="submit" className="line-btn" unstyled onClick={() => setShowFilter(!showFilter)}>
                <Icon.Add className="margin-right-2px" />
                <span>Add Filter</span>
            </Button>
        </FilterPanel>
    );

    return (
        <FilterWrapper toggleModal={toggleModal} isModalHidden={isModalHidden} name="Filter">
            {renderPanel}
        </FilterWrapper>
    );
};
