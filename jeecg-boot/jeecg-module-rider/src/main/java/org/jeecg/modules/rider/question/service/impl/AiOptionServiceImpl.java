package org.jeecg.modules.rider.question.service.impl;

import org.jeecg.modules.rider.question.entity.AiOption;
import org.jeecg.modules.rider.question.mapper.AiOptionMapper;
import org.jeecg.modules.rider.question.service.IAiOptionService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 选项
 * @Author: jeecg-boot
 * @Date:   2025-05-18
 * @Version: V1.0
 */
@Service
public class AiOptionServiceImpl extends ServiceImpl<AiOptionMapper, AiOption> implements IAiOptionService {
	
	@Autowired
	private AiOptionMapper aiOptionMapper;
	
	@Override
	public List<AiOption> selectByMainId(String mainId) {
		return aiOptionMapper.selectByMainId(mainId);
	}
}
