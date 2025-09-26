package org.jeecg.modules.system.vo;

import lombok.Data;
import org.jeecg.modules.system.entity.SysCategory;
import java.util.List;

/**
 * @Description: 分类字典
 */
@Data
public class SysCategoryVo extends SysCategory{
    private static final long serialVersionUID = 1L;

	private List<SysCategory> children;

}
