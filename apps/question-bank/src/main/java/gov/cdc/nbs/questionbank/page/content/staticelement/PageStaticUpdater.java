package gov.cdc.nbs.questionbank.page.content.staticelement;

import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.UpdateStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.response.UpdateStaticResponse;

@Component
public class PageStaticUpdater {

    private final EntityManager entityManager;

    public PageStaticUpdater(
            final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UpdateStaticResponse updateHyperlink(Long pageId, Long componentId,
            StaticContentRequests.UpdateHyperlink request, Long user) {

        WaUiMetadata staticElement = entityManager.find(WaUiMetadata.class, componentId);

        if (staticElement == null) {
            throw new UpdateStaticElementException("could not find component with id " + componentId);
        }

        staticElement.update(asUpdateHyperlink(user, request.adminComments(), request.label(), request.linkUrl()));
        entityManager.flush();

        return new UpdateStaticResponse(staticElement.getId());
    }

    private PageContentCommand.UpdateHyperlink asUpdateHyperlink(
            long userId,
            String adminComments,
            String label,
            String linkUrl) {
        return new PageContentCommand.UpdateHyperlink(userId, adminComments, label, linkUrl, Instant.now());
    }

    public UpdateStaticResponse updateDefaultStaticElement(Long pageId, Long componentId,
            StaticContentRequests.UpdateDefault request, Long user) {

        WaUiMetadata staticElement = entityManager.find(WaUiMetadata.class, componentId);

        if (staticElement == null) {
            throw new UpdateStaticElementException("could not find component with id " + componentId);
        }

        staticElement.update(asUpdateDefaultStaticElement(user, request.adminComments()));
        entityManager.flush();

        return new UpdateStaticResponse(staticElement.getId());
    }

    private PageContentCommand.UpdateDefaultStaticElement asUpdateDefaultStaticElement(
            long userId,
            String adminComments) {
        return new PageContentCommand.UpdateDefaultStaticElement(userId, adminComments, Instant.now());
    }

    public UpdateStaticResponse updateReadOnlyComments(Long pageId, Long componentId,
            StaticContentRequests.UpdateReadOnlyComments request, Long user) {
        
        WaUiMetadata staticelement = entityManager.find(WaUiMetadata.class, componentId);

        if(staticelement == null) {
            throw new UpdateStaticElementException("could not find component with id " + componentId);
        }

        staticelement.update(asUpdateReadOnlyComments(user, request.commentsText(), request.adminComments()));

        return new UpdateStaticResponse(componentId);
    }

    private PageContentCommand.UpdateReadOnlyComments asUpdateReadOnlyComments(Long userId, String adminComments, String comments) {
        return new PageContentCommand.UpdateReadOnlyComments(userId, comments, adminComments, Instant.now());
    }
}
