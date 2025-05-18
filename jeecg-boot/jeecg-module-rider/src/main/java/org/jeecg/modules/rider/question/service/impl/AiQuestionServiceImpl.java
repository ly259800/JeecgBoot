package org.jeecg.modules.rider.question.service.impl;

import org.jeecg.modules.rider.question.entity.AiQuestion;
import org.jeecg.modules.rider.question.entity.AiOption;
import org.jeecg.modules.rider.question.mapper.AiOptionMapper;
import org.jeecg.modules.rider.question.mapper.AiQuestionMapper;
import org.jeecg.modules.rider.question.service.IAiQuestionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 问题
 * @Author: jeecg-boot
 * @Date:   2025-05-18
 * @Version: V1.0
 */
@Service
public class AiQuestionServiceImpl extends ServiceImpl<AiQuestionMapper, AiQuestion> implements IAiQuestionService {

	@Autowired
	private AiQuestionMapper aiQuestionMapper;
	@Autowired
	private AiOptionMapper aiOptionMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(AiQuestion aiQuestion, List<AiOption> aiOptionList) {
		aiQuestionMapper.insert(aiQuestion);
		if(aiOptionList!=null && aiOptionList.size()>0) {
			for(AiOption entity:aiOptionList) {
				//外键设置
				entity.setQuesId(aiQuestion.getId());
				aiOptionMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(AiQuestion aiQuestion,List<AiOption> aiOptionList) {
		aiQuestionMapper.updateById(aiQuestion);
		
		//1.先删除子表数据
		aiOptionMapper.deleteByMainId(aiQuestion.getId());
		
		//2.子表数据重新插入
		if(aiOptionList!=null && aiOptionList.size()>0) {
			for(AiOption entity:aiOptionList) {
				//外键设置
				entity.setQuesId(aiQuestion.getId());
				aiOptionMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		aiOptionMapper.deleteByMainId(id);
		aiQuestionMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			aiOptionMapper.deleteByMainId(id.toString());
			aiQuestionMapper.deleteById(id);
		}
	}
	
}
