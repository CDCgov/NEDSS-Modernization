package gov.cdc.nbs.questionbank.question;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/questions/")
@AllArgsConstructor
public class DeleteQuestionController {


    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public void deleteQuestion(@PathVariable Long id) {

    }

}
