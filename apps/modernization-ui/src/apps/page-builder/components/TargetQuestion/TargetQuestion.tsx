import React, { useEffect } from 'react';
import {
    ButtonGroup,
    Checkbox,
    ModalFooter,
    ModalRef,
    ModalToggleButton,
    Tag,
    Icon as UswIcon
} from '@trussworks/react-uswds';
import { PagesResponse } from 'apps/page-builder/generated';
import { RefObject, useContext, useState } from 'react';
import { UserContext } from 'user';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import './TargetQuestion.scss';
import { Icon } from '../../../../components/Icon/Icon';
import { fetchPageDetails } from '../../services/pagesAPI';

type CommonProps = {
    modalRef: RefObject<ModalRef>;
    pageId: string;
    getList?: (data: any) => void;
    multiSelected?: boolean;
    header?: string;
};
type QuestionProps = {
    id: number;
    question: string;
    name: string;
    selected: boolean;
    valueSet: string;
};
const TargetQuestion = ({ modalRef, pageId, getList, header, multiSelected = true }: CommonProps) => {
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [activeTab, setActiveTab] = useState(0);
    const [sourceList, setSourceList] = useState<QuestionProps[]>([]);
    const [subsectionOpen, setSubsectionOpen] = useState(false);
    const [sourceId, setSource] = useState(-1);
    const [page, setPage] = useState<PagesResponse>();
    // const [queList, setQuestions] = useState([]);

    useEffect(() => {
        if (pageId) {
            fetchPageDetails(token, Number(pageId)).then((data) => {
                setPage(data);
            });
        }
    }, [pageId]);

    const visible = true;
    const selectedRecord = sourceList.filter((list) => list.selected);
    const isSelectedAll = selectedRecord?.length === sourceList.length;
    const handleSelectAll = (e: any) => {
        setSourceList((prevState: any) => prevState.map((list: any) => ({ ...list, selected: e.target.checked })));
    };
    const handleRemove = (val: any) => {
        const updateList = [...sourceList];
        const index = sourceList.findIndex((list) => list?.name === val);
        updateList[index] = { ...updateList[index], selected: false };
        setSourceList(updateList);
    };

    const handleSelect = (e: any, key: number) => {
        let updateList = [...sourceList];

        if (!multiSelected) {
            updateList = updateList.map((qtn: QuestionProps) => ({
                name: qtn.name,
                id: qtn.id,
                question: qtn.question,
                valueSet: qtn.valueSet,
                selected: false
            }));
        }
        updateList[key] = { ...updateList[key], selected: e.target.checked };

        setSourceList(updateList);
    };
    const handleSourceList = (question: QuestionProps[]) => {
        const newList = question.map((qtn: QuestionProps) => ({
            name: qtn.name,
            id: qtn.id,
            question: qtn.question,
            valueSet: qtn.valueSet,
            selected: false
        }));
        setSourceList(newList);
    };

    return (
        <ModalComponent
            modalRef={modalRef}
            isLarge
            modalHeading={header || 'Target Questions'}
            modalBody={
                <>
                    <div style={{ padding: '0 24px' }}>
                        <h5>{`{Please select ${header ? header.toLowerCase() : 'targeted questions'}.}`}</h5>
                        <div className="target-question-tabs">
                            <ul className="tabs">
                                {page?.tabs?.map(({ name }, key) => (
                                    <li
                                        key={key}
                                        className={activeTab === key ? 'active' : ''}
                                        onClick={() => setActiveTab(key)}>
                                        {name}
                                    </li>
                                ))}
                            </ul>
                        </div>
                        <div className="selected-question list">
                            <div className="title">Selected Question</div>
                            <>
                                <div className="search-tag">
                                    {sourceList
                                        .filter((list) => list.selected)
                                        .map((list: any, index: number) => (
                                            <div className="tag-cover" key={index}>
                                                <Tag background="#F0F7FD">{list.name}</Tag>
                                                <UswIcon.Close onClick={() => handleRemove(list.name)} />
                                            </div>
                                        ))}
                                </div>
                            </>
                        </div>
                        <div className="question-list-container">
                            <div className="tree-section">
                                {page?.tabs?.[activeTab] &&
                                    page?.tabs?.[activeTab]?.sections?.map((section: any, key) => (
                                        <div key={key} className={`reorder-section ${visible ? '' : 'hidden'}`}>
                                            <div className="reorder-section__tile">
                                                <div
                                                    className={`reorder-section__toggle ${
                                                        subsectionOpen == section.id ? 'open' : ''
                                                    } `}
                                                    onClick={() => setSubsectionOpen(section.id)}>
                                                    <Icon name={'group'} size={'m'} />
                                                    <span>{section.name}</span>
                                                </div>
                                            </div>
                                            <div
                                                className={`reorder-section__subsections ${
                                                    subsectionOpen === section.id ? '' : 'closed'
                                                }`}>
                                                {section.subSections.map((sub: any, id: number) => (
                                                    <div
                                                        key={id}
                                                        className="reorder-section__tiles"
                                                        onClick={() => {
                                                            setSource(id);
                                                            // setQuestions(sub.questions);
                                                            handleSourceList(sub.questions);
                                                        }}>
                                                        <div
                                                            className={`reorder-question__tile ${
                                                                id === sourceId && 'selected'
                                                            }`}>
                                                            <Icon name="group" size={'m'} />
                                                            <span>{sub.name}</span>
                                                        </div>
                                                    </div>
                                                ))}
                                            </div>
                                        </div>
                                    ))}
                            </div>
                            <div className="list-section">
                                {multiSelected && (
                                    <Checkbox
                                        onChange={handleSelectAll}
                                        id="hots1"
                                        checked={isSelectedAll}
                                        name={'race1'}
                                        label="Select All"
                                    />
                                )}
                                <br />
                                {sourceList.map((list: any, index) => {
                                    return (
                                        <Checkbox
                                            onChange={(e) => handleSelect(e, index)}
                                            key={index}
                                            id={`sourceId${index}`}
                                            checked={list.selected}
                                            name={`sourceName ${index}`}
                                            label={list?.name!}
                                        />
                                    );
                                })}
                            </div>
                        </div>
                    </div>
                    <ModalFooter className="padding-2 margin-left-auto footer">
                        <ButtonGroup className="flex-justify-end">
                            <ModalToggleButton modalRef={modalRef} closer outline data-testid="condition-cancel-btn">
                                Cancel
                            </ModalToggleButton>
                            <ModalToggleButton
                                modalRef={modalRef}
                                closer
                                data-testid="section-add-btn"
                                onClick={() => {
                                    getList !== undefined && getList(selectedRecord);
                                }}>
                                continue
                            </ModalToggleButton>
                        </ButtonGroup>
                    </ModalFooter>
                </>
            }></ModalComponent>
    );
};

export default TargetQuestion;
