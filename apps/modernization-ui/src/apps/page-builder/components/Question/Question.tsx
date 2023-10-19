import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import './Question.scss';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { PagesQuestion } from 'apps/page-builder/generated';
import { Button, ButtonGroup, Grid, Icon, ModalRef, ModalToggleButton, Radio } from '@trussworks/react-uswds';
import React, { useRef, useState } from 'react';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { CreateQuestion } from '../CreateQuestion/CreateQuestion';
import { useNavigate } from 'react-router-dom';
import ReactSelect, { components } from 'react-select';
import { fieldType, logicList } from '../../constant/constant';
import { SelectControl } from '../../../../components/FormInputs/SelectControl';
import { useForm } from 'react-hook-form';
import { MultiSelectInput } from '../../../../components/selection/multi';

export const Question = ({ question }: { question: PagesQuestion }) => {
    const [qtnId, setQtnId] = useState(0);
    const editQtn = (id: any) => {
        setQtnId(id);
    };
    return (
        <>
            {question.id !== qtnId ? (
                <div className="question">
                    <div className="question__name">
                        {question.name}
                        <span>{question.question}</span>
                    </div>
                    <div className="question__field">
                        {question.dataType === 'CODED' ? (
                            <SelectInput options={[]} />
                        ) : question.dataType === 'DATE' ? (
                            <DatePickerInput />
                        ) : (
                            // Readonly to skirt errors for now
                            <Input type="text" readOnly />
                        )}
                    </div>
                    <Icon.Edit
                        className="margin-right-05 edit-qtn-icon"
                        size={3}
                        color="gray"
                        onClick={() => editQtn(question.id)}
                    />
                </div>
            ) : (
                <QuestionWrap que={question} />
            )}
        </>
    );
};

const initLogic = {
    function: '',
    logic: '',
    sourcevalue: '',
    targetType: 'Question',
    targetQuestions: ''
};
export const QuestionWrap = ({ que }: any) => {
    const [activeTab, setActiveTab] = useState('Question');
    const navigateTo = useNavigate();
    const methods = useForm();
    const { control } = methods;
    const [selectedFieldType, setSelectedFieldType] = useState('Enable');
    const [logicDetails, setLogicDetails] = useState(initLogic);
    const [isLogicForm, setIsLogicForm] = useState(false);
    const queEditModalRef = useRef<ModalRef>(null);

    const handleLogicDetails = ({ target }: React.ChangeEvent<HTMLInputElement>) => {
        setLogicDetails({ ...logicDetails, [target.name]: target.value });
    };
    const renderEditQuestionModal = (que: any) => (
        <>
            <ModalToggleButton className="submit-btn" modalRef={queEditModalRef} unstyled outline>
                <Icon.Edit style={{ cursor: 'pointer' }} className="primary-color" onClick={() => {}} />
            </ModalToggleButton>
            <ModalComponent
                isLarge
                modalRef={queEditModalRef}
                modalHeading={'Edit question'}
                modalBody={<CreateQuestion modalRef={queEditModalRef} question={que} />}
            />
        </>
    );
    const renderIconFieldType = (type: string): string => {
        switch (type) {
            case 'radio':
                return '/icons/single-select.svg';
            case 'check':
                return '/icons/multi-select.svg';
            case 'dropdown':
                return '/icons/expand.svg';
            case 'TEXT':
                return '/icons/textbox.svg';
            case 'area':
                return '/icons/textarea.svg';
            case 'multi-select':
                return '/icons/multi-drop.svg';
            case 'date-time':
                return '/icons/calender.svg';
            default:
                return '/icons/single-select.svg';
        }
    };

    const customStyles = {
        control: (base: any, state: any) => ({
            ...base,
            background: 'none',
            borderRadius: 0,
            borderColor: '#565c65',
            boxShadow: state.isFocused ? null : null,
            minWidth: 250,
            '&:hover': {
                borderColor: '#565c65'
            },
            outline: state.isFocused ? '0.25rem solid #2491ff' : 'none'
        }),
        container: (base: any) => {
            return {
                ...base
            };
        },
        indicatorSeparator: (base: any) => {
            return {
                ...base,
                backgroundColor: 'unset'
            };
        },
        path: (base: any) => ({
            ...base,
            display: 'none !important'
        })
    };
    const setFieldType = (type: string) => fieldType.find((data) => data.value === type);
    const formatOptionLabel = ({ value, label }: any) => (
        <div key={value} style={{ display: 'flex', alignItems: 'center', lineHeight: '12px' }}>
            <div style={{ marginRight: '16px' }}>
                <img src={renderIconFieldType(value)} />
            </div>
            <div>{label}</div>
        </div>
    );
    const USWDSDropdownIndicator = (props: any) => (
        <components.DropdownIndicator {...props}>
            <div className="multi-select select-indicator margin-top-neg-1" />
        </components.DropdownIndicator>
    );

    const handleAddLogic = () => {
        setIsLogicForm(!isLogicForm);
    };

    const fieldTypeTab = [
        { name: 'Enable' },
        { name: 'Disable' },
        { name: 'Hide' },
        { name: 'Unhide' },
        { name: 'Require If' }
    ];
    return (
        <div className="question-wrap que-active ">
            <div className="que-operator">
                <ul className="tabs">
                    <li
                        key="qtn-3-bent"
                        className={activeTab == 'Question' ? 'active' : ''}
                        onClick={() => setActiveTab('Question')}>
                        Question
                    </li>
                </ul>
                <ul className="icon-btn">
                    <li key="create-qtn-1">{renderEditQuestionModal(que)}</li>
                    <li key="delete-qtn-2">
                        <Icon.Delete style={{ cursor: 'pointer' }} className="primary-color" onClick={() => {}} />
                    </li>
                    <li key="diveder-qtn-3">
                        <div />
                    </li>
                    <li key="label-qtn-4" className="label">
                        Required
                    </li>
                    <li key="check-qtn-5">
                        <label className="switch">
                            <input type="checkbox" checked readOnly />
                            <span className="slider round"></span>
                        </label>
                    </li>
                </ul>
            </div>
            <div className="que-content">
                <div className="que-list">
                    <div className="question-format">
                        <div>
                            <div className="label">Question Label</div>
                            <div className="que-valueset">
                                <div className="strong-label">{que?.name}</div>
                            </div>
                        </div>
                        <div>
                            <div className="label">Question Type</div>
                            <ReactSelect
                                className="field-space"
                                options={fieldType}
                                name="programArea"
                                defaultValue={setFieldType('Dropdown')}
                                placeholder="- Select -"
                                formatOptionLabel={formatOptionLabel}
                                styles={{
                                    ...customStyles
                                }}
                                isSearchable={false}
                                components={{ DropdownIndicator: USWDSDropdownIndicator }}></ReactSelect>
                        </div>
                    </div>
                    {isLogicForm && (
                        <div className="logic-form-container">
                            <h4>Logic</h4>
                            <Grid row className="inline-field">
                                <Grid col={3}>
                                    <label className="input-label">Function</label>
                                </Grid>
                                <Grid col={8}>
                                    <ButtonGroup type="segmented">
                                        {fieldTypeTab.map((field, index) => (
                                            <Button
                                                key={index}
                                                type="button"
                                                outline={field.name !== selectedFieldType}
                                                onClick={() => setSelectedFieldType(field.name)}>
                                                {field.name}
                                            </Button>
                                        ))}
                                    </ButtonGroup>
                                </Grid>
                            </Grid>
                            <Grid row className="inline-field">
                                <Grid col={3}>
                                    <label className="input-label">Logic</label>
                                </Grid>
                                <Grid col={6}>
                                    <SelectControl
                                        control={control}
                                        name="logic"
                                        onChangeMethod={() => {}}
                                        options={logicList}
                                    />
                                </Grid>
                            </Grid>
                            <Grid row className="inline-field">
                                <Grid col={3}>
                                    <label className="input-label">Source value</label>
                                </Grid>
                                <Grid col={6}>
                                    <div className="width-48-p">
                                        <MultiSelectInput
                                            onChange={handleLogicDetails}
                                            name="sourceValue"
                                            options={logicList}
                                        />
                                    </div>
                                </Grid>
                            </Grid>
                            <Grid row className="inline-field">
                                <Grid col={3}>
                                    <label className="input-label">Target type</label>
                                </Grid>
                                <Grid col={8}>
                                    <Grid col={6} className="targetType-radio">
                                        <Radio
                                            type="radio"
                                            name="targetType"
                                            value="Question"
                                            id="targetType"
                                            checked={logicDetails.targetType === 'Question'}
                                            onChange={handleLogicDetails}
                                            label="Question"
                                        />
                                        <Radio
                                            id="targetType"
                                            name="targetType"
                                            value="subsection"
                                            checked={logicDetails.targetType === 'subsection'}
                                            onChange={handleLogicDetails}
                                            label="Subsection"
                                        />
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid row className="inline-field">
                                <Grid col={3}>
                                    <label className="input-label">Target Questions</label>
                                </Grid>
                                <Grid col={8}>
                                    <div className="width-48-p">
                                        <Button className="width-full margin-top-1em" type="submit" outline>
                                            Search Target Question
                                        </Button>
                                    </div>
                                </Grid>
                            </Grid>
                        </div>
                    )}{' '}
                    <Grid row className="inline-field margin-top-1em">
                        {!isLogicForm ? (
                            <Grid col={12} className="add-logic-footer">
                                <Button type="submit" className="line-btn" unstyled onClick={handleAddLogic}>
                                    <Icon.Add className="margin-right-2px" />
                                    <span> Add Logic</span>
                                </Button>
                            </Grid>
                        ) : (
                            <Grid col={12} className=" ds-u-text-align--right footer-line-btn-block margin-bottom-1em">
                                <Button
                                    type="submit"
                                    className="margin-right-2 line-btn"
                                    unstyled
                                    onClick={handleAddLogic}>
                                    <span> Cancel</span>
                                </Button>
                                <Button type="submit" onClick={() => {}}>
                                    <span> Apply Logic</span>
                                </Button>
                            </Grid>
                        )}
                    </Grid>
                    <Button
                        className="add-btn"
                        type="button"
                        onClick={() => {
                            localStorage.setItem('selectedQuestion', que.id);
                            navigateTo('/page-builder/manage/valueset-library');
                        }}>
                        Add value set
                    </Button>
                </div>
            </div>
        </div>
    );
};
