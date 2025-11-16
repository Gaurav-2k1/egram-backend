package in.gram.gov.app.egram_service.dto.filters;

import in.gram.gov.app.egram_service.constants.enums.PanchayatStatus;
import in.gram.gov.app.egram_service.utility.PaginationParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class PanchayatFilter extends PaginationParams {
  private PanchayatStatus status;
  private  String district;
  private String state;
}