package gov.cdc.nbs.questionbank.page.response;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageDetailResponse {
  private Long id;
  private String pageName;
  private String pageDescription;
  

}
