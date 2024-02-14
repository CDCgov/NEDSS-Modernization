import {
    Concept,
    Condition,
    PageInformation,
    PageInformationChangeRequest,
    PageInformationService,
    SelectableCondition
} from 'apps/page-builder/generated';
import { fetchConditions } from 'apps/page-builder/services/conditionAPI';
import { fetchMMGOptions } from 'apps/page-builder/services/valueSetAPI';
import React, { useEffect, useState } from 'react';
import { Button, Form, Icon } from '@trussworks/react-uswds';
import './PageDetails.scss';
import { PageDetailsField } from './PageDetailsField';
import { useNavigate, useParams } from 'react-router-dom';
import { authorization } from 'authorization';
import { PagesBreadcrumb } from '../../../../components/PagesBreadcrumb/PagesBreadcrumb';
import { useForm } from 'react-hook-form';
import { useAlert } from '../../../../../../alert';
import { useGetPageDetails } from '../../useGetPageDetails';

export const PageDetails = () => {
    const token = authorization();
    const { pageId } = useParams();
    const [pageEvent, setPageEvent] = useState('');
    const [conditions, setConditions] = useState<Condition[]>([]);
    const [mmgs, setMmgs] = useState<Concept[]>([]);
    const navigate = useNavigate();
    const { alertError } = useAlert();
    const { page } = useGetPageDetails();
    const isEnabled = ['Initial draft', 'Published with draft', 'Draft'].includes(page?.status!);

    const form = useForm<PageInformationChangeRequest>({
        mode: 'onBlur',
        defaultValues: {}
    });
    const { control } = form;
    useEffect(() => {
        fetchMMGOptions(token)
            .then((data) => {
                setMmgs(data);
            })
            .catch((error) => {
                console.log('Error', error);
            });
        fetchConditions(token).then((data) => {
            setConditions(data);
        });
    }, []);
    useEffect(() => {
        if (pageId) {
            const getConditions = (data?: SelectableCondition[]) => {
                return data?.map((dt: SelectableCondition) => dt.value);
            };
            PageInformationService.find({
                authorization: token,
                page: Number(pageId)
            }).then((data: PageInformation) => {
                setPageEvent(data?.eventType?.value ?? '');
                const condition = getConditions(data.conditions);
                form.reset({
                    ...form.getValues(),
                    datamart: data.datamart,
                    messageMappingGuide: data?.messageMappingGuide?.value,
                    description: data?.description,
                    name: data.name,
                    conditions: condition
                });
            });
        }
    }, [pageId]);
    const handleCancel = () => {
        navigate('..');
    };
    const onSubmit = form.handleSubmit((request) => {
        PageInformationService.change({
            authorization: token,
            page: Number(pageId),
            request
        })
            .then(() => {
                form.reset();
                navigate('..');
            })
            .catch(() => {
                alertError({ message: 'Failed to save page' });
            });
    });

    return (
        <div className="page-details">
            <div className="breadcrumb-wrap">
                <PagesBreadcrumb currentPage={page?.name} />
            </div>
            <Form onSubmit={onSubmit}>
                <div className="page-details__form">
                    <div className="page-details__content">
                        <Button type="button" className="page-details-printer" name="Button" outline>
                            <Icon.Print size={3} />
                        </Button>
                        <h2 className="page-title">Page Details</h2>
                        <>
                            <div className="fields-info">
                                All fields with <span className="mandatory-indicator">*</span> are required
                            </div>
                            <PageDetailsField
                                conditions={conditions}
                                control={control}
                                mmgs={mmgs}
                                eventType={pageEvent}
                                isEnabled={!isEnabled}
                            />
                        </>
                    </div>
                </div>
                <div className="page-details__buttons">
                    <Button type="button" outline onClick={handleCancel}>
                        {isEnabled ? 'Cancel' : 'Close'}
                    </Button>
                    {isEnabled && (
                        <Button type="submit" className="createPage" disabled={!form.formState.isValid}>
                            Save
                        </Button>
                    )}
                </div>
            </Form>
        </div>
    );
};
