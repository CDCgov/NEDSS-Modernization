import { Input } from 'components/FormInputs/Input';
import { Button, Checkbox, Icon, Tag } from '@trussworks/react-uswds';
import { useContext, useState } from 'react';
import { SelectControl } from '../../../../components/FormInputs/SelectControl';
import { useForm } from 'react-hook-form';
import { useSubGroupAPI } from './useQuestionAPI';
import { QuestionsContext } from '../../context/QuestionsContext';

export const SearchBar = ({ onChange }: any) => {
    const methods = useForm();
    const { control } = methods;
    const initial = { questionSubGroup: '', questionType: '', newestToOldest: false };
    const subGroups = useSubGroupAPI();
    const { setFilter } = useContext(QuestionsContext);

    const [searchTags, setSearchTags] = useState<any>([]);
    const [search, setSearch] = useState<string>('');
    const [isModalHidden, setIsModalHidden] = useState<any>(true);
    const [filterData, setFilterData] = useState<any>(initial);

    const toggleModal = () => {
        setIsModalHidden(!isModalHidden);
    };
    const applyFilter = () => {
        setIsModalHidden(true);
        setFilter(filterData);
    };
    const removeFilter = () => {
        setIsModalHidden(true);
        setFilterData(initial);
        setFilter(initial);
    };
    const handleOnChange = ({ target }: any) => {
        setFilterData({ ...filterData, [target.name]: target?.type === 'checkbox' ? target?.checked : target.value });
    };
    const handleSearch = ({ target }: any) => {
        setSearch(target.value);
    };

    const handleSubmit = () => {
        setSearchTags([...searchTags, search]);
        onChange(search);
    };
    const handleRemove = (removeTag: string) => {
        setSearch('');
        onChange('');
        setSearchTags((preTag: string[]) => preTag.filter((tag) => tag !== removeTag));
    };

    return (
        <div className="question-local-library__header outer-search-box">
            <div className="search-tag">
                {searchTags.map((tagName: string, index: number) => (
                    <div className="tag-cover" key={index}>
                        <Tag background="#005EA2">{tagName}</Tag>
                        <Icon.Close onClick={() => handleRemove(tagName)} />
                    </div>
                ))}
            </div>
            <div className="search-filter">
                <Input
                    placeholder="Search pages by keyword"
                    type="text"
                    htmlFor="searchbar"
                    onChange={handleSearch}
                    id="searchbar"
                    value={search}
                />
                <Button type="submit" onClick={handleSubmit}>
                    <Icon.Search size={3} />
                </Button>
                <div className="config-panel">
                    <div className="filter-bar">
                        <Button type="submit" className="filter-btn" onClick={toggleModal} outline>
                            <Icon.FilterAlt />
                            <label className="filter-label"></label>
                            Filter
                        </Button>
                    </div>
                    {!isModalHidden && (
                        <div className="edit-filter-modal">
                            <label className="header-title"> Add Filter</label>
                            <div className="filter-input">
                                <Checkbox
                                    id="newestToOldest"
                                    checked={filterData.newestToOldest}
                                    name="newestToOldest"
                                    label={'Newest to oldest'}
                                    onChange={handleOnChange}
                                    className="check-input"
                                />
                                <SelectControl
                                    control={control}
                                    name="questionType"
                                    label="Type"
                                    onChangeMethod={handleOnChange}
                                    options={[
                                        {
                                            name: 'LOCAL',
                                            value: 'LOCAL'
                                        },
                                        {
                                            name: 'PHIN',
                                            value: 'PHIN'
                                        },
                                        {
                                            name: 'SYS',
                                            value: 'SYS'
                                        }
                                    ]}
                                />
                                <SelectControl
                                    control={control}
                                    name="questionSubGroup"
                                    label="Sub Group"
                                    onChangeMethod={handleOnChange}
                                    options={subGroups}
                                />
                            </div>
                            <div className="action-block">
                                <Button type="submit" className="filter-btn" onClick={removeFilter} outline>
                                    Cancel
                                </Button>
                                <Button type="submit" className="filter-btn" onClick={applyFilter}>
                                    Apply
                                </Button>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};
