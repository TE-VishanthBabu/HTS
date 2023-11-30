package com.htsevv.request;

import com.htsevv.entity.TimeSheet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddManualEntryRequest {
    private List<TimeSheet> timeSheetEntry;
}
