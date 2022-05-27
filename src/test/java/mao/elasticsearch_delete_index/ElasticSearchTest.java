package mao.elasticsearch_delete_index;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * Project name(项目名称)：elasticsearch_delete_Index
 * Package(包名): mao.elasticsearch_delete_index
 * Class(类名): ElasticSearchTest
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/5/27
 * Time(创建时间)： 11:44
 * Version(版本): 1.0
 * Description(描述)： SpringBootTest
 */

@SpringBootTest
public class ElasticSearchTest
{

    private static RestHighLevelClient client;


    @BeforeAll
    static void beforeAll()
    {
        client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")));
    }

    /**
     * 删除索引
     *
     * @throws IOException IOException
     */
    @Test
    void delete() throws IOException
    {
        //构建请求
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("my_index");
        //获得操作索引的客户端
        IndicesClient indices = client.indices();
        //发起请求
        AcknowledgedResponse acknowledgedResponse = indices.delete(deleteIndexRequest, RequestOptions.DEFAULT);
        //获得结果
        boolean acknowledged = acknowledgedResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

    /**
     * 删除索引
     * 异步请求
     */
    @Test
    void delete_async()
    {
        //构建请求
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("my_index");
        //获得操作索引的客户端
        IndicesClient indices = client.indices();
        //发起异步请求
        indices.deleteAsync(deleteIndexRequest, RequestOptions.DEFAULT, new ActionListener<AcknowledgedResponse>()
        {
            /**
             * 成功的回调
             * @param acknowledgedResponse AcknowledgedResponse
             */
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse)
            {
                //获得结果
                boolean acknowledged = acknowledgedResponse.isAcknowledged();
                System.out.println(acknowledged);
            }

            /**
             * 失败的回调
             * @param e Exception
             */
            @Override
            public void onFailure(Exception e)
            {
                System.out.println(e.getMessage());
            }
        });

        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void afterAll() throws IOException
    {
        client.close();
    }


}
