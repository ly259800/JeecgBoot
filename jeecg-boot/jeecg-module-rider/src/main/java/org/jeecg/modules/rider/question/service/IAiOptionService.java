package org.jeecg.modules.rider.question.service;

import org.jeecg.modules.rider.question.entity.AiOption;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 选项
 * @Author: jeecg-boot
 * @Date:   2025-05-18
 * @Version: V1.0
 */
public interface IAiOptionService extends IService<AiOption> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<AiOption>
	 */
	public List<AiOption> selectByMainId(String mainId);
}
