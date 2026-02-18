package gov.cdc.nbs.questionbank.page.information.change;

import gov.cdc.nbs.questionbank.RequestContext;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.DatamartNameVerifier;
import gov.cdc.nbs.questionbank.page.PageCommand;
import gov.cdc.nbs.questionbank.page.PageNameVerifier;
import gov.cdc.nbs.questionbank.page.PageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
class PageInformationChanger {

  private final PageService service;
  private final PageNameVerifier nameVerifier;
  private final DatamartNameVerifier datamartVerifier;

  PageInformationChanger(
      final PageService service,
      final PageNameVerifier nameVerifier,
      final DatamartNameVerifier datamartVerifier) {
    this.service = service;
    this.nameVerifier = nameVerifier;
    this.datamartVerifier = datamartVerifier;
  }

  void change(
      final RequestContext context, final long page, final PageInformationChangeRequest request) {
    this.service.using(page, existing -> applyChanges(existing, context, request));
  }

  private void applyChanges(
      final WaTemplate existing,
      final RequestContext context,
      final PageInformationChangeRequest request) {

    String mmg =
        request.messageMappingGuide() == null
            ? existing.getNndEntityIdentifier()
            : request.messageMappingGuide();

    existing.update(
        new PageCommand.UpdateInformation(
            mmg, request.description(), context.requestedBy(), context.requestedAt()));

    maybeUpdateName(existing, context, request);
    maybeUpdateDatamart(existing, context, request);
    maybeUpdateConditions(existing, context, request);
  }

  private void maybeUpdateName(
      final WaTemplate existing,
      final RequestContext context,
      final PageInformationChangeRequest request) {
    if (request.name() != null && !Objects.equals(existing.getTemplateNm(), request.name())) {
      existing.changeName(
          nameVerifier,
          new PageCommand.ChangeName(request.name(), context.requestedBy(), context.requestedAt()));
    }
  }

  private void maybeUpdateDatamart(
      final WaTemplate existing,
      final RequestContext context,
      final PageInformationChangeRequest request) {
    if (!Objects.equals(existing.getDatamartNm(), request.datamart())) {
      existing.changeDatamart(
          datamartVerifier,
          new PageCommand.ChangeDatamart(
              request.datamart(), context.requestedBy(), context.requestedAt()));
    }
  }

  private void maybeUpdateConditions(
      final WaTemplate page,
      final RequestContext context,
      final PageInformationChangeRequest request) {

    List<String> existing =
        page.getConditionMappings().stream().map(PageCondMapping::getConditionCd).toList();

    List<String> added = new ArrayList<>(request.conditions());
    added.removeAll(existing);

    added.stream()
        .map(
            condition ->
                new PageCommand.RelateCondition(
                    condition, context.requestedBy(), context.requestedAt()))
        .forEach(page::relate);

    List<String> removed = new ArrayList<>(existing);
    removed.removeAll(request.conditions());

    removed.stream()
        .map(
            condition ->
                new PageCommand.DissociateCondition(
                    condition, context.requestedBy(), context.requestedAt()))
        .forEach(page::dissociate);
  }
}
