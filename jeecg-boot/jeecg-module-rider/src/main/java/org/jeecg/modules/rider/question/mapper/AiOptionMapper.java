package org.jeecg.modules.rider.question.mapper;

import java.util.List;
import org.jeecg.modules.rider.question.entity.AiOption;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 选项
 * @Author: jeecg-boot
 * @Date:   2025-05-18
 * @Version: V1.0
 */
public interface AiOptionMapper extends BaseMapper<AiOption> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<AiOption>
   */
	public List<AiOption> selectByMainId(@Param("mainId") String mainId);
}
