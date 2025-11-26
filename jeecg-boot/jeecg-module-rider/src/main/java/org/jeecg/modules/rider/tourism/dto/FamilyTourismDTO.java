package org.jeecg.modules.rider.tourism.dto;


import lombok.Data;
import org.jeecg.modules.rider.tourism.entity.FamilyTourism;
import org.jeecg.modules.rider.tourism.entity.FamilyTourismDetail;

@Data
public class FamilyTourismDTO extends FamilyTourism {
    private FamilyTourismDetail familyTourismDetail;
}
