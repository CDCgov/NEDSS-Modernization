import { Button, Form, Icon } from '@trussworks/react-uswds';
import {
    Concept,
    PageInformation,
    PageInformationChangeRequest,
    PageInformationService
} from 'apps/page-builder/generated';
import { useFindConditionsNotInUse } from 'apps/page-builder/hooks/api/useFindConditionsNotInUse';
import { fetchMMGOptions } from 'apps/page-builder/services/valueSetAPI';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router';
import { useAlert } from 'libs/alert';
import { LinkButton } from 'components/button';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
import { useGetPageDetails } from 'apps/page-builder/page/management/useGetPageDetails';
import './PageDetails.scss';
import { PageDetailsField } from './PageDetailsField';

export const PageDetails = () => {
    const { pageId } = useParams();
    const [pageEvent, setPageEvent] = useState('');
    const [mmgs, setMmgs] = useState<Concept[]>([]);
    const navigate = useNavigate();
    const { showError, showSuccess } = useAlert();
    const { page } = useGetPageDetails();
    const { conditions } = useFindConditionsNotInUse(Number(pageId));
    const isEnabled = ['Initial Draft', 'Published with Draft', 'Draft'].includes(page?.status ?? '');
    const pageStatus = page?.status;

    const form = useForm<PageInformationChangeRequest>({
        mode: 'onBlur',
        defaultValues: {}
    });
    useEffect(() => {
        fetchMMGOptions()
            .then((data) => {
                setMmgs(data);
            })
            .catch((error) => {
                console.log('Error', error);
            });
    }, []);
    useEffect(() => {
        if (pageId) {
            PageInformationService.find({
                page: Number(pageId)
            }).then((data: PageInformation) => {
                setPageEvent(data.eventType.value ?? '');
                form.reset({
                    ...form.getValues(),
                    datamart: data.datamart,
                    messageMappingGuide: data?.messageMappingGuide?.value,
                    description: data?.description,
                    name: data.name,
                    conditions: data.conditions.map((c) => c.value)
                });
            });
        }
    }, [pageId]);
    const handleCancel = () => {
        navigate('..');
    };
    const onSubmit = form.handleSubmit((request) => {
        PageInformationService.change({
            page: Number(pageId),
            requestBody: {
                ...request,
                conditions: request.conditions
            }
        })
            .then(() => {
                showSuccess('You have successfully performed a task');
                navigate('..');
                form.reset();
            })
            .catch((error) => {
                showError(error.body.message || 'Failed to save page');
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
                        <LinkButton
                            className="page-details-printer"
                            href={`/nbs/page-builder/api/v1/pages/${pageId}/print`}
                            aria-label="open simplified page details view for printing">
                            <Icon.Print size={3} />
                        </LinkButton>
                        <h2 className="page-title">Page Details</h2>
                        <div className="fields-info">
                            All fields with <span className="mandatory-indicator">*</span> are required
                        </div>
                        <FormProvider {...form}>
                            <PageDetailsField
                                conditions={conditions}
                                mmgs={mmgs}
                                eventType={pageEvent}
                                isEnabled={!isEnabled}
                                pageStatus={pageStatus}
                            />
                        </FormProvider>
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
