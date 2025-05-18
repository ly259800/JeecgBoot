package org.jeecg.modules.rider.question.service;

import org.jeecg.modules.rider.question.entity.AiOption;
import org.jeecg.modules.rider.question.entity.AiQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 问题
 * @Author: jeecg-boot
 * @Date:   2025-05-18
 * @Version: V1.0
 */
public interface IAiQuestionService extends IService<AiQuestion> {

	/**
	 * 添加一对多
	 *
	 * @param aiQuestion
	 * @param aiOptionList
	 */
	public void saveMain(AiQuestion aiQuestion,List<AiOption> aiOptionList) ;
	
	/**
	 * 修改一对多
	 *
   * @param aiQuestion
   * @param aiOptionList
	 */
	public void updateMain(AiQuestion aiQuestion,List<AiOption> aiOptionList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
