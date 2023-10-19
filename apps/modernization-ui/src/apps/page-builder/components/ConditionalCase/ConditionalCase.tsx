import React, { useContext, useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ConditionalCase.scss';
import { ValuesetLibrary } from '../../pages/ValuesetLibrary/ValuesetLibrary';
import { Accordion, Button, ButtonGroup, Icon, ModalToggleButton, ModalRef } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { QuestionControllerService } from '../../generated';
import { UserContext } from '../../../../providers/UserContext';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { CreateQuestion } from '../CreateQuestion/CreateQuestion';
import { QuestionLibrary } from '../../pages/QuestionLibrary/QuestionLibrary';
import { PageBuilder } from '../../pages/PageBuilder/PageBuilder';

export const ConditionalCase = () => {
    const { state } = useContext(UserContext);
    const authorization = `Bearer ${state.getToken()}`;
    const modalRef = useRef<ModalRef>(null);
    const queEditModalRef = useRef<ModalRef>(null);
    const queListModalRef = useRef<ModalRef>(null);

    const count = 1;
    const subCount = 2;
    const [activeTab, setActiveTab] = useState('Question');
    const [isEditQtn, setEditQtn] = useState(false);
    const [qtnId, setQtnId] = useState(0);
    const [questions, setQuestions] = useState([]);
    const navigateTo = useNavigate();

    useEffect(() => {
        QuestionControllerService.findAllQuestionsUsingGet({
            authorization
        }).then((resp: any) => {
            setQuestions(resp?.content);
        });
    }, []);
    const deletQtn = () => {};
    const editQtn = (id: any) => {
        setQtnId(id);
        setEditQtn(!isEditQtn);
    };
    const singleSelect = '';

    const handleAddQuestion = () => {
        navigateTo('/page-builder/manage/question-library');
    };

    const renderEditQuestionModal = (que: any) => (
        <>
            <ModalToggleButton className="submit-btn" modalRef={queEditModalRef} unstyled outline>
                <Icon.Edit style={{ cursor: 'pointer' }} className="primary-color" onClick={() => editQtn(que.id)} />
            </ModalToggleButton>
            <ModalComponent
                isLarge
                modalRef={queEditModalRef}
                modalHeading={'Edit question'}
                modalBody={<CreateQuestion modalRef={queEditModalRef} question={que} />}
            />
        </>
    );
    const renderQuestionListModal = () => (
        <>
            <Button className="add-btn" type="button" onClick={handleAddQuestion}>
                Add Questions
            </Button>
            <ModalToggleButton className="add-btn display-none" onClick={handleAddQuestion} modalRef={queListModalRef}>
                Add Questions
            </ModalToggleButton>
            <ModalComponent
                isLarge
                modalRef={queListModalRef}
                modalHeading={'Add question'}
                modalBody={<QuestionLibrary hideTabs modalRef={queListModalRef} />}
            />
        </>
    );

    const content = questions.map((que: any, index: number) => (
        <div key={index}>
            {que.id !== qtnId ? (
                <div className="que-wrap">
                    <div className="que-box">
                        <span className="strong-label">{que?.label}</span>
                        <span>
                            <ButtonGroup type="segmented">
                                <Button className="add-btn" type="button" onClick={() => {}} outline>
                                    Yes
                                </Button>
                                <Button className="add-btn" type="button" onClick={() => {}} outline>
                                    No
                                </Button>
                                <Button className="add-btn" type="button" onClick={() => {}} outline>
                                    Unknown
                                </Button>
                            </ButtonGroup>
                        </span>
                    </div>
                    <Icon.Edit className="margin-right-05" size={3} color="gray" onClick={() => editQtn(que.id)} />
                </div>
            ) : (
                <div className="que-wrap que-active ">
                    <div className="que-operator">
                        <ul className="tabs">
                            <li
                                className={activeTab == 'Question' ? 'active' : ''}
                                onClick={() => setActiveTab('Question')}>
                                Question
                            </li>
                            <li className={activeTab == 'Logic' ? 'active' : ''} onClick={() => setActiveTab('Logic')}>
                                Logic
                            </li>
                        </ul>
                        <ul className="icon-btn">
                            <li>{renderEditQuestionModal(que)}</li>
                            <li>
                                <Icon.Delete
                                    style={{ cursor: 'pointer' }}
                                    className="primary-color"
                                    onClick={() => deletQtn()}
                                />
                            </li>
                            <li>
                                <div />
                            </li>
                            <li className="label">Required</li>
                            <li>
                                <label className="switch">
                                    <span className="slider round"></span>
                                </label>
                            </li>
                        </ul>
                    </div>
                    <div className="que-content">
                        <div className="que-list">
                            {/* Where did the mother give birth? */}
                            <div className="strong-label">{que?.label}</div>
                            <div className="small-body-text">Please answer to the best of your knowledge.</div>
                        </div>
                        <div className="que-add-valueset">
                            <div className="label">
                                {singleSelect}
                                Radio boxes (single select)
                            </div>
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
            )}
        </div>
    ));

    const subItems: AccordionItemProps[] = [
        {
            title: (
                <div className="sub-title">
                    <div>
                        <h2 className="sub-heading">
                            Add Subsection <span className={'count'}>{subCount}</span>
                        </h2>
                        <p className="desc">Question group for patient</p>
                    </div>
                    <span className="header-actions">
                        {renderQuestionListModal()}
                        <Icon.MoreVert className="primary-color" size={5} />
                        <Icon.ExpandLess className="primary-color" size={5} />
                    </span>
                </div>
            ),
            content,
            expanded: true,
            id: '1',
            headingLevel: 'h4',
            className: 'accordion-item'
        }
    ];
    const Items: AccordionItemProps[] = [
        {
            title: (
                <div className="main-title">
                    <div id="header-title">
                        <h2 className="heading" data-testid="header-title-condition-case">
                            Jurisdictional Questions <span className={'count'}>{count}</span>
                        </h2>
                        <p className="desc">Please fill out all fields in this section</p>
                    </div>
                    <span className="header-actions">
                        <Button className="add-btn" type={'submit'} onClick={() => {}}>
                            Add Subsection
                        </Button>
                        <Icon.MoreVert className="primary-color" size={5} />
                        <Icon.ExpandLess className="primary-color" size={5} />
                    </span>
                </div>
            ),
            content: <Accordion bordered={false} items={subItems} />,
            expanded: true,
            id: '11',
            headingLevel: 'h4',
            className: 'accordion-item'
        }
    ];
    return (
        <PageBuilder page="">
            <div className="question-container">
                <Accordion bordered={false} items={Items} />
                <ModalComponent
                    isLarge
                    modalRef={modalRef}
                    modalHeading={'Add value set'}
                    modalBody={<ValuesetLibrary modalRef={modalRef} hideTabs types="recent" />}
                />
            </div>
        </PageBuilder>
    );
};
