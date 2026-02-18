package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import gov.cdc.nbs.questionbank.valueset.exception.ValuesetUpdateException;
import gov.cdc.nbs.questionbank.valueset.model.Valueset;
import gov.cdc.nbs.questionbank.valueset.request.UpdateValueSetRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ValueSetUpdater {
  private static final Pattern NAME_PATTERN = Pattern.compile("^\\w+$");

  private final EntityManager entityManager;
  private final ValueSetFinder finder;

  public ValueSetUpdater(final EntityManager entityManager, final ValueSetFinder finder) {
    this.entityManager = entityManager;
    this.finder = finder;
  }

  public Valueset update(String codeSetNm, UpdateValueSetRequest request) {
    if (request.name() == null || !NAME_PATTERN.matcher(request.name()).matches()) {
      throw new ValuesetUpdateException("Invalid Value set name. Only [a-zA-Z0-9_] are supported");
    }
    try {
      Codeset codeset =
          entityManager.find(Codeset.class, new CodesetId("code_value_general", codeSetNm));
      if (codeset == null) {
        throw new ValuesetUpdateException("Failed to find value set: " + codeSetNm);
      }
      codeset.update(asUpdate(request));
      return finder.find(codeSetNm);
    } catch (Exception e) {
      throw new ValuesetUpdateException("Failed to update value set");
    }
  }

  private ValueSetCommand.Update asUpdate(UpdateValueSetRequest request) {
    return new ValueSetCommand.Update(request.name(), request.description());
  }
}
