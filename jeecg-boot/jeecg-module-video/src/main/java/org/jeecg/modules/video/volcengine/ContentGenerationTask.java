package org.jeecg.modules.video.volcengine;
import com.volcengine.ark.runtime.model.content.generation.DeleteContentGenerationTaskResponse;
import com.volcengine.ark.runtime.model.content.generation.*;
import com.volcengine.ark.runtime.model.content.generation.CreateContentGenerationTaskRequest.Content;
import com.volcengine.ark.runtime.service.ArkService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ContentGenerationTask {

    // 请确保您已将 API Key 存储在环境变量 ARK_API_KEY 中
    // 初始化Ark客户端，从环境变量中读取您的API Key
    static String apiKey = System.getenv("ARK_API_KEY");
    static ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
    static Dispatcher dispatcher = new Dispatcher();
    static ArkService service = ArkService.builder()
            .dispatcher(dispatcher)
            .connectionPool(connectionPool)
            .apiKey(apiKey)
            .build();
    public static void TextGenerationVideo(String text) {
        //替换为您的 Model ID
        String model = "doubao-seaweed-241128";
        log.info("----- CREATE Task Request -----");
        List<Content> contents = new ArrayList<>();
        // 文本提示词与参数组合
        contents.add(Content.builder()
                .type("text")
                .text(text)
                .build());

        // 创建视频生成任务
        CreateContentGenerationTaskRequest createRequest = CreateContentGenerationTaskRequest.builder()
                .model(model)
                .content(contents)
                .build();
        CreateContentGenerationTaskResult createResult = service.createContentGenerationTask(createRequest);
        log.info("生成任务结果："+createResult.toString());
        System.out.println("----- GET Task Request -----");

        // 获取任务详情
        GetContentGenerationTaskRequest getRequest = GetContentGenerationTaskRequest.builder()
                .taskId(createResult.getId())
                .build();

        GetContentGenerationTaskResponse getResult = service.getContentGenerationTask(getRequest);
        System.out.println(getResult);

        System.out.println("----- LIST Task Request -----");

                // 列出符合特定条件的任务
                ListContentGenerationTasksRequest listRequest = ListContentGenerationTasksRequest.builder()
                        .pageNum(1)
                        .pageSize(10)
                        .status(TaskStatus.RUNNING)
                        .addTaskId(createResult.getId()) //  按 task_id 筛选
                        //  如果您需要按多个 task_id 进行筛选
                        // .taskIds(Arrays.asList("test-id-1", "test-id-2"))
                        .model(model)
                        .build();

        ListContentGenerationTasksResponse listResponse = service.listContentGenerationTasks(listRequest);
        System.out.println(listResponse);

        System.out.println("----- DELETE Task Request -----");

                // 通过任务 id 删除任务
                DeleteContentGenerationTaskRequest deleteRequest = DeleteContentGenerationTaskRequest.builder()
                        .taskId(getResult.getId())
                        .build();

        try {
            DeleteContentGenerationTaskResponse deleteResult = service.deleteContentGenerationTask(deleteRequest);
            System.out.println(deleteResult);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        service.shutdownExecutor();
    }


}
